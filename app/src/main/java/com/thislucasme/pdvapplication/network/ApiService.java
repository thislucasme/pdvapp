package com.thislucasme.pdvapplication.network;


import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.model.ProdutoResponse;
import com.thislucasme.pdvapplication.model.Token;
import com.thislucasme.pdvapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login")
    Call<Token> login(@Body User user);

    @GET("produtos")
    Call<List<Produto>> getProdutos(@Query("page") int page, @Query("limit")  int limit, @Query("queryText")  String queryText);
    @GET("produtos/produto")
    Call<Produto> getProduto(@Query("codigoBarras")  String codigoBarras);
}