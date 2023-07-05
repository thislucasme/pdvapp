package com.thislucasme.pdvapplication.helpers;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.adapter.ClientesCartPdvAdapter;
import com.thislucasme.pdvapplication.cadastro.CadastroClienteActivity;
import com.thislucasme.pdvapplication.callbacks.DialogTecladoAcrescimoDescontoCallBack;
import com.thislucasme.pdvapplication.callbacks.StartActivityFromAnotherScreen;
import com.thislucasme.pdvapplication.model.Cliente;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;
import com.thislucasme.pdvapplication.recycler.RecyclerItemClickListener;
import com.thislucasme.pdvapplication.viewmodel.PedidoPdvViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DialogClientes {
    private static List<Cliente> clientes = new ArrayList<>();

    public static void showModalClientes(Context context , PedidoPdvViewModel pedidoPdvViewModel){
        //clientes
        Cliente cliente = new Cliente();
        cliente.setNome("Cristino");
        clientes.add(cliente);

        cliente = new Cliente();
        cliente.setNome("Cristino");
        clientes.add(cliente);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_clientes_pdv, null);

        RecyclerView recyclerViewClientes = dialogView.findViewById(R.id.recyclerViewClientesPdv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewClientes.setLayoutManager(layoutManager);

        recyclerViewClientes.addOnItemTouchListener(
                new RecyclerItemClickListener(context.getApplicationContext(), recyclerViewClientes, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })
        );



        ClientesCartPdvAdapter clientesCartPdvAdapter = new ClientesCartPdvAdapter(clientes);
        recyclerViewClientes.setAdapter(clientesCartPdvAdapter);
        clientesCartPdvAdapter.notifyDataSetChanged();

        Button buttonAlterar = dialogView.findViewById(R.id.buttonAdicionarCliente);
        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CadastroClienteActivity.class);
                context.startActivity(intent);
            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
