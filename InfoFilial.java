import java.util.ArrayList;
import java.util.List;

public class InfoFilial {
    String cliente;
    int quantidadeComprada;
    int numVendas;

    public InfoFilial(String cliente, int quantidadeComprada) {
        this.cliente = cliente;
        this.quantidadeComprada = quantidadeComprada;
        this.numVendas = 1;
    }

    public InfoFilial(InfoFilial i){
        this.cliente = i.getCliente();
        this.quantidadeComprada = i.getQuantidadeComprada();
        this.numVendas = i.getNumVendas();
    }

    public InfoFilial(String codCli) {
        this.cliente = codCli;
        this.quantidadeComprada = 0;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void setQuantidadeComprada(int quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public void setNumVendas(int numVendas) {
        this.numVendas = numVendas;
    }

    public void incrementaNumVendas(){
        this.numVendas++;
    }

    public int getNumVendas() {
        return numVendas;
    }

    public boolean equals(InfoFilial info){
        return this.cliente.equals(info.getCliente());
    }

    public InfoFilial clone(){
        return new InfoFilial(this);
    }

}