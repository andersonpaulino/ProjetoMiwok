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

public class FamiliaActivity extends AppCompatActivity {
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
        palavras.add(new Palavra("father","apa",R.drawable.family_father,R.raw.family_father));
        palavras.add(new Palavra("mother","ata",R.drawable.family_mother,R.raw.family_mother));
        palavras.add(new Palavra("son","angsi",R.drawable.family_son,R.raw.family_son));
        palavras.add(new Palavra("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        palavras.add(new Palavra("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        palavras.add(new Palavra("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        palavras.add(new Palavra("older sister","tete",R.drawable.family_older_sister,R.raw.family_older_sister));
        palavras.add(new Palavra("younger sister","kalliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        palavras.add(new Palavra("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        palavras.add(new Palavra("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_grandfather));
        /*
         *a activity cria seu adapter passando o contexto, vetor de palavras e recurso de cor
         */
        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras,R.color.categoria_familia);

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
                            MediaPlayer.create(FamiliaActivity.this, palavraClicada.getReferenciaAudio());

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