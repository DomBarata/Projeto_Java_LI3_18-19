import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import static java.lang.System.out;

public class TesteVendas {

/*    public static List<String> lerLinhasWithBuff(String fichtxt){
        List<String> linhas = new ArrayList<>();
        BufferedReader inFile = null; String linha = null;
        try{
            inFile = new BufferedReader(new FileReader(fichtxt));
            while((linha = inFile.readLine())!= null) linhas.add(linha);
        }
        catch (IOException exc) {out.println(exc);}
        return linhas;
    }
*/

    public static List<String> lerAllLines(String fichtxt){
        List<String> linhas = new ArrayList<String>();
        try{
            linhas = Files.readAllLines(Paths.get(fichtxt));
        }
        catch(IOException exc){ out.println(exc);}
        return linhas;
    }

    public static IVenda lerLinhaToVenda(String linha){
        String codProd, codCli, tipo;
        int mes = 0; int filial = 0; int quant = 0;
        double preco = 0.0;
        char[] codigo;

        String[] campos = linha.split(" ");     // linha.split("\\-"); caso sejam caracteres especiais
        codProd = campos[0];
        codCli = campos[4];
        tipo = campos[3];
        try{
            mes = Integer.parseInt(campos[5]);
            filial = Integer.parseInt(campos[6]);
            quant = Integer.parseInt(campos[2]);
            preco = Double.parseDouble(campos[1]);
        }catch(InputMismatchException exc) {return null;}

        if(codProd.length()!=6 ||
                codProd.charAt(0)<'A' && codProd.charAt(0)>'Z' ||
                codProd.charAt(1)<'A' && codProd.charAt(1)>'Z' ||
                codProd.charAt(2)<'0' && codProd.charAt(2)>'9' ||
                codProd.charAt(3)<'0' && codProd.charAt(3)>'9' ||
                codProd.charAt(4)<'0' && codProd.charAt(4)>'9' ||
                codProd.charAt(5)<'0' && codProd.charAt(5)>'9') return null;
        if(codCli.length()!=5 ||
                codCli.charAt(0)<'A' && codCli.charAt(0)>'Z' ||
                codCli.charAt(1)<'0' && codCli.charAt(1)>'9' ||
                codCli.charAt(2)<'0' && codCli.charAt(2)>'9' ||
                codCli.charAt(3)<'0' && codCli.charAt(3)>'9' ||
                codCli.charAt(4)<'0' && codCli.charAt(4)>'9') return null;
        if(tipo.charAt(0)!='P' && tipo.charAt(0)!='N') return null;
        if(mes<1 || mes>12) return null;
        if(filial<1 || filial>3) return null;
        if(quant<0) return null;
        if(preco<0.0) return null;

        return new Venda(codProd, codCli, tipo, mes, filial, quant, preco);
    }

    public static void main(String[] args){
        List<String> lines;
        Crono.start();
        lines = lerAllLines("Vendas_1M.txt");
        out.println(Crono.stop()); out.println(lines.size());
    }
}
