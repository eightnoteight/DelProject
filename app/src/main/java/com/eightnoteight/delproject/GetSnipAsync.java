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


public class GetSnipAsync extends AsyncTask<Long, Void, Snip> {
    private Context context;
    private Snipapi snipapi = null;
    public GetSnipAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Snip doInBackground(Long... snip_ids) {
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
        // TODO:
        // find a way to execute an async task with only single parameter sent to the doInBackground
        // if it isn't available try to make a new AsyncTask class yourself.
        try {
            return snipapi.get(snip_ids[0]).execute();
        } catch (IOException e) {
            Snip dummySnip = new Snip();
            dummySnip.setId((long)-1);
            dummySnip.setContent(e.toString());
            return dummySnip;
        }
    }

    @Override
    protected void onPostExecute(Snip snip) {
        if (snip.getId() == -1) {
            Toast.makeText(context, "failed to get the snippet.", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(context, "snippet(id=" + snip.getId() + ") content is copied to the clipboard", Toast.LENGTH_LONG).show();
        // TODO: copy the snip.getContent() to the clipboard.
    }
}
