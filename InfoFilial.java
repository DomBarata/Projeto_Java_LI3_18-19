import java.util.ArrayList;
import java.util.List;

public class InfoFilial {
    String cliente;
    int[] quantidadeComprada;
    boolean[] promo;
    int[] numVendas;

    public InfoFilial(String cliente, int quantidadeComprada, int mes, boolean promo) {
        this.cliente = cliente;
        this.quantidadeComprada = new int[12];
        for(int i = 0; i < 12; i++){
            if (mes-1 == i)
                this.quantidadeComprada[i] = quantidadeComprada;
            this.quantidadeComprada[i] = 0;
        }
        this.promo = new boolean[12];
        for(int i = 0; i < 12; i++){
            if (mes-1 == i)
                this.promo[i] = promo;
        }
        this.numVendas = new int[12];
        for(int i = 0; i < 12; i++){
            if(mes-1==i){
                this.numVendas[i] = 1;
            }
        }
    }

    public InfoFilial(InfoFilial i){
        this.cliente = i.getCliente();
        this.quantidadeComprada = i.getQuantidadeComprada();
        this.promo = i.getPromo();
        this.numVendas = i.getNumVendas();
    }

    public InfoFilial(String codCli) {
        this.cliente = codCli;
        this.quantidadeComprada = null;
        this.promo = null;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int[] getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public int getQuantidadeComprada(int mes) {
        return quantidadeComprada[mes];
    }

    public void setQuantidadeComprada(int[] quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public boolean[] getPromo() {
        return promo;
    }

    public void setPromo(boolean[] promo) {
        this.promo = promo;
    }

    public void setNumVendas(int[] numVendas) {
        this.numVendas = numVendas;
    }

    public void incrementaNumVendas(int mes){
        this.numVendas[mes-1]++;
    }

    public int[] getNumVendas() {
        return numVendas;
    }

    public int getNumVendas(int mes){
        return numVendas[mes];
    }

    public boolean equals(InfoFilial info){
        return this.cliente.equals(info.getCliente());
    }

    public InfoFilial clone(){
        return new InfoFilial(this);
    }

}