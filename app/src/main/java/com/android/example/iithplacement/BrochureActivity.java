package com.android.example.iithplacement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class BrochureActivity extends AppCompatActivity {

    PDFView brochurePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brochure);

        brochurePdf = (PDFView) findViewById(R.id.brohurepdf);
        brochurePdf.fromAsset("Brochure.pdf").load();
    }
}
