package mp.adfaber.pricescan.wrapper;

import java.util.List;

/**
 * Created by Dragos on 30.03.2017.
 */

public class DetaliiProdus {
    public boolean succes;
    public String name;
    public String image_path;
    public List<Magazin> results;

    public class Magazin {
        public String id_magazin;
        public String oras;
        public String firma;
        public String strada;
        public String image_path;
        public float pret;
    }
}
