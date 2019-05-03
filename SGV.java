import java.io.Serializable;
import java.util.List;

public class SGV implements ISGV, Serializable {

    ICatProds catProds;
    ICatCli catCli;
    IFatura fat;
    List<IFilial> filiais;


}
