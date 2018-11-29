package com.example.kleber.gamefaroeste;


import com.example.kleber.gamefaroeste.AndGraph.AGGameManager;
import com.example.kleber.gamefaroeste.AndGraph.AGInputManager;
import com.example.kleber.gamefaroeste.AndGraph.AGScene;
import com.example.kleber.gamefaroeste.AndGraph.AGScreenManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSoundManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSprite;

//UMA CENA DA APLICACAO
public class CenaSobre extends AGScene {

    AGSprite bg = null;
    private int somToqueNaTela;

    public CenaSobre(AGGameManager pManager) {
        super(pManager);
    }

    //CHAMADO TODA VEZ QUE A CENA FOR ATIVADA
    //SEMPRE QUE A CENA FOR EXIBIDA
    @Override
    public void init() {

        //CARREGA EFEITOS SONOROS
        somToqueNaTela = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");

        //CARREGA BACKGROUND
        bg = createSprite(R.mipmap.tela_about, 1, 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight / 2);
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
        if (AGInputManager.vrTouchEvents.screenClicked()){

            AGSoundManager.vrSoundEffects.play(somToqueNaTela);
            vrGameManager.setCurrentScene(1);
            return;
        }
    }
}
