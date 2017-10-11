package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by anderson on 04/10/17.
 */

public class PalavraAdapter extends ArrayAdapter<Palavra> {
    private int mCorFundo;

    /** Construtor recebe...
     * @param context recebe a activity onde será criado o adapter
     * @param palavras array de palavras
     * @param corFundo variavel para adicionar cor
     */
    public PalavraAdapter(Activity context, ArrayList<Palavra>palavras, int corFundo) {
        super(context, 0,palavras);
        mCorFundo = corFundo;
    }

    /**Este metodo que popula cada item da lista um a um
     * @param position a posicao  do item junto às informaçoes do adapter
     * @param convertView  antiga view caso seja reutilizada
     * @param parent o pai da view atual
     * @return retorna a lista
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent){
        View itemListaView = convertView;
        if (itemListaView == null){
            itemListaView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_lista,parent,false);
        }
        Palavra palavraAtual = getItem(position);

        TextView miwok = (TextView)itemListaView.findViewById(R.id.miwok);
        miwok.setText(palavraAtual.getTraducaoMiwok());

        TextView padrao = (TextView)itemListaView.findViewById(R.id.padrao);
        padrao.setText(palavraAtual.getTraducaoPadrao());
/*
 * adiciona o recurso de imagem informando um id
 * abaixo criamos uma condicao, em que se há imagem o componente é exibido
 * caso contrario, o componente nao é exibido e nao ocupa espaço na tela
 */
        ImageView imagem = (ImageView)itemListaView.findViewById(R.id.container_imagem);
        if (palavraAtual.hasImagem()){
            imagem.setImageResource(palavraAtual.getReferenciaImagem());
            imagem.setVisibility(View.VISIBLE);
        }else{
            imagem.setVisibility(View.GONE);
        }

        /* LinearLayout
         * resgata o componente de cada lista e adiciona a cor à ele
         */
        LinearLayout layout = (LinearLayout) itemListaView.findViewById(R.id.container_global);
        layout.setBackgroundResource(mCorFundo);


        return  itemListaView;
    }
}
