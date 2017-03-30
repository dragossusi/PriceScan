package mp.adfaber.pricescan.wrapper;

import java.util.List;

/**
 * Created by Dragos on 30.03.2017.
 */

public class DetaliiProdus {
    public String name;
    public String image_path;
    public List<Magazin> results;

    public class Magazin {
        public float pret;
        public String magazin;
        public String image_path;
    }
}
