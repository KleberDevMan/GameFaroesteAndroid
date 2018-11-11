package com.example.kleber.gamefaroeste;


import com.example.kleber.gamefaroeste.AndGraph.AGGameManager;
import com.example.kleber.gamefaroeste.AndGraph.AGInputManager;
import com.example.kleber.gamefaroeste.AndGraph.AGScene;
import com.example.kleber.gamefaroeste.AndGraph.AGScreenManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSoundManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSprite;

//UMA CENA DA APLICACAO
public class CenaPlay extends AGScene {

    private AGSprite bg = null;
    private int codigoDoSom;
    private int somTiro;

    //------------------- COWBOY

    private AGSprite cowboy = null;
    private float posXCowboy = 0;
    private float posYCowboy = 0;

    //CONTROLAM O PULO
    private boolean pulando = false;
    private boolean alturaMax = false;
    private float altruaMaxDoPulo = 0.45f;

    //------------------- TIRO
    private AGSprite tiro = null;
    private float posXTiro = 0;

    //------------------- PASSARO INIMIGO
    private AGSprite passaro = null;
    private float posXPassaro = AGScreenManager.iScreenWidth;


    public CenaPlay(AGGameManager pManager) {
        super(pManager);
    }

    //CHAMADO TODA VEZ QUE A CENA FOR ATIVADA
    //SEMPRE QUE A CENA FOR EXIBIDA
    @Override
    public void init() {
        //COLOCO SOM DE TOQUE NA MEMORIA
        codigoDoSom = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
        somTiro = AGSoundManager.vrSoundEffects.loadSoundEffect("tiro.mp3");

        //DESLIGA MUSICA DO MENU
        AGSoundManager.vrMusic.stop();

        //CARREGA BACKGROUND
        bg = createSprite(R.mipmap.bg, 1, 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

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

        //CARREGA TIRO
        tiro = createSprite(R.mipmap.tiro, 1, 1);
        tiro.setScreenPercent(10, 5);
        posXTiro = posXCowboy + posXCowboy / 2;
        tiro.vrPosition.setX(posXTiro);
        tiro.vrPosition.setY(posYCowboy + posYCowboy / 3);
        tiro.iMirror = AGSprite.HORIZONTAL;
//        tiro.getCurrentAnimation().release();

        //CARREGA PASSARO INIMIGO
        passaro = createSprite(R.mipmap.passaro, 5, 1);
        passaro.setScreenPercent(20, 20);
        passaro.vrPosition.setX(posXPassaro);
        passaro.vrPosition.setY(AGScreenManager.iScreenHeight * 0.35f);
        passaro.iMirror = AGSprite.HORIZONTAL;//espelhamento horizontal
        //ANIMACAO 0 -> VOANDO
        passaro.addAnimation(15, true, 0, 4);


    }

    //APOS O RETORNO DE UMA INTERRUPCAO
    @Override
    public void restart() {
    }

    //QUANDO UM INTERRUPCAO OCERRER
    @Override
    public void stop() {
    }

    //CHAMADO N VEZES POR SEGUNDO
    @Override
    public void loop() {

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
                cowboy.setCurrentAnimation(1);
                tiro.vrPosition.setX(posXCowboy + posXCowboy / 2);
                tiro.bVisible = true;
//                tiro.reloadTexture(AGGameManager.vrOpenGL);
            } else {
                //CLICOU NA ESQUERDA
                AGSoundManager.vrSoundEffects.play(codigoDoSom);
                pulando = true;
            }
        }

        //VERIFICA SE ESTA PULANDO
        if (pulando) {

            //ELEVA/DECLINA
            cowboy.vrPosition.setY(posYCowboy);

            //VERIFICA SE JA CHEGOU NA ALTURA MAX
            if (posYCowboy >= AGScreenManager.iScreenHeight * altruaMaxDoPulo) {
                alturaMax = true;
            }

            //VERIFICA SE É PARA SUBIR OU PARA DESCER
            if (alturaMax == false) {
                posYCowboy += 10;
            } else {
                posYCowboy -= 10;
            }

            //VERIFICA VOLTOU AO CHAO
            if (posYCowboy <= AGScreenManager.iScreenHeight * 0.25f) {
                pulando = false;
                alturaMax = false;
                return;
            }

        }

        //ANDAR PASSARO INIMIGO
        posXPassaro--;
        if (posXPassaro <= 0)
            posXPassaro = AGScreenManager.iScreenWidth;
        passaro.vrPosition.setX(posXPassaro);

        //ANDAR TIRO
        posXTiro++;
        tiro.vrPosition.setX(posXTiro);
        if (tiro.collide(passaro)){
            passaro.release();
        }
    }
}
