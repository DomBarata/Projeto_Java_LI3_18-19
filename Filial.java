import java.util.*;

public class Filial implements InterfFilial{
    //key produto, value set com todas as informacoes necessarias
    Map<String, List<InfoFilial>> produtos;

    public Filial(){
        this.produtos = new HashMap<>();
    }

    public void adiciona(InterfVenda venda) {
        if(this.produtos.containsKey(venda.getCodPro())){//se o mapa já tiver esse produto
            List<InfoFilial> clientes = this.produtos.get(venda.getCodPro()); //todos os clientes
                                                                            // que já compraram esse produto
            if(clientes.contains(new InfoFilial(venda.getCodCli()))){//se ja tiver um produto com o mesmo codigo de cliente
                //ir buscar esse info
                //adicionar info
                //reincerir info na list
                //reincerir list no map
            }else{
                //criar nova info
                //inserir numa lista
                //inserir lisa no map
            }
        }else{//Se o mapa nao tiver esse produto
            List<InfoFilial> clientes = new ArrayList<>();
            clientes.add(new InfoFilial(venda.getCodCli(), venda.getQuant(),
                                        venda.getMes(), venda.getTipo().equals("P")));
            this.produtos.put(venda.getCodPro(), clientes);
        }
    }
}
