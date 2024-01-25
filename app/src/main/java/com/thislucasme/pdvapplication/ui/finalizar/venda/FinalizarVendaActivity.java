package com.thislucasme.pdvapplication.ui.finalizar.venda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.callbacks.DialogTecladoAcrescimoDescontoCallBack;
import com.thislucasme.pdvapplication.enums.FormaPagamento;
import com.thislucasme.pdvapplication.enums.StatusOrder;
import com.thislucasme.pdvapplication.helpers.DialogFinalizarVenda;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.network.ProdutoRepository;
import com.thislucasme.pdvapplication.pdv.PdvActivity;
import com.thislucasme.pdvapplication.sqlite.DatabaseHandler;
import com.thislucasme.pdvapplication.ui.detalheProdutoPdv.DetalheProdutoPdvctivity;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinalizarVendaActivity extends AppCompatActivity implements DialogTecladoAcrescimoDescontoCallBack {
    LinearLayout linearLayoutDinheiro;
    LinearLayout linearLayoutCartaoCredito;
    LinearLayout linearLayoutCartaoDebito;
    public DatabaseHandler db;
    Pedido pedido;
    int formaPagamento = 0;
    Double troco = 0.0;
    List<Produto> produtoList;
    ProdutoRepository produtoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_venda);
        getSupportActionBar().setTitle("Forma de pagamento");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new DatabaseHandler(this);

        produtoList = db.getProdutosByPedidoId(1);
        produtoRepository = new ProdutoRepository(this);

        List<Pedido> pedidosTemp = db.getAllPedidos();
        pedido = pedidosTemp.get(0);

        linearLayoutDinheiro = findViewById(R.id.dinheiroLayout);
        linearLayoutCartaoDebito = findViewById(R.id.cartaoDebito);
        linearLayoutCartaoCredito = findViewById(R.id.cartaoCredito);

        linearLayoutDinheiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formaPagamento = FormaPagamento.DINHEIRO.getFormaPagamento();
                DialogFinalizarVenda.showTecladoNumericoFinalizarVenda(FinalizarVendaActivity.this, FinalizarVendaActivity.this);
                //showCustomDialog();
            }
        });
        linearLayoutCartaoDebito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formaPagamento = FormaPagamento.CARTAO_DEBITO.getFormaPagamento();
                DialogFinalizarVenda.showTecladoNumericoFinalizarVenda(FinalizarVendaActivity.this, FinalizarVendaActivity.this);
            }
        });
        linearLayoutCartaoCredito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formaPagamento = FormaPagamento.CARTAO_CREDITO.getFormaPagamento();
                DialogFinalizarVenda.showTecladoNumericoFinalizarVenda(FinalizarVendaActivity.this, FinalizarVendaActivity.this);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void showCustomDialog() {
        // Criar o construtor do AlertDialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_finalizar_pagamento, null);
        TextView total = dialogView.findViewById(R.id.textViewPrecoProdutoItem);
        ImageView iconFormaPagamento = dialogView.findViewById(R.id.iconFormaPagamento);
        TextView nomeFormaPagamento = dialogView.findViewById(R.id.textViewNomeFormaPagamento);
        Button finalizarCompra = dialogView.findViewById(R.id.buttonFinalizarCompra);
        LinearLayout layoutTroco = dialogView.findViewById(R.id.layout_troco);
        TextView textViewTroco = dialogView.findViewById(R.id.textViewTroco);
        EditText descricao = dialogView.findViewById(R.id.editTextDescricao);
        TextView data = dialogView.findViewById(R.id.textViewDataVenda);

        if(pedido.getTotalGeral() == troco){
            layoutTroco.setVisibility(View.GONE);
        }else {
            layoutTroco.setVisibility(View.VISIBLE);
            textViewTroco.setText( brazilianReal(troco - pedido.getTotalGeral() ));
        }

        if(formaPagamento == FormaPagamento.DINHEIRO.getFormaPagamento()){
            iconFormaPagamento.setImageResource(R.drawable.money);
            nomeFormaPagamento.setText("1 - Dinheiro");
        }
        if(formaPagamento == FormaPagamento.CARTAO_DEBITO.getFormaPagamento()){
            iconFormaPagamento.setImageResource(R.drawable.cartao_de_debito);
            nomeFormaPagamento.setText("2 - Cartão Débito");
        }
        if(formaPagamento == FormaPagamento.CARTAO_CREDITO.getFormaPagamento()){
            iconFormaPagamento.setImageResource(R.drawable.cartao_de_debito);
            nomeFormaPagamento.setText("3 - Cartão Crédito");
        }
        total.setText(brazilianReal(pedido.getTotalGeral()));
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        LocalDateTime agora = LocalDateTime.now();
        data.setText(formatarLocalDateTimeParaiew(agora));

        finalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pedido.setProdutoList(produtoList);
                pedido.setAcrescimo(pedido.getAcrescimo() / 100);
                pedido.setDesconto(pedido.getDesconto() / 100);
                pedido.setObservacao(descricao.getText().toString());
                LocalDateTime agora = LocalDateTime.now();
                String formattedDateTime = formatarLocalDateTimeParaMySQL(agora);
                pedido.setDataVenda(formattedDateTime);
                //data.setText(formatarLocalDateTimeParaiew(agora));
                pedido.setFormaPagamento(formaPagamento);
                pedido.setOrderStatus(StatusOrder.CONCLUÍDO.getStatus());

                if(produtoList.size() > 0){
                    pedido.setUserId(produtoList.get(0).getUserUuid());
                }

                Log.i("FINAL-PART", pedido.toString());
                for(Produto produto: produtoList){
                    Log.i("FINAL-PART", produto.toString());
                }
                finalizarCompra.setEnabled(false);
                finalizarCompra.setText("Carregando...");

                produtoRepository.criarPedido(pedido, new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.i("CONCLUINDO", "OK"+response.code());
                            // A solicitação foi bem-sucedida (código de resposta HTTP 2xx)
                            // Faça algo aqui se necessário
                            if(response.code() == 201){
                                //finalizarCompra.setEnabled(true);
                                finalizarCompra.setText("Finalizar");
                                // finalizarCompra.setEnabled(true);
                                finalizarCompra.setText("Finalizar");

                                showDialogPrint();
                                alertDialog.dismiss();
                            }
                        } else {
                            // A solicitação falhou (código de resposta HTTP diferente de 2xx)
                            // Você pode lidar com o erro aqui
                            finalizarCompra.setEnabled(true);
                            finalizarCompra.setText("Finalizar");
                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                            Log.i("CONCLUINDO", "NÃO"+response.code()+" "+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Ocorreu um erro na comunicação com o servidor
                        // Você pode lidar com o erro aqui
                        finalizarCompra.setEnabled(true);
                        finalizarCompra.setText("Finalizar");
                        Toast.makeText(getApplicationContext(), "Erro ao Finalizar Venda", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        alertDialog.show();
    }
    public String formatarLocalDateTimeParaMySQL(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
    public String formatarLocalDateTimeParaiew(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTime.format(formatter);
    }
    private void showDialogPrint() {
        // Criar o construtor do AlertDialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_imprimir, null);

        TextView trocoText = dialogView.findViewById(R.id.textViewTroco);
        Button concluirButton = dialogView.findViewById(R.id.buttonConcluir);
        trocoText.setText(brazilianReal(troco - pedido.getTotalGeral() ));

        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        concluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                finalizarVenda();

            }
        });
        alertDialog.show();
    }
    public String brazilianReal(Double value) {
        Locale locale = new Locale("pt", "BR");

        // Criar uma instância do NumberFormat para a formatação de moeda
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        // Formatar o valor para a moeda brasileira
        String valorFormatado = format.format(value);
        return valorFormatado;
    }

    public void finalizarVenda(){


       // db.deleteProduto(produto.getId());

        List<Produto> produtoList = db.getProdutosByPedidoId(1);
        double totalTemp = 0.0;
        for (Produto product : produtoList) {
            db.deleteProduto(product.getId());
        }
        List<Pedido> pedidosTemp = db.getAllPedidos();
        Pedido pedidoTemporario = pedidosTemp.get(0);
        Pedido pedidoFicticio = new Pedido(
                1,
                null,
                0.0,
               0.0,
                produtoList,
                0.0,
                0,
                0.0,
                null
        );
        db.addOrUpdatePedido(pedidoFicticio);

        enviarDadosParaActivityA();
        finish();
    }

    private void enviarDadosParaActivityA() {
        Intent intent = new Intent(FinalizarVendaActivity.this, PdvActivity.class);
        intent.putExtra("result","algum dado aqui");
        setResult(RESULT_OK,intent);
        finish();
    }
    @Override
    public void onDataEntered(String data) {
       if(!data.isEmpty()){
           Log.i("IDIOT", String.valueOf(Double.parseDouble(data) /100));
           //troco pedido.getTotalGeral()
           troco = Double.parseDouble(data) /100;
       }else{
           troco = pedido.getTotalGeral();
       }
        showCustomDialog();
    }
}