package com.thislucasme.pdvapplication.helpers;
import android.app.AlertDialog;
import android.content.Context;
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
import com.thislucasme.pdvapplication.viewmodel.PedidoPdvViewModel;

public class DialogAcrescimoDecrescimo {
    private static String currentTextTecladoNumerico = "";
    public static void showTecladoNumericoDescontoAcrescimo(Context context, DialogTecladoAcrescimoDescontoCallBack dialogTecladoAcrescimoDesconto, PedidoPdvViewModel pedidoPdvViewModel, String tipo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.keyboard_rasc, null);
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
        pedidoPdvViewModel.getPedido().observe((LifecycleOwner) context, new Observer<Pedido>() {
            @Override
            public void onChanged(Pedido pedido) {
                double value = Double.parseDouble(String.valueOf(pedido.getTotalGeral() * 100)) / 100;
                 String formattedText = String.format("R$ %.2f", value);
                 total.setText(formattedText);
            }
        });

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
               if(!currentTextTecladoNumerico.equals("")){
                   if(tipo.equals("acrescimo")){
                       pedidoPdvViewModel.setAcrescimo(Double.valueOf(currentTextTecladoNumerico));
                   }else{
                       pedidoPdvViewModel.setDesconto(Double.valueOf(currentTextTecladoNumerico));
                   }

               }
                currentTextTecladoNumerico = "";

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
