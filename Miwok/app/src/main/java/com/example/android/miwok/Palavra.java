package com.example.android.miwok;

/**
 * Created by anderson on 29/09/17.
 */

public class Palavra {

    /**
     * recebe traducao padrao
     */
    private String mTraducaoPadrao;
    /**
     * recebe traducao miwok
     */
    private String mTraducaoMiwok;
    /**
     * recebe audio
     */
    private int mReferenciaAudio;
    /**
     * recebe uma imagem
     */
    private int mReferenciaImagem = SEM_IMAGEM_FORNECIDA;
    private static final int SEM_IMAGEM_FORNECIDA = -1;

    /**
     * Esse construtor recebe os seguintes parametros
     * @param traducaoPadrao armazena a palavra na linguagem padrao
     * @param traducaoMiwok armazena a palavra na linguagem miwok
     * @param referenciaImagem armazena uma imagem
     * @param referenciaAudio armazena um audio
     */
    public Palavra(String traducaoPadrao, String traducaoMiwok, int referenciaImagem, int referenciaAudio) {

        mTraducaoPadrao = traducaoPadrao;
        mTraducaoMiwok = traducaoMiwok;
        mReferenciaImagem = referenciaImagem;
        mReferenciaAudio = referenciaAudio;
    }

    /**Construtor criado para tratar em especial o array da activity Frases, que nao recebe uma imagem
     * @param traducaoPadrao armazena a palavra na linguagem padrao
     * @param traducaoMiwok  armazena a palavra na linguagem miwok
     * @param referenciaAudio armazena um audio
     */
    public Palavra(String traducaoPadrao, String traducaoMiwok, int referenciaAudio){
        mTraducaoPadrao = traducaoPadrao;
        mTraducaoMiwok = traducaoMiwok;
        mReferenciaAudio = referenciaAudio;
    }

    /**
     * @return metodo usado para verificar se existe uma imagem para exibir
     */
    public boolean hasImagem(){
        return mReferenciaImagem != SEM_IMAGEM_FORNECIDA;
    }

    public String getTraducaoPadrao() {
        return mTraducaoPadrao;
    }

    public String getTraducaoMiwok() {
        return mTraducaoMiwok;
    }

    public int getReferenciaImagem(){return mReferenciaImagem;}

    public int getReferenciaAudio(){return mReferenciaAudio;}
}
