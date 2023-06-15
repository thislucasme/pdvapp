package com.thislucasme.pdvapplication.ui.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.model.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    String[] item = {"Categoria X", "Categoria Y"};
    AutoCompleteTextView autoCompleteTextView;
    private Produto produto;
    private ImageView produtoIMagem;
    private EditText codigo;
    private EditText descricao;
    private EditText valorVenda;
    private EditText valorCusto;
    private EditText editTextCodigoBarras;


    ArrayAdapter<String> adapterItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        getSupportActionBar().setTitle("Novo produto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        produtoIMagem = findViewById(R.id.iconProduto);
        codigo = findViewById(R.id.textInputEditTextCodigo);
        descricao = findViewById(R.id.editTextDescricao);
        valorVenda = findViewById(R.id.editTextValorVenda);
        valorCusto = findViewById(R.id.editTextValorCusto);
        editTextCodigoBarras = findViewById(R.id.editTextCodigoBarras);


        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("produto");
        if(produto != null){
            Picasso.get().load(produto.getUrlImagem()).into(produtoIMagem);
            codigo.setText(String.valueOf(produto.getId()));
            descricao.setText(String.valueOf(produto.getDescricao()));
            valorVenda.setText(String.valueOf(produto.getPreco_venda()));
            valorCusto.setText(String.valueOf(produto.getPreco_custo()));
            editTextCodigoBarras.setText(String.valueOf(produto.getCodigoBarras()));
            //Toast.makeText(getApplicationContext(), produto.getId()+"", Toast.LENGTH_SHORT).show();
        }


        autoCompleteTextView = findViewById(R.id.autoCompleteTextViewCategoria);
        adapterItens = new ArrayAdapter<String>(this, R.layout.list_item, item);

        autoCompleteTextView.setAdapter(adapterItens);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
            }
        });
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(item.getItemId() == R.id.itemCodigoSalvarProduto){
            Toast.makeText(getApplicationContext(), "Salvar produto", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}