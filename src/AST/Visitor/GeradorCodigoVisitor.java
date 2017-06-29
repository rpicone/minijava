package AST.Visitor;

import AST.*;

import java.io.IOException;
import java.io.PrintWriter;

public class GeradorCodigoVisitor implements Visitor {
    PrintWriter fileWriter;

    public GeradorCodigoVisitor() {
        try{
            fileWriter = new PrintWriter("d:\\codigogerado\\codigo-gerado.cs", "UTF-8");
        } catch (IOException e) {
            System.out.print("ERRO AO CRIAR O ARQUIVO " + e.toString());
        }
    }

    public void escreverLinhaCodigo() {
        System.out.print("\r\n");
        fileWriter.write("\r\n");
    }

    public void escreverLinhaCodigo(Object codigo) {
        System.out.print(codigo.toString() + "\r\n");
        fileWriter.write(codigo.toString());
    }

    public void escreverCodigo(Object codigo) {
        System.out.print(codigo.toString());
        fileWriter.write(codigo.toString());
    }

    public void concluirGeracao() {
        fileWriter.close();

        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec("C:\\Program Files (x86)\\MSBuild\\14.0\\Bin\\amd64\\csc d:\\codigogerado\\codigo-gerado.cs /out:d:\\codigogerado\\programa.exe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Display added for toy example language.  Not used in regular MiniJava
    public void visit(Display n) {
        escreverCodigo("display ");
        n.e.accept(this);
        escreverCodigo(";");
    }

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        escreverLinhaCodigo("using System;");

        escreverLinhaCodigo("namespace Programa {");
        escreverLinhaCodigo("\tpublic class Program {");

        n.m.accept(this);
        for ( int i = 0; i < n.cl.size(); i++ ) {
            escreverLinhaCodigo();
            n.cl.get(i).accept(this);
        }

        escreverLinhaCodigo("\t}");

        escreverLinhaCodigo("}");

        concluirGeracao();
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        escreverCodigo("public class ");
        n.i1.accept(this);
        escreverLinhaCodigo("{");
        escreverCodigo("public static void Main (string[] ");
        n.i2.accept(this);
        escreverLinhaCodigo("){");
        n.s.accept(this);
        escreverCodigo("Console.ReadKey();");
        escreverLinhaCodigo("}");
        escreverLinhaCodigo("}");
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        escreverCodigo("class ");
        n.i.accept(this);
        escreverLinhaCodigo(" { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            escreverCodigo("  ");
            n.vl.get(i).accept(this);
            if ( i+1 < n.vl.size() ) { escreverLinhaCodigo(); }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            escreverLinhaCodigo();
            n.ml.get(i).accept(this);
        }
        escreverLinhaCodigo();
        escreverLinhaCodigo("}");
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        escreverCodigo("class ");
        n.i.accept(this);
        escreverLinhaCodigo(" extends ");
        n.j.accept(this);
        escreverLinhaCodigo(" { ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            escreverCodigo("  ");
            n.vl.get(i).accept(this);
            if ( i+1 < n.vl.size() ) { escreverLinhaCodigo(); }
        }
        for ( int i = 0; i < n.ml.size(); i++ ) {
            escreverLinhaCodigo();
            n.ml.get(i).accept(this);
        }
        escreverLinhaCodigo();
        escreverLinhaCodigo("}");
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n) {
        n.t.accept(this);
        escreverCodigo(" ");
        n.i.accept(this);
        escreverCodigo(";");
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        escreverCodigo(" public ");
        n.t.accept(this);
        escreverCodigo(" ");
        n.i.accept(this);
        escreverCodigo(" (");
        for ( int i = 0; i < n.fl.size(); i++ ) {
            n.fl.get(i).accept(this);
            if (i+1 < n.fl.size()) { escreverCodigo(", "); }
        }
        escreverLinhaCodigo("){ ");
        for ( int i = 0; i < n.vl.size(); i++ ) {
            escreverCodigo("    ");
            n.vl.get(i).accept(this);
            escreverLinhaCodigo("");
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            escreverCodigo("    ");
            n.sl.get(i).accept(this);
            if ( i < n.sl.size() ) { escreverLinhaCodigo(""); }
        }
        escreverCodigo(" return ");
        n.e.accept(this);
        escreverLinhaCodigo(";");
        escreverCodigo("}");
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n) {
        n.t.accept(this);
        escreverCodigo(" ");
        n.i.accept(this);
    }

    public void visit(IntArrayType n) {
        escreverCodigo("int []");
    }

    public void visit(BooleanType n) {
        escreverCodigo("Boolean");
    }

    public void visit(IntegerType n) {
        escreverCodigo("int");
    }

    // String s;
    public void visit(IdentifierType n) {
        escreverCodigo(n.s);
    }

    // StatementList sl;
    public void visit(Block n) {
        escreverLinhaCodigo("{ ");
        for ( int i = 0; i < n.sl.size(); i++ ) {
            escreverCodigo("      ");
            n.sl.get(i).accept(this);
            escreverLinhaCodigo();
        }
        escreverCodigo("    } ");
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n) {
        escreverCodigo("if (");
        n.e.accept(this);
        escreverLinhaCodigo(") ");
        escreverCodigo("    ");
        n.s1.accept(this);
        escreverLinhaCodigo();
        escreverCodigo(" else ");
        n.s2.accept(this);
    }

    // Exp e;
    // Statement s;
    public void visit(While n) {
        escreverCodigo("while (");
        n.e.accept(this);
        escreverCodigo(") ");
        n.s.accept(this);
    }

    // Exp e;
    public void visit(Print n) {
        escreverCodigo("Console.WriteLine(");
        n.e.accept(this);
        escreverCodigo(");");
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n) {
        n.i.accept(this);
        escreverCodigo(" = ");
        n.e.accept(this);
        escreverCodigo(";");
    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        escreverCodigo("[");
        n.e1.accept(this);
        escreverCodigo("] = ");
        n.e2.accept(this);
        escreverCodigo(";");
    }

    // Exp e1,e2;
    public void visit(And n) {
        escreverCodigo("(");
        n.e1.accept(this);
        escreverCodigo(" && ");
        n.e2.accept(this);
        escreverCodigo(")");
    }

    // Exp e1,e2;
    public void visit(LessThan n) {
        escreverCodigo("(");
        n.e1.accept(this);
        escreverCodigo(" < ");
        n.e2.accept(this);
        escreverCodigo(")");
    }

    // Exp e1,e2;
    public void visit(Plus n) {
        escreverCodigo("(");
        n.e1.accept(this);
        escreverCodigo(" + ");
        n.e2.accept(this);
        escreverCodigo(")");
    }

    // Exp e1,e2;
    public void visit(Minus n) {
        escreverCodigo("(");
        n.e1.accept(this);
        escreverCodigo(" - ");
        n.e2.accept(this);
        escreverCodigo(")");
    }

    // Exp e1,e2;
    public void visit(Times n) {
        escreverCodigo("(");
        n.e1.accept(this);
        escreverCodigo(" * ");
        n.e2.accept(this);
        escreverCodigo(")");
    }

    // Exp e1,e2;
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        escreverCodigo("[");
        n.e2.accept(this);
        escreverCodigo("]");
    }

