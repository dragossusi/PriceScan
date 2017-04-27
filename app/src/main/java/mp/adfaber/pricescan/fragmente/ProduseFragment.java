package mp.adfaber.pricescan.fragmente;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.adapters.ProduseAdapter;
import mp.adfaber.pricescan.wrapper.Categorie;
import mp.adfaber.pricescan.wrapper.Produs;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

/**
 * Created by Dragos on 24.04.2017.
 */

public class ProduseFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProduseAdapter adapter;
    ArrayAdapter adapterSpinner;
    Spinner spinner;
    List<Produs> produse;
    ProdusAPI api;
    String categorie = "";
    List<String> numecategorii = null;
    AlertDialog alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ProdusAPI();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        spinner = new Spinner(getActivity());
        builder.setView(spinner)
                .setTitle("Categorie")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categorie = spinner.getSelectedItem().toString();
                        new GetProduse().execute(null,null,null);
                    }
                })
                .setNegativeButton("Sterge", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        categorie = "";
                        new GetProduse().execute(null,null,null);
                    }
                });
        alertDialog = builder.create();
        new GetCategorii().execute(null,null,null);
        setHasOptionsMenu(true);
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

        new GetProduse().execute(null, null, null);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            if(numecategorii!=null)
                alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected class GetProduse extends AsyncTask<Void, Void, Void> {
        boolean succes;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if(categorie.equals(""))
                    produse = api.getProduse();
                else
                    produse = api.getProduse(categorie);
                succes = true;
            } catch (IOException e) {
                e.printStackTrace();
                succes = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (succes) {
                adapter = new ProduseAdapter(getActivity(), produse);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getActivity(), "Nu pot cauta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected class GetCategorii extends AsyncTask<Void, Void, Void> {
        boolean succes;
        List<Categorie> categories;

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                categories = api.getCategorii();
                succes = true;
            } catch (IOException e) {
                e.printStackTrace();
                succes = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (succes) {
                numecategorii = new ArrayList<>();
                for (Categorie categorie : categories)
                    numecategorii.add(categorie.nume);
                adapterSpinner = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,numecategorii);
                spinner.setAdapter(adapterSpinner);
            }
        }
    }
}
