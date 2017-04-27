package mp.adfaber.pricescan.wrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

/**
 * Created by Dragos on 30.03.2017.
 */

public class ProdusAPI {
    //TODO modifica asta cand ai host
    private static final String APIHOST = "http://teamp.go.ro:4567/api/";
    private static final Type listtring = new TypeToken<List<String>>(){}.getType();
    private static final Type listprodus = new TypeToken<List<Produs>>(){}.getType();
    private static final Type listmagazin = new TypeToken<List<Shop>>(){}.getType();
    private static final Type listfirma = new TypeToken<List<Firma>>(){}.getType();
    private static final Type listcateg = new TypeToken<List<Categorie>>(){}.getType();
    Gson gson;

    public ProdusAPI() {
        gson = new Gson();
    }

    public DetaliiProdus searchProduct(String barcode) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "search/" + barcode), DetaliiProdus.class);
    }

    public List<Categorie> getCategorii() throws IOException {
        return gson.fromJson(readUrl(APIHOST + "categorii"),
                listcateg);
    }

    public List<Firma> getFirme(String oras) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "firme/" + oras),
                listfirma);
    }

    public List<Firma> getFirme() throws IOException {
        return gson.fromJson(readUrl(APIHOST + "firme"),
                listfirma);
    }

    public List<Shop> getShops(String oras) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "magazine/" + oras),
                listmagazin);
    }

    public List<Shop> getShops() throws IOException {
        return gson.fromJson(readUrl(APIHOST + "magazine"),
                listmagazin);
    }

    public List<Produs> getProduse() throws IOException {
        return gson.fromJson(readUrl(APIHOST + "produse"),
                listprodus);
    }

    public List<Produs> getProduse(String categorie) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "produse/"+categorie),
                listprodus);
    }

    public ResultList getStrazi(String oras, String firma) throws IOException {
        System.out.println(APIHOST + "strazi/" + oras + "/" + firma);
        return gson.fromJson(readUrl(APIHOST + "strazi/" + oras + "/" + firma), ResultList.class);
    }

    public List<String> getOrase() throws IOException {
        return gson.fromJson(readUrl(APIHOST + "orase"),
                listtring);
    }

    public List<String> getOrase(String firma) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "orase/" + firma),
                listtring);
    }

    public Result getFirmaId(String nume) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "id/firma/" + nume), Result.class);
    }

    public Result getMagazinId(String idFirma, String oras, String strada) throws IOException {
        return gson.fromJson(readUrl(String.format(APIHOST + "id/magazin/%s/%s/%s", idFirma, oras, strada)), Result.class);
    }

    public Result getMagazinId(String idFirma, String oras) throws IOException {
        return gson.fromJson(readUrl(String.format(APIHOST + "id/magazin/%s/%s", idFirma, oras)), Result.class);
    }

    public Result postProduct(String oras, String firma, String strada, String barcode, String pret) throws IOException {
        System.out.println(pret);
        Result resFirma = getFirmaId(firma);
        if (resFirma.succes) {
            Result resMagazin = getMagazinId(resFirma.val, oras, strada);
            if (resMagazin.succes)
                return gson.fromJson(readUrl(APIHOST + "insert/stocare/" + resMagazin.val + "/" + barcode + "/" + pret),
                        Result.class);
            else {
                resMagazin.val = "nu exista magazin";
                return resMagazin;
            }
        } else {
            resFirma.val = "nu exista firma";
            return resFirma;
        }
    }

    public Result postProduct(String oras, String firma, String barcode, String pret) throws IOException {
        System.out.println(pret);
        Result resFirma = getFirmaId(firma);
        if (resFirma.succes) {
            Result resMagazin = getMagazinId(resFirma.val, oras);
            if (resMagazin.succes)
                return gson.fromJson(readUrl(APIHOST + "insert/stocare/" + resMagazin.val + "/" + barcode + "/" + pret),
                        Result.class);
            else {
                resMagazin.val = "nu exista magazin";
                return resMagazin;
            }
        } else {
            resFirma.val = "nu exista firma";
            return resFirma;
        }
    }

    ///utile
    private static String readUrl(String urlString) throws IOException {
        System.out.println("Requesting " + urlString.replace(" ", "%20"));
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString.replace(" ", "%20"));
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public class Result {
        public boolean succes;
        public String val;

        public Result(boolean succes) {
            this.succes = succes;
        }
    }

    public class ResultList {
        public boolean succes;
        public List<String> items;
    }
}
