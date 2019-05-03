import java.io.Serializable;
import java.util.Objects;

public class Venda implements IVenda, Serializable {

    private String codProd;
    private String codCli;
    private String tipo;
    private int mes = 0;
    private int filial = 0;
    private int quant = 0;
    private double preco = 0.0;

    public Venda(String codProd, String codCli, String tipo, int mes, int filial, int quant, double preco) {
        this.codProd = codProd;
        this.codCli = codCli;
        this.tipo = tipo;
        this.mes = mes;
        this.filial = filial;
        this.quant = quant;
        this.preco = preco;
    }

    public Venda(Venda v) {
        this.codProd = v.getCodProd();
        this.codCli = v.getCodCli();
        this.tipo = v.getTipo();
        this.mes = v.getMes();
        this.filial = v.getFilial();
        this.quant = v.getQuant();
        this.preco = v.getPreco();
    }

    public String getCodProd() {
        return codProd;
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

    public int getQuant() {
        return quant;
    }

    public double getPreco() {
        return preco;
    }

    public double getValor(){
        return this.preco*this.quant;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Venda{");
        sb.append("codProd='").append(codProd).append('\'')
            .append(", codCli='").append(codCli).append('\'')
            .append(", tipo='").append(tipo).append('\'')
            .append(", mes=").append(mes)
            .append(", filial=").append(filial)
            .append(", quant=").append(quant)
            .append(", preco=").append(preco)
            .append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Venda)) return false;
        Venda venda = (Venda) o;
        return getMes() == venda.getMes() &&
                getFilial() == venda.getFilial() &&
                getQuant() == venda.getQuant() &&
                Double.compare(venda.getPreco(), getPreco()) == 0 &&
                Objects.equals(getCodProd(), venda.getCodProd()) &&
                Objects.equals(getCodCli(), venda.getCodCli()) &&
                Objects.equals(getTipo(), venda.getTipo());
    }

    public Venda clone(){
        return this;
    }
}
