package com.thislucasme.pdvapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thislucasme.pdvapplication.R;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class ProdutosCartPdvAdapter extends RecyclerView.Adapter<ProdutosCartPdvAdapter.MyViewHolder> {

    private Pedido pedido;

    public ProdutosCartPdvAdapter(Pedido pedidoItem) {
        Log.i("JUIO", pedidoItem.getProdutoList().size()+"lucas");

        this.pedido = pedidoItem;
    }


    private Context mcContext;

    //public ProdutoAdapter(List<Produto> produtos, Context context) {
    //    this.mcContext = context;
       // this.produto = produtos;
   // }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_edicao_pdv  , parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Produto produto = pedido.getProdutoList().get(i);
        Log.i("JUIO", pedido.getProdutoList().size()+"miue");
        for (Produto product : pedido.getProdutoList()) {
            Log.i("JUIO", product.toString());
        }
        holder.nome.setText(produto.getDescricao());
        holder.preco.setText(testBrazilianReal(produto.getPreco_venda() * produto.getEstoque()));
        holder.id.setText(String.valueOf(produto.getId()));
        holder.quantidade.setText(String.valueOf(produto.getEstoque()));

        Picasso.get().load(produto.getUrlImagem()).into(holder.iconProduto);

        //if(mensagem.getTimeStamp() != null){

          //  String hora = DataHora.getHour(mensagem.getTimeStamp());
           // holder.hora.setText(hora);
        //}


    }
    @Override
    public int getItemCount() {
        return pedido.getProdutoList().size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // GridView gridView;

        ImageView iconProduto;
        TextView nome;
        TextView preco;
        TextView id;
        TextView quantidade;


        public MyViewHolder(View itemView) {
            super(itemView);

            iconProduto = itemView.findViewById(R.id.iconProduto);
            nome = itemView.findViewById(R.id.textViewNomeProdutoItem);
            preco = itemView.findViewById(R.id.textViewPrecoProdutoItem);
            id = itemView.findViewById(R.id.textViewIdItem);
            quantidade = itemView.findViewById(R.id.textViewQuantidadeItem);
            //hora = itemView.findViewById(R.id.textViewTime);
            //gridView = itemView.findViewById(R.id.grid_view);

//            imageView = itemView.findViewById(R.id.imagemProfileNotification);
//            titulo = itemView.findViewById(R.id.textViewTitiloNotification);
//            descricao = itemView.findViewById(R.id.textViewDescricaoNotification);
//            aceitar = itemView.findViewById(R.id.buttonAceitarNotificacoes);
//
//            aceitar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //Mensagem mensagem =  mensagems.get(getAbsoluteAdapterPosition());

        }

    }
    public String testBrazilianReal(Double value) {
        Locale locale = new Locale("pt", "BR");

        // Criar uma instância do NumberFormat para a formatação de moeda
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        // Formatar o valor para a moeda brasileira
        String valorFormatado = format.format(value);
        return valorFormatado;
    }


}