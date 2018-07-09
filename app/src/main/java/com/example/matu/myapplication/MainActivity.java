package com.example.matu.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.matu.myapplication.model.Article;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private final String baseUrl = "http://10.0.2.2:3000/";

    private ZXingScannerView scanerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openScaner(View view) {
        scanerView = new ZXingScannerView(this);
        setContentView(scanerView);
        scanerView.setResultHandler(this);
        scanerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(scanerView!=null) {
            scanerView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del escaner");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public Article getArticleByCodeScan(String codeScan) {
        return null;
    }

    public void redirectArticleActivity(View view) {
        Intent myIntent = new Intent(this, ArticlesActivity.class);
        startActivity(myIntent);
    }
}
