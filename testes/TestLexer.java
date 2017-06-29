class Factorial {
    public static void main(String[] a) {
        System.out.println(new Fac().ComputeFac(10));
    }
}

class Fac {
    int variavelNaClasse;
    public int ComputeFac(int num) {
        int num_aux;
        int preco;
        int valorPago;
        int troco;
        boolean precoCaro;
        boolean promocao;
        variavelNaClasse = 9;
        preco = 4;
        valorPago = 10;
        troco = valorPago + preco; 
        promocao = true;
        precoCaro = false;
        precoCaro = true;
        precoCaro = valorPago < preco;
        precoCaro = precoCaro && promocao;
        if (num < 1)
            num_aux = 1;
        else
            num_aux = num * (this.ComputeFac(num - 1));
        return num_aux;
    }

}