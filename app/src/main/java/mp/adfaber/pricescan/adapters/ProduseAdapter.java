package mp.adfaber.pricescan.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import mp.adfaber.pricescan.ListActivity;
import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.wrapper.Produs;

/**
 * Created by Dragos on 26.04.2017.
 */

public class ProduseAdapter extends RecyclerView.Adapter<ProduseAdapter.ViewHolder> {
    private List<Produs> produse;
    private Activity activity;
    public ProduseAdapter(Activity activity,List<Produs> produse) {
        this.produse = produse;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO creeare cardview
        CardView view = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_produs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.produs = produse.get(position);
        final String cod = holder.produs.cod;
        if(holder.produs.image_path==null)
            holder.imageView.setVisibility(View.GONE);
        else {
            Ion.with(holder.imageView)
                    .placeholder(R.drawable.blank)
                    .error(R.drawable.blank)
                    .load("http://titumaiorescu.comli.com/produs/"+holder.produs.image_path);
        }
        holder.txtNume.setText(holder.produs.nume);
        holder.txtCategorie.setText(holder.produs.categorie);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ListActivity.class);
                intent.putExtra("cod",cod);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produse.size();
    }

    //viewholder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public Produs produs;
        public ImageView imageView;
        public TextView txtNume;
        public TextView txtCategorie;
        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
            imageView = (ImageView) v.findViewById(R.id.imagine_produs);
            txtNume = (TextView) v.findViewById(R.id.nume_text);
            txtCategorie = (TextView) v.findViewById(R.id.categorie_text);
            //cardView = v;
        }
    }

}
