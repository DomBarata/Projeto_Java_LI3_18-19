public class InfoFilial {
    String cliente;
    int[] quantidadeComprada;
    boolean[] promo;

    public InfoFilial(String cliente, int quantidadeComprada, int mes, boolean promo) {
        this.cliente = cliente;
        this.quantidadeComprada = new int[12];
        for(int i = 0; i < 12; i++){
            if (mes == i)
                this.quantidadeComprada[i] = quantidadeComprada;
            this.quantidadeComprada[i] = 0;
        }
        this.promo = new boolean[12];
        for(int i = 0; i < 12; i++){
            if (mes == i)
                this.promo[i] = promo;
        }
    }

    public InfoFilial(InfoFilial i){
        this.cliente = i.getCliente();
        this.quantidadeComprada = i.getQuantidadeComprada();
        this.promo = i.getPromo();
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

    public void setQuantidadeComprada(int[] quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public boolean[] getPromo() {
        return promo;
    }

    public void setPromo(boolean[] promo) {
        this.promo = promo;
    }

    public boolean equals(InfoFilial info){
        return this.cliente.equals(info.getCliente());
    }

    public InfoFilial clone(){
        return new InfoFilial(this);
    }

}
