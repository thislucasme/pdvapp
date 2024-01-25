package com.thislucasme.pdvapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thislucasme.pdvapplication.model.Pedido;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM pedidos")
    Pedido getPedidos();

    @Insert
    void insetPedido(Pedido pedido);
}
