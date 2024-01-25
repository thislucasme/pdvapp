package com.thislucasme.pdvapplication.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.thislucasme.pdvapplication.model.EstudanteList;
import com.thislucasme.pdvapplication.model.Pedido;
import com.thislucasme.pdvapplication.model.Produto;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 20 ;
    private static final String DATABASE_NAME = "studentlist";
    private static final String TABLE_STUDENTS = "estudantes";
    private static final String TABLE_PEDIDO = "pedidos";
    private static final String TABLE_PRODUTOS = "produtos";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String TAG = "JUJUBI";
    private static final String ID = "id";
    private static final String OBSERVACAO = "observacao";
    private static final String ACRESCIMO = "acrescimo";
    private static final String DESCONTO = "desconto";
    private static final String TOTAL_GERAL = "totalGeral";
    private static final String QUANTIDADE_PRODUTO_PEDIDO = "quantidade_produto_pedido";
    private static final String ULTIMO_VALOR_PRODUTO = "ultimo_valor_produto";
    private static final String ULTIMO_NOME_PRODUTO = "ultimo_nome_produto";


    private static final String PROMOCAO_ID = "pedidos";
    private static final String ESTOQUE = "estoque";
    private static final String CODIGO_BARRAS = "codigoBarras";
    private static final String OBSERVACAO_PRODUTO = "observacao";
    private static final String CONTROLAR_ESTOQUE = "controlarEstoque";
    private static final String VENDA_FRACIONADA = "vendaFracionada";
    private static final String VALOR_ABERTO = "valorAberto";
    private static final String FORNECEDORES_ID = "fornecedoresId";
    private static final String DESCRICAO = "descricao";
    private static final String URL_IMAGEM = "urlImagem";
    private static final String PRECO_CUSTO = "preco_custo";
    private static final String PRECO_VENDA = "preco_venda";
    private static final String QUANTIDADE = "quantidade";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"onCreate.");
        try{
            String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_NAME + " TEXT"
                    + ")";
            db.execSQL(CREATE_STUDENTS_TABLE);

            String CREATE_PEDIDOS_TABLE = "CREATE TABLE " + TABLE_PEDIDO + "(" +
                    "    id INTEGER PRIMARY KEY," +
                    "    observacao TEXT," +
                    "    acrescimo REAL DEFAULT 0.0," +
                    "    desconto REAL DEFAULT 0.0," +
                    "    totalGeral REAL DEFAULT 0.0," +
                   "quantidade_produto_pedido INTEGER DEFAULT 0," +
                    "ultimo_valor_produto REAL DEFAULT 0.0," +
                    "ultimo_nome_produto TEXT" +
                    ");";
            db.execSQL(CREATE_PEDIDOS_TABLE);

            String CREATE_PRODUTOS_TABLE = "CREATE TABLE " + TABLE_PRODUTOS + "(" +
                    "id INTEGER PRIMARY KEY," +
                    "categoriasId INTEGER," +
                    "promocaoId INTEGER," +
                    "estoque INTEGER," +
                    "codigoBarras TEXT," +
                    "observacao TEXT," +
                    "controlarEstoque INTEGER," +
                    "vendaFracionada INTEGER," +
                    "valorAberto REAL," +
                    "fornecedoresId INTEGER," +
                    "usersId INTEGER," +
                    "descricao TEXT," +
                    "urlImagem TEXT," +
                    "preco_custo REAL," +
                    "preco_venda REAL," +
                    "quantidade INTEGER," +
                    "id_pedido INTEGER," + // Chave estrangeira referenciando a tabela de pedidos
                    "uuid TEXT,"+
                    "uuid_user TEXT,"+
                    "FOREIGN KEY (id_pedido) REFERENCES " + TABLE_PEDIDO + "(id)" +
                    ");";

            db.execSQL(CREATE_PRODUTOS_TABLE);
            Log.i(TAG,"Tabelas criadas.");
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG,"Erro ao criar tabelas."+e.getMessage());
        }

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        // Create tables again
        onCreate(db);
    }
    // code to add the new student
    public void addStudent(EstudanteList student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName()); // studentName

        // Inserting Row
        db.insert(TABLE_STUDENTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void addOrUpdatePedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBSERVACAO, pedido.getObservacao());
        values.put(ACRESCIMO, pedido.getAcrescimo());
        values.put(DESCONTO, pedido.getDesconto());
        values.put(TOTAL_GERAL, pedido.getTotalGeral());
        values.put(QUANTIDADE_PRODUTO_PEDIDO, pedido.getQuantidadeProdutosPedido());
        values.put(ULTIMO_VALOR_PRODUTO, pedido.getUltimoValorProduto());
        values.put(ULTIMO_NOME_PRODUTO, pedido.getUltimoNomeProduto());
        Log.i("JUJUBI", pedido.getUltimoNomeProduto()+", utimo nome");

        try {
            // Verificar se já existe um registro de pedido com id 1
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PEDIDO + " WHERE " + KEY_ID + " = 1", null);

            if (cursor.getCount() > 0) {
                // Já existe um registro de pedido com id 1, então faz um update
                long rowId = db.update(TABLE_PEDIDO, values, KEY_ID + " = 1", null);

                if (rowId == -1) {
                    Log.i(TAG, "Erro ao atualizar o pedido no banco de dados.");
                } else {
                    Log.i(TAG, "Pedido atualizado com sucesso.");
                }
            } else {
                // Não existe um registro de pedido com id 1, então faz uma inserção
                long rowId = db.insert(TABLE_PEDIDO, null, values);

                if (rowId == -1) {
                    Log.i(TAG, "Erro ao inserir o pedido no banco de dados.");
                } else {
                    Log.i(TAG, "Pedido inserido com sucesso.");
                }
            }

            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "Erro ao inserir/atualizar o pedido no banco de dados. Exceção: " + e.getMessage());
            e.printStackTrace(); // Exibir a exceção no log
        } finally {
            db.close();
        }
    }

    public void addPedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues pedidoValues = new ContentValues();
        pedidoValues.put(OBSERVACAO, pedido.getObservacao());
        pedidoValues.put(ACRESCIMO, pedido.getAcrescimo());
        pedidoValues.put(DESCONTO, pedido.getDesconto());
        pedidoValues.put(TOTAL_GERAL, pedido.getTotalGeral());
        pedidoValues.put(QUANTIDADE_PRODUTO_PEDIDO, pedido.getQuantidadeProdutosPedido());
        pedidoValues.put(ULTIMO_VALOR_PRODUTO, pedido.getUltimoValorProduto());
        pedidoValues.put(ULTIMO_NOME_PRODUTO, pedido.getUltimoNomeProduto());

        try {
            // Inserir o pedido na tabela de pedidos
            long pedidoRowId = db.insert(TABLE_PEDIDO, null, pedidoValues);

            if (pedidoRowId == -1) {
                Log.i(TAG, "Erro ao inserir o pedido no banco de dados.");
            } else {
                Log.i(TAG, "Pedido inserido com sucesso");

                // Inserir os produtos associados ao pedido na tabela de produtos
                for (Produto produto : pedido.getProdutoList()) {
                    ContentValues produtoValues = new ContentValues();
                    produtoValues.put("id", pedidoRowId); // PEDIDO_ID é o nome da coluna na tabela de produtos que armazena a chave estrangeira

                    produtoValues.put(PROMOCAO_ID, produto.getPromocaoId());
                    produtoValues.put(ESTOQUE, produto.getEstoque());
                    produtoValues.put(CODIGO_BARRAS, produto.getCodigoBarras());
                    produtoValues.put(OBSERVACAO_PRODUTO, produto.getObservacao());
                    produtoValues.put(CONTROLAR_ESTOQUE, produto.getControlarEstoque());
                    produtoValues.put(VENDA_FRACIONADA, produto.getVendaFracionada());
                    produtoValues.put(VALOR_ABERTO, produto.getValorAberto());
                    produtoValues.put(FORNECEDORES_ID, produto.getFornecedoresId());
                    //produtoValues.put(USERS_ID, produto.getUsersId());
                    produtoValues.put(DESCRICAO, produto.getDescricao());
                    produtoValues.put(URL_IMAGEM, produto.getUrlImagem());
                    produtoValues.put(PRECO_CUSTO, produto.getPreco_custo());
                    produtoValues.put(PRECO_VENDA, produto.getPreco_venda());
                    produtoValues.put(QUANTIDADE, produto.getQuantidade());

                    // Inserir o produto associado ao pedido
                    long produtoRowId = db.insert(TABLE_PRODUTOS, null, produtoValues);

                    if (produtoRowId == -1) {
                        Log.i(TAG, "Erro ao inserir o produto no banco de dados.");
                    } else {
                        Log.i(TAG, "Produto inserido com sucesso");
                    }
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Erro ao inserir o pedido no banco de dados. Exceção: " + e.getMessage());
            e.printStackTrace(); // Exibir a exceção no log
        } finally {
            db.close();
        }
    }

    public void updatePedido(Pedido pedido) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OBSERVACAO, pedido.getObservacao());
        values.put(ACRESCIMO, pedido.getAcrescimo());
        values.put(DESCONTO, pedido.getDesconto());
        values.put(TOTAL_GERAL, pedido.getTotalGeral());
        values.put(QUANTIDADE_PRODUTO_PEDIDO, pedido.getQuantidadeProdutosPedido());
        values.put(ULTIMO_VALOR_PRODUTO, pedido.getUltimoValorProduto());
        values.put(ULTIMO_NOME_PRODUTO, pedido.getUltimoNomeProduto());

        try {
            long rowId = db.update(TABLE_PEDIDO,  values, KEY_ID + " = ?",
                    new String[] {String.valueOf(pedido.getIdentificador())});
            if (rowId == -1) {
                Log.i(TAG,"Erro ao atualizar o pedido no banco de dados.");
            } else {
                Log.i(TAG, "Inserido com Successo");
            }
        } catch (Exception e) {
            Log.i(TAG,"Erro ao atualizar o pedido no banco de dados error exception: "+e.getMessage());
            e.printStackTrace(); // Exibir a exceção no log
        } finally {
            db.close();
        }
    }
    public List<Produto> getProdutosByPedidoId(int idPedido) {
        List<Produto> produtos = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Consulta SQL para obter produtos com base no id_pedido
        String selectQuery = "SELECT * FROM " + TABLE_PRODUTOS +
                " WHERE id_pedido = " + idPedido;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    System.out.println(i+": "+columnName + ": " + columnValue);
                    Log.i("KAYUMOVA", i+": "+columnName + ": " + columnValue);
                }
                System.out.println("--------------------"); // Separador entre as linhas
                Log.i("KAYUMOVA", "--------------------");
            } while (cursor.moveToNext());
        }
        // Loop através das linhas e adicionando à lista

        if (cursor.moveToFirst()) {
            do {
                Produto produto = new Produto();
                produto.setId(cursor.getInt(0));
                produto.setCategoriasId(cursor.getInt(1));
                produto.setPromocaoId(cursor.getInt(2));
                produto.setEstoque(cursor.getInt(3));
                produto.setCodigoBarras(cursor.getString(4));
                produto.setObservacao(cursor.getString(5));
                produto.setControlarEstoque(cursor.getInt(6));
                produto.setVendaFracionada(cursor.getInt(7));
                produto.setValorAberto(cursor.getInt(8));
                produto.setFornecedoresId(cursor.getInt(9));
                produto.setUsersId(cursor.getInt(10));
                produto.setDescricao(cursor.getString(11));
                produto.setUrlImagem(cursor.getString(12));
                produto.setPreco_custo(cursor.getDouble(13));
                produto.setPreco_venda(cursor.getDouble(14));
                produto.setQuantidade(cursor.getInt(15));
                produto.setUuid(cursor.getString(17));
               produto.setUserUuid(cursor.getString(18));
//                produto.setIdPedido(cursor.getInt(16)); // Supondo que o índice 16 seja a coluna id_pedido

                // Adiciona o produto à lista de produtos
                produtos.add(produto);
            } while (cursor.moveToNext());
        }

        // Fecha o cursor e retorna a lista de produtos
        cursor.close();
        return produtos;
    }
    public void deleteProduto(long produtoId) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            int rowsAffected = db.delete(TABLE_PRODUTOS, "id=?", new String[]{String.valueOf(produtoId)});

            if (rowsAffected > 0) {
                Log.i(TAG, "Produto deletado com sucesso.");
            } else {
                Log.i(TAG, "Nenhum produto deletado. Produto com ID não encontrado.");
            }
        } catch (Exception e) {
            Log.i(TAG, "Erro ao deletar o produto no banco de dados: " + e.getMessage());
            e.printStackTrace(); // Exibir a exceção no log
        } finally {
            db.close();
        }
    }

    public void updateProduto(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("categoriasId", produto.getCategoriasId());
        values.put("promocaoId", produto.getPromocaoId());
        values.put("estoque", produto.getEstoque());
        values.put("codigoBarras", produto.getCodigoBarras());
        values.put("observacao", produto.getObservacao());
        values.put("controlarEstoque", produto.getControlarEstoque());
        values.put("vendaFracionada", produto.getVendaFracionada());
        values.put("valorAberto", produto.getValorAberto());
        values.put("fornecedoresId", produto.getFornecedoresId());
        values.put("usersId", produto.getUsersId());
        values.put("descricao", produto.getDescricao());
        values.put("urlImagem", produto.getUrlImagem());
        values.put("preco_custo", produto.getPreco_custo());
        values.put("preco_venda", produto.getPreco_venda());
        values.put("quantidade", produto.getQuantidade());

        try {
            int rowsAffected = db.update(TABLE_PRODUTOS, values, "id=?", new String[]{String.valueOf(produto.getId())});

            if (rowsAffected > 0) {
                Log.i(TAG, "Produto atualizado com sucesso.");
            } else {
                Log.i(TAG, "Nenhum produto atualizado. Produto com ID não encontrado.");
            }
        } catch (Exception e) {
            Log.i(TAG, "Erro ao atualizar o produto no banco de dados: " + e.getMessage());
            e.printStackTrace(); // Exibir a exceção no log
        } finally {
            db.close();
        }
    }

    public void addProduto(Produto produto, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("categoriasId", produto.getCategoriasId());
        values.put("promocaoId", produto.getPromocaoId());
        values.put("estoque", produto.getEstoque());
        values.put("codigoBarras", produto.getCodigoBarras());
        values.put("observacao", produto.getObservacao());
        values.put("controlarEstoque", produto.getControlarEstoque());
        values.put("vendaFracionada", produto.getVendaFracionada());
        values.put("valorAberto", produto.getValorAberto());
        values.put("fornecedoresId", produto.getFornecedoresId());
        values.put("usersId", produto.getUsersId());
        values.put("descricao", produto.getDescricao());
        values.put("urlImagem", produto.getUrlImagem());
        values.put("preco_custo", produto.getPreco_custo());
        values.put("preco_venda", produto.getPreco_venda());
        values.put("quantidade", produto.getQuantidade());
        values.put("id_pedido", id);
        values.put("uuid", produto.getUuid());
        values.put("uuid_user", produto.getUserUuid());


        try {
            long rowId = db.insert(TABLE_PRODUTOS, null, values);
            if (rowId == -1) {
                Log.i(TAG, "Erro ao inserir o produto no banco de dados.");
            } else {
                Log.i(TAG, "Produto inserido com sucesso.");
            }
        } catch (Exception e) {
            Log.i(TAG, "Erro ao inserir o produto no banco de dados: " + e.getMessage());
            e.printStackTrace(); // Exibir a exceção no log
        } finally {
            db.close();
        }
    }

    public List<Pedido> getAllPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PEDIDO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pedido pedido = new Pedido();
                pedido.setIdentificador(Integer.parseInt(cursor.getString(0)));
                pedido.setObservacao(cursor.getString(1));
                pedido.setAcrescimo(cursor.getDouble(2));
                pedido.setDesconto(cursor.getDouble(3));
                pedido.setTotalGeral(cursor.getDouble(4));
                pedido.setQuantidadeProdutosPedido(cursor.getInt(5));
                pedido.setUltimoValorProduto(cursor.getDouble(6));
                pedido.setUltimoNomeProduto(cursor.getString(7));

                // Adding student to list
                pedidos.add(pedido);
            } while (cursor.moveToNext());
        }

        // return student list
        return pedidos;
    }
    public List<EstudanteList> getAllStudentList() {
        List<EstudanteList> studentList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                EstudanteList student = new EstudanteList();
                student.setId(Integer.parseInt(cursor.getString(0)));
                student.setName(cursor.getString(1));
                // Adding student to list
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        // return student list
        return studentList;
    }

    // code to update the single student
    public int updateStudent(EstudanteList student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());

        // updating row
        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }


    // Deleting single student
    public void deleteStudent(EstudanteList student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
        db.close();
    }


    // Getting student Count
    public int getStudentListCount() {
        String countQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
