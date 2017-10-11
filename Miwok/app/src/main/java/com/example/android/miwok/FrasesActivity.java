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
public class FrasesActivity extends AppCompatActivity {
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
        palavras.add(new Palavra("where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        palavras.add(new Palavra("what's your name?","tinna oyase'na",R.raw.phrase_what_is_your_name));
        palavras.add(new Palavra("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        palavras.add(new Palavra("How are you feeling?","michaksas?",R.raw.phrase_how_are_you_feeling));
        palavras.add(new Palavra("I'm feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        palavras.add(new Palavra("Are you coming?","aanas'aa?",R.raw.phrase_are_you_coming));
        palavras.add(new Palavra("Yes, I'm coming.","haa'aanam",R.raw.phrase_yes_im_coming));
        palavras.add(new Palavra("I'm coming.","aanam",R.raw.phrase_im_coming));
        palavras.add(new Palavra("Let's go.","youwutis",R.raw.phrase_lets_go));
        palavras.add(new Palavra("Come here.","anni'nem",R.raw.phrase_come_here));
        /*
         *a activity cria seu adapter passando o contexto, vetor de palavras e recurso de cor
         */
        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras, R.color.categoria_frases);

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
                            MediaPlayer.create(FrasesActivity.this, palavraClicada.getReferenciaAudio());

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