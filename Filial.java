import java.util.*;

public class Filial implements InterfFilial{
    //key produto, value set com todas as informacoes necessarias
    private Map<String, List<InfoFilial>> produtos;

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

                info.incrementaNumVendas(venda.getMes());

                //reinserir info na list
                clientes.set(clientes.indexOf(info),info);
                //reinserir list no map
                produtos.put(venda.getCodPro(),clientes);
            }else{
                //criar nova info
                boolean[] promo = new boolean[12];
                promo[venda.getMes()-1] = venda.getTipo().equals("P");
                info.setPromo(promo);

                int[] quantidadeComprada = new int[12];
                quantidadeComprada[venda.getMes()-1] = venda.getQuant();
                info.setQuantidadeComprada(quantidadeComprada);

                int[] numVendas = new int[12];
                numVendas[venda.getMes()-1] = 1;
                info.setNumVendas(numVendas);

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


    public int[] totalVendasEClientesMes(int mes){
        int[] totalVendasEClientes = new int[2];
        Set<String> clientes = new HashSet<>();

        for(List<InfoFilial> l: this.produtos.values()){
            for(InfoFilial info: l){
                if(info.getQuantidadeComprada(mes) > 0) {
                    totalVendasEClientes[0] += info.getNumVendas(mes);
                    clientes.add(info.getCliente());
                }
            }
            totalVendasEClientes[1] = clientes.size();
        }
        return totalVendasEClientes;
    }

    public int[] vezesProdComprado(String codProd){
        List<InfoFilial> info = this.produtos.get(codProd);
        int[] vezesComprado = new int[12];

/*      //se numero de vezes comprado for a quantidade
        for(InfoFilial iF: info){
            for(int i = 0; i < 12; i++){
                vezesComprado[i] += iF.getQuantidadeComprada(i);
            }
        }
*/
        //se numero de vezes comprado for numero de vendas
        for(InfoFilial iF: info){
            for(int i = 0; i < 12; i++){
                vezesComprado[i] += iF.getNumVendas(i);
            }
        }

        return vezesComprado;
    }

    public int[] clientesProd(String codProd){
        List<InfoFilial> clientes = this.produtos.get(codProd);
        int[] total = new int[12];

        for(InfoFilial info: clientes){
            for(int i=0; i<12; i++){
                if(info.getNumVendas(i)>0){
                    total[i]++;
                    i = 12;
                }
            }
        }
        return total;
    }
}
