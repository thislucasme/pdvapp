package com.thislucasme.pdvapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thislucasme.pdvapplication.model.Pedido;

public class PedidoPdvViewModel extends ViewModel {
    private MutableLiveData<Pedido> pedidoState = new MutableLiveData(new Pedido());

    public MutableLiveData<Pedido> getPedido(){
        return pedidoState;
    }
    public void setPedido(Pedido pedido){
        pedidoState.setValue(pedido);
    }
}
