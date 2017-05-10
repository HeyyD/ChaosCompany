package com.mygdx.chaoscompany;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;

/**
 * Manages the music and sound effects of our game
 */

public class SoundManager {

    /**
     * Currently played background music in the game
     */
    public Music backgroundMusic;

    public Sound blop;

    private float musicVolume = 0.5f;
    private float effectVolume = 1;

    /**
     * At the start the background music is set and all the sound effects are created
     */
    public SoundManager(){
        setBackgroundMusic("Sounds/background.mp3");

        blop = Gdx.audio.newSound(Gdx.files.internal("Sounds/blop.wav"));
    }

    /**
     * Sets the background music
     * @param musicFile The name of the music file
     */
    public void setBackgroundMusic(String musicFile){

       /*if(backgroundMusic != null)
            backgroundMusic.stop();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(musicVolume);
        backgroundMusic.play();*/

       if(backgroundMusic == null) {
           backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
           backgroundMusic.setLooping(true);
           backgroundMusic.setVolume(musicVolume);
           backgroundMusic.play();
       } else {
           System.out.println("täälä");
           fadeOut(musicFile);
       }
    }

    /**
     * Plays a sound effect
     * @param sound The sound that we want to play. Has to be already constructed in the program.
     */
    public void playSound(Sound sound){
        sound.play(effectVolume, MathUtils.random(0.5f, 2.0f), 0);
    }

    /**
     * Puts sounds on or off
     * @param mute If true the sound is off
     */
    public void mute(boolean mute){

        System.out.println(mute);

        if(mute == true){
            musicVolume = 0;
            effectVolume = 0;
        } else {
            musicVolume = 0.5f;
            effectVolume = 1;
        }

        backgroundMusic.setVolume(musicVolume);
    }

    private void fadeOut(final String musicFile){

        final float musicFadeStep = 0.01f;
        float fadeRate = 0.05f;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(backgroundMusic.getVolume() >= musicFadeStep)
                    backgroundMusic.setVolume(backgroundMusic.getVolume() - musicFadeStep);
                else {
                    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
                    backgroundMusic.setLooping(true);
                    backgroundMusic.play();
                    backgroundMusic.setVolume(0);
                    fadeIn();
                    this.cancel();
                }
            }
        }, 0f, fadeRate);
    }

    private void fadeIn(){

        final float musicFadeStep = 0.01f;
        float fadeRate = 0.05f;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(backgroundMusic.getVolume() <= musicVolume)
                    backgroundMusic.setVolume(backgroundMusic.getVolume() + musicFadeStep);
                else {
                    this.cancel();
                }
            }
        }, 0f, fadeRate);
    }
}
