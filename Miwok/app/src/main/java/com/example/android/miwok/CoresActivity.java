package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by anderson on 29/09/17.
 */
public class CoresActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer; /*variavel local para utilizar a classe mediaplayer*/
    private AudioManager mAudioManager;/*variavel local para utilizar a classe audioplayer*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_palavras);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Palavra> palavras = new ArrayList<>();
        /**
         * arraylist recebendo como parametros uma palavra, sua traducao miwok,uma imagem e um audio
         */
        palavras.add(new Palavra("red","weteti",R.drawable.color_red,R.raw.color_red));
        palavras.add(new Palavra("green","chokokki",R.drawable.color_green,R.raw.color_green));
        palavras.add(new Palavra("brown","takaakki",R.drawable.color_brown,R.raw.color_brown));
        palavras.add(new Palavra("gray","topoppi",R.drawable.color_gray,R.raw.color_gray));
        palavras.add(new Palavra("black","kululli",R.drawable.color_black,R.raw.color_black));
        palavras.add(new Palavra("white","keleli",R.drawable.color_white,R.raw.color_white));
        palavras.add(new Palavra("dusty yellow","topiisa",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        palavras.add(new Palavra("mustard yellow","chiwiita",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        /*
         *a activity cria seu adapter passando o contexto, vetor de palavras e recurso de cor
         */

        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras,R.color.categoria_cores);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itensAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Palavra palavraClicada = palavras.get(position);
                releaseMediaPlayer();
                /*a variavel resultado solicita o audiofocus*/
                int resultado = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (resultado == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer =
                            MediaPlayer.create(CoresActivity.this, palavraClicada.getReferenciaAudio());

                    mMediaPlayer.start();

                }

                /*
                     *cria o  objeto mediaplayer e o prepara para ser tocado, o iniciando logo depois
                     */

                MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        releaseMediaPlayer();
                    }
                };
                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            }
            /**
             * criado para tratar como esse app lida com outras aplicaçoes que requisitam o audio
             */
            AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
                    new AudioManager.OnAudioFocusChangeListener(){
                        @Override
                        public void onAudioFocusChange(int focusChange){
                            /* quando ocorre perda de audiofocus temporaria, o audio eh pausado ou o volume diminuido*/
                            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                                mMediaPlayer.pause();
                                mMediaPlayer.seekTo(0);
                            }/* retoma a reproducao de audio*/else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                                mMediaPlayer.start();
                            }/* o recurso do mediaplayer eh parado e a memoria liberada*/else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                                releaseMediaPlayer();
                            }
                        }
                    };

        });

    }
    /**
     * metodo utilzado quando o aplicativo eh abandonado pelo usuario
     */
    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }
    /**
     * libera recurso usado pelo mediaplayer
     */
    private void releaseMediaPlayer(){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;

            /**
             mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener); problema aqui nao solucionado*/
        }
    }
}
