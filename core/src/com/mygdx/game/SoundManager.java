package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Manages the music and sound effects of our game
 */

public class SoundManager {

    /**
     * Currently played background music in the game
     */
    public Music backgroundMusic;

    public Sound blop;

    /**
     * At the start the background music is set and all the sound effects are created
     */
    public SoundManager(){
        setBackgroundMusic("Sounds/bensound-moose.mp3");

        blop = Gdx.audio.newSound(Gdx.files.internal("Sounds/blop.wav"));
    }

    /**
     * Sets the background music
     * @param musicFile The name of the music file
     */
    public void setBackgroundMusic(String musicFile){

        if(backgroundMusic != null)
            backgroundMusic.stop();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }

    /**
     * Plays a sound effect
     * @param sound The sound that we want to play. Has to be already constructed in the program.
     */
    public void playSound(Sound sound){
        sound.play();
    }
}
