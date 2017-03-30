package mp.adfaber.pricescan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {
    String cod;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        cod = getIntent().getStringExtra("cod");
        tv = (TextView)findViewById(R.id.textView);
        tv.setText(cod);
    }
}
