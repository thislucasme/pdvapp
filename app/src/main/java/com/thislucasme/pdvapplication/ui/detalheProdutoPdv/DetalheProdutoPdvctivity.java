package com.thislucasme.pdvapplication.ui.detalheProdutoPdv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.model.Produto;

import java.text.NumberFormat;
import java.util.Locale;

public class DetalheProdutoPdvctivity extends AppCompatActivity {

    private Produto produto;
    private ImageView imageView;
    private TextView title;
    private EditText observacao;
    private TextView total;

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
        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("produto");
        if(produto != null){
            Picasso.get().load(produto.getUrlImagem()).into(imageView);
            title.setText(String.valueOf(produto.getDescricao()));
            getSupportActionBar().setTitle(produto.getDescricao());
            //observacao.setText(produto.getObservacao());
            total.setText(brazilianReal(produto.getPreco_venda() * produto.getEstoque()));
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(item.getItemId() == R.id.itemCodigoSalvarProduto){
            Toast.makeText(getApplicationContext(), "Salvar produto", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
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