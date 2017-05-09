package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by hmhat on 9.5.2017.
 */

public class SoundManager {

    public Music backgroundMusic;

    public Sound blop;

    public SoundManager(){
        setBackgroundMusic("Sounds/bensound-moose.mp3");

        blop = Gdx.audio.newSound(Gdx.files.internal("Sounds/blop.wav"));
    }

    public void update(){

    }

    public void setBackgroundMusic(String musicFile){

        if(backgroundMusic != null)
            backgroundMusic.stop();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }

    public void playSound(Sound sound){
        sound.play();
    }
}
