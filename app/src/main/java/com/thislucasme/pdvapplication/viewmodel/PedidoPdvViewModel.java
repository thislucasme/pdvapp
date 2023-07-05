package com.thislucasme.pdvapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thislucasme.pdvapplication.model.Pedido;

public class PedidoPdvViewModel extends ViewModel {
    private Pedido pedido;
    private MutableLiveData<Pedido> pedidoState = new MutableLiveData();

    public PedidoPdvViewModel() {
        this.pedido = new Pedido();
        pedidoState.setValue(pedido);
    }

    public MutableLiveData<Pedido> getPedido(){
        return pedidoState;
    }
    public void setPedido(Pedido pedido){
        pedidoState.setValue(pedido);
    }
    public void setAcrescimo(Double acrescimo){
        this.pedido.setAcrescimo(acrescimo);
        this.pedidoState.setValue(pedido);
    }
    public void setDesconto(Double desconto){
        this.pedido.setDesconto(desconto);
        this.pedidoState.setValue(pedido);
    }

}
