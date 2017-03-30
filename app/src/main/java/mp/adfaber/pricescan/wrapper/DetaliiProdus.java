package mp.adfaber.pricescan.wrapper;

import java.util.List;

/**
 * Created by Dragos on 30.03.2017.
 */

public class DetaliiProdus {
    String name;
    String image_path;
    List<Magazin> results;

    public class Magazin {
        float pret;
        String magazin;
        String image_path;
    }
}
