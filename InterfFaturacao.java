import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfFaturacao {
    void adiciona(InterfVenda venda);

    Set<String> getListaOrdenadaProdutosNuncaComprados(InterfCatProds catpro);

    double getTotalFaturado(String prod, int[] quant, int mes);

    List<Double> totalfaturado(List<Map<String,int[]>> prodsQuant);

    boolean isEmpty();

    List<String> getClientesMaisGastadores(InterfFilial f);

    double[] getPrecoNormalProd(String codProd);

    double[] getPrecoPromoProd(String codProd);

    Double getTotalFaturado();

    Map<String, List<double[]>> getPrecoProds();
}