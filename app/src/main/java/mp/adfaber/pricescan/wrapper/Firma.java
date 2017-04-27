package mp.adfaber.pricescan.wrapper;

/**
 * Created by Dragos on 27.04.2017.
 */

public class Firma {
    public String id_produs;
    public String nume;
    public String image_path;
    public Firma(String id_produs, String nume, String image_path) {
        this.id_produs = id_produs;
        this.nume = nume;
        this.image_path = image_path;
    }
}
