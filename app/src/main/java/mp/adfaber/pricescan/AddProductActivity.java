package mp.adfaber.pricescan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import mp.adfaber.pricescan.wrapper.ProdusAPI;

public class AddProductActivity extends AppCompatActivity {
    EditText editCodBare;
    Spinner spinnerOras;
    Spinner spinnerMagazin;
    Spinner spinnerStrada;
    EditText editPret;
    Button btnAdauga;
    ProdusAPI api;
    List<String> orase;
    List<String> firme;
    List<String> strazi;
    String [] details;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        editCodBare = (EditText) findViewById(R.id.editCod);
        editPret = (EditText) findViewById(R.id.editPret);

        spinnerOras = (Spinner) findViewById(R.id.spinner_oras);
        spinnerMagazin = (Spinner) findViewById(R.id.spinner_magazin);
        spinnerStrada = (Spinner) findViewById(R.id.spinner_strada);
        btnAdauga = (Button) findViewById(R.id.btn_add);
        linearLayout = (LinearLayout) findViewById(R.id.linearstrada);
        api = new ProdusAPI();

        details = new String[2];

        new GetOrase().execute(null,null,null);

        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new PostProduct(spinnerStrada.getVisibility())
                            .execute(new Produs(
                                    editCodBare.getText().toString(),
                                    spinnerOras.getSelectedItem().toString(),
                                    spinnerMagazin.getSelectedItem().toString(),
                                    spinnerStrada.getSelectedItem().toString(),
                                    editPret.getText().toString())
                            , null, null);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        spinnerOras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedView, int pos, long id) {
                System.out.println("a selectat ceva "+((TextView)selectedView).getText().toString());
                details[0]=((TextView)selectedView).getText().toString();
                new GetMagazine().execute(((TextView)selectedView).getText().toString(),
                        null,null);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerMagazin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("a selectat ceva "+((TextView)view).getText().toString());
                details[1]=((TextView)view).getText().toString();
                new GetStrazi().execute(null,null,null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    ///asyncuri
    protected class GetStrazi extends AsyncTask<Void,Void,Void> {
        boolean success;
        ProdusAPI.ResultList noi;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                noi = api.getStrazi(details[0],details[1]);
                success=true;
            }catch (Exception e) {
                System.err.println(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(success) {
                if(noi.succes){
                    strazi = noi.items;
                    spinnerStrada.setAdapter(new ArrayAdapter(AddProductActivity.this,android.R.layout.simple_spinner_dropdown_item,strazi));
                    linearLayout.setVisibility(View.VISIBLE);
                } else{
                    Toast.makeText(AddProductActivity.this,"Nu are strazi bo$$",Toast.LENGTH_SHORT).show();
                    linearLayout.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(AddProductActivity.this,"Are o eroare cand ia strazile",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected class GetMagazine extends AsyncTask<String,Void,Void> {
        boolean success=false;
        @Override
        protected Void doInBackground(String... oras) {
            try{
                firme = api.getFirme(oras[0]);
                success = true;
            } catch (Exception e) {
                System.err.println(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(success) {
                spinnerMagazin.setAdapter(new ArrayAdapter(AddProductActivity.this,android.R.layout.simple_spinner_dropdown_item,firme));
            } else {
                Toast.makeText(AddProductActivity.this,"Are o eroare cand ia firmele",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected class GetOrase extends AsyncTask<Void,Void,Void> {
        boolean success=false;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                orase = api.getOrase();
                success=true;
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(success)
                spinnerOras.setAdapter(new ArrayAdapter(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, orase));
            else
                Toast.makeText(AddProductActivity.this,"Nu merge, probabil nu s-a conectat la server",Toast.LENGTH_SHORT).show();
        }
    }

    //TODO
    protected class PostProduct extends AsyncTask<Produs,Void,Void> {
        boolean succes=false;
        boolean arestrada;
        ProdusAPI.Result result;

        public PostProduct(int vis) {
            if(vis==View.VISIBLE)
                this.arestrada = true;
            else
                this.arestrada = false;
        }

        @Override
        protected Void doInBackground(Produs... products) {
            try {
                System.out.println(products[0].strada);
                if(arestrada)
                    result = api.postProduct(products[0].oras,products[0].firma,products[0].strada,products[0].barcode,products[0].pret);
                else
                    result = api.postProduct(products[0].oras,products[0].firma,products[0].barcode,products[0].pret);
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
                Toast.makeText(AddProductActivity.this,"Nu s-a adaugat bo$$\n"+result.val,Toast.LENGTH_LONG).show();
            }
        }
    }


    protected class Produs {
        public Produs(String barcode, String oras, String firma, String strada, String pret) {
            this.barcode = barcode;
            this.firma = firma;
            this.oras = oras;
            this.strada = strada;
            this.pret = pret;
        }

        String barcode;
        String firma;
        String oras;
        String strada;
        String pret;
    }
}
