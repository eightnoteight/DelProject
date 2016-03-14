package com.eightnoteight.delproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.eightnoteight.delproject.backend.snipapi.model.Snip;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void createSnip(View view) {
        hideKeyboard(this);
        EditText editTextCreateSnip = (EditText) findViewById(R.id.editTextCreateSnip);
        Snip snip = new Snip();
        snip.setContent(editTextCreateSnip.getText().toString());
        new CreateSnipAsync(this).execute(snip);
//        Snackbar.make(view, "snip created", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    public void getSnip(View view) {
        hideKeyboard(this);
        EditText editTextGetSnip = (EditText) findViewById(R.id.editTextGetSnip);
        new GetSnipAsync(this).execute(Long.decode(editTextGetSnip.getText().toString()));
//        Snackbar.make(view, "snip copied to clipboard", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    public void deleteSnip(View view) {
        hideKeyboard(this);
        EditText editTextDeleteSnip = (EditText) findViewById(R.id.editTextDeleteSnip);
        new DeleteSnipAsync(this).execute(Long.decode(editTextDeleteSnip.getText().toString()));
//        Snackbar.make(view, "snip deleted", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }
}
