import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CatProds implements InterfCatProds {
    private Set<String> produtos;

    public CatProds(){
        this.produtos = new TreeSet<>();
    }


    public void adiciona(String s) {
        this.produtos.add(s);
    }

    @Override
    public boolean contains(String codPro) {
        return this.produtos.contains(codPro);
    }

    public Set<String> getProdutos(){
        return new HashSet<>(this.produtos);
    }

    public boolean isEmpty(){
        return this.produtos.isEmpty();
    }
}

