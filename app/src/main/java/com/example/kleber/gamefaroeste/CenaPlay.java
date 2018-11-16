package com.example.kleber.gamefaroeste;


import android.support.annotation.NonNull;

import com.example.kleber.gamefaroeste.AndGraph.AGGameManager;
import com.example.kleber.gamefaroeste.AndGraph.AGInputManager;
import com.example.kleber.gamefaroeste.AndGraph.AGScene;
import com.example.kleber.gamefaroeste.AndGraph.AGScreenManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSoundManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSprite;
import com.example.kleber.gamefaroeste.AndGraph.AGTimer;

import java.util.ArrayList;
import java.util.List;

//UMA CENA DA APLICACAO
public class CenaPlay extends AGScene {

    private AGSprite bg;
    private int somToqueNaTela;
    private int somTiro;
    private int somCorvo;
    private int somGameOver;

    AGTimer timeDead = null;

    //------------------- COWBOY
    private AGSprite cowboy;
    private float posXCowboy;
    private float posYCowboy;

    private boolean pulando;
    private boolean chegouNaAlturaMax;
    private float altruaMaxDoPulo;

    //------------------- TIRO
    private List<AGSprite> tiros;
    private List<AGSprite> tirosAindaEmTela;
    private int velocidadeTiro;

    //------------------- PASSARO INIMIGO
    private AGSprite passaro;
    private float posXPassaro;
    private int intervaloTempoQuePassaroAparece;
    private float velocidadeVooPassaro;

    //------------------- BTN PAUSE
    private AGSprite pause;

    public CenaPlay(AGGameManager pManager) {
        super(pManager);
    }

