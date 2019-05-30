import java.util.HashSet;
import java.util.Set;

public class CatProds implements InterfCatProds{
    private Set<String> produtos;

    public CatProds(){
        this.produtos = new HashSet<>();
    }


    public void adiciona(String s) {
        this.produtos.add(s);
    }

    @Override
    public boolean contains(String codPro) {
        return this.produtos.contains(codPro);
    }
}
