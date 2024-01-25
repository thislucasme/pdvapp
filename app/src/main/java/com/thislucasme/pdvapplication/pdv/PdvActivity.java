    package com.thislucasme.pdvapplication.pdv;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.adapter.ProdutoAdapter;
import com.thislucasme.pdvapplication.adapter.ProdutosCartPdvAdapter;
import com.thislucasme.pdvapplication.cadastro.CadastroOperadorActivity;
import com.thislucasme.pdvapplication.callbacks.DialogTecladoAcrescimoDescontoCallBack;
import com.thislucasme.pdvapplication.helpers.DBHelper;
import com.thislucasme.pdvapplication.helpers.DialogAcrescimoDecrescimo;
import com.thislucasme.pdvapplication.helpers.DialogClientes;
import com.thislucasme.pdvapplication.model.EstudanteList;
import com.thislucasme.pdvapplication.model.PaginationInfo;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.model.Vendedor;
import com.thislucasme.pdvapplication.preference.PreferencesManager;
import com.thislucasme.pdvapplication.recycler.RecyclerItemClickListener;
import com.thislucasme.pdvapplication.sqlite.DatabaseHandler;
import com.thislucasme.pdvapplication.teste.DatabaseHelper;
import com.thislucasme.pdvapplication.ui.cadastroprodutos.ListagemProdutosActivity;
import com.thislucasme.pdvapplication.ui.detalheProdutoPdv.DetalheProdutoPdvctivity;
import com.thislucasme.pdvapplication.ui.finalizar.venda.FinalizarVendaActivity;
import com.thislucasme.pdvapplication.viewmodel.PedidoPdvViewModel;
import com.thislucasme.pdvapplication.viewmodel.ProdutoViewModel;
import com.thislucasme.pdvapplication.viewmodel.TesteViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PdvActivity extends AppCompatActivity implements DialogTecladoAcrescimoDescontoCallBack {
    FloatingActionButton floatingActionButton;
    private static final String CHAVE_DADO = "chave_dado";
    private static final int REQUEST_CODE_ACTIVITY_B = 1;
    private ActivityResultLauncher<Intent> launcher;
    ProdutosCartPdvAdapter produtosCartPdvAdapter;
    RecyclerView recyclerViewProdutosCart;


    private ProdutoViewModel produtoViewModel;
    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;
    private List<Produto> produtos = new ArrayList<>();
    private int limit = 12;
    private int currentPage = 1;
    private int total = 0;


    private boolean isLastPage = false;
    private boolean isLoading = false;
    int itemCount = 0;
    private EditText searchView;

    private LinearLayout serverLinarError;
    private ImageView imageError;
    private TextView messageError;
    private Pedido pedidoPdv;


    private TextView ultimoNomeProduto;
    private TextView ultimoValorProduto;
    private TextView quantidadeProdutosPedido;
    private Button cobrar;
    private LinearLayout valoresPedidoPdv;
    private List<Vendedor> vendedores = new ArrayList<>();

    private LinearLayout linearLayoutClientes;
    private LinearLayout linearLayoutCaixas;

    private TesteViewModel testeViewModel;
    private PedidoPdvViewModel pedidoPdvViewModel;
    private DBHelper mydb;
    DatabaseHandler db;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdv);
        getSupportActionBar().setTitle("Pdv");

        pedidoPdv = new Pedido();
        db = new DatabaseHandler(this);


        List<Produto> prods = new ArrayList<>();

        Produto produto1 = new Produto(1, 1, 1, 10, "123456789", "Observação do Produto 1", 1, 0, 1, 1, 1, "Produto 1", "https://example.com/imagem1.jpg", 5.0, 10.0,"","", 2);
        //db.addProduto(produto1, 1);
        prods.add(produto1);
        Produto produto2 = new Produto(2, 2, 2, 20, "987654321", "Observação do Produto 2", 1, 1, 0, 2, 1, "Produto 2", "https://example.com/imagem2.jpg", 8.0, 15.0,"", "", 3);
        prods.add(produto2);
        //db.addProduto(produto2, 1);

        Pedido pedidoFicticio = new Pedido(
                1,
                null,
                0.0,
                0.0,
                prods,
                0.0,
                0,
                0.0,
                null
        );
        List<Pedido> pedidos = db.getAllPedidos();
        if (pedidos.size() <= 0) {
            db.addOrUpdatePedido(pedidoFicticio);
        }

        //db.updatePedido(pedidoFicticio);
        //db.addPedido(pedidoFicticio);
        // Inserting Students
        //Log.d("bancodedados: ", "Inserting ..");
        //db.addStudent(new EstudanteList(1, "A"));
        //db.addStudent(new EstudanteList(2, "B"));
        //db.addStudent(new EstudanteList(3, "C"));

        // Reading all Students