    //CHAMADO TODA VEZ QUE A CENA FOR ATIVADA
    //SEMPRE QUE A CENA FOR EXIBIDA
    @Override
    public void init() {
        //COLOCO SOM DE TOQUE NA MEMORIA
        somToqueNaTela = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
        somTiro = AGSoundManager.vrSoundEffects.loadSoundEffect("tiro.mp3");
        somCorvo = AGSoundManager.vrSoundEffects.loadSoundEffect("corvo.mp3");
        somGameOver = AGSoundManager.vrSoundEffects.loadSoundEffect("game_over.mp3");
        timeDead = new AGTimer(600);

        //DESLIGA MUSICA DO MENU
        AGSoundManager.vrMusic.pause();

        //INICIALIZANDO ALGUMAS VARIAVEIS
        //-- cowboy
        altruaMaxDoPulo = 0.45f;
        pulando = false;
        chegouNaAlturaMax = false;
        //-- tiros
        tiros = new ArrayList<>();
        tirosAindaEmTela = new ArrayList<>();
        velocidadeTiro = 6;
        //-- passaro inimigo
        passaro = null;
        posXPassaro = AGScreenManager.iScreenWidth;
        intervaloTempoQuePassaroAparece = 5000;
        posXPassaro = AGScreenManager.iScreenWidth;
        velocidadeVooPassaro = 9;

        //CARREGA BACKGROUND
        bg = createSprite(R.mipmap.bg, 1, 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        //CARREGA BTN PAUSE
        pause = createSprite(R.mipmap.pause, 1, 1);
        pause.setScreenPercent(10, 10);
        pause.vrPosition.setX(AGScreenManager.iScreenWidth * 0.95f);
        pause.vrPosition.setY(AGScreenManager.iScreenHeight * 0.90f);

        //CARREGA CAWBOY
        cowboy = createSprite(R.mipmap.cowboy, 14, 1);
        cowboy.setScreenPercent(30, 50);
        posXCowboy = AGScreenManager.iScreenWidth * 0.15f;
        posYCowboy = AGScreenManager.iScreenHeight * 0.25f;
        cowboy.vrPosition.setX(posXCowboy);
        cowboy.vrPosition.setY(posYCowboy);
        //ANIMACAO 0 -> ANDANDO
        cowboy.addAnimation(15, true, 1, 9);
        //ANIMACAO 1 -> ATIRANDO
        cowboy.addAnimation(15, false, 10, 13);
    }

    private void carregaPassaroInimigo() {
        passaro = createSprite(R.mipmap.passaro, 5, 1);
        passaro.setScreenPercent(10, 10);
        posXPassaro = AGScreenManager.iScreenWidth;
        passaro.vrPosition.setX(posXPassaro);
        passaro.vrPosition.setY(AGScreenManager.iScreenHeight * 0.35f);
        passaro.iMirror = AGSprite.HORIZONTAL;//espelhamento horizontal
        //ANIMACAO 0 -> VOANDO
        passaro.addAnimation(15, true, 0, 4);
    }

    private void CarregarTiro() {

        AGSprite tiro;
        AGSprite ultimoTiro = null;

        //PEGA O ULTIMO TIRO DISPARADO
        if (!tiros.isEmpty()) {
            ultimoTiro = tiros.get(tiros.size() - 1);
        }

        //CRIA SPRITE, SE A LISTA ESTIVER VAZIA
        if (tiros.isEmpty()) {
            tiro = criaSpriteTiro();
        } else {
            //CRIA SPRITE, SE ESSE NOVO TIRO NAO FOR FICAR EM CIMO DO ULTIMO TIRO DISPARADO
            if (ultimoTiro.vrPosition.getX() > (posXCowboy + posXCowboy / 2)) {
                tiro = criaSpriteTiro();
            } else {
                tiro = null;
            }
        }

        //VERIFICA SE É PARA ADICIONAR O TIRO
        if (tiro != null) {
            tiros.add(tiro);
        }
    }

    @NonNull
    private AGSprite criaSpriteTiro() {
        AGSprite tiro;
        tiro = createSprite(R.mipmap.tiro, 1, 1);
        tiro.setScreenPercent(10, 5);
        tiro.vrPosition.setX(posXCowboy + posXCowboy / 2);
        tiro.vrPosition.setY(posYCowboy + posYCowboy / 3);
        tiro.iMirror = AGSprite.HORIZONTAL;
        return tiro;
    }

    //APOS O RETORNO DE UMA INTERRUPCAO
    @Override
    public void restart() {
        this.init();
    }

    //QUANDO UM INTERRUPCAO OCERRER
    @Override
    public void stop() {

    }

    long tempoInicioPassaro = System.currentTimeMillis();

    //CHAMADO N VEZES POR SEGUNDO
    @Override
    public void loop() {

        //COWBOY ESTA MORTO
        if (!cowboy.fadeEnded()){
//            AGSoundManager.vrSoundEffects.play(somGameOver);
            AGSoundManager.vrMusic.loadMusic("game_over.mp3", true);
            AGSoundManager.vrMusic.play();
            timeDead.update();
            if (timeDead.isTimeEnded()) {
                vrGameManager.setCurrentScene(4);
                return;
            }
        }

        //APERTOU O BTN PAUSE
        if (pause.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
            vrGameManager.setCurrentScene(4);
            return;
        }

        //CARREGA PASSARO INIMIGO
        if (passaro == null) {
            //VERIFICO O INTERVALO
            if ((System.currentTimeMillis() - tempoInicioPassaro) > intervaloTempoQuePassaroAparece) {
                carregaPassaroInimigo();
                tempoInicioPassaro = System.currentTimeMillis();
            }
        }

        //TERMINOU DE DAR O TIRO
        if (cowboy.getCurrentAnimation().isAnimationEnded())
            cowboy.setCurrentAnimation(0);

        //CLICOU NA TELA
        if (AGInputManager.vrTouchEvents.screenClicked()) {

            float a = AGScreenManager.iScreenWidth / 2;
            float b = AGInputManager.vrTouchEvents.getLastPosition().getX();

            //CLICOU NA DIREITA
            if (b > a) {
                AGSoundManager.vrSoundEffects.play(somTiro);
                //ANIMACAO: CAWBOY ATIRANDO
                cowboy.setCurrentAnimation(1);
                CarregarTiro();
            } else {
                //CLICOU NA ESQUERDA
                AGSoundManager.vrSoundEffects.play(somToqueNaTela);
                pulando = true;
            }
        }

        //VERIFICA SE ESTA PULANDO
        if (pulando) {
            //ELEVA/DECLINA
            cowboy.vrPosition.setY(posYCowboy);

            //VERIFICA SE JA CHEGOU NA ALTURA MAX
            if (posYCowboy >= AGScreenManager.iScreenHeight * altruaMaxDoPulo) {
                chegouNaAlturaMax = true;
            }

            //VERIFICA SE É PARA SUBIR OU PARA DESCER
            if (chegouNaAlturaMax == false) {
                posYCowboy += 10;
            } else {
                posYCowboy -= 10;
            }

            //VERIFICA VOLTOU AO CHAO
            if (posYCowboy <= AGScreenManager.iScreenHeight * 0.25f) {
                pulando = false;
                chegouNaAlturaMax = false;
                return;
            }
        }


        if (passaro != null) {
            //PASSARO COLIDE COM COWBOY
            if (passaro.collide(cowboy)) {
                removeSprite(passaro);
                passaro = null;
                cowboy.setCurrentAnimation(1);
                AGSoundManager.vrSoundEffects.play(somGameOver);
                cowboy.fadeOut(600);
//                timeDead.update();
//
//                if (timeDead.isTimeEnded()){
//                    vrGameManager.setCurrentScene(1);
//                    return;
//                }

            } else {
                posXPassaro -= velocidadeVooPassaro;
                passaro.vrPosition.setX(posXPassaro);
            }

        }

        //ANDAR TIROs
        if (tiros.size() > 0) {
            //AUMENTA A POS_X DE CADA TIRO
            for (AGSprite tiro : tiros) {
                tiro.vrPosition.setX(tiro.vrPosition.getX() + velocidadeTiro);
            }

            //COLOCO OS TIROS QUE AINDA ESTAO VISIVEIS EM UMA LISTA SEPARADA
            tirosAindaEmTela.clear();
            for (AGSprite tiro : tiros) {
                if (!(tiro.vrPosition.getX() > AGScreenManager.iScreenWidth)) {
                    tirosAindaEmTela.add(tiro);
                }
            }

            //EXIBE A LISTA DE TIROS QUE AINDA ESTAO VISIVEIS
            for (AGSprite tiro : tirosAindaEmTela) {
                if (passaro != null && tiro != null) {
                    if (tiro.collide(passaro)) {
                        this.removeSprite(passaro);
                        this.passaro = null;
                        this.removeSprite(tiro);
                        tiros.remove(tiro);
                    }
                }
            }
        }

    }

}

