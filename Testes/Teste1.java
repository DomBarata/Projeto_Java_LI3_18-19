import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import static java.lang.System.out;

public class Teste1
{
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

    public static InterfVenda lerLinhaToVenda(String linha) {
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
        catch (InputMismatchException exc){return null;}

        if(mes < 1 || mes > 12) return null;
        if(filial < 1 || filial > 3) return null;

        return new Venda(codPro, codCli, tipo, mes, filial, quant, preco);
    }

    public static void teste1(){
        List<String> lines;
        out.println("Vendas_1M");
        Crono.start();
        lines = lerLinhasWithBuffer("Vendas_1M.txt");
        out.println("Buffer  " + Crono.stop());
        out.println(lines.size());
        Crono.start();
        lines = lerAllLines("Vendas_1M.txt");
        out.println("All Lines  " + Crono.stop());
        out.println(lines.size());
        Crono.start();
        out.println("Vendas_3M");
        lines = lerLinhasWithBuffer("Vendas_3M.txt");
        out.println("Buffer  " + Crono.stop());
        out.println(lines.size());
        Crono.start();
        lines = lerAllLines("Vendas_3M.txt");
        out.println("All Lines  " + Crono.stop());
        out.println(lines.size());
        out.println("Vendas_5M");
        Crono.start();
        lines = lerLinhasWithBuffer("Vendas_5M.txt");
        out.println("Buffer  " + Crono.stop());
        out.println(lines.size());
        Crono.start();
        lines = lerAllLines("Vendas_5M.txt");
        out.println("All Lines  " + Crono.stop());
        out.println(lines.size());
    }
}
