package com.example.matu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.matu.myapplication.interfaces.ArticleService;
import com.example.matu.myapplication.model.Article;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticlesActivity extends AppCompatActivity {
    public ListView listView;

    private final String baseUrl = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_articles);

            listView = (ListView) findViewById(R.id.listView);
            this.getAllArticles();
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void getAllArticles() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ArticleService articleService = retrofit.create(ArticleService.class);

        Call<List<Article>> listCall = articleService.getArticles();

        listCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(ArticlesActivity.this, android.R.layout.simple_list_item_1, response.body());

                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }
}
