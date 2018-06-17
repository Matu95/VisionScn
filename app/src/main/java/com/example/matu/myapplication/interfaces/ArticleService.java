package com.example.matu.myapplication.interfaces;

import com.example.matu.myapplication.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ArticleService {

    @GET("articles")
    Call<Article> getArticles();

}
