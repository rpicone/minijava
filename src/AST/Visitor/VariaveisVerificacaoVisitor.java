package AST.Visitor;

import java.util.*;
import AST.*;

public class VariaveisVerificacaoVisitor implements Visitor {
    private int errosEncontrados = 0;

    public int getErrosEncontrados() {
        return errosEncontrados;
    }

    private static Tipo tipoVariavel(Type t) {
        if (t instanceof BooleanType) {
            return Tipo.Boolean;
        } else if (t instanceof IntegerType) {
            return Tipo.Int;
        } else {
            return Tipo.Desconhecido;
        }
    }

    private List<Escopo> escopos = new LinkedList<Escopo>();

    public Escopo removeEscopo() {
        return this.escopos.remove(this.escopos.size() - 1);
    }

    public Escopo getEscopoAtual() {
        return this.escopos.get(this.escopos.size() - 1);
    }

    public void insereEscopo() {
        Escopo escopo = new Escopo();
        this.escopos.add(escopo);
    }

    public Variavel procuraVariavel(String nome) {
        for(Escopo escopo : escopos) {
            if (escopo.variaveis.containsKey(nome)) {
                return escopo.variaveis.get(nome);
            }
        }
        return null;
    }

    public VariaveisVerificacaoVisitor() {
        super();
    }

    public void exibirVariaveis() {
        int nivel = 0;
        for(Escopo escopo : escopos) {
            nivel++;
            System.out.print(String.join("", Collections.nCopies(nivel, "    ")));
            for (Map.Entry<String, Variavel> variavelEntry : escopo.variaveis.entrySet()) {
                System.out.print(variavelEntry.getValue().getNome());
                System.out.print(": ");
                System.out.print(variavelEntry.getValue().getTipo().toString());
                System.out.print("\r\n");
            }
        }
    }

    public void visit(Program n) {
        ClassDeclList classDeclarations = n.cl;
        int size = classDeclarations.size();
        for (int i = 0; i < size; ++i) {
            classDeclarations.get(i).accept(this);
        }
    }

    public void visit(MainClass n) {
        this.insereEscopo();
    }

    public void visit(ClassDeclSimple n) {
        this.insereEscopo();

        VarDeclList variaveis = n.vl;
        int variablesCount = variaveis.size();
        for (int i = 0; i < variablesCount; ++i) {
            variaveis.get(i).accept(this);
        }

        MethodDeclList metodos = n.ml;
        int methodsCount = metodos.size();
        for (int i = 0; i < methodsCount; ++i) {
            metodos.get(i).accept(this);
        }

        this.exibirVariaveis();
        this.removeEscopo();
    }

    public void visit(ClassDeclExtends n) {
        this.insereEscopo();

        VarDeclList variaveis = n.vl;
        int variablesCount = variaveis.size();
        for (int i = 0; i < variablesCount; ++i) {
            variaveis.get(i).accept(this);
        }

        MethodDeclList metodos = n.ml;
        int methodsCount = metodos.size();
        for (int i = 0; i < methodsCount; ++i) {
            metodos.get(i).accept(this);
        }

        this.exibirVariaveis();
        this.removeEscopo();
    }

    public void visit(VarDecl n) {
        Variavel variavel = new Variavel();
        variavel.setTipo(tipoVariavel(n.t));
        variavel.setNome(n.i.s);

        this.getEscopoAtual().variaveis.put(variavel.getNome(), variavel);
    }

    public void visit(MethodDecl n) {
        this.insereEscopo();

        FormalList parameters = n.fl;
        int parametersCount = parameters.size();
        for (int i = 0; i < parametersCount; ++i) {
            parameters.get(i).accept(this);
        }

        VarDeclList variables = n.vl;
        int variablesCount = variables.size();
        for (int i = 0; i < variablesCount; ++i) {
            variables.get(i).accept(this);
        }

        // Agora que temos as variáveis incluídas no escopo,
        // vamos verificar as atribuições
        VarDeclList variaveis = n.vl;
        for (int i = 0; i < variaveis.size(); ++i) {
            variaveis.get(i).accept(this);
        }

        StatementList statements = n.sl;
        for (int i = 0; i < statements.size(); ++i) {
            statements.get(i).accept(this);
        }

        this.exibirVariaveis();
        this.removeEscopo();
    }

    public void visit(Formal n) {
        Variavel variavel = new Variavel();
        variavel.setTipo(tipoVariavel(n.t));
        variavel.setNome(n.i.s);

        Escopo escopoAtual = this.getEscopoAtual();
        escopoAtual.variaveis.put(variavel.getNome(), variavel);
    }

    public void visit(Assign n) {
        Variavel variavel = this.procuraVariavel(n.i.s);
        if (variavel == null) {
            System.err.println("ERRO: Variável " + n.i.s + " não declarada");
            errosEncontrados++;
        } else {
            // Verificando o tipo
            System.out.println("Variável " + n.i.s + " encontrada. Tipo " + variavel.getTipo().toString());
            if (variavel.getTipo() == Tipo.Int) {
                if (!(n.e instanceof AST.IntegerLiteral || n.e instanceof AST.Minus ||
                        n.e instanceof AST.Plus || n.e instanceof AST.Times)) {
                    System.err.println("ERRO: Atribuição inválida. Tentando atribuir um " + n.e.toString() +
                            " em um tipo " + variavel.getTipo().toString());
                    errosEncontrados++;
                }
            } else if (variavel.getTipo() == Tipo.Boolean) {
                if (!(n.e instanceof AST.False || n.e instanceof AST.True || n.e instanceof AST.LessThan ||
                    n.e instanceof AST.Not || n.e instanceof AST.And)) {
                    System.err.println("ERRO: Atribuição inválida. Tentando atribuir um " + n.e.toString() +
                            " em um tipo " + variavel.getTipo().toString());
                    errosEncontrados++;
                }
            }
        }
    }

    public void visit(IntArrayType n) { }
    public void visit(BooleanType n) { }
    public void visit(IntegerType n) { }
    public void visit(IdentifierType n) { }
    public void visit(Block n) { }
    public void visit(If n) { }
    public void visit(While n) { }
    public void visit(Print n) { }
    public void visit(ArrayAssign n) { }
    public void visit(And n) { }
    public void visit(LessThan n) { }
    public void visit(Plus n) { }
    public void visit(Minus n) { }
    public void visit(Times n) { }
    public void visit(ArrayLookup n) { }
    public void visit(ArrayLength n) { }
    public void visit(Call n) { }
    public void visit(IntegerLiteral n) { }
    public void visit(True n) { }
    public void visit(False n) { }
    public void visit(IdentifierExp n) { }
    public void visit(This n) { }
    public void visit(NewArray n) { }
    public void visit(NewObject n) { }
    public void visit(Not n) { }
    public void visit(Identifier n) { }
    public void visit(Display n) { }
}
