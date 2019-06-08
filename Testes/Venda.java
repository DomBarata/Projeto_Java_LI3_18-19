import java.io.Serializable;
import java.util.InputMismatchException;
import static java.lang.System.out;

public class Venda implements InterfVenda, Serializable {
    private static final int FILIAIS = 3;
    private String codPro;
    private String codCli;
    private String tipo;
    private int mes;
    private int filial;
    private int quant;
    private double preco;

    public Venda(String codPro, String codCli, String tipo, int mes,
                 int filial, int quant, double preco) {
        this.codPro = codPro;
        this.codCli = codCli;
        this.tipo = tipo;
        this.mes = mes;
        this.filial = filial;
        this.quant = quant;
        this.preco = preco;
    }

    public Venda(Venda v) {
        this.codPro = v.getCodPro();
        this.codCli = v.getCodCli();
        this.tipo = v.getTipo();
        this.mes = v.getMes();
        this.filial = v.getFilial();
        this.quant = v.getQuant();
        this.preco = v.getPreco();
    }

    public static InterfVenda divideVenda(String linha) {
        String codPro, codCli, tipo;
        int mes = 0, filial = 0, quant = 0;
        double preco = 0;
        String[] campos = linha.split(" ");
        codPro = campos[0];
        tipo = campos[3];
        codCli = campos[4];
        try{
            preco = Double.parseDouble(campos[1]);
            quant = Integer.parseInt(campos[2]);
            mes = Integer.parseInt(campos[5]);
            filial = Integer.parseInt(campos[6]);
        }
        catch (InputMismatchException exc){
            if(codPro.equals("JU1146"))
                out.println("Venda Inválida");
            return null;}

        if(mes < 1 || mes > 12) return null;
        if(filial < 1 || filial > FILIAIS) return null;
        return new Venda(codPro, codCli, tipo, mes, filial, quant, preco);
    }

    public String getCodPro() {
        return codPro;
    }

    public String getCodCli() {
        return codCli;
    }

    public String getTipo() {
        return tipo;
    }

    public int getMes() {
        return mes;
    }

    public int getFilial() {
        return filial;
    }

    public int getQuant() { return quant;}

    public double getPreco() { return preco; }

    @Override
    public boolean isPromo() {
        return this.tipo.equals("P");
    }

    public Venda clone() {
        return new Venda(this);
    }

    @Override
    public String toString() {
        return ("Fil: " + filial + " Prod: " + this.codPro + " cli: " + this.codCli
                + " tipo" + tipo + " mes: " + mes + " qtd: " + quant
                + " preco: " + preco);
    }
}