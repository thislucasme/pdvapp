package com.thislucasme.pdvapplication.ui.detalheProdutoPdv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.thislucasme.pdvapplication.R;

public class DetalheProdutoPdvctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto_pdvctivity);
        getSupportActionBar().setTitle("Heinken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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