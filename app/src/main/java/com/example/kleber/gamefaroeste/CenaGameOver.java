package com.example.kleber.gamefaroeste;


import com.example.kleber.gamefaroeste.AndGraph.AGGameManager;
import com.example.kleber.gamefaroeste.AndGraph.AGInputManager;
import com.example.kleber.gamefaroeste.AndGraph.AGScene;
import com.example.kleber.gamefaroeste.AndGraph.AGScreenManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSoundManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSprite;
import com.example.kleber.gamefaroeste.AndGraph.AGTimer;

//UMA CENA DA APLICACAO
public class CenaGameOver extends AGScene {

    AGSprite bg = null;
    private AGSprite jogarNovamente = null;
    private AGSprite irParaMenu = null;
    private int somToqueNaTela;

    public CenaGameOver(AGGameManager pManager) {
        super(pManager);
    }

    //CHAMADO TODA VEZ QUE A CENA FOR ATIVADA
    //SEMPRE QUE A CENA FOR EXIBIDA
    @Override
    public void init() {

        //CARREGA EFEITOS SONOROS
        somToqueNaTela = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");

        //MUSICA
        AGSoundManager.vrMusic.loadMusic("music_game_over.mp3", true);
        AGSoundManager.vrMusic.play();

        //BACKGRAUD COLOR
        setSceneBackgroundColor(0.255f, 0, 0);

        //CARREGA BACKGROUND
        bg = createSprite(R.mipmap.game_over, 1, 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        jogarNovamente = createSprite(R.mipmap.play_again, 1, 1);
        jogarNovamente.setScreenPercent(30, 30);
        jogarNovamente.vrPosition.setX(AGScreenManager.iScreenWidth * 0.35f);
        jogarNovamente.vrPosition.setY(AGScreenManager.iScreenHeight * 0.3f);

        irParaMenu = createSprite(R.mipmap.home, 1, 1);
        irParaMenu.setScreenPercent(30, 30);
        irParaMenu.vrPosition.setX(AGScreenManager.iScreenWidth * 0.7f);
        irParaMenu.vrPosition.setY(AGScreenManager.iScreenHeight * 0.3f);
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

        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (irParaMenu.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(somToqueNaTela);
                vrGameManager.setCurrentScene(1);
                return;
            }

            if (jogarNovamente.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(somToqueNaTela);
                vrGameManager.setCurrentScene(2);
                return;
            }
        }
    }
}
