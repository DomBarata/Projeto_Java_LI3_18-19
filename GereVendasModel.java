import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.lang.System.out;

public class GereVendasModel implements InterfGereVendasModel, Serializable {
    private static String CLIENTES;
    private static String PRODUTOS;
    private static String VENDAS;
    private static int FILIAIS;
    private static int VENDASTOTAL = 0;
    private static int VENDASVALIDAS = 0;
    private static int VENDASGRATIS = 0;


    private InterfCatProds ctprods;
    private InterfCatClientes catcli;
    private InterfFaturacao fact;
    private List<InterfFilial> filial;

    public GereVendasModel(){
        List<String> files = lerAllLines("configs.txt");
        setCLIENTES(files.get(0));
        setPRODUTOS(files.get(1));
        setVENDAS(files.get(2));
        setFILIAIS(Integer.parseInt(files.get(3)));
        files.clear();

        ctprods = new CatProds();
        catcli = new CatClientes();
        fact = new Faturacao();
        filial = new ArrayList<>(FILIAIS);
        for (int i = 0; i < FILIAIS; i++)
            filial.add(i,new Filial());
    }

    public void createData() {
        List<String> files;
        int i = 0;
        /* TESTE
           out.println(CLIENTES);
           out.println(PRODUTOS);
           out.println(VENDAS);
           out.println(FILIAIS);
        */
        files = lerAllLines(CLIENTES);
        for(String s : files){
            catcli.adiciona(s);
            i++;
        }
        // out.println(i); //teste
        i=0;
        files = lerAllLines(PRODUTOS);
        for(String s : files){
            ctprods.adiciona(s);
            i++;
        }
        //out.println(i); //teste
        i=0;
        files = lerAllLines(VENDAS);
        VENDASTOTAL = files.size();
        for(String s : files) {
            InterfVenda venda = divideVenda(s);
            if(verificaVenda(venda)) {
                this.fact.adiciona(venda);
                InterfFilial f = filial.get(venda.getFilial()-1);
                f.adiciona(venda);
                fact.adiciona(venda);
                VENDASVALIDAS++;
                if(venda.getPreco() == 0.0)
                    VENDASGRATIS++;
            }
            //   out.println(i);
        }
        out.println(i); //teste
    }

    private boolean verificaVenda(InterfVenda venda) {
        return venda != null &&
                catcli.contains(venda.getCodCli()) &&
                ctprods.contains(venda.getCodPro()) &&
                (venda.getTipo().equals("P") || venda.getTipo().equals("N"));
    }

    private static InterfVenda divideVenda(String linha) {
        String codPro, codCli, tipo;
        int mes = 0, filial = 0, quant = 0;
        double preco = 0;
        String[] campos = linha.split(" ");
        codPro = campos[0];
        tipo = campos[3];
        codCli = campos[4];
        try{
            preco = Double.parseDouble(campos[1]);
            quant = Integer.parseInt(campos[2]);
            mes = Integer.parseInt(campos[5]);
            filial = Integer.parseInt(campos[6]);
        }
        catch (InputMismatchException exc){
            if(codPro.equals("JU1146"))
                out.println("Venda Inválida");
            return null;}

        if(mes < 1 || mes > 12) return null;
        if(filial < 1 || filial > FILIAIS) return null;
        return new Venda(codPro, codCli, tipo, mes, filial, quant, preco);
    }

    private static List<String> lerAllLines(String fichtxt) {
        List<String> linhas = new ArrayList<>();
        try{
            linhas = Files.readAllLines(Paths.get(fichtxt));
        }
        catch (IOException exc) {out.println(exc);}
        return linhas;
    }

    public static void setCLIENTES(String CLIENTES) {
        GereVendasModel.CLIENTES = CLIENTES;
    }

    public static void setPRODUTOS(String PRODUTOS) {
        GereVendasModel.PRODUTOS = PRODUTOS;
    }

    public static void setVENDAS(String VENDAS) {
        GereVendasModel.VENDAS = VENDAS;
    }

