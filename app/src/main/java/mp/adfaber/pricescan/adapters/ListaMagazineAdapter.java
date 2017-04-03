package mp.adfaber.pricescan.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.util.List;

import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.wrapper.DetaliiProdus;

/**
 * Created by Dragos on 30.03.2017.
 */

public class ListaMagazineAdapter extends ArrayAdapter<DetaliiProdus.Magazin> {
    float main;
    DecimalFormat df;
    public ListaMagazineAdapter(Context context, List<DetaliiProdus.Magazin> list) {
        super(context, 0, list);
        main = list.get(0).pret;
        df = new DecimalFormat("#.00");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_magazin, parent, false);
        }
        TextView tv_nume = (TextView) convertView.findViewById(R.id.tv_firma);
        TextView tv_pret = (TextView) convertView.findViewById(R.id.tv_pret);
        TextView tv_diferenta = (TextView) convertView.findViewById(R.id.tv_diferenta);
        ImageView img = (ImageView) convertView.findViewById(R.id.img_magazin);

        tv_nume.setText(getItem(position).magazin);
        tv_pret.setText(df.format(getItem(position).pret));

        Ion.with(img)
                .placeholder(R.drawable.blank)
                .error(R.drawable.blank)
                .load("http://titumaiorescu.comli.com/");

        if(position!=0)
            tv_diferenta.setText(df.format(getItem(position).pret-main));
        else
            tv_diferenta.setVisibility(View.GONE);
        return convertView;
    }
}