//        Log.d("bancodedados: ", "Reading all students..");
//        List<EstudanteList> students = db.getAllStudentList();
        List<Produto> prodDb = db.getProdutosByPedidoId(1);
        for (Produto p : prodDb) {
            Log.i("JUJUBI", p.toString());
        }
        pedidoPdv.setProdutoList(prodDb);
        Log.i("JUJUBI", prodDb.size() + " produtos");
        List<Pedido> ped = db.getAllPedidos();
        Log.i("JUJUBI", ped.get(0).getTotalGeral() + " total geral");
//        try{
//            //List<Pedido> pedidos =  db.getAllPedidos();
//            Log.i("JUJUBI", pedidos.size()+" pedidos");
//            Log.i("JUJUBI", "id:"+pedidos.get(0).getIdentificador());
//            Log.d("JUJUBI: ",
//                    pedidos.get(0).getObservacao()+
//                            ", identificador: "+pedidos.get(0).getIdentificador()+
//                            ", total"+pedidos.get(0).getTotalGeral()+
//                            ", ultimo valor: "+pedidos.get(0).getUltimoValorProduto()+
//                    "quantidade produtos:"+pedidos.get(0).getProdutoList().size()+
//                            "id:"+pedidos.get(0).getProdutoList().size());
//        }catch (Exception e){
//            Log.i("JUJUBI", "error:"+e.getMessage());
//        }


        serverLinarError = findViewById(R.id.serverErro);
        imageError = findViewById(R.id.imageError);
        messageError = findViewById(R.id.textViewMessageServer);
        ultimoNomeProduto = findViewById(R.id.textViewUltimoProdutoName);
        ultimoValorProduto = findViewById(R.id.textViewUltimoValorProduto);
        quantidadeProdutosPedido = findViewById(R.id.textViewQuantidadeProdutosPedido);
        cobrar = findViewById(R.id.buttonCobrarPdv);
        valoresPedidoPdv = findViewById(R.id.valoresPedidoPDV);
        linearLayoutClientes = findViewById(R.id.linearLayoutCliente);
        searchView = findViewById(R.id.editTextTextCodigoBarras);
        floatingActionButton = findViewById(R.id.floatingActionButtonPdv);
        produtoViewModel = new ProdutoViewModel(getApplicationContext());
        linearLayoutCaixas = findViewById(R.id.linearLayoutCaixa);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerProdutosPdv);
        recyclerView.setLayoutManager(layoutManager);

        testeViewModel = new ViewModelProvider(this).get(TesteViewModel.class);
        pedidoPdvViewModel = new ViewModelProvider(this).get(PedidoPdvViewModel.class);


        produtoAdapter = new ProdutoAdapter();
        produtoAdapter.setItems(produtos);


        //vendedores
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Lucas Silva");
        vendedores.add(vendedor);
        vendedor = new Vendedor();
        vendedor.setNome("Eduardo");
        vendedores.add(vendedor);

        showDatasProductOnPdv(db);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Log.i("ELINA", "TESTE");
                   Toast.makeText(getApplicationContext(), "CAIU NA FUNÇÃO", Toast.LENGTH_SHORT).show();
                    if (result.getResultCode() == RESULT_OK) {
                      //  Toast.makeText(getApplicationContext(), "ATUALIZADO", Toast.LENGTH_SHORT).show();
                        Intent data = result.getData();
                        //tvResult.setText(data.getStringExtra("result"));

                        getSupportActionBar().setTitle("Lucas");
                        List<Produto> products = db.getProdutosByPedidoId(1);

                        double totalTemp = 0.0;
                        for (Produto product : products) {
                            totalTemp += product.getPreco_venda() * product.getEstoque();

                        }
                        pedidoPdv.setTotalGeral(totalTemp);

                        pedidoPdv.setProdutoList(new ArrayList<Produto>());
                        pedidoPdv.setProdutoList(products);
                        for (Produto product : products) {
                            Log.i("ELINA", product.toString());
                        }


                        //CARREGAR PDV


//                        List<Produto> produtoList = db.getProdutosByPedidoId(1);
//                        double totalTemp = 0.0;
//                        for (Produto product : produtoList) {
//                            totalTemp += product.getPreco_venda() * product.getEstoque();
//                        }
//                        List<Pedido> pedidosTemp = db.getAllPedidos();
//                        Pedido pedidoTemporario = pedidosTemp.get(0);
//                        pedidoTemporario.setTotalGeral(totalTemp);
//                        pedidoTemporario.setProdutoList(produtoList);
//
//                        db.addOrUpdatePedido(pedidoTemporario);
//                        pedidoPdv = pedidoTemporario;
//
//
                        showDatasProductOnPdv(db);
                        produtosCartPdvAdapter = new ProdutosCartPdvAdapter(pedidoPdv);
                        recyclerViewProdutosCart.setAdapter(produtosCartPdvAdapter);
                        produtosCartPdvAdapter.notifyDataSetChanged();
                       pedidoPdvViewModel.setPedido(pedidoPdv);

                    }
                }
        );


        getSupportActionBar().setTitle("Carregando...");
        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(pedidoPdv.getTotalGeral() > 0){
                   Intent i =  new Intent(getApplicationContext(), FinalizarVendaActivity.class);
                   startActivity(i);
               }
            }
        });
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

                            if (response.body() != null) {

                                String json = response.headers().get("Custom-Header");
                                Gson gson = new Gson();
                                PaginationInfo paginationInfo = gson.fromJson(json, PaginationInfo.class);
                                total = paginationInfo.getTotal();
                                produtos = response.body();
                                produtoAdapter.setItems(produtos);
                                produtoAdapter.notifyDataSetChanged();
                                getSupportActionBar().setTitle("Produtos");
                            }

                            if (response.code() == 204) {
                                showNotFound();
                            }
                        } else {
                            getSupportActionBar().setTitle("Produtos");
                            if (response.code() >= 500 || response.code() <= 599) {
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
        linearLayoutCaixas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialogCaixas();
            }
        });
        valoresPedidoPdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pedidoPdv != null) {
                    if (pedidoPdv.getProdutoList().size() > 0) {
                        showBottomSheetDialogProdutos(PdvActivity.this);
                    }
                }
            }
        });
        linearLayoutClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialogCLientes(getApplicationContext());
            }
        });
        recyclerView.setAdapter(produtoAdapter);
        produtoViewModel.carregarProdutos(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {

                Log.i("JURURU", "TESTE");
                hiddenNotFount();
                hiddenServerError();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String json = response.headers().get("Custom-Header");
                        Gson gson = new Gson();
                        PaginationInfo paginationInfo = gson.fromJson(json, PaginationInfo.class);
                        total = paginationInfo.getTotal();
                        produtos = response.body();
                        Log.i("PRODUTOS", produtos.get(0).toString());
                        produtoAdapter.setItems(produtos);
                        produtoAdapter.notifyDataSetChanged();
                        getSupportActionBar().setTitle("Produtos");
                    }
                } else {
                    getSupportActionBar().setTitle("Produtos");
                    if (response.code() >= 500 || response.code() <= 599) {
                        showServerError();
                    }
                    if (response.code() == 204) {
                        showNotFound();
                    }
                    //Toast.makeText(getApplicationContext(), "is not sucess "+response.code(), Toast.LENGTH_SHORT).show();
                }
                // currentPage++;
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                getSupportActionBar().setTitle("ERRO");
                Log.i("JURURU", t.getMessage());
            }

        }, limit, currentPage, "");


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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Produto produto = produtos.get(position);
                        produto.setEstoque(1);

                        //adicionando o produto ao banco
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        db.addProduto(produto, 1);
                        Log.i("JOKER", produto.toString());

                        List<Produto> produtoList = db.getProdutosByPedidoId(1);
                        double totalTemp = 0.0;
                        for (Produto product : produtoList) {
                            totalTemp += product.getPreco_venda() * product.getEstoque();
                        }
                        List<Pedido> pedidosTemp = db.getAllPedidos();
                        Pedido pedidoTemporario = pedidosTemp.get(0);
                        Pedido pedidoFicticio = new Pedido(
                                1,
                                pedidoTemporario.getObservacao(),
                                pedidoTemporario.getAcrescimo(),
                                pedidoTemporario.getDesconto(),
                                produtoList,
                                totalTemp,
                                produtoList.size(),
                                produto.getPreco_venda(),
                                produto.getDescricao()
                        );
                        db.addOrUpdatePedido(pedidoFicticio);
                        pedidoPdv = pedidoFicticio;


                        showDatasProductOnPdv(db);
                        pedidoPdvViewModel.setPedido(pedidoPdv);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
        );


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PdvActivity.this, ListagemProdutosActivity.class);
                startActivity(i);
            }
        });
    }

    public void showServerError() {
        recyclerView.setVisibility(View.GONE);
        imageError.setImageResource(R.drawable.undraw_server_down_s4lk);
        serverLinarError.setVisibility(View.VISIBLE);

    }

    ;

    private void showDatasProductOnPdv(DatabaseHandler db) {
        PreferencesManager preferencesManager = new PreferencesManager(getApplicationContext());

        Log.i("JUJUBI",  "token:"+preferencesManager.getUserToken());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        List<Pedido> pedidos = db.getAllPedidos();
        Pedido pedidoTemporario = pedidos.get(0);
        ultimoNomeProduto.setText(pedidoTemporario.getUltimoNomeProduto());
        String formattedValue = currencyFormat.format(pedidoTemporario.getUltimoValorProduto());
        ultimoValorProduto.setText(formattedValue);
        String total = currencyFormat.format(pedidoTemporario.getTotalGeral());
        cobrar.setText("Cobrar\n" + total);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.drop_animation);
        valoresPedidoPdv.startAnimation(animation);
        List<Produto> produtoList = db.getProdutosByPedidoId(1);
        quantidadeProdutosPedido.setText(String.valueOf(produtoList.size()));
        Pedido pedidoFicticio = new Pedido(
                1,
                pedidoTemporario.getObservacao(),
                pedidoTemporario.getAcrescimo(),
                pedidoTemporario.getDesconto(),
                produtoList,
                pedidoTemporario.getTotalGeral(),
                produtoList.size(),
                pedidoTemporario.getUltimoValorProduto(),
                pedidoTemporario.getUltimoNomeProduto()
        );
        pedidoPdv = pedidoFicticio;


    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage++;
        getSupportActionBar().setTitle("Carregando...");
        // Toast.makeText(getApplicationContext(), "KDKDK", Toast.LENGTH_SHORT).show();
        produtoViewModel.carregarProdutos(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (produtos.size() >= total) {
                            isLastPage = true;
                        }
                        for (Produto p : response.body()) {
                            p.setQuantidade(1);
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

    public void hiddenServerError() {
        recyclerView.setVisibility(View.VISIBLE);
        serverLinarError.setVisibility(View.INVISIBLE);
        messageError.setText("Erro no servidor");

    }

    ;

    public void showNotFound() {
        recyclerView.setVisibility(View.GONE);
        imageError.setImageResource(R.drawable.undraw_empty_re_opql);
        serverLinarError.setVisibility(View.VISIBLE);
        messageError.setText("Item não encontrado");

    }

    ;

    public void hiddenNotFount() {
        recyclerView.setVisibility(View.VISIBLE);
        serverLinarError.setVisibility(View.INVISIBLE);

    }

    ;

    private void showBottomSheetDialogProdutos(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_produtos_pdv, null);

        recyclerViewProdutosCart = dialogView.findViewById(R.id.recyclerProdutosSheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewProdutosCart.setLayoutManager(layoutManager);

        recyclerViewProdutosCart.addOnItemTouchListener(
                new RecyclerItemClickListener(context.getApplicationContext(), recyclerViewProdutosCart, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Produto produto = pedidoPdv.getProdutoList().get(position);
                        //Toast.makeText(getApplicationContext(), produto.getId()+"oi", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PdvActivity.this, DetalheProdutoPdvctivity.class);
                        intent.putExtra("produto", produto);
                        //startActivity(intent);
//                        startActivityForResult(intent, DETALHE_PRODUTO_REQUEST_CODE);
                        launcher.launch(intent);



                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
        );

        produtosCartPdvAdapter = new ProdutosCartPdvAdapter(pedidoPdv);
        recyclerViewProdutosCart.setAdapter(produtosCartPdvAdapter);
        produtosCartPdvAdapter.notifyDataSetChanged();


        Button buttonAlterar = dialogView.findViewById(R.id.buttonAlterar);
        ImageView acrescimo = dialogView.findViewById(R.id.imageViewPlusDesconto);
        ImageView desconto = dialogView.findViewById(R.id.imageViewMinusDesconto);

        TextView textViewAcrescimo = dialogView.findViewById(R.id.textViewAcrescimo);
        TextView textViewDesconto = dialogView.findViewById(R.id.textViewDescontoProdutos);

        DatabaseHandler db =   new DatabaseHandler(context);
        List<Pedido> pedidosTemp = db.getAllPedidos();
        Pedido pedidoTemporario = pedidosTemp.get(0);

        pedidoPdvViewModel.getPedido().observe(this, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                String formattedAcrescimo = String.format("R$ %.2f", pedido.getAcrescimo() / 100);
                String formattedDesconto = String.format("R$ %.2f", pedido.getDesconto() / 100);
                textViewAcrescimo.setText("Acréscimo \n" + formattedAcrescimo);
                textViewDesconto.setText("Desconto \n" + formattedDesconto);
            }
        });
        String formattedAcrescimo = String.format("R$ %.2f", pedidoTemporario.getAcrescimo() / 100);
        String formattedDesconto = String.format("R$ %.2f", pedidoTemporario.getDesconto() / 100);
        textViewAcrescimo.setText("Acréscimo \n" + formattedAcrescimo);
        textViewDesconto.setText("Desconto \n" + formattedDesconto);

//        testeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                textViewAcrescimo.setText(s);
//            }
//        });

        acrescimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("acrescimo");
            }
        });
        desconto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("desconto");
            }
        });
        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetalheProdutoPdvctivity.class);
                startActivity(intent);
            }
        });
        // LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
        //LinearLayout upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
        // LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
        // LinearLayout delete = bottomSheetDialog.findViewById(R.id.delete);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showBottomSheetDialogCLientes(Context context) {
        DialogClientes.showModalClientes(PdvActivity.this, pedidoPdvViewModel);
    }

    private void showBottomSheetDialogCaixas() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_caixas_pdv);

        Button buttonAlterar = bottomSheetDialog.findViewById(R.id.buttonAdcionarOperador);
        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CadastroOperadorActivity.class);
                startActivity(intent);
            }
        });
        // LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
        //LinearLayout upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
        // LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
        // LinearLayout delete = bottomSheetDialog.findViewById(R.id.delete);

        bottomSheetDialog.show();
    }

    private void showDialog(String tipo) {
        DialogAcrescimoDecrescimo.showTecladoNumericoDescontoAcrescimo(PdvActivity.this, PdvActivity.this, pedidoPdvViewModel, tipo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pdv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (item.getItemId() == R.id.itemCodigoDeletarProduto) {
            Toast.makeText(getApplicationContext(), "Salvar cliente", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataEntered(String data) {
        double value = Double.parseDouble(data) / 100;
        pedidoPdv.setAcrescimo(value);
        Toast.makeText(getApplicationContext(), value + "", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {

        List<Produto> products = db.getProdutosByPedidoId(1);

        double totalTemp = 0.0;
        for (Produto product : products) {
            totalTemp += product.getPreco_venda() * product.getEstoque();

        }
        pedidoPdv.setTotalGeral(totalTemp);

        pedidoPdv.setProdutoList(new ArrayList<Produto>());
        pedidoPdv.setProdutoList(products);
        for (Produto product : products) {
            Log.i("ELINA", product.toString());
        }


        //CARREGAR PDV


//                        List<Produto> produtoList = db.getProdutosByPedidoId(1);
//                        double totalTemp = 0.0;
//                        for (Produto product : produtoList) {
//                            totalTemp += product.getPreco_venda() * product.getEstoque();
//                        }
//                        List<Pedido> pedidosTemp = db.getAllPedidos();
//                        Pedido pedidoTemporario = pedidosTemp.get(0);
//                        pedidoTemporario.setTotalGeral(totalTemp);
//                        pedidoTemporario.setProdutoList(produtoList);
//
//                        db.addOrUpdatePedido(pedidoTemporario);
//                        pedidoPdv = pedidoTemporario;
//
//
        showDatasProductOnPdv(db);
        super.onResume();
    }
}

