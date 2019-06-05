import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InterfFaturacao {
    void adiciona(InterfVenda venda);

    Set<String> getListaOrdenadaProdutosNuncaComprados(InterfCatProds catpro);
    double getTotalFaturadoProd(String codProd);
    double getTotalFaturado(String prod, int[] quant, int mes);

    List<Double> totalfaturado(Map<String,int[]> prodsQuantNormal, Map<String,int[]> prodsQuantPromo);

    boolean isEmpty();
}