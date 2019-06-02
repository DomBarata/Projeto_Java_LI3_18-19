
public interface InterfFilial {
    void adiciona(InterfVenda venda);

    int[] totalVendasEClientesMes(int mes);
    int[] vezesProdComprado(String codProd);
    int[] clientesProd(String codProd);
}