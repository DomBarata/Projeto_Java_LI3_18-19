import java.io.Serializable;
import java.util.*;

public class Filial implements InterfFilial, Serializable {
    private DadosFilial produtoClientes;
    private DadosFilial clienteProdutos;

    public Filial(){
        this.produtoClientes = new DadosFilial();
        this.clienteProdutos = new DadosFilial();
    }

    public void adiciona(InterfVenda venda) {
        produtoClientes.adiciona(venda.getCodPro(), venda.getCodCli(), venda.getMes(), venda.getQuant(), venda.isPromo());
        clienteProdutos.adiciona(venda.getCodCli(), venda.getCodPro(), venda.getMes(), venda.getQuant(), venda.isPromo());
    }

    public AbstractMap.SimpleEntry<Integer, Set<String>> totalVendasEClientesMes(int mes) {
        int total = 0;
        Set<String> clientes = new TreeSet<>();
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = produtoClientes.getDados();
        for(Map<String, Map<Integer, Set<InfoFilial>>> tipo : dados) {
            for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : tipo.entrySet()) {
                Set<InfoFilial> info = entry.getValue().get(mes - 1);
                if (info != null) {
                    for (InfoFilial i : info) {
                        clientes.add(i.getCodigo());
                        total += i.getNumVendas();
                    }
                }
            }
        }

