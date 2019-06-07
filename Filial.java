import java.util.*;

public class Filial implements InterfFilial {
    //key produto, value map com key mes e value informacoes necessarias
    private Map<String, Map<Integer, Set<InfoFilial>>> normal;
    private Map<String, Map<Integer, Set<InfoFilial>>> promo;
    private int i;

    public Filial() {
        this.normal = new HashMap<>();
        this.promo = new HashMap<>();
        this.i = 0;
    }

    public void adiciona(InterfVenda venda) {
        if (venda.getCodCli().equals("T3219")) {
            System.out.println(venda);
            i++;
        }
        if (venda.isPromo()) {
            if (this.promo.containsKey(venda.getCodPro())) { //mapa contem produto
                Map<Integer, Set<InfoFilial>> meses = this.promo.get(venda.getCodPro());
                Set<InfoFilial> clientes = meses.get(venda.getMes() - 1);
                if (clientes != null) {//mes já iniciado
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while (it.hasNext() && flag) {
                        InfoFilial a = it.next();
                        if (a.getCliente().equals(venda.getCodCli())) {
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(venda.getQuant());
                            clientes.add(a);
                            meses.put(venda.getMes() - 1, clientes);
                            this.promo.put(venda.getCodPro(), meses);
                        }
                    }
                    if (flag) {
                        InfoFilial infoNovo = new InfoFilial(venda.getCodCli(), venda.getQuant());
                        clientes.add(infoNovo);
                        meses.put(venda.getMes() - 1, clientes);
                        this.promo.put(venda.getCodPro(), meses);
                    }
                } else {
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(), venda.getQuant());
                    clientes.add(infoNovo);
                    meses.put(venda.getMes() - 1, clientes);
                    this.promo.put(venda.getCodPro(), meses);
                }
            } else {//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                Map<Integer, Set<InfoFilial>> meses = new HashMap<>(12);
                meses.put(venda.getMes() - 1, info);
                promo.put(venda.getCodPro(), meses);
            }
        } else {
            if (this.normal.containsKey(venda.getCodPro())) { //mapa contem produto
                Map<Integer, Set<InfoFilial>> meses = this.normal.get(venda.getCodPro());
                //verificar a existencia do cliente no Set
                Set<InfoFilial> clientes = meses.get(venda.getMes() - 1);
                if (clientes != null) {
                    Iterator<InfoFilial> it = clientes.iterator();
                    boolean flag = true;
                    while (it.hasNext() && flag) {
                        InfoFilial a = it.next();
                        if (a.getCliente().equals(venda.getCodCli())) {
                            flag = false;
                            a.incrementaNumVendas();
                            a.adicionaQuantidade(venda.getQuant());
                            clientes.add(a);
                            meses.put(venda.getMes() - 1, clientes);
                            this.normal.put(venda.getCodPro(), meses);
                        }
                    }
                    if (flag) {
                        InfoFilial infoNovo = new InfoFilial(venda.getCodCli(), venda.getQuant());
                        clientes.add(infoNovo);
                        meses.put(venda.getMes() - 1, clientes);
                        this.normal.put(venda.getCodPro(), meses);
                    }
                } else {
                    clientes = new HashSet<>();
                    InfoFilial infoNovo = new InfoFilial(venda.getCodCli(), venda.getQuant());
                    clientes.add(infoNovo);
                    meses.put(venda.getMes() - 1, clientes);
                    this.normal.put(venda.getCodPro(), meses);
                }
            } else {//O map não tem o produto
                Set<InfoFilial> info = new HashSet<>();
                info.add(new InfoFilial(venda.getCodCli(), venda.getQuant()));
                Map<Integer, Set<InfoFilial>> meses = new HashMap<>(12);
                meses.put(venda.getMes() - 1, info);
                normal.put(venda.getCodPro(), meses);
            }
        }
    }

    public int getI() {
        return this.i;
    }

    public Map<Integer, Set<String>> totalVendasEClientesMes(int mes) {
        int total = 0;
        Set<String> clientes = new TreeSet<>();
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : normal.entrySet()) {
            Set<InfoFilial> info = entry.getValue().get(mes - 1);
            if (info != null) {
                for (InfoFilial i : info) {
                    clientes.add(i.getCliente());
                    total += i.getNumVendas();
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            Set<InfoFilial> info = entry.getValue().get(mes - 1);
            if (info != null) {
                for (InfoFilial i : info) {
                    clientes.add(i.getCliente());
                    total += i.getNumVendas();
                }
            }
        }
        Map<Integer, Set<String>> ret = new HashMap<>();
        ret.put(total, clientes);
        return ret;
    }

    public int getQuantidadeTotalProduto(String prod, int mes) {
        int quantidade = 0;
        if (normal.containsKey(prod)) {
            Set<InfoFilial> info = normal.get(prod).get(mes);
            if (info != null) {
                for (InfoFilial in : info) {
                    quantidade += in.getQuantidadeComprada();
                }
            }
        }
        if (promo.containsKey(prod)) {
            Set<InfoFilial> info = promo.get(prod).get(mes);
            if (info != null) {
                for (InfoFilial in : info) {
                    quantidade += in.getQuantidadeComprada();
                }
            }
        }
        return quantidade;
    }

    public int[] getQuantidadePorTipoProduto(String prod, int mes) {
        int[] total = new int[2];
        if (normal.containsKey(prod)) {
            Set<InfoFilial> info = normal.get(prod).get(mes);
            if (info != null) {
                for (InfoFilial i : info) {
                    total[0] += i.getQuantidadeComprada();
                    // System.out.println("normal" + mes + "," + i.getQuantidadeComprada());
                }
            }
        }
        if (promo.containsKey(prod)) {
            Set<InfoFilial> info = promo.get(prod).get(mes);
            if (info != null) {
                for (InfoFilial in : info) {
                    total[1] += in.getQuantidadeComprada();
                    // System.out.println("promo " + mes + "," + in.getQuantidadeComprada());
                }
            }
        }
        return total;
    }

    public Set<String> getClientes(String prod, int mes) {
        Set<String> clientes = new TreeSet<>();
        if (normal.containsKey(prod)) {
            Set<InfoFilial> infos = normal.get(prod).get(mes);
            if (infos != null) {
                for (InfoFilial info : infos) {
                    clientes.add(info.getCliente());
                }
            }
        }
        if (promo.containsKey(prod)) {
            Set<InfoFilial> infos = promo.get(prod).get(mes);
            if (infos != null) {
                for (InfoFilial info : infos) {
                    clientes.add(info.getCliente());
                }
            }
        }
        return clientes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            sb.append("----------Produto: ").append(entry.getKey()).append("------------\n");
            Map<Integer, Set<InfoFilial>> meses = entry.getValue();
            for (Map.Entry<Integer, Set<InfoFilial>> infos : meses.entrySet()) {
                for (InfoFilial inf : infos.getValue()) {
                    sb.append(inf.getCliente()).append(",").append(inf.getQuantidadeComprada()).append("\n");
                }
            }
        }
        return sb.toString();
    }

    public int totalCompras(String codCliente, int mes) {
        int total = 0;
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : normal.entrySet()) {
            if (entry.getValue().containsKey(mes)) {
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total += infoFil.getNumVendas();
                    }
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            if (entry.getValue().containsKey(mes)) {
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total += infoFil.getNumVendas();
                    }
                }
            }
        }
        return total;
    }

    public Set<String> getProdutos(String codCliente, int mes) {
        Set<String> total = new TreeSet<>();
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : normal.entrySet()) {
            if (entry.getValue().containsKey(mes)) {
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total.add(entry.getKey());
                    }
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            if (entry.getValue().containsKey(mes)) {
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        total.add(entry.getKey());
                    }
                }
            }
        }
        return total;
    }

    public Map<String, int[]> prodsQuant(String codCliente, int mes) {
        Map<String, int[]> prodsQuant = new HashMap<>();
        int[] quant = new int[2];
        quant[0] = 0;
        quant[1] = 0;

        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : normal.entrySet()) {
            if (entry.getValue().containsKey(mes - 1)) {
                Set<InfoFilial> info = entry.getValue().get(mes - 1);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        if (!prodsQuant.containsKey(entry.getKey())) {
                            quant[0] += infoFil.getQuantidadeComprada();
                            prodsQuant.put(entry.getKey(), quant);
                        } else {
                            quant = prodsQuant.get(entry.getKey());
                            quant[0] += infoFil.getQuantidadeComprada();
                            prodsQuant.put(entry.getKey(), quant);
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            if (entry.getValue().containsKey(mes)) {
                Set<InfoFilial> info = entry.getValue().get(mes);
                for (InfoFilial infoFil : info) {
                    if (infoFil.getCliente().equals(codCliente)) {
                        if (!prodsQuant.containsKey(entry.getKey())) {
                            quant[1] += infoFil.getQuantidadeComprada();
                            prodsQuant.put(entry.getKey(), quant);
                        } else {
                            quant = prodsQuant.get(entry.getKey());
                            quant[1] += infoFil.getQuantidadeComprada();
                            prodsQuant.put(entry.getKey(), quant);
                        }
                    }
                }
            }
        }
        return prodsQuant;
    }

    public boolean isEmpty() {
        return this.normal.isEmpty() && this.promo.isEmpty();
    }

    public Map<Integer, Set<String>> getProdutosEQuantidades(Map<Integer, Set<String>> prods, String cli) {
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : normal.entrySet()) {
            for (Map.Entry<Integer, Set<InfoFilial>> listaMeses : entry.getValue().entrySet()) {
                for (InfoFilial cliente : listaMeses.getValue()) {
                    if (cliente.getCliente().equals(cli)) {
                        int qtd = cliente.getQuantidadeComprada();
                        //Mudar for para Iterator
                        for (Iterator<Map.Entry<Integer, Set<String>>> iterator = prods.entrySet().iterator(); iterator.hasNext(); ) {
                            Map.Entry<Integer, Set<String>> prodsEntry = iterator.next();
                            for (String produto : prodsEntry.getValue()) {
                                if (produto.equals(entry.getKey())) {
                                    qtd += prodsEntry.getKey();
                                    prodsEntry.getValue().remove(entry.getKey());
                                    prods.put(prodsEntry.getKey(), prodsEntry.getValue());
                                }
                            }
                        }
                        if (prods.containsKey(qtd)) {
                            Set<String> produtos = prods.get(qtd);
                            produtos.add(entry.getKey());
                            prods.put(qtd, produtos);
                        } else {
                            Set<String> produtos = new TreeSet<>();
                            produtos.add(entry.getKey());
                            prods.put(qtd, produtos);
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            for (Map.Entry<Integer, Set<InfoFilial>> listaMeses : entry.getValue().entrySet()) {
                for (InfoFilial cliente : listaMeses.getValue()) {
                    if (cliente.getCliente().equals(cli)) {
                        int qtd = cliente.getQuantidadeComprada();
                        //Mudar for para Iterator
                        for (Map.Entry<Integer, Set<String>> prodsEntry : prods.entrySet()) {
                            for (String produto : prodsEntry.getValue()) {
                                if (produto.equals(entry.getKey())) {
                                    qtd += prodsEntry.getKey();
                                    prodsEntry.getValue().remove(entry.getKey());
                                    prods.put(prodsEntry.getKey(), prodsEntry.getValue());
                                }
                            }
                        }
                        if (prods.containsKey(qtd)) {
                            Set<String> produtos = prods.get(qtd);
                            produtos.add(entry.getKey());
                            prods.put(qtd, produtos);
                        } else {
                            Set<String> produtos = new TreeSet<>();
                            produtos.add(entry.getKey());
                            prods.put(qtd, produtos);
                        }
                    }
                }
            }
        }
        return prods;
    }

    @Override
    public TreeMap<Integer, Set<String>> getProdMaisComprado(TreeMap<Integer, Set<String>> prods) {

        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : this.normal.entrySet()) {
            int quant = 0;
            for (Map.Entry<Integer, Set<InfoFilial>> infoEntry : entry.getValue().entrySet()) {
                for (InfoFilial info : infoEntry.getValue()) {
                    quant += info.getQuantidadeComprada();

                    for (Map.Entry<Integer, Set<String>> prodsEntry : prods.entrySet()) {
                        Iterator<String> it = prodsEntry.getValue().iterator();
                        while (it.hasNext()) {
                            String prod = it.next();
                            if (prod.equals(entry.getKey())) {
                                quant += prodsEntry.getKey();
                                it.remove();
                                prods.put(prodsEntry.getKey(), prodsEntry.getValue());
                            }
                        }
                    }
                    if (prods.containsKey(quant)) {
                        Set<String> set = prods.get(quant);
                        set.add(entry.getKey());
                        prods.put(quant, set);
                    } else {
                        Set<String> set = new HashSet<>();
                        set.add(entry.getKey());
                        prods.put(quant, set);
                    }
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : this.promo.entrySet()) {
            int quant = 0;
            for (Map.Entry<Integer, Set<InfoFilial>> infoEntry : entry.getValue().entrySet()) {
                for (InfoFilial info : infoEntry.getValue()) {
                    quant += info.getQuantidadeComprada();

                    for (Map.Entry<Integer, Set<String>> prodsEntry : prods.entrySet()) {
                        Iterator<String> it = prodsEntry.getValue().iterator();
                        while (it.hasNext()) {
                            String prod = it.next();
                            if (prod.equals(entry.getKey())) {
                                quant += prodsEntry.getKey();
                                it.remove();
                                prods.put(prodsEntry.getKey(), prodsEntry.getValue());
                            }
                        }
                    }
                    if (prods.containsKey(quant)) {
                        Set<String> set = prods.get(quant);
                        set.add(entry.getKey());
                        prods.put(quant, set);
                    } else {
                        Set<String> set = new HashSet<>();
                        set.add(entry.getKey());
                        prods.put(quant, set);
                    }
                }
            }
        }
        return prods;
    }

    @Override
    public Map<String, Set<String>> clientesMaisProds(Map<String, Set<String>> cliProds) {
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : normal.entrySet()) {
            for (Map.Entry<Integer, Set<InfoFilial>> meses : entry.getValue().entrySet()) {
                for (InfoFilial infoFilial : meses.getValue()) {
                    String codCli = infoFilial.getCliente();
                    if (cliProds.containsKey(codCli)) {
                        Set<String> setProds = cliProds.get(codCli);
                        setProds.add(entry.getKey());
                    } else {
                        Set<String> setProds = new HashSet<>();
                        setProds.add(entry.getKey());
                        cliProds.put(codCli, setProds);
                    }
                }
            }
        }
        for (Map.Entry<String, Map<Integer, Set<InfoFilial>>> entry : promo.entrySet()) {
            for (Map.Entry<Integer, Set<InfoFilial>> meses : entry.getValue().entrySet()) {
                for (InfoFilial infoFilial : meses.getValue()) {
                    String codCli = infoFilial.getCliente();
                    if (cliProds.containsKey(codCli)) {
                        Set<String> setProds = cliProds.get(codCli);
                        setProds.add(entry.getKey());
                    } else {
                        Set<String> setProds = new HashSet<>();
                        setProds.add(entry.getKey());
                        cliProds.put(codCli, setProds);
                    }
                }
            }
        }
        return cliProds;
    }

    @Override
    public Map<Integer, Map<String, Double>> clisProdQ9(String codProd, Map<Integer, Map<String, Double>> clis, double[] precoN, double[] precoP) {
        if (normal.containsKey(codProd)) {
            Map<Integer, Set<InfoFilial>> meses = normal.get(codProd);
            for (Iterator<Map.Entry<Integer, Set<InfoFilial>>> iterator = meses.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<Integer, Set<InfoFilial>> entry = iterator.next();
                for (Iterator<InfoFilial> iter = entry.getValue().iterator(); iter.hasNext(); ) {
                    InfoFilial info = iter.next();
                    int qnt = info.getQuantidadeComprada();
                    double gasto = 0.0;

                    for (Iterator<Map.Entry<Integer, Map<String, Double>>> it = clis.entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry<Integer, Map<String, Double>> entryQnt = it.next();
                        for (Iterator<Map.Entry<String, Double>> iterator1 = entryQnt.getValue().entrySet().iterator(); iterator1.hasNext(); ) {
                            Map.Entry<String, Double> entryClis = iterator1.next();
                            if (entryClis.getKey().equals(info.getCliente())) {
                                Map<String, Double> map = clis.get(qnt);
                                qnt += entryQnt.getKey();
                                gasto = map.remove(entryClis.getKey());
                                clis.put(entryQnt.getKey(),map);
                            }
                        }
                        if (clis.containsKey(qnt)) {
                            Map<String, Double> map = clis.get(qnt);
                            double totalGasto = gasto + (qnt * precoN[entry.getKey()]);
                            map.put(info.getCliente(), totalGasto);
                            clis.put(qnt, map);
                        } else {
                            Map<String, Double> map = new TreeMap<>();
                            double totalGasto = gasto + (qnt * precoN[entry.getKey()]);
                            map.put(info.getCliente(), totalGasto);
                            clis.put(qnt, map);
                        }
                    }
                }
            }
        }
        if (promo.containsKey(codProd)) {
            Map<Integer, Set<InfoFilial>> meses = promo.get(codProd);
            for (Iterator<Map.Entry<Integer, Set<InfoFilial>>> it = meses.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<Integer, Set<InfoFilial>> entry = it.next();
                for (InfoFilial info : entry.getValue()) {
                    int qnt = info.getQuantidadeComprada();
                    double gasto = 0.0;

                    for (Iterator<Map.Entry<Integer, Map<String, Double>>> iter = clis.entrySet().iterator(); iter.hasNext(); ) {
                        Map.Entry<Integer, Map<String, Double>> entryQnt = iter.next();
                        for (Iterator<Map.Entry<String, Double>> iterator = entryQnt.getValue().entrySet().iterator(); iterator.hasNext(); ) {
                            Map.Entry<String, Double> entryClis = iterator.next();
                            if (entryClis.getKey().equals(info.getCliente())) {
                                Map<String, Double> map = clis.get(qnt);
                                qnt += entryQnt.getKey();
                                gasto = map.remove(entryClis.getKey());
                                clis.put(entryQnt.getKey(),map);
                            }
                        }
                        if (clis.containsKey(qnt)) {
                            Map<String, Double> map = clis.get(qnt);
                            double totalGasto = gasto + (qnt * precoP[entry.getKey()]);
                            map.put(info.getCliente(), totalGasto);
                            clis.put(qnt, map);
                        } else {
                            Map<String, Double> map = new TreeMap<>();
                            double totalGasto = gasto + (qnt * precoP[entry.getKey()]);
                            map.put(info.getCliente(), totalGasto);
                            clis.put(qnt, map);
                        }
                    }
                }
            }
        }
        return clis;
    }
}
