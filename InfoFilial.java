import java.io.Serializable;

public class InfoFilial implements Serializable {
    private String codigo;
    private int quantidadeComprada;
    private int numVendas;

    public InfoFilial(String cliente, int quantidadeComprada) {
        this.codigo = cliente;
        this.quantidadeComprada = quantidadeComprada;
        this.numVendas = 1;
    }

    public InfoFilial(InfoFilial i){
        this.codigo = i.getCodigo();
        this.quantidadeComprada = i.getQuantidadeComprada();
        this.numVendas = i.getNumVendas();
    }

    public InfoFilial(){
        this.codigo = "";
        this.quantidadeComprada = 0;
        this.numVendas = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void incrementaNumVendas(){
        this.numVendas++;
    }

    public int getNumVendas() {
        return numVendas;
    }

    public InfoFilial clone(){
        return new InfoFilial(this);
    }

    public void adicionaQuantidade(int qtd){
        this.quantidadeComprada += qtd;
    }

}