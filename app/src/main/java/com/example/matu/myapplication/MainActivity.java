package com.example.matu.myapplication;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.util.Log;
import android.view.View;

import com.example.matu.myapplication.interfaces.ArticleService;
import com.example.matu.myapplication.model.Article;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private final String baseUrl = "http://10.0.2.2:3000/";

    private ZXingScannerView scanerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ArticleService articleService = retrofit.create(ArticleService.class);

        Call<Article> listCall = articleService.getArticles();

        listCall.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                if(response.isSuccessful()){
                    System.out.print("se cargo");
                }
            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });

    }

    public void escanerQR(View view) {
        scanerView = new ZXingScannerView(this);
        setContentView(scanerView);
        scanerView.setResultHandler(this);
        scanerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado del escaner");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
