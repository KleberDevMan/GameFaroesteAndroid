package com.example.kleber.gamefaroeste;


import com.example.kleber.gamefaroeste.AndGraph.AGGameManager;
import com.example.kleber.gamefaroeste.AndGraph.AGScene;
import com.example.kleber.gamefaroeste.AndGraph.AGScreenManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSoundManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSprite;
import com.example.kleber.gamefaroeste.AndGraph.AGTimer;

//UMA CENA DA APLICACAO
public class CenaAbertura extends AGScene {

    AGTimer tempo = null;
    AGSprite pantera = null;
    AGSprite load = null;


    public CenaAbertura(AGGameManager pManager) {
        super(pManager);
    }

    //CHAMADO TODA VEZ QUE A CENA FOR ATIVADA
    //SEMPRE QUE A CENA FOR EXIBIDA
    @Override
    public void init() {

        //TEMPO DE DURACAO
        tempo = new AGTimer(5000);

        //MUSICA
        AGSoundManager.vrMusic.loadMusic("oeste.mp3", true);
        AGSoundManager.vrMusic.play();

        //BACKGRAUD COLOR
        setSceneBackgroundColor(0.255f, 0, 0);

        //PANTERA
        pantera = createSprite(R.mipmap.pantera, 2, 4);
        pantera.setScreenPercent(30, 30);
        pantera.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        pantera.vrPosition.setY(AGScreenManager.iScreenHeight * 0.7f);
        pantera.addAnimation(30, true, 0, 7);

        //LOAD
        load = createSprite(R.mipmap.load, 12, 4);
        load.setScreenPercent(10, 10);
        load.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        load.vrPosition.setY(AGScreenManager.iScreenHeight * 0.2f);
        load.addAnimation(40, true, 0, 47);
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

//        tempo.update();
//
//        if (tempo.isTimeEnded()) {
            vrGameManager.setCurrentScene(1);
//        }
    }
}
