import javax.print.attribute.DocAttributeSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

import static java.lang.System.out;
import static java.lang.System.in;

public class GereVendasView implements InterfGereVendasView, Serializable {

    private static final int PORPAGINA = 10;
    private static final int CODIFICADOR = 9998999;
    private static final int PORMES = 1;

    public void printPaginacaoMes(String str, List<Integer> compras, List<Integer> produtos, List<Double> gasto){
        int tamanho = 12;
        int index = 0;
        while(index < tamanho){
            int counter = 0;
            while (counter < PORMES && index < tamanho){
                out.println("---------Mês "+(index+1)+ "---------");
                out.println("Total de compras: "+compras.get(index));
                out.println("Total de " + str + " distintos comprados : "+produtos.get(index));
                out.println("Total de dinheiro: "+ gasto.get(index));
                index++;
                counter++;
            }

            int op = gestorPaginacao(index, tamanho, PORMES);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORMES);
                }
            }else{ // int
                index = (op-1) * PORMES;
            }
        }
    }

    public void printPaginacao(int x, Map<Integer, Set<String>> clientes) {
        List<Map.Entry<Integer, Set<String>>> toPrint = new ArrayList<>(clientes.entrySet());

        int index = 0;
        int aux = 0;
        while(aux < x){
            int counter = 0;
            while (counter < PORPAGINA && aux < x){
                for(String s : toPrint.get(index).getValue()){
                    out.println(s + ": " + toPrint.get(index).getKey());
                    counter++;
                    aux++;
                }
                index++;
            }

            int op = gestorPaginacao(aux, x, PORPAGINA);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORPAGINA);
                }
            }else{ // int
                index = (op-1) * PORPAGINA;
            }
        }
    }

    public void printPaginacao(Map<Integer, Set<String>> produtos) {
        List<Map.Entry<Integer, Set<String>>> toPrint = new ArrayList<>(produtos.entrySet());

        int index = 0;
        int tamanho = toPrint.size();
        while(index < tamanho){
            int counter = 0;
            while (counter < PORPAGINA && index < tamanho){
                for(String s : toPrint.get(index).getValue()){
                    out.println(s + ": " + toPrint.get(index).getKey());
                    counter++;
                }
                index++;
            }

            int op = gestorPaginacao(index, tamanho, PORPAGINA);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORPAGINA);
                }
            }else{ // int
                index = (op-1) * PORPAGINA;
            }
        }
    }

    public void printPaginacao(List<List<String>> querie7){
        int tamanho = querie7.size();
        int index = 0;
        while(index < tamanho){
            out.println("---------FILIAL " + (index+1) + "---------");
            int counter = 0;
            while (counter < PORMES && index < tamanho){
                for(String s : querie7.get(index)){
                    out.println(s);
                }
                counter++;
                index++;
            }

            int op = gestorPaginacao(index, tamanho, PORMES);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORMES);
                }
            }else{ // int
                index = (op-1) * PORMES;
            }
        }
    }

    public void printPaginacao(List<String> prods, List<Integer> prodsEClientes, int x){
        int index = 0;
        while(index < x){
            int counter = 0;
            while (counter < PORPAGINA && index < x){
                out.println((index+1) + " - " + prods.get(index) + " com " + prodsEClientes.get(index) + " clientes diferentes a comprar");
                index++;
                counter++;
            }

            int op = gestorPaginacao(index, x, PORPAGINA);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORPAGINA);
                }
            }else{ // int
                index = (op-1) * PORPAGINA;
            }
        }
    }

    public void printPaginacao(Map<Integer, Map<String, Double>> clis, int x){
        List<Map.Entry<Integer, Map<String, Double>>> toPrint = new ArrayList<>(clis.entrySet());
        int index = 0;
        int aux = 0;
        while(aux < x){
            int counter = 0;
            while (counter < PORPAGINA && aux < x){
                for(Map.Entry<String, Double> entry : toPrint.get(index).getValue().entrySet()){
                    out.println(entry.getKey() + ": " + entry.getValue());
                    counter++;
                    aux++;
                }
                index++;
            }

            int op = gestorPaginacao(index, x, PORPAGINA);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORPAGINA);
                }
            }else{ // int
                index = (op-1) * PORPAGINA;
            }
        }
    }

    public void printQuerie10(Map<Integer, List<Map<String, Double>>> meses) {
        int mes;
        do {
            out.println("Escolha um mês para apresentar resultados ou insira um mês inexistente para sair");
            mes = lerInt();
            if(mes > 0 && mes < 13) {
                out.println("Escolha uma filial");
                int filial = lerInt();

                Map<String, Double> prods = meses.get(mes - 1).get(filial - 1);
                List<Map.Entry<String, Double>> toPrint = new ArrayList<>(prods.entrySet());
                int tamanho = toPrint.size();
                int index = 0;
                out.println("---------FILIAL " + filial + " --- Mês " + mes + "---------");
                while (index < tamanho) {
                    int counter = 0;
                    while (counter < PORPAGINA && index < tamanho) {
                        out.println(toPrint.get(index).getKey() + ": " + toPrint.get(index).getValue());
                        counter++;
                        index++;
                    }

                    int op = gestorPaginacao(index, tamanho, PORPAGINA);
                    if (op - CODIFICADOR > 0) { //char
                        if (op - CODIFICADOR == 98) {
                            index -= (2 * PORPAGINA);
                        }
                    } else { // int
                        index = (op - 1) * PORPAGINA;
                    }
                }
            }
        }while(mes > 0 && mes < 13);
    }

    public void printTotalComprasMes(int[] total) {
        out.println("Total de compras efetuadas em cada mês");
        for(int mes=0; mes<12; mes++){
            out.println("Mês "+(mes+1)+": "+total[mes]);
        }
        waiting();
    }

    public void printPaginacao(Set<String> set){
        List<String> toPrint = new ArrayList<>(set);
        int tamanho = toPrint.size();
        int index = 0;
        while(index < tamanho){
            int counter = 0;
            while (counter < PORPAGINA && index < tamanho){
                out.println(toPrint.get(index));
                index++;
                counter++;
            }

            int op = gestorPaginacao(index, tamanho, PORPAGINA);
            if(op-CODIFICADOR > 0){ //char
                if(op-CODIFICADOR == 98){
                    index -= (2*PORPAGINA);
                }
            }else{ // int
                index = (op-1) * PORPAGINA;
            }
        }
    }

    private static String lerString() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        String txt = "";
        while(!ok) {
            try {
                txt = input.nextLine();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Texto Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return txt;
    }

    private static int lerInt() {
        Scanner input = new Scanner(in);
        boolean ok = false;
        int i = 0;
        while(!ok) {
            try {
                i = input.nextInt();
                ok = true;
            }
            catch(InputMismatchException e)
            { out.println("Inteiro Invalido");
                out.print("Novo valor: ");
                input.nextLine();
            }
        }
        //input.close();
        return i;
    }

    public int menu(){
        out.println();
        out.println("----------------------------------- MENU -----------------------------------");
        out.println();
        out.println("1 - Consultar lista de códigos de produtos nunca comprados e o seu respectivo total");
        out.println("2 - Consultar número total de vendas realizadas num determinado mês");
        out.println("3 - Consultar, para cada mês, as informações relativas a um cliente");
        out.println("4 - Consultar, para cada mês, as informações relativas a um produto");
        out.println("5 - Consultar produtos mais comprados e respetivas quantidades, de um determinado cliente");
        out.println("6 - Consultar conjunto de produtos mais vendidos ao longo do ano");
        out.println("7 - Consultar os 3 maiores compradores de cada filial");
        out.println("8 - Consultar os clientes que compraram mais produtos diferentes");
        out.println("9 - Consultar os clientes que mais compraram e o respeivo valor gasto");
        out.println("10 - Consultar, para cada mês e filial, a faturação com cada produto");
        out.println("11 - Consultas estatísticas");
        out.println("0 - Sair");

        return lerInt();
    }

    public int submenuQuerie2(){
        out.println("Como quer a apresentação dos resultados?");
        out.println("1- Resultados globais");
        out.println("2- Resultados filial a filial");

        return lerInt();
    }

    public int getMes(){
        out.println("Mes: ");

        return lerInt();
    }

    public void printNumMesInvalido(){
        out.println("Numero de mês inválido!");
        waiting();
    }

    public void printQuerie2(int[] total) {
        out.println("Número total de vendas: " +total[0]+ "" +
                "\nNúmero total de clientes: "+total[1]+"\n");
        waiting();
    }

    public void printQuerie2(int[] total, int fil) {
        out.println("---------FILIAL " + (fil+1) + "---------");
        out.println("Número de vendas: " + total[0]);
        out.println("Número de clientes: " + total[1]);
    }

    public String getCodCliente() {
        out.println("Insira o código do cliente");

        return lerString();
    }

    public String getCodProduto() {
        out.println("Insira o código do produto");

        return lerString();
    }

    public int getX() {
        out.println("Insira o número de resultados a apresentar: ");
        return lerInt();
    }

    public void printInvalido(String cod) {
        out.println("O código " + cod + " não existe");
        waiting();
    }

    public void printOpInvalida(){
        out.println("Opção inválida!");
    }

    private int gestorPaginacao(int index, int tamanho, int quantidadePagina){
        int pag;
        if(index%quantidadePagina != 0){
            pag = (index/quantidadePagina)+1;
        }else{
            pag = index/quantidadePagina;
        }
        int paginas;
        if(tamanho%quantidadePagina != 0){
            paginas = tamanho / quantidadePagina + 1;
        }else{
            paginas = tamanho / quantidadePagina;
        }

        out.println("Página " + pag + " de " + paginas);
        out.println("Pressione ENTER para página seguinte, 'b' para a página anterior, ou insira o número da página para a qual pretende navegar");

        String ret = lerString();
        try {
            return Integer.parseInt(ret);
        }catch (NumberFormatException exc){
            try{
                return ret.charAt(0) + CODIFICADOR;
            }
            catch(StringIndexOutOfBoundsException enter){
                return 10 + CODIFICADOR;
            }
        }
    }

    public void waiting(){
        try {
            in.read();

        }catch (IOException io){out.println(io);}
    }

    public void tempoDeExecucao(double tempoDecorrido){
        out.println("A querie demorou " + tempoDecorrido + " a executar");
    }
}