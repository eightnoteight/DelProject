package com.eightnoteight.delproject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.eightnoteight.delproject.backend.snipapi.Snipapi;
import com.eightnoteight.delproject.backend.snipapi.model.Snip;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class CreateSnipAsync extends AsyncTask<Snip, Void, Snip> {
    private Context context;
    private Snipapi snipapi = null;

    public CreateSnipAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Snip doInBackground(Snip... snips) {
        if (snipapi == null) {
            Snipapi.Builder builder = new Snipapi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://delproject-1244.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            snipapi = builder.build();
        }
        try {
            return snipapi.create(snips[0]).execute();
        }
        catch (IOException e) {
            Snip dummySnip = new Snip();
            dummySnip.setId((long)-1);
            dummySnip.setContent(e.toString());
            return dummySnip;
        }
        // TODO: autocomplete catch exception after the rebuild.
    }

    @Override
    protected void onPostExecute(Snip snip) {
        if (snip.getId() == -1) {
            Toast.makeText(context, "failed to create the snippet.", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(context, "snippet(id=" + snip.getId() + "), ID is copied to the clipboard", Toast.LENGTH_LONG).show();
        // TODO: copy the snip.getId() to the clipboard.
    }
}
