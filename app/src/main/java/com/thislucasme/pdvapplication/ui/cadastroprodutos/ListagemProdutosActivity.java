package com.thislucasme.pdvapplication.ui.cadastroprodutos;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.adapter.ProdutoAdapter;
import com.thislucasme.pdvapplication.capture.CaptureAct;
import com.thislucasme.pdvapplication.listener.PaginationListener;
import com.thislucasme.pdvapplication.model.PaginationInfo;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.pdv.PdvActivity;
import com.thislucasme.pdvapplication.ui.cadastroproduto.CadastroProdutoActivity;
import com.thislucasme.pdvapplication.viewmodel.ProdutoViewModel;
import com.thislucasme.pdvapplication.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListagemProdutosActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private ProdutoViewModel produtoViewModel;
    private RecyclerView recyclerView;
    private EditText searchView;
    private ProdutoAdapter produtoAdapter;
    private List<Produto> produtos = new ArrayList<>();
    private int limit = 12;
    private int currentPage = 1;
    private int total = 0;

    private String codigoBarras;
    private LinearLayout serverLinarError;
    private ImageView imageError;
    private TextView messageError;



    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_cadastro);
        getSupportActionBar().setTitle("Produtos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchView = findViewById(R.id.editTextTextSearchView);
        serverLinarError = findViewById(R.id.serverErro);
        imageError = findViewById(R.id.imageError);
        messageError = findViewById(R.id.textViewMessageServer);

        floatingActionButton = findViewById(R.id.floatingActionButtonCadastro);
        produtoViewModel = new ProdutoViewModel(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        produtoAdapter = new ProdutoAdapter();
        produtoAdapter.setItems(produtos);
        getSupportActionBar().setTitle("Carregando...");



        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentPage = 1;

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String searchText = charSequence.toString();
                produtoViewModel.carregarProdutos(new Callback<List<Produto>>() {
                    @Override
                    public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                        hiddenNotFount();
                        hiddenServerError();
                        if (response.isSuccessful()) {

                            if(response.body() != null){
                                String json = response.headers().get("Custom-Header");
                                Gson gson = new Gson();
                                PaginationInfo paginationInfo = gson.fromJson(json, PaginationInfo.class);
                                total = paginationInfo.getTotal();
                                produtos = response.body();
                                produtoAdapter.setItems(produtos);
                                produtoAdapter.notifyDataSetChanged();
                                getSupportActionBar().setTitle("Produtos");
                            }

                            if(response.code() == 204){
                                showNotFound();
                            }
                        }else{
                            getSupportActionBar().setTitle("Produtos");
                            if(response.code() >= 500 || response.code() <= 599) {
                                Toast.makeText(getApplicationContext(), "Erro ao conectar ao servidor", Toast.LENGTH_SHORT).show();
                                showServerError();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Produto>> call, Throwable t) {
                        getSupportActionBar().setTitle("error");

                    }

                }, limit, currentPage, searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        produtoViewModel.carregarProdutos(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                hiddenNotFount();
                hiddenServerError();
                if (response.isSuccessful()) {
                    String json = response.headers().get("Custom-Header");
                    Gson gson = new Gson();
                    PaginationInfo paginationInfo = gson.fromJson(json, PaginationInfo.class);
                    total = paginationInfo.getTotal();
                    produtos = response.body();
                    produtoAdapter.setItems(produtos);
                    produtoAdapter.notifyDataSetChanged();
                    getSupportActionBar().setTitle("Produtos");
                }else{
                    getSupportActionBar().setTitle("Produtos");
                    if(response.code() >= 500 || response.code() <= 599){
                        showServerError();
                    }
                    if(response.code() == 204){
                        showNotFound();
                    }
                    //Toast.makeText(getApplicationContext(), "is not sucess "+response.code(), Toast.LENGTH_SHORT).show();
                }
                // currentPage++;
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                getSupportActionBar().setTitle("Produtos");
            }

        }, limit, currentPage, "");

        recyclerView.setAdapter(produtoAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= limit) {
                        loadMoreItems();
                    }
                }
            }
        });




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListagemProdutosActivity.this, CadastroProdutoActivity.class);
                startActivity(i);
            }
        });

    }
    public void showServerError(){
        recyclerView.setVisibility(View.GONE);
        imageError.setImageResource(R.drawable.undraw_server_down_s4lk);
        serverLinarError.setVisibility(View.VISIBLE);

    };
    private void loadMoreItems() {
        isLoading = true;
        currentPage++;
        getSupportActionBar().setTitle("Carregando...");
        // Toast.makeText(getApplicationContext(), "KDKDK", Toast.LENGTH_SHORT).show();
        produtoViewModel.carregarProdutos(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null){
                        if(produtos.size() >= total){
                            isLastPage = true;
                        }
                        for (Produto p : response.body()) {
                            produtos.add(p);
                            // produtoAdapter.notifyDataSetChanged();
                        }
                        produtoAdapter.notifyDataSetChanged();
                    }
                }
                isLoading = false;
                getSupportActionBar().setTitle("Produtos");

            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                getSupportActionBar().setTitle("Produtos");
                isLoading = false;
            }
        }, limit, currentPage, searchView.getText().toString());
        Log.i("JUBI", "carregando..." + currentPage);

    }
    public void hiddenServerError(){
        recyclerView.setVisibility(View.VISIBLE);
        serverLinarError.setVisibility(View.INVISIBLE);
        messageError.setText("Erro no servidor");

    };
    public void showNotFound(){
        recyclerView.setVisibility(View.GONE);
        imageError.setImageResource(R.drawable.undraw_empty_re_opql);
        serverLinarError.setVisibility(View.VISIBLE);
        messageError.setText("Item não encontrado");

    };
    public void hiddenNotFount(){
        recyclerView.setVisibility(View.VISIBLE);
        serverLinarError.setVisibility(View.INVISIBLE);

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_produtos, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (item.getItemId() == R.id.codigoBarrasItem) {
            scanCode();
            //Toast.makeText(getApplicationContext(), "Código de Barras", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume para cima para ligar o flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if (result.getContents() != null) {
            codigoBarras = result.getContents();
            produtoViewModel.carregarProduto(new Callback<Produto>() {
                @Override
                public void onResponse(Call<Produto> call, Response<Produto> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Produto produto = response.body();
                            Intent intent = new Intent(ListagemProdutosActivity.this, CadastroProdutoActivity.class);
                            intent.putExtra("produto", produto);
                            startActivity(intent);
                           //Toast.makeText(getApplicationContext(), produto.getDescricao(), Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListagemProdutosActivity.this);
                            builder.setTitle("Informação");
                            builder.setMessage("Produto "+ result.getContents()+" não foi encontrato");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                        }
                    }


                }

                @Override
                public void onFailure(Call<Produto> call, Throwable t) {
                    getSupportActionBar().setTitle("Produtos");
                }
            }, codigoBarras);
        }
    });

}