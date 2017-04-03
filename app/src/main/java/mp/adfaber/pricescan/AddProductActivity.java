package mp.adfaber.pricescan;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import mp.adfaber.pricescan.wrapper.ProdusAPI;

public class AddProductActivity extends AppCompatActivity {
    EditText editCodBare;
    Spinner spinnerOras;
    Spinner spinnerMagazin;
    EditText editPret;
    Button btnAdauga;
    ProdusAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        editCodBare = (EditText) findViewById(R.id.editCod);
        editPret = (EditText) findViewById(R.id.editPret);
        spinnerOras = (Spinner) findViewById(R.id.spinner_oras);
        spinnerMagazin = (Spinner) findViewById(R.id.spinner_magazin);
        btnAdauga = (Button) findViewById(R.id.btn_add);
        api = new ProdusAPI();
    }
    protected class PostProduct extends AsyncTask<Produs,Void,Void> {
        boolean succes=false;
        ProdusAPI.Result result;
        @Override
        protected Void doInBackground(Produs... products) {
            try {
                result = api.postProduct(products[0].barcode,products[0].oras,products[0].magazin,products[0].pret);
                succes=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(succes && result.succes) {
                Toast.makeText(AddProductActivity.this,"S-a adaugat in baza de date",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddProductActivity.this,"Nu s-a adaugat bo$$\n"+result.description,Toast.LENGTH_LONG).show();
            }
        }
    }
    protected class Produs {
        String barcode;
        String magazin;
        String oras;
        float pret;
    }
}
