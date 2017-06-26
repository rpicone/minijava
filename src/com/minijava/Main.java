package com.minijava;

import java.io.FileReader;

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
            p.parse();
            System.out.println("Nenhum erro encontrado");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
