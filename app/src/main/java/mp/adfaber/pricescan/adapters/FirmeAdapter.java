package mp.adfaber.pricescan.adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.wrapper.Firma;

/**
 * Created by Dragos on 27.04.2017.
 */

public class FirmeAdapter extends RecyclerView.Adapter<FirmeAdapter.ViewHolder>{
    private Activity activity;
    private List<Firma> firme;

    public FirmeAdapter(Activity activity, List<Firma> firme) {
        this.activity = activity;
        this.firme = firme;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_firma, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.firma = firme.get(position);
        if(holder.firma.image_path==null)
            holder.imageView.setVisibility(View.GONE);
        else {
            Ion.with(holder.imageView)
                    .placeholder(R.drawable.blank)
                    .error(R.drawable.blank)
                    .load("http://titumaiorescu.comli.com/magazin/"+holder.firma.image_path);
        }
        holder.txtNume.setText(holder.firma.nume);
    }

    @Override
    public int getItemCount() {
        return firme.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public Firma firma;
        public ImageView imageView;
        public TextView txtNume;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            imageView = (ImageView) v.findViewById(R.id.imagine_firma);
            txtNume = (TextView) v.findViewById(R.id.nume_text);
            //cardView = v;
        }
    }
}
