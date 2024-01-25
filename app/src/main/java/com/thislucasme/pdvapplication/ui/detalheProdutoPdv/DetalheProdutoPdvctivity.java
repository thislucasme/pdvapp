package com.thislucasme.pdvapplication.ui.detalheProdutoPdv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.pdv.PdvActivity;
import com.thislucasme.pdvapplication.sqlite.DatabaseHandler;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetalheProdutoPdvctivity extends AppCompatActivity {

    public Produto produto;
    private ImageView imageView;
    private TextView title;
    private EditText observacao;
    private TextView total;
    private int quantidade;
    public ImageButton add;
    private ImageButton remove;
    private EditText editTextQuantidade;
    public DatabaseHandler db;
    public Button buttonConcluir;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto_pdvctivity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        imageView = findViewById(R.id.imageViewProduto);
        title = findViewById(R.id.textViewTitle);
        observacao =  findViewById(R.id.editTextObservacao);
        total =  findViewById(R.id.textViewTotalDetalhe);

        add = findViewById(R.id.imageButtonAdd);
        remove = findViewById(R.id.imageButtonRemove);
        editTextQuantidade =  findViewById(R.id.editTextNumber);
        buttonConcluir = findViewById(R.id.buttonConcluir);

        db = new DatabaseHandler(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //quantidade++;
                produto.setEstoque(produto.getEstoque() + 1);
                editTextQuantidade.setText(String.valueOf(produto.getEstoque()));
                total.setText(brazilianReal(produto.getPreco_venda() * produto.getEstoque()));

            }
        });

        buttonConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarProduto();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produto.getEstoque() > 1){
                    produto.setEstoque(produto.getEstoque() - 1);
                    //quantidade--;
                    editTextQuantidade.setText(String.valueOf(produto.getEstoque()));
                    total.setText(brazilianReal(produto.getPreco_venda() * produto.getEstoque()));

                }

            }
        });

        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("produto");
        if(produto != null){
            Picasso.get().load(produto.getUrlImagem()).into(imageView);
            title.setText(String.valueOf(produto.getDescricao()));
            getSupportActionBar().setTitle(produto.getDescricao());
            editTextQuantidade.setText(String.valueOf(produto.getEstoque()));
            observacao.setText(produto.getObservacao());
            total.setText(brazilianReal(produto.getPreco_venda() * produto.getEstoque()));
            //Toast.makeText(getApplicationContext(), produto.getId()+"oi", Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_novo_produto, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.itemCodigoDeletarProduto) {
            // Suponha que "produto" seja uma instância válida da classe Produto e que "db" seja uma instância válida do seu helper de banco de dados
            deletarProduto();
        }
        return super.onOptionsItemSelected(item);
    }
    private void enviarDadosParaActivityA() {
        Intent intent = new Intent(DetalheProdutoPdvctivity.this, PdvActivity.class);
        intent.putExtra("result","algum dado aqui");
        setResult(RESULT_OK,intent);
        finish();
    }
        public void salvarProduto(){

            produto.setObservacao(observacao.getText().toString());

            db.updateProduto(produto);

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

            Log.i("JUJUBI", "========**********======");
            Log.i("JUJUBI", produto.toString());
            Log.i("JUJUBI", "========**********======");
            List<Produto> prodDb = db.getProdutosByPedidoId(1);
            for (Produto p : prodDb) {
                Log.i("JUJUBI", "==============");
                Log.i("JUJUBI", p.getDescricao()+", id:"+p.getId());
                Log.i("JUJUBI", p.toString());
            }
            //Toast.makeText(getApplicationContext(), ""+produto.getId(), Toast.LENGTH_LONG).show();
            enviarDadosParaActivityA();

    //        if (sucessoAtualizacao) {
    //            Toast.makeText(getApplicationContext(), "Produto salvo com sucesso", Toast.LENGTH_LONG).show();
    //        } else {
    //            Toast.makeText(getApplicationContext(), "Erro ao salvar o produto", Toast.LENGTH_LONG).show();
    //        }
        }

    public void deletarProduto(){


        db.deleteProduto(produto.getId());

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

        Log.i("JUJUBI", "========**********======");
        Log.i("JUJUBI", produto.toString());
        Log.i("JUJUBI", "========**********======");
        List<Produto> prodDb = db.getProdutosByPedidoId(1);
        for (Produto p : prodDb) {
            Log.i("JUJUBI", "==============");
            Log.i("JUJUBI", p.getDescricao()+", id:"+p.getId());
            Log.i("JUJUBI", p.toString());
        }
        //Toast.makeText(getApplicationContext(), ""+produto.getId(), Toast.LENGTH_LONG).show();
        enviarDadosParaActivityA();

        //        if (sucessoAtualizacao) {
        //            Toast.makeText(getApplicationContext(), "Produto salvo com sucesso", Toast.LENGTH_LONG).show();
        //        } else {
        //            Toast.makeText(getApplicationContext(), "Erro ao salvar o produto", Toast.LENGTH_LONG).show();
        //        }
    }

    public String brazilianReal(Double value) {
        Locale locale = new Locale("pt", "BR");

        // Criar uma instância do NumberFormat para a formatação de moeda
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        // Formatar o valor para a moeda brasileira
        String valorFormatado = format.format(value);
        return valorFormatado;
    }

}