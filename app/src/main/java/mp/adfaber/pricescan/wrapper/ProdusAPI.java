package mp.adfaber.pricescan.wrapper;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Dragos on 30.03.2017.
 */

public class ProdusAPI {
    //TODO modifica asta cand ai host
    private static final String APIHOST = "http://109.97.237.38:4567/";

    Gson gson;
    public ProdusAPI() {
        gson = new Gson();
    }

    public DetaliiProdus searchProduct(String barcode) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "search/" + barcode),DetaliiProdus.class);
    }

    public Result postProduct(String barcode, String oras, String magazin,float pret) throws IOException {
        return gson.fromJson(readUrl(APIHOST + "post/?cod=" +barcode
                + "&oras=" + oras + "&magazin=" + magazin + "&pret=" + pret),
                Result.class);
    }

    private String readUrl(String urlString) throws IOException {
        System.out.println("Requesting " + urlString);
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
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
    }
}
