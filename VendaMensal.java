import java.io.Serializable;

public class VendaMensal implements Serializable {
    private int nVendas;
    private double totalFaturado;

    public VendaMensal(int nVendas, double preco) {
        this.nVendas = nVendas;
        this.totalFaturado = preco*nVendas;
    }

    public VendaMensal() {
        this.nVendas = 0;
        this.totalFaturado = 0;
    }

    public VendaMensal(VendaMensal vendaMensal) {
        this.nVendas = vendaMensal.getnVendas();
        this.totalFaturado = vendaMensal.getTotalFaturado();
    }

    public int getnVendas() {
        return nVendas;
    }

    public void setnVendas(int nVendas) {
        this.nVendas = nVendas;
    }

    public double getTotalFaturado() {
        return totalFaturado;
    }

    public void setTotalFaturado(double totalFaturado) {
        this.totalFaturado = totalFaturado;
    }

    public void insertVenda(int qtd, double preco){
        this.nVendas += qtd;
        this.totalFaturado += qtd*preco;
    }

    public double getPrecoUnitario() {
        return this.totalFaturado/this.nVendas;
    }


    public VendaMensal clone() {
        return new VendaMensal(this);
    }
}
