package AST.Visitor;

import AST.*;

public class TreeVisitor implements Visitor {

    private int nivel;

    private void println(String message) {
        for (int i = 0; i < nivel; ++i) {
            System.out.print("    ");
        }
        System.out.println(message);
    }

    public void visit(Display n) {

    }


    public void visit(Program n) {
        println("Program:");
        ++nivel;

        n.m.accept(this);

        ClassDeclList classes = n.cl;
        int size = classes.size();
        for (int i = 0; i < size; ++i) {
            classes.get(i).accept(this);
        }

        --nivel;
    }


    public void visit(MainClass n) {
        println("main class named " + n.i1.s + ":");
        ++nivel;

        println("name of parameter to main() method: " + n.i2.s);

        println("body of main:");
        ++nivel;
        n.s.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(ClassDeclSimple n) {
        println("class of name " + n.i.s + ":");
        ++nivel;

        println("instance variables:");
        ++nivel;
        VarDeclList vars = n.vl;
        int varsize = vars.size();
        for (int i = 0; i < varsize; ++i) {
            vars.get(i).accept(this);
        }
        --nivel;

        println("methods:");
        ++nivel;
        MethodDeclList methods = n.ml;
        int methodsize = methods.size();
        for (int i = 0; i < methodsize; ++i) {
            methods.get(i).accept(this);
        }
        --nivel;

        --nivel;
    }


    public void visit(ClassDeclExtends n) {

        println("class of name " + n.i.s + ":");
        ++nivel;

        println("parent class: " + n.j.s);

        println("instance variables:");
        ++nivel;
        VarDeclList vars = n.vl;
        int varsize = vars.size();
        for (int i = 0; i < varsize; ++i) {
            vars.get(i).accept(this);
        }
        --nivel;

        println("methods:");
        ++nivel;
        MethodDeclList methods = n.ml;
        int methodsize = methods.size();
        for (int i = 0; i < methodsize; ++i) {
            methods.get(i).accept(this);
        }
        --nivel;

        --nivel;

    }


    public void visit(VarDecl n) {
        println("declare " + n.i.s + " of type:");
        ++nivel;
        n.t.accept(this);
        --nivel;
    }


    public void visit(MethodDecl n) {

        println("method named " + n.i.s + ":");
        ++nivel;

        println("return type:");
        ++nivel;
        n.t.accept(this);
        --nivel;

        println("parameters:");
        ++nivel;
        FormalList formals = n.fl;
        int formalsize = formals.size();
        for (int i = 0; i < formalsize; ++i) {
            formals.get(i).accept(this);
        }
        --nivel;

        println("variables:");
        ++nivel;
        VarDeclList vars = n.vl;
        int varsize = vars.size();
        for (int i = 0; i < varsize; ++i) {
            vars.get(i).accept(this);
        }
        --nivel;

        println("statements:");
        ++nivel;
        StatementList stmts = n.sl;
        int stmtsize = stmts.size();
        for (int i = 0; i < stmtsize; ++i) {
            stmts.get(i).accept(this);
        }
        --nivel;

        println("return value:");
        ++nivel;
        n.e.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(Formal n) {
        println("formal parameter " + n.i.s + " of type:");
        ++nivel;
        n.t.accept(this);
        --nivel;
    }


    public void visit(IntArrayType n) {
        println("integer array");

    }


    public void visit(BooleanType n) {
        println("boolean");
    }


    public void visit(IntegerType n) {
        println("int");
    }


    public void visit(IdentifierType n) {
        println("identifier type (" + n.s + ")");
    }


    public void visit(Block n) {
        println("block:");
        ++nivel;

        StatementList sl = n.sl;
        int size = sl.size();

        for (int i = 0; i < size; ++i) {
            sl.get(i).accept(this);
        }

        --nivel;
    }


    public void visit(If n) {
        println("if:");
        ++nivel;

        println("condition:");
        ++nivel;
        n.e.accept(this);
        --nivel;

        println("if true:");
        ++nivel;
        n.s1.accept(this);
        --nivel;

        println("if false:");
        ++nivel;
        n.s2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(While n) {
        println("while:");
        ++nivel;

        println("condition:");
        ++nivel;
        n.e.accept(this);
        --nivel;

        println("body:");
        ++nivel;
        n.s.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(Print n) {
        println("System.out.println:");
        ++nivel;
        n.e.accept(this);
        --nivel;
    }


    public void visit(Assign n) {
        println("assignment of variable " + n.i.s + " to value:");
        ++nivel;
        n.e.accept(this);
        --nivel;
    }


    public void visit(ArrayAssign n) {
        println("assignment to array " + n.i.s + ":");
        ++nivel;

        println("position in array:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("value:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(And n) {
        println("boolean and:");
        ++nivel;

        println("left side:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("right side:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(LessThan n) {
        println("less than:");
        ++nivel;

        println("left side:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("right side:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(Plus n) {
        println("addition:");
        ++nivel;

        println("left side:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("right side:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }

    public void visit(Minus n) {
        println("subtraction:");
        ++nivel;

        println("left side:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("right side:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(Times n) {
        println("multiplication:");
        ++nivel;

        println("left side:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("right side:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(ArrayLookup n) {
        println("array lookup:");
        ++nivel;

        println("array:");
        ++nivel;
        n.e1.accept(this);
        --nivel;

        println("position:");
        ++nivel;
        n.e2.accept(this);
        --nivel;

        --nivel;
    }


    public void visit(ArrayLength n) {
        println("length of array:");
        ++nivel;
        n.e.accept(this);
        --nivel;
    }


    public void visit(Call n) {
        println("method call:");
        ++nivel;

        println("invocant:");
        ++nivel;
        n.e.accept(this);
        --nivel;

        println("method name: " + n.i.s);

        println("parameters:");
        ++nivel;
        ExpList exprs = n.el;
        int size = exprs.size();
        for (int i = 0; i < size; ++i) {
            exprs.get(i).accept(this);
        }
        --nivel;

        --nivel;
    }


    public void visit(IntegerLiteral n) {
        println("integer literal: " + n.i);
    }


    public void visit(True n) {
        println("true");
    }


    public void visit(False n) {
        println("false");
    }


    public void visit(IdentifierExp n) {
        println("identifier: " + n.s);
    }


    public void visit(This n) {
        println("this");
    }


    public void visit(NewArray n) {
        println("new integer array with size:");
        ++nivel;
        n.e.accept(this);
        --nivel;
    }


    public void visit(NewObject n) {
        println("new object of identifier:");
        ++nivel;
        n.i.accept(this);
        --nivel;
    }


    public void visit(Not n) {
        println("not:");
        ++nivel;
        n.e.accept(this);
        --nivel;
    }


    public void visit(Identifier n) {
        println("identifier: " + n.s);
    }
}
