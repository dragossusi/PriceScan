package mp.adfaber.pricescan.fragmente;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.adapters.ProduseAdapter;
import mp.adfaber.pricescan.wrapper.Produs;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

/**
 * Created by Dragos on 24.04.2017.
 */

public class ProduseFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProduseAdapter adapter;
    List<Produs> produse;
    ProdusAPI api;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        api = new ProdusAPI();
        View rootView = inflater.inflate(R.layout.fragment_produse,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        new GetProduse().execute(null,null,null);

        return rootView;
    }

    protected class GetProduse extends AsyncTask<Void,Void,Void> {
        boolean succes;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                produse = api.getProduse();
                succes = true;
            } catch (IOException e) {
                e.printStackTrace();
                succes = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(succes) {
                adapter = new ProduseAdapter(getActivity(),produse);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(),"Nu pot cauta", Toast.LENGTH_SHORT);
            }
        }
    }
}
