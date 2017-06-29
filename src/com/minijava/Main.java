package com.minijava;

import java.io.FileReader;

import AST.Program;
import AST.Visitor.GeradorCodigoVisitor;
import AST.Visitor.PrettyPrintVisitor;
import AST.Visitor.VariaveisVerificacaoVisitor;
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
            Symbol simboloInicial = p.parse();
            Program program = (Program) simboloInicial.value;

            // Exibindo código-fonte do programa (PrettyPrint)
            System.out.println("");
            System.out.println("CÓDIGO FONTE");
            System.out.println("========================================================================");
            PrettyPrintVisitor pretty = new PrettyPrintVisitor();
            pretty.visit(program);

            // Imprimindo a AST
            System.out.println("");
            System.out.println("AST");
            System.out.println("========================================================================");
            program.Print();

            // Verificando as declarações e tipos de variáveis
            System.out.println("");
            System.out.println("VERIFICAÇÃO DAS VARIÁVEIS");
            System.out.println("========================================================================");
            VariaveisVerificacaoVisitor variaveisVerificacaoVisitor = new VariaveisVerificacaoVisitor();
            variaveisVerificacaoVisitor.visit(program);

            // Exibindo código-fonte do programa (PrettyPrint)
            System.out.println("");
            System.out.println("CÓDDIGO C# GERADO");
            System.out.println("========================================================================");
            GeradorCodigoVisitor gerador = new GeradorCodigoVisitor();
            gerador.visit(program);

            System.out.println("");
            System.out.println("RELATÓRIO FINAL");
            System.out.println("========================================================================");
            // Exibindo resumo das atividades
            int errosTipo = variaveisVerificacaoVisitor.getErrosEncontrados();
            if (errosTipo > 0) {
                System.err.println("Encontrados " + errosTipo);
            } else {
                System.out.println("Nenhum erro encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
