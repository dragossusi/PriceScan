package mp.adfaber.pricescan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import mp.adfaber.pricescan.adapters.ListaMagazineAdapter;
import mp.adfaber.pricescan.wrapper.DetaliiProdus;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

public class ListActivity extends AppCompatActivity {
    String cod;
    TextView tv;
    ListView listView;
    ProdusAPI api;
    DetaliiProdus detaliiProdus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        System.out.println("s-a creat");
        cod = getIntent().getStringExtra("cod");
        tv = (TextView)findViewById(R.id.textView);
        listView = (ListView)findViewById(R.id.list_magazine);
        tv.setText(cod);
        api = new ProdusAPI();
        new GetDetalii().execute(null,null,null);
    }
    protected class GetDetalii extends AsyncTask<Void,Void,Void> {
        boolean succes = false;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                detaliiProdus = api.searchProduct(cod);
                succes = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(succes) {
                listView.setAdapter(new ListaMagazineAdapter(ListActivity.this,detaliiProdus.results));
                System.out.println("detalii"+detaliiProdus.name);
            } else {
                Toast.makeText(ListActivity.this,"Nu merge bo$$",Toast.LENGTH_LONG);
            }
        }
    }
}
