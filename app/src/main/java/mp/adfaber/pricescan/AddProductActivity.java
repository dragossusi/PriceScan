package mp.adfaber.pricescan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProductActivity extends AppCompatActivity {
    EditText editCodBare;
    Spinner spinnerOras;
    Spinner spinnerMagazin;
    EditText editPret;
    Button btnAdauga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        editCodBare = (EditText) findViewById(R.id.editCod);
        editPret = (EditText) findViewById(R.id.editPret);
        spinnerOras = (Spinner) findViewById(R.id.spinner_oras);
        spinnerMagazin = (Spinner) findViewById(R.id.spinner_magazin);
        btnAdauga = (Button) findViewById(R.id.btn_add);
    }
}
