package mp.adfaber.pricescan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import mp.adfaber.pricescan.wrapper.DetaliiProdus;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

public class ListActivity extends AppCompatActivity {
    String cod;
    TextView tv;
    ProdusAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        cod = getIntent().getStringExtra("cod");
        tv = (TextView)findViewById(R.id.textView);
        tv.setText(cod);
        api = new ProdusAPI();
        try {
            DetaliiProdus detaliiProdus = api.searchProduct(cod);
        } catch (IOException e) {
            Toast.makeText(this,"cica nu merge rip",Toast.LENGTH_LONG);
        }
    }
}
