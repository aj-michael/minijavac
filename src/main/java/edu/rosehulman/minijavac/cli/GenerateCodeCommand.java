package edu.rosehulman.minijavac.cli;

import com.google.common.io.Files;
import edu.rosehulman.minijavac.ast.Program;
import edu.rosehulman.minijavac.generated.Lexer;
import edu.rosehulman.minijavac.generated.Parser;
import edu.rosehulman.minijavac.generator.CodeGenerator;
import edu.rosehulman.minijavac.typechecker.TypeChecker;
import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Command(name = "generate", description = "Generate bytecode for Minijava programs")
public class GenerateCodeCommand implements Runnable {
    @Arguments(description = "File to compile", required = true)
    public String filename;

    @Option(name = {"-l", "--load"}, description = "Native libraries to load")
    public List<String> libraries = new ArrayList<>();

    @Override
    public void run() {
        try {
            Lexer lexer = new Lexer(new FileReader(filename));
            Parser parser = new Parser(lexer);
            Program program = parser.parseProgram();
            program.typecheck();
            TypeChecker typechecker = new TypeChecker();
            if (!typechecker.isValid(program)) {
                typechecker.getTypeCheckerLog().forEach(System.out::println);
            } else {
                Map<String, byte[]> classContents = new CodeGenerator(program, libraries).generate();
                for (String className : classContents.keySet()) {
                    Files.write(classContents.get(className), new File(className + ".class"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}