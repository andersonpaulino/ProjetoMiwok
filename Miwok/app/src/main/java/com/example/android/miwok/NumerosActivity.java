package com.example.android.miwok;

import android.content.Context;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by anderson on 29/09/17.
 */
public class NumerosActivity extends AppCompatActivity {


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
        palavras.add(new Palavra("um","lutti",R.drawable.number_one,R.raw.number_one));
        palavras.add(new Palavra("dois","otiiko",R.drawable.number_two,R.raw.number_two));
        palavras.add(new Palavra("tres","tolookosu",R.drawable.number_three,R.raw.number_three));
        palavras.add(new Palavra("quatro","oyiisa",R.drawable.number_four,R.raw.number_four));
        palavras.add(new Palavra("cinco","massokka",R.drawable.number_five,R.raw.number_five));
        palavras.add(new Palavra("seis","temmokka",R.drawable.number_six,R.raw.number_six));
        palavras.add(new Palavra("sete","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        palavras.add(new Palavra("oito","kawinta",R.drawable.number_eight,R.raw.number_eight));
        palavras.add(new Palavra("nove","wo'e",R.drawable.number_nine,R.raw.number_nine));
        palavras.add(new Palavra("dez","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        /*
         *a activity cria seu adapter passando o contexto, vetor de palavras e recurso de cor
         */
        PalavraAdapter itensAdapter = new PalavraAdapter(this, palavras,R.color.categoria_numeros);

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
                    /*
                     *cria o  objeto mediaplayer e o prepara para ser tocado, o iniciando logo depois
                     */
                    mMediaPlayer =
                            MediaPlayer.create(NumerosActivity.this, palavraClicada.getReferenciaAudio());

                    mMediaPlayer.start();

                }



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
                        /**
                         * @param focusChange aqui sao passadas as condiçoes para tratar o audiofocus
                         */
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
        if (mMediaPlayer != null  ){
            mMediaPlayer.release();
            mMediaPlayer = null;


           /* mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener); problema aqui nao solucionado*/
        }
    }
}