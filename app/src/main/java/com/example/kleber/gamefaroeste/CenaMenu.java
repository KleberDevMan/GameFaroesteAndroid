package com.example.kleber.gamefaroeste;


import com.example.kleber.gamefaroeste.AndGraph.AGGameManager;
import com.example.kleber.gamefaroeste.AndGraph.AGInputManager;
import com.example.kleber.gamefaroeste.AndGraph.AGScene;
import com.example.kleber.gamefaroeste.AndGraph.AGScreenManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSoundManager;
import com.example.kleber.gamefaroeste.AndGraph.AGSprite;

//UMA CENA DA APLICACAO
public class CenaMenu extends AGScene {

    private AGSprite play = null;
    private AGSprite sobre = null;
    private AGSprite sair = null;
    private int efeitoSonoro1 = 0;

    public CenaMenu(AGGameManager pManager) {
        super(pManager);
    }

    //CHAMADO TODA VEZ QUE A CENA FOR ATIVADA
    //SEMPRE QUE A CENA FOR EXIBIDA
    @Override
    public void init() {

        //LIGA MUSICA DO MENU
        AGSoundManager.vrMusic.play();

        //CARREGA BACKGROUD
        AGSprite bg = createSprite(R.mipmap.bgmenu, 1, 1);
        bg.setScreenPercent(100, 100);
        bg.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        bg.vrPosition.setY(AGScreenManager.iScreenHeight / 2);

        play = createSprite(R.mipmap.btn_play, 1, 1);
        play.setScreenPercent(25, 15);
        play.vrPosition.setX(AGScreenManager.iScreenWidth * 0.7f);
        play.vrPosition.setY(AGScreenManager.iScreenHeight * 0.5f);

        sobre = createSprite(R.mipmap.btn_about, 1, 1);
        sobre.setScreenPercent(25, 15);
        sobre.vrPosition.setX(AGScreenManager.iScreenWidth * 0.7f);
        sobre.vrPosition.setY(AGScreenManager.iScreenHeight * 0.3f);

        sair = createSprite(R.mipmap.btn_exit, 1, 1);
        sair.setScreenPercent(25, 15);
        sair.vrPosition.setX(AGScreenManager.iScreenWidth * 0.7f);
        sair.vrPosition.setY(AGScreenManager.iScreenHeight * 0.1f);

        //Carrega um efeito sonoro
        efeitoSonoro1 = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
    }

    //APOS O RETORNO DE UMA INTERRUPCAO
    @Override
    public void restart() {
        //LIGA MUSICA DO MENU
        AGSoundManager.vrMusic.play();
    }

    //QUANDO UM INTERRUPCAO OCERRER
    @Override
    public void stop() {
    }

    //CHAMADO N VEZES POR SEGUNDO
    @Override
    public void loop() {

        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (play.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrSoundEffects.play(efeitoSonoro1);
                vrGameManager.setCurrentScene(2);
                return;
            }

            if (sobre.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrSoundEffects.play(efeitoSonoro1);
                vrGameManager.setCurrentScene(3);
                return;
            }

            if (sair.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrSoundEffects.play(efeitoSonoro1);
                vrGameManager.vrActivity.finish();
                return;
            }
        }



    }
}
