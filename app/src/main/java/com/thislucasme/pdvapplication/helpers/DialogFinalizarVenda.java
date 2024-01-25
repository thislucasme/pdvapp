package com.thislucasme.pdvapplication.helpers;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.callbacks.DialogTecladoAcrescimoDescontoCallBack;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.sqlite.DatabaseHandler;
import com.thislucasme.pdvapplication.viewmodel.PedidoPdvViewModel;

import java.util.List;

public class DialogFinalizarVenda {
    private static String currentTextTecladoNumerico = "";
    public static void showTecladoNumericoFinalizarVenda(Context context, DialogTecladoAcrescimoDescontoCallBack dialogTecladoFinalizarVenda){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.keyboard_finalizar, null);
        Button button = dialogView.findViewById(R.id.buttonConfirmar);

        Button um = dialogView.findViewById(R.id.buttonUm);
        Button dois = dialogView.findViewById(R.id.buttonDois);
        Button tres = dialogView.findViewById(R.id.buttonTres);
        Button quatro = dialogView.findViewById(R.id.buttonQuatro);
        Button cinco = dialogView.findViewById(R.id.buttonCinco);
        Button seis = dialogView.findViewById(R.id.buttonSeis);
        Button sete = dialogView.findViewById(R.id.buttonSete);
        Button oito = dialogView.findViewById(R.id.buttonOito);
        Button nove = dialogView.findViewById(R.id.buttonNove);
        Button zero = dialogView.findViewById(R.id.buttonZero);
        Button clear = dialogView.findViewById(R.id.buttonClear);
        ImageButton backSpace = dialogView.findViewById(R.id.buttonBackSpace);
        TextView desconto = dialogView.findViewById(R.id.textViewDesconto);
        TextView total = dialogView.findViewById(R.id.textViewTotal);

        //double value = Double.parseDouble(String.valueOf(pedido.getTotalGeral() * 100)) / 100;
       // String formattedText = String.format("R$ %.2f", value);
       // total.setText(formattedText);

        //total.setText(testeViewModel.getText());
        DatabaseHandler db =   new DatabaseHandler(context);
        List<Pedido> pedidosTemp = db.getAllPedidos();
        Pedido pedidoTemporario = pedidosTemp.get(0);
        String formattedText = String.format("R$ %.2f", pedidoTemporario.getTotalGeral());
        total.setText(formattedText);
       // desconto.setText(formattedText);
        //DialogFinalizarVenda.currentTextTecladoNumerico = String.valueOf(pedidoTemporario.getTotalGeral() * 100);
        desconto.setText(String.format("R$ %.2f", Double.parseDouble(String.valueOf(pedidoTemporario.getTotalGeral())) ));
//        if(tipo.equals("acrescimo")){
//            String formattedAcrescimo = String.format("R$ %.2f", pedidoTemporario.getAcrescimo() / 100);
//            desconto.setText(formattedAcrescimo);
//        }else{
//            String formattedDesconto = String.format("R$ %.2f", pedidoTemporario.getDesconto() / 100);
//            desconto.setText(formattedDesconto);
//        }


        um.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("1", desconto);
                //testeViewModel.setText("Çucas");
            }
        });
        dois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("2", desconto);
            }
        });
        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("3", desconto);
            }
        });
        quatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("4", desconto);
            }
        });
        cinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("5", desconto);
            }
        });
        seis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("6", desconto);
            }
        });
        sete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("7", desconto);
            }
        });
        oito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("8", desconto);
            }
        });
        nove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("9", desconto);
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToDisplay("0", desconto);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearDisplay(desconto);
            }
        });
        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backspaceDisplay(desconto);
            }
        });

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                dialogTecladoFinalizarVenda.onDataEntered(currentTextTecladoNumerico);
//               if(!currentTextTecladoNumerico.equals("")){
//                   if(tipo.equals("acrescimo")){
//                       pedidoPdvViewModel.setAcrescimo(Double.valueOf(currentTextTecladoNumerico));
//                       pedidoTemporario.setAcrescimo(Double.valueOf(currentTextTecladoNumerico));
//                       db.addOrUpdatePedido(pedidoTemporario);
//                       Log.i("JAJA", pedidoPdvViewModel.getPedido().getValue().toString());
//                       Log.i("JAJA", "======================");
//                       Log.i("JAJA", pedidoTemporario.toString());
//                   }else{
//                       pedidoPdvViewModel.setDesconto(Double.valueOf(currentTextTecladoNumerico));
//                       pedidoTemporario.setDesconto(Double.valueOf(currentTextTecladoNumerico));
//                       db.addOrUpdatePedido(pedidoTemporario);
//                       Log.i("JAJA", pedidoPdvViewModel.getPedido().getValue().toString());
//                       Log.i("JAJA", "======================");
//                       Log.i("JAJA", pedidoTemporario.toString());
//                   }
//
//               }
//                currentTextTecladoNumerico = "";

            }
        });
        alertDialog.show();
    }

    private static void addToDisplay(String text, TextView desconto) {
        currentTextTecladoNumerico += text;
        updateDisplay(desconto);
    }

    private static void clearDisplay(TextView desconto) {
        currentTextTecladoNumerico = "0";
        updateDisplay(desconto);
    }

    private static void backspaceDisplay(TextView desconto) {
        if (!currentTextTecladoNumerico.isEmpty()) {
            currentTextTecladoNumerico = currentTextTecladoNumerico.substring(0, currentTextTecladoNumerico.length() - 1);
            updateDisplay(desconto);
        }
    }

    private static void updateDisplay(TextView desconto) {
        if (currentTextTecladoNumerico.isEmpty()) {
            double value = Double.parseDouble("0") / 100;
            String formattedText = String.format("R$ %.2f", value);
            desconto.setText(formattedText);
        } else {
            double value = Double.parseDouble(currentTextTecladoNumerico) / 100;
            String formattedText = String.format("R$ %.2f", value);
            desconto.setText(formattedText);
        }
    }
}
