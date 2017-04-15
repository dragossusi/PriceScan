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
    private static final String APIHOST = "http://79.116.141.207:4567/";
    private static final Type listtring = new TypeToken<List<String>>() {
    }.getType();
    Gson gson;

    public ProdusAPI() {
        gson = new Gson();
    }

    public DetaliiProdus searchProduct(String barcode) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "search/" + barcode), DetaliiProdus.class);
    }

    public List<String> getFirme(String oras) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "firme/" + oras),
                listtring);
    }

    public List<Shop> getShops(String oras) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "magazine/" + oras),
                listtring);
    }

    public ResultList getStrazi(String oras, String firma) throws IOException {
        System.out.println(APIHOST + "strazi/" + oras + "/" + firma);
        return gson.fromJson(readUrl(APIHOST + "strazi/" + oras + "/" + firma), ResultList.class);
    }

    public List<String> getOrase() throws IOException {
        return gson.fromJson(readUrl(APIHOST + "orase"),
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
        Result resFirma = getFirmaId(firma);
        if (resFirma.succes) {
            Result resMagazin = getMagazinId(resFirma.description, oras, strada);
            if (resMagazin.succes)
                return gson.fromJson(readUrl(APIHOST + "insert/stocare/" + resMagazin.description + "/" + barcode + "/" + pret),
                        Result.class);
            else {
                resMagazin.description = "nu exista magazin";
                return resMagazin;
            }
        } else {
            resFirma.description = "nu exista firma";
            return resFirma;
        }
    }

    public Result postProduct(String oras, String firma, String barcode, String pret) throws IOException {
        Result resFirma = getFirmaId(firma);
        if (resFirma.succes) {
            Result resMagazin = getMagazinId(resFirma.description, oras);
            if (resMagazin.succes)
                return gson.fromJson(readUrl(APIHOST + "insert/stocare/" + resMagazin.description + "/" + barcode + "/" + pret),
                        Result.class);
            else {
                resMagazin.description = "nu exista magazin";
                return resMagazin;
            }
        } else {
            resFirma.description = "nu exista firma";
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
        public String description;

        public Result(boolean succes) {
            this.succes = succes;
        }
    }

    public class ResultList {
        public boolean succes;
        public List<String> items;
    }
}
