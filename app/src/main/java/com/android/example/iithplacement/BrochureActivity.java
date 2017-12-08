package com.android.example.iithplacement;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class BrochureActivity extends AppCompatActivity {

    PDFView brochurePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        brochurePdf = (PDFView) findViewById(R.id.brohurepdf);
        brochurePdf.fromAsset("Brochure.pdf").load();
    }
}
