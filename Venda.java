import java.io.Serializable;

public class Venda implements InterfVenda, Serializable {
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