    public static void setFILIAIS(int FILIAIS) {
        GereVendasModel.FILIAIS = FILIAIS;
    }

    public Set<String> querie1(){
        return this.fact.getListaOrdenadaProdutosNuncaComprados(ctprods);
    }

    public int[] querie2(int mes) {
        Set<String> clientes = new TreeSet<>();
        int[] total = new int[2];

        for(int i = 0; i < FILIAIS; i++){
            AbstractMap.SimpleEntry<Integer, Set<String>> fil =
                    new AbstractMap.SimpleEntry<>(filial.get(i).totalVendasEClientesMes(mes));
            clientes.addAll(fil.getValue());
            total[0] += fil.getKey();
            total[1] = clientes.size();
        }
        return total;
    }

    public int[] querie2(int mes, int fil){
        int[] total = new int[2];

        AbstractMap.SimpleEntry<Integer, Set<String>> fili =
                new AbstractMap.SimpleEntry<>(filial.get(fil).totalVendasEClientesMes(mes));
        Set<String> clientes = new TreeSet<>(fili.getValue());
        total[0] += fili.getKey();
        total[1] = clientes.size();

        return total;
    }

    public List<Integer> Querie3TotalComprasCliente(String codCliente){
        List<Integer> compras = new ArrayList<>(12);
        int total;
        for(int mes=0; mes<12; mes++){
            total = 0;
            for(InterfFilial fil:filial){
                total += fil.totalCompras(codCliente, mes);
            }
            compras.add(mes,total);
        }
        return compras;
    }

    public List<Integer> Querie3TotalProds(String codCliente){
        List<Integer> prods = new ArrayList<>(12);
        Set<String> aux = new TreeSet<>();
        for(int mes = 0; mes < 12; mes++){
            aux.clear();
            for(InterfFilial fil: filial){
                aux.addAll(fil.getProdutos(codCliente, mes));
            }
            prods.add(mes,aux.size());
        }
        return prods;
    }

