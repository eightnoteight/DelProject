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


public class DeleteSnipAsync extends AsyncTask<Long, Void, Long> {
    private Context context;
    private Snipapi snipapi = null;
    public DeleteSnipAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Long... snip_ids) {
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
            snipapi.delete(snip_ids[0]).execute();
            return snip_ids[0];
        }
        catch (IOException e) {
            return (long)-1;
        }
    }

    @Override
    protected void onPostExecute(Long snip_id) {
        if (snip_id == -1) {
            Toast.makeText(context, "failed to delete the snippet.", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(context, "snippet(id=" + snip_id + ") deleted", Toast.LENGTH_LONG).show();
        // TODO: copy the snip_id to the clipboard.
    }
}
