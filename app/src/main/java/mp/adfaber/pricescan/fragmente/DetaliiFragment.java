package mp.adfaber.pricescan.fragmente;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import mp.adfaber.pricescan.R;
import mp.adfaber.pricescan.wrapper.ProdusAPI;

/**
 * Created by Dragos on 24.04.2017.
 */

public class DetaliiFragment extends Fragment {
    AppCompatImageView imageView;
    GetStatus task;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalii, container, false);
        imageView = (AppCompatImageView) rootView.findViewById(R.id.isUp);
        task = new GetStatus();
        task.execute(null,null,null);
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        task.cancel(true);
    }

    protected class GetStatus extends AsyncTask<Void,Void,Void> {
        boolean succes;
        @Override
        protected void onPreExecute() {
            imageView.setImageResource(R.drawable.ic_cloud_download_black_24dp);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            succes = isAvailable();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(succes)
                imageView.setImageResource(R.drawable.ic_cloud_done);
            else
                imageView.setImageResource(R.drawable.ic_cloud_off);
        }

        boolean isAvailable(){
            try {
                URL url = new URL(ProdusAPI.APIHOST.replace(" ", "%20"));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10*1000);
                urlConnection.connect();
                urlConnection.disconnect();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
