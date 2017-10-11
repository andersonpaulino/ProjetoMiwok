package com.example.android.miwok;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * Created by anderson on 29/09/17.
 */

/**
 * Classe principal do app
 */
public class MainActivity extends AppCompatActivity {


    /**
     * @param savedInstanceState
     * nesse metodo eh instanciado os textviews
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView numerosTextView = (TextView) findViewById(R.id.numeros);
        TextView familiaTextView = (TextView) findViewById(R.id.familia);
        TextView coresTextView = (TextView) findViewById(R.id.cores);
        TextView frasesTextView = (TextView) findViewById(R.id.frases);

        numerosTextView.setOnClickListener(new View.OnClickListener() {
            /**onClick
             * @param view
             * aqui setamos o textview para receber um evento de clique, no qual o clique
             *redireciona para uma nova activity.
             *o intent permite sair da classe atual, e acessar a nova classe
             */
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this,
                        NumerosActivity.class);
                startActivity(intent);
            }
        });
        familiaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        FamiliaActivity.class);
                startActivity(intent);
            }
        });
        coresTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        CoresActivity.class);
                startActivity(intent);
            }
        });
        frasesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        FrasesActivity.class);
                startActivity(intent);
            }
        });
    }
}
