package mp.adfaber.pricescan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.IOException;

import mp.adfaber.pricescan.adapters.ListaMagazineAdapter;
import mp.adfaber.pricescan.wrapper.DetaliiProdus;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

public class ListActivity extends AppCompatActivity {
    String cod;
    TextView tv;
    ImageView imageView;
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
        imageView = (ImageView)findViewById(R.id.iv_product);
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
                if(detaliiProdus.results.size()!=0)
                    listView.setAdapter(new ListaMagazineAdapter(ListActivity.this,detaliiProdus.results));
                tv.setText(detaliiProdus.name);
                if(detaliiProdus.image_path!=null) {
                    imageView.setVisibility(View.VISIBLE);
                    Ion.with(imageView)
                            .placeholder(R.drawable.blank)
                            .error(R.drawable.blank)
                            .load("http://titumaiorescu.comli.com/produs/"+detaliiProdus.image_path);
                }
                System.out.println("detalii"+detaliiProdus.name);
            } else {
                Toast.makeText(ListActivity.this,"Nu merge bo$$",Toast.LENGTH_LONG).show();
            }
        }
    }
}
