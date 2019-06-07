public class VendaMensal {
    private int nVendas;
    private double totalFaturado;
    private double preco;

    public VendaMensal(int nVendas, double preco) {
        this.nVendas = nVendas;
        this.totalFaturado = preco*nVendas;
        this.preco = preco;
    }

    public VendaMensal() {
        this.nVendas = 0;
        this.totalFaturado = 0;
        this.preco = 0;
    }

    public VendaMensal(VendaMensal vendaMensal) {
        this.nVendas = vendaMensal.getnVendas();
        this.totalFaturado = vendaMensal.getTotalFaturado();
        this.preco = vendaMensal.getPreco();
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

    public double getPreco() { return this.preco;}

    public double getPrecoUnitario() {
        return this.totalFaturado/this.nVendas;
    }


    public VendaMensal clone() {
        return new VendaMensal(this);
    }
}
