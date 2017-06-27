package com.minijava;

import java.io.FileReader;

import AST.Program;
import AST.Visitor.PrettyPrintVisitor;
import java_cup.runtime.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Nenhum arquivo fonte informado");
            return;
        }

        if (args.length > 1) {
            System.out.println("Apenas um arquivo fonte é suportado");
            return;
        }

        String sourceFile = args[0];
        try {
            System.out.println("Parsing " + sourceFile);
            Scanner s = new Scanner(new UnicodeEscapes(new FileReader(sourceFile)));
            parser p = new parser(s);
            Symbol root;
            // replace p.parse() with p.debug_parse() in next line to see trace
            // of
            // parser shift/reduce actions during parse
            root = p.parse(); // parses the program
            Program program = (Program) root.value;
            program.Print();

            PrettyPrintVisitor pretty = new PrettyPrintVisitor();
            pretty.visit(program);

            System.out.println("Nenhum erro encontrado");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
