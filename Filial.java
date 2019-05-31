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
            InfoFilial info = new InfoFilial(venda.getCodCli());
            if(clientes.contains(info)){//se ja tiver um produto com o mesmo codigo de cliente
                //ir buscar esse info
                info = clientes.get(clientes.indexOf(info));

                // adicionar info
                boolean[] promo = info.getPromo();
                if(venda.getTipo().equals("P")) {
                    promo[venda.getMes()-1] = true;
                }else{
                    promo[venda.getMes()-1] = false;
                }
                info.setPromo(promo);

                int[] quantidadeComprada = info.getQuantidadeComprada();
                quantidadeComprada[venda.getMes()-1] = venda.getQuant();
                info.setQuantidadeComprada(quantidadeComprada);

                //reinserir info na list
                clientes.set(clientes.indexOf(info),info);
                //reinserir list no map
                produtos.put(venda.getCodPro(),clientes);
            }else{
                //criar nova info
                boolean[] promo = new boolean[12];
                if(venda.getTipo().equals("P")) {
                    promo[venda.getMes()-1] = true;
                }else{
                    promo[venda.getMes()-1] = false;
                }
                info.setPromo(promo);

                int[] quantidadeComprada = new int[12];
                quantidadeComprada[venda.getMes()-1] = venda.getQuant();
                info.setQuantidadeComprada(quantidadeComprada);

                //inserir numa lista
                clientes.add(info);
                //inserir lisa no map
                produtos.put(venda.getCodPro(),clientes);
            }
        }else{//Se o mapa nao tiver esse produto
            List<InfoFilial> clientes = new ArrayList<>();
            clientes.add(new InfoFilial(venda.getCodCli(), venda.getQuant(),
                                        venda.getMes(), venda.getTipo().equals("P")));
            this.produtos.put(venda.getCodPro(), clientes);
        }
    }
}