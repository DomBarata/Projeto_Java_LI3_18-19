import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import static java.lang.System.out;

public class Teste3 {

    private static CatProds ctprods = new CatProds();
    private static CatClientes catcli = new CatClientes();


    public static List<String> lerLinhasWithBuffer(String fichtxt){
        List<String> linhas = new ArrayList<>();
        BufferedReader inFile = null;
        String linha = null;

        try{
            inFile = new BufferedReader(new FileReader(fichtxt));
            while((linha = inFile.readLine()) != null)
                linhas.add(linha);
        }
        catch(IOException exc) {out.println(exc);}
        return linhas;
    }

    public static List<String> lerAllLines(String fichtxt) {
        List<String> linhas = new ArrayList<>();
        try{
            linhas = Files.readAllLines(Paths.get(fichtxt));
        }
        catch (IOException exc) {out.println(exc);}
        return linhas;
    }

    private static boolean verificaVenda(InterfVenda venda) {
        return venda != null &&
                catcli.contains(venda.getCodCli()) &&
                ctprods.contains(venda.getCodPro()) &&
                (venda.getTipo().equals("P") || venda.getTipo().equals("N"));
    }

    static void teste3(){
        List<String> lines;
        InterfVenda venda;
        int i;

        lines = lerAllLines("Clientes.txt");
        for(String s : lines){
            catcli.adiciona(s);
        }
        lines = lerAllLines("Produtos.txt");
        for(String s : lines){
            ctprods.adiciona(s);
        }

        out.println("Vendas_1M");
        Crono.start();
        lines = lerLinhasWithBuffer("Vendas_1M.txt");
        i=0;
        for(String s: lines){
            venda = Venda.divideVenda(s);
            if(verificaVenda(venda)){
                i++;
            }
        }
        out.println("Buffer  " + Crono.stop() +" "+ i + " vendas válidas");
        out.println(lines.size());
        Crono.start();
        lines = lerAllLines("Vendas_1M.txt");
        i=0;
        for(String s: lines){
            venda = Venda.divideVenda(s);
            if(verificaVenda(venda)){
                i++;
            }
        }
        out.println("All Lines  " + Crono.stop() +" "+ i + " vendas válidas");
        out.println(lines.size());
        out.println("Vendas_3M");
        Crono.start();
        lines = lerLinhasWithBuffer("Vendas_3M.txt");
        i=0;
        for(String s: lines){
            venda = Venda.divideVenda(s);
            if(verificaVenda(venda)){
                i++;
            }
        }
        out.println("Buffer  " + Crono.stop() +" "+ i + " vendas válidas");
        out.println(lines.size());
        Crono.start();
        lines = lerAllLines("Vendas_3M.txt");
        i=0;
        for(String s: lines){
            venda = Venda.divideVenda(s);
            if(verificaVenda(venda)){
                i++;
            }
        }
        out.println("All Lines  " + Crono.stop() +" "+ i + " vendas válidas");
        out.println(lines.size());
        out.println("Vendas_5M");
        Crono.start();
        lines = lerLinhasWithBuffer("Vendas_5M.txt");
        i=0;
        for(String s: lines){
            venda = Venda.divideVenda(s);
            if(verificaVenda(venda)){
                i++;
            }
        }
        out.println("Buffer  " + Crono.stop() +" "+ i + " vendas válidas");
        out.println(lines.size());
        Crono.start();
        lines = lerAllLines("Vendas_5M.txt");
        i=0;
        for(String s: lines){
            venda = Venda.divideVenda(s);
            if(verificaVenda(venda)){
                i++;
            }
        }
        out.println("All Lines  " + Crono.stop() +" "+ i + " vendas válidas");
        out.println(lines.size());
    }
}