    // Exp e;
    public void visit(ArrayLength n) {
        n.e.accept(this);
        escreverCodigo(".length");
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n) {
        n.e.accept(this);
        escreverCodigo(".");
        n.i.accept(this);
        escreverCodigo("(");
        for ( int i = 0; i < n.el.size(); i++ ) {
            n.el.get(i).accept(this);
            if ( i+1 < n.el.size() ) { escreverCodigo(", "); }
        }
        escreverCodigo(")");
    }

    // int i;
    public void visit(IntegerLiteral n) {
        escreverCodigo(n.i);
    }

    public void visit(True n) {
        escreverCodigo("true");
    }

    public void visit(False n) {
        escreverCodigo("false");
    }

    // String s;
    public void visit(IdentifierExp n) {
        escreverCodigo(n.s);
    }

    public void visit(This n) {
        escreverCodigo("this");
    }

    // Exp e;
    public void visit(NewArray n) {
        escreverCodigo("new int [");
        n.e.accept(this);
        escreverCodigo("]");
    }

    // Identifier i;
    public void visit(NewObject n) {
        escreverCodigo("new ");
        escreverCodigo(n.i.s);
        escreverCodigo("()");
    }

    // Exp e;
    public void visit(Not n) {
        escreverCodigo("!");
        n.e.accept(this);
    }

    // String s;
    public void visit(Identifier n) {
        escreverCodigo(n.s);
    }
}