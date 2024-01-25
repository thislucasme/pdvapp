package com.thislucasme.pdvapplication.network;

import android.content.Context;

import com.thislucasme.pdvapplication.model.PaginationProduto;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.model.ProdutoResponse;
import com.thislucasme.pdvapplication.model.Token;
import com.thislucasme.pdvapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdutoRepository {
    private ApiService apiService;
    public ProdutoRepository(Context context) {
      Retrofit retrofit = RetrofitClient.getClient(context);
      apiService = retrofit.create(ApiService.class);
    }

    public void retornaProdutos(PaginationProduto query, Callback<List<Produto>> callback) {
        Call<List<Produto>> call = apiService.getProdutos(query.getPage(), query.getLimit(), query.getQueryText());
        call.enqueue(callback);
    }
    public void retornaProduto(PaginationProduto query, Callback<Produto> callback) {
        Call<Produto> call = apiService.getProduto(query.getCodigoBarras());
        call.enqueue(callback);
    }
    public void criarPedido(Pedido pedido, Callback<Void> callback) {
        Call<Void> call = apiService.criarPedido(pedido);
        call.enqueue(callback);
    }
}