    public List<Double> Querie3TotalGasto(String codCliente){
        List<Double> gasto;
        List<Map<String,int[]>> prodsQuantMes = new ArrayList<>(12);
        for(int mes = 0; mes < 12; mes++) {
            Map<String,int[]> produtosEQuantidades = new HashMap<>();
            for (InterfFilial fil: filial){
                Map<String,int[]> aux = new HashMap<>(fil.prodsQuant(codCliente, mes));
                for(Map.Entry<String, int[]> entry : aux.entrySet()){
                    if(produtosEQuantidades.containsKey(entry.getKey())){
                        int[] total = produtosEQuantidades.get(entry.getKey());
                        int[] total2 = aux.get(entry.getKey());
                        total[0] += total2[0];
                        total[1] += total2[1];
                        produtosEQuantidades.put(entry.getKey(), total);
                    }else{
                        produtosEQuantidades.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            prodsQuantMes.add(mes,produtosEQuantidades);
        }
        gasto = this.fact.totalfaturado(prodsQuantMes);
        return gasto;
    }

    public List<Integer> querie4getQuantidade(String prod) {
        List<Integer> meses = new ArrayList<>(12);
        int total;
        for(int mes = 0; mes < 12; mes++) {
            total = 0;
            for(InterfFilial fil : filial){
                total += fil.getQuantidadeTotalProduto(prod, mes);
            }
            meses.add(mes,total);
        }
        return meses;
    }

    public List<Integer> querie4getClientes(String prod) {
        List<Integer> meses = new ArrayList<>(12);
        int cliNum = 0;
        for(int mes = 0; mes < 12; mes++){
            cliNum = 0;
            for (InterfFilial f : filial) {
                cliNum += f.getClientes(prod, mes).size();
            }
            meses.add(mes, cliNum);
        }

        return meses;
    }

    public List<Double> querie4getTotalFaturado(String prod) {
        List<Double> meses = new ArrayList<>(12);
        double total = 0;
        for(int mes = 0; mes < 12; mes++){
            total = 0;
            for(InterfFilial fil : this.filial){
                int[] quantidades = fil.getQuantidadePorTipoProduto(prod, mes);
                total += fact.getTotalFaturado(prod, quantidades, mes);
            }
            meses.add(mes, total);
        }
        return meses;
    }

    public Map<Integer, Set<String>> querie5(String cli){
        Map<Integer, Set<String>> prods = new TreeMap<>(Comparator.reverseOrder());
        Map<String, Integer> produtosEQuantidades = new TreeMap<>();


        for(InterfFilial f : this.filial){
            Map<String, int[]> aux = new TreeMap<>(f.prodsQuant(cli));
            for(Map.Entry<String, int[]> entry : aux.entrySet()){
                if(!produtosEQuantidades.containsKey(entry.getKey())){
                    produtosEQuantidades.put(entry.getKey(), entry.getValue()[0] + entry.getValue()[1]);
                }else{
                    int total = produtosEQuantidades.get(entry.getKey());
                    total += entry.getValue()[0] + entry.getValue()[1];
                    produtosEQuantidades.put(entry.getKey(),total);
                }
            }
            aux.clear();
        }
        for(Map.Entry<String, Integer> entry : produtosEQuantidades.entrySet()){
            if(!prods.containsKey(entry.getValue())){
                Set<String> produtos = new TreeSet<>();
                produtos.add(entry.getKey());
                prods.put(entry.getValue(), produtos);
            }else{
                Set<String> produtos = prods.get(entry.getValue());
                produtos.add(entry.getKey());
                prods.put(entry.getValue(), produtos);
            }
        }
        return prods;
    }

    public List<String> querie6ProdsMaisComprados(int x) {
        Map<String,Integer> prodsCliQtd = new TreeMap<>();
        Map<String,Integer> aux = new TreeMap<>();

        for(InterfFilial f : filial){
            aux.putAll(f.getProdMaisComprado(prodsCliQtd));
            prodsCliQtd.putAll(aux);
            aux.clear();
        }

        TreeMap<Integer, Set<String>> invertido = new TreeMap<>(Comparator.reverseOrder());
        for(Map.Entry<String, Integer> prod : prodsCliQtd.entrySet()){
            if(invertido.containsKey(prod.getValue())){
                Set<String> produtosMesmaQuant = invertido.get(prod.getValue());
                produtosMesmaQuant.add(prod.getKey());
                invertido.put(prod.getValue(), produtosMesmaQuant);
            }else{
                Set<String> produtosMesmaQuant = new TreeSet<>();
                produtosMesmaQuant.add(prod.getKey());
                invertido.put(prod.getValue(), produtosMesmaQuant);
            }
        }
        prodsCliQtd.clear();

        List<String> produtos = new ArrayList<>();
        int i = 0;
        while(i < x){
            Map.Entry<Integer, Set<String>> entry = invertido.pollFirstEntry();
            for(String s : entry.getValue()){
                produtos.add(i,s);
                i++;
            }
        }

        return produtos;
    }

    public List<Integer> querie6Clientes(List<String> prod) {
        Set<String> clientes = new HashSet<>();
        List<Integer> qts = new ArrayList<>();
        int i = 0;
        for(String s : prod){
            for(InterfFilial f: filial){
                clientes.addAll(f.getClientes(prod.get(i)));
            }
            qts.add(clientes.size());
            clientes.clear();
            i++;
        }
        return qts;
    }

    public List<List<String>> querie7(){
        List<List<String>> filiais = new ArrayList<>(FILIAIS);
        for(InterfFilial f : filial) {
            filiais.add(fact.getClientesMaisGastadores(f));
        }
        return filiais;
    }

    public Map<Integer, Set<String>> querie8(int x) {
        Map<Integer,Set<String>> clientes = new TreeMap<>(Collections.reverseOrder());
        Map<String,Set<String>> cliProds = new HashMap<>();

        for(InterfFilial fil : filial){
            cliProds = fil.clientesMaisProds(cliProds);
        }
        int i = 0;
        for(Map.Entry<String, Set<String>> entry : cliProds.entrySet()){
            if(i<x) {
                if (clientes.containsKey(entry.getValue().size())) {
                    Set<String> set = clientes.get(entry.getValue().size());
                    set.add(entry.getKey());
                    clientes.put(entry.getValue().size(), set);
                } else {
                    Set<String> set = new TreeSet<>();
                    set.add(entry.getKey());
                    clientes.put(entry.getValue().size(), set);
                }
                i++;
            }
        }
        return clientes;
    }

    public Map<Integer, Map<String, Double>> querie9(String codProd) {
        double[] precoN;
        double[] precoP;
        precoN = this.fact.getPrecoNormalProd(codProd);
        precoP = this.fact.getPrecoPromoProd(codProd);

        Map<Integer,Map<String,Double>> clis = new TreeMap<>(Collections.reverseOrder());

        if(precoN == null && precoP == null){
            return clis;
        }

        for(InterfFilial fil: filial){
            clis = fil.clisProdQ9(codProd,clis,precoN,precoP);
        }
        return clis;
    }

    public Map<Integer, List<Map<String, Double>>> querie10() {
        Map<String,Double> prodPreco = new TreeMap<>();
        List<Map<String,Double>> filiais = new ArrayList<>();
        Map<Integer,List<Map<String,Double>>> meses = new TreeMap<>();

        Map<String, List<double[]>> precos;
        precos = this.fact.getPrecoProds();

        int[] quant = new int[2];
        double faturado = 0.0;

        for(int mes=0; mes<12; mes++){
            for(int fil=0; fil<FILIAIS; fil++){
                for(Map.Entry<String, List<double[]>> entryPreco: precos.entrySet()) {
                    quant = filial.get(fil).getQuantidadePorTipoProduto(entryPreco.getKey(), mes);
                    faturado = quant[0]*entryPreco.getValue().get(0)[mes] + quant[1]*entryPreco.getValue().get(1)[mes];

                    if(prodPreco.containsKey(entryPreco.getKey())){
                        faturado += prodPreco.get(entryPreco.getKey());
                        prodPreco.put(entryPreco.getKey(),faturado);
                    }else {
                        prodPreco.put(entryPreco.getKey(), faturado);
                    }
                }
                filiais.add(fil,prodPreco);
            }
            meses.put(mes,filiais);
        }
        return meses;
    }

    public int getTotalCompras(int mes) {
        int total = 0;
        for(int fil=0; fil<FILIAIS; fil++) {
            total += filial.get(fil).totalCompras(mes);
        }
        return total;
    }

    public boolean existeCodCliente(String codCli) {
        return catcli.contains(codCli);
    }

    public boolean existeCodProd(String codProd) {
        return ctprods.contains(codProd);
    }

    public int getFILIAIS() {
        return FILIAIS;
    }

    public boolean isEmpty(){
        boolean flag = false;

        for(InterfFilial f : filial){
            if(f.isEmpty())
                flag = true;
        }

        return this.catcli.isEmpty() && this.ctprods.isEmpty()
                && this.fact.isEmpty() && flag;
    }

    @Override
    public String getVENDAS() {
        return VENDAS;
    }

    @Override
    public int getVENDASTOTAL() {
        return VENDASTOTAL;
    }

    @Override
    public int getVENDASVALIDAS() {
        return VENDASVALIDAS;
    }

    @Override
    public int getCatProdsSize() {
        return this.ctprods.size();
    }

    @Override
    public int getCatCliSize() {
        return this.catcli.size();
    }

    public int getClientesQueCompraram(){
        Set<String> clientes = new TreeSet<>();
        for(InterfFilial f : filial){
            clientes.addAll(f.getClientes());
        }
        return clientes.size();
    }

    public int getVENDASGRATIS(){
        return VENDASGRATIS;
    }

    @Override
    public Double getFaturacaoTotal() {
        return this.fact.getTotalFaturado();
    }
}