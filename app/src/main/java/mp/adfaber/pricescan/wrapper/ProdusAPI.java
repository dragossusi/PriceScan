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
    private static final String APIHOST = "server";

    Gson gson;
    public ProdusAPI() {
        gson = new Gson();
    }

    public DetaliiProdus searchProduct(String barcode) throws IOException {
        return gson.fromJson(readUrl(APIHOST + barcode),DetaliiProdus.class);
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
}