        return new AbstractMap.SimpleEntry(total, clientes);
    }

    public int getQuantidadeTotalProduto(String prod, int mes) {
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = produtoClientes.getDados();
        int quantidade = 0;
        for(Map<String, Map<Integer, Set<InfoFilial>>> prods : dados) {
            if (prods.containsKey(prod)) {
                Set<InfoFilial> info = prods.get(prod).get(mes);
                if (info != null) {
                    for (InfoFilial in : info) {
                        quantidade += in.getQuantidadeComprada();
                    }
                }
            }
        }
        return quantidade;
    }

    public int[] getQuantidadePorTipoProduto(String prod, int mes) {
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = produtoClientes.getDados();
        int[] total = new int[2];
        for(Map<String, Map<Integer, Set<InfoFilial>>> prods : dados) {
            if (prods.containsKey(prod)) {
                Set<InfoFilial> info = prods.get(prod).get(mes);
                if (info != null) {
                    for (InfoFilial i : info) {
                        total[dados.indexOf(prods)] += i.getQuantidadeComprada();
                    }
                }
            }
        }
        return total;
    }

    public Set<String> getClientes(String prod, int mes){
        Set<String> clientes = new TreeSet<>();
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = produtoClientes.getDados();
        for(Map<String, Map<Integer, Set<InfoFilial>>> prods : dados) {
            if (prods.containsKey(prod)) {
                Set<InfoFilial> infos = prods.get(prod).get(mes);
                if (infos != null) {
                    for (InfoFilial info : infos) {
                        clientes.add(info.getCodigo());
                    }
                }
            }
        }
        return clientes;
    }

    public int totalCompras(String codCliente, int mes){
        int total = 0;
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = clienteProdutos.getDados();
        for(Map<String, Map<Integer, Set<InfoFilial>>> cli : dados) {
            Set<InfoFilial> produtos = cli.get(codCliente).get(mes);
            if(produtos != null){
                for(InfoFilial produto : produtos){
                    total += produto.getNumVendas();
                }
            }
        }
        return total;
    }


    public Set<String> getProdutos(String codCliente, int mes){
        Set<String> total = new TreeSet<>();
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = clienteProdutos.getDados();
        for(Map<String, Map<Integer, Set<InfoFilial>>> cli : dados) {
            Set<InfoFilial> produtos = cli.get(codCliente).get(mes);
            if(produtos != null){
                for(InfoFilial produto : produtos){
                    total.add(produto.getCodigo());
                }
            }
        }
        return total;
    }

    public Map<String,int[]> prodsQuant(String codCliente, int mes) {
        Map<String,int[]> prodsQuant = new HashMap<>();
        int[] quant = new int[2];
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = clienteProdutos.getDados();

        for(Map<String, Map<Integer, Set<InfoFilial>>> cli : dados) {
            Set<InfoFilial> produtos = cli.get(codCliente).get(mes);
            if(produtos != null){
                for(InfoFilial produto : produtos){
                    quant[dados.indexOf(cli)] = produto.getQuantidadeComprada();
                    if(!prodsQuant.containsKey(produto.getCodigo())){
                        prodsQuant.put(produto.getCodigo(), quant);
                    }else{
                        int[] soma = prodsQuant.get(produto.getCodigo());
                        soma[dados.indexOf(cli)] += produto.getQuantidadeComprada();
                        prodsQuant.put(produto.getCodigo(), soma);
                    }
                }
            }
        }
        return prodsQuant;
    }

    public Map<String,int[]> prodsQuant(String codCliente) {
        Map<String,int[]> prodsQuant = new HashMap<>();

        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = clienteProdutos.getDados();

        for(Map<String, Map<Integer, Set<InfoFilial>>> cli : dados) {
            Map<Integer, Set<InfoFilial>> produtos = cli.get(codCliente);
            for (Map.Entry<Integer, Set<InfoFilial>> meses : produtos.entrySet()) {
                for (InfoFilial produto : meses.getValue()) {
                    int[] quant = new int[2];
                    quant[dados.indexOf(cli)] = produto.getQuantidadeComprada();
                    if (!prodsQuant.containsKey(produto.getCodigo())) {
                        prodsQuant.put(produto.getCodigo(), quant);
                    }else {
                        int[] soma = prodsQuant.get(produto.getCodigo());
                        soma[dados.indexOf(cli)] += produto.getQuantidadeComprada();
                        prodsQuant.put(produto.getCodigo(), soma);
                    }
                }
            }
        }
        return prodsQuant;
    }

    public boolean isEmpty(){
        return this.produtoClientes.isEmpty() && this.clienteProdutos.isEmpty();
    }


    public Map<String, Integer> getProdMaisComprado(Map<String, Integer> prodsCliQtd) {
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = produtoClientes.getDados();

        for(Map<String, Map<Integer, Set<InfoFilial>>> tipoVenda : dados){
            for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> produto : tipoVenda.entrySet()){
                for(Map.Entry<Integer, Set<InfoFilial>> mes : produto.getValue().entrySet()){
                    for(InfoFilial cliente : mes.getValue()){
                        if(prodsCliQtd.containsKey(produto.getKey())){
                            int total = prodsCliQtd.get(produto.getKey());
                            total += cliente.getQuantidadeComprada();
                            prodsCliQtd.put(produto.getKey(), total);
                        }else{
                            prodsCliQtd.put(produto.getKey(), cliente.getQuantidadeComprada());
                        }
                    }
                }
            }
        }
        return prodsCliQtd;
    }

    public Set<String> getTodosOsClientesQueCompraram(){
        Set<String> clientes = new TreeSet<>();
        for(Map<String, Map<Integer, Set<InfoFilial>>> entry : this.clienteProdutos.getDados()){
            clientes.addAll(entry.keySet());
        }
        return clientes;
    }

    public Set<String> getTodosOsProdutosQueCompraram(){
        Set<String> produtos = new TreeSet<>();
        for(Map<String, Map<Integer, Set<InfoFilial>>> entry : this.produtoClientes.getDados()){
            produtos.addAll(entry.keySet());
        }
        return produtos;
    }

    public List<Map<String, Map<Integer, Set<InfoFilial>>>> getClientesInfo(){
        return this.clienteProdutos.getDados();
    }

    @Override
    public Map<String, Set<String>> clientesMaisProds(Map<String, Set<String>> cliProds) {
        for(Map<String, Map<Integer, Set<InfoFilial>>> dados : this.clienteProdutos.getDados()) {
            for(Map.Entry<String, Map<Integer, Set<InfoFilial>>> cliente : dados.entrySet()){
                for(Map.Entry<Integer, Set<InfoFilial>> mes : cliente.getValue().entrySet()){
                    for(InfoFilial produto : mes.getValue()) {
                        if (cliProds.containsKey(cliente.getKey())) {
                            Set<String> produtos = cliProds.get(cliente.getKey());
                            produtos.add(produto.getCodigo());
                            cliProds.put(cliente.getKey(), produtos);
                        } else {
                            Set<String> produtos = new TreeSet<>();
                            produtos.add(produto.getCodigo());
                            cliProds.put(cliente.getKey(), produtos);
                        }
                    }
                }
            }
        }
        return cliProds;
    }

    public Set<String> getClientes(String prod){
        Set<String> clientes = new TreeSet<>();
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = produtoClientes.getDados();
        for(Map<String, Map<Integer, Set<InfoFilial>>> prods : dados) {
            if (prods.containsKey(prod)) {
                Map<Integer, Set<InfoFilial>> meses = prods.get(prod);
                for(Map.Entry<Integer, Set<InfoFilial>> mes : meses.entrySet()) {
                    for (InfoFilial info : mes.getValue()) {
                        clientes.add(info.getCodigo());
                    }
                }
            }
        }
        return clientes;
    }

    public Map<Integer, Map<String, Double>> clisProdQ9(String codProd, Map<Integer, Map<String, Double>> clis, double[] precoN, double[] precoP) {
        List<Map<String, Map<Integer, Set<InfoFilial>>>> dados = this.produtoClientes.getDados();
        for(Map<String, Map<Integer, Set<InfoFilial>>> tipo : dados){
            double[] preco;
            if (dados.indexOf(tipo) == 0) {
                preco = precoN;
            }else {
                preco = precoP;
            }
            if(tipo.containsKey(codProd)){
                Map<Integer, Set<InfoFilial>> meses = tipo.get(codProd);
                for (Map.Entry<Integer, Set<InfoFilial>> entry : meses.entrySet()) {
                    for (InfoFilial info : entry.getValue()) {
                        int qnt = info.getQuantidadeComprada();
                        double gasto = 0.0;

                        for (Map.Entry<Integer, Map<String, Double>> entryQnt : clis.entrySet()) {
                            for (Map.Entry<String, Double> entryClis : entryQnt.getValue().entrySet()) {
                                if (entryClis.getKey().equals(info.getCodigo())) {
                                    Map<String, Double> map = clis.get(qnt);
                                    qnt += entryQnt.getKey();
                                    gasto = map.remove(entryClis.getKey());
                                    clis.put(entryQnt.getKey(), map);
                                }
                            }
                        }
                        if (clis.containsKey(qnt)) {
                            Map<String, Double> map = clis.get(qnt);
                            double totalGasto = gasto + (qnt * preco[entry.getKey()]);
                            map.put(info.getCodigo(), totalGasto);
                            clis.put(qnt, map);
                        } else {
                            Map<String, Double> map = new TreeMap<>();
                            double totalGasto = gasto + (qnt * preco[entry.getKey()]);
                            map.put(info.getCodigo(), totalGasto);
                            clis.put(qnt, map);
                        }
                    }
                }
            }
        }
        return clis;
    }

    @Override
    public Set<String> getClientes() {
        Set<String> clientes = new TreeSet<>();

        for(Map<String, Map<Integer, Set<InfoFilial>>> tipo : this.clienteProdutos.getDados()){
            clientes.addAll(tipo.keySet());
        }

        return clientes;
    }
}
