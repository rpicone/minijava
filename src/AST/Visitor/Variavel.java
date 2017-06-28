package AST.Visitor;

/**
 * Created by reina on 27/06/2017.
 */
public class Variavel {
    private Tipo tipo;
    private String nome;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}