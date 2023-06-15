package com.thislucasme.pdvapplication.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.thislucasme.pdvapplication.model.PaginationInfo;
import com.thislucasme.pdvapplication.model.PaginationProduto;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.model.ProdutoResponse;
import com.thislucasme.pdvapplication.model.Token;
import com.thislucasme.pdvapplication.model.User;
import com.thislucasme.pdvapplication.network.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoViewModel extends ViewModel {

    private ProdutoRepository produtoRepository;
    private Context context;

    public ProdutoViewModel(Context applicationContext) {
        this.context = applicationContext;
    }

    public void carregarProdutos( final Callback<List<Produto>> callback, int limit, int page, String query) {
        PaginationProduto paginationProduto = new PaginationProduto();
        paginationProduto.setPage(page);
        paginationProduto.setLimit(limit);
        paginationProduto.setQueryText(query);
        produtoRepository = new ProdutoRepository(context.getApplicationContext());
        produtoRepository.retornaProdutos(paginationProduto, new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                callback.onFailure(call, t);
            }

        });
    }
    public void carregarProduto( final Callback<Produto> callback, String codigoBarras) {
        PaginationProduto paginationProduto = new PaginationProduto();
        paginationProduto.setCodigoBarras(codigoBarras);
        produtoRepository = new ProdutoRepository(context.getApplicationContext());
        produtoRepository.retornaProduto(paginationProduto, new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                //callback.onResponse(call, t);
            }
        });
    }
}