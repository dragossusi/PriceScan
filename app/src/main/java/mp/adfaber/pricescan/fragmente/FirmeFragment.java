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

import java.io.IOException;
import java.util.List;

import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.adapters.FirmeAdapter;
import mp.adfaber.pricescan.wrapper.Firma;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

/**
 * Created by Dragos on 24.04.2017.
 */

public class FirmeFragment extends Fragment{
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProdusAPI api;
    List<Firma> firme;
    FirmeAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ProdusAPI();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.show();
        View rootView = inflater.inflate(R.layout.fragment_produse, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        new GetFirme().execute(null, null, null);

        return rootView;
    }
    protected class GetFirme extends AsyncTask<Void,Void,Void> {
        boolean succes;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                firme = api.getFirme();
                succes = true;
            }catch (IOException e) {
                e.printStackTrace();
                succes = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(succes) {
                //TODO
                adapter = new FirmeAdapter(getActivity(),firme);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
