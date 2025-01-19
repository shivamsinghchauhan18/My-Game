package de.tum.cit.fop.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and managing audio resources for the MazeRunnerGame.
 * Manages background music, event sounds, and volume control.
 */
public class MusicLoader {
    private Music menuMusic;
    private Music currentGameMusic;
    private Music winningMusic;
    private Music losingMusic;
    private Music lifeLostMusic;
    private Music coinCollected;
    private Music walkingSound;
    private List<Music> gameMusicList;
    private boolean menuMusicDisabled;
    private boolean gameMusicDisabled;
    private boolean soundEffectsDisabled;
    private int lastGameMusicIndex;


    public MusicLoader() {
        this.gameMusicList = new ArrayList<>();
        this.menuMusicDisabled = false;
        this.gameMusicDisabled = false;
        this.soundEffectsDisabled = false;
        this.lastGameMusicIndex = -1;
    }

    public void loadMusic() {
        menuMusic = loadMusicFile("audio/menu.mp3", true);
        winningMusic = loadMusicFile("audio/winning.mp3", false);
        losingMusic = loadMusicFile("audio/losing.mp3", false);
        lifeLostMusic = loadMusicFile("audio/life_lost.ogg", false);
        coinCollected = loadMusicFile("audio/coin_collected.ogg", false);
        walkingSound = loadMusicFile("audio/walking.mp3", false);

    }

    private Music loadMusicFile(String filePath, boolean loop) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        music.setLooping(loop);
        return music;
    }
    /**
     * Plays the menu music if it's enabled.
     */
    public void playMenuMusic() {
        if (!menuMusicDisabled) {
            menuMusic.play();
        }
    }

    /**
     * Stops the menu music.
     */
    public void stopMenuMusic() {
        menuMusic.stop();
    }


    /**
     * Plays the winning music.
     */
    public void playWinningMusic() {
        if (!gameMusicDisabled) {
            winningMusic.play();
        }
    }

    /**
     * Plays the losing music.
     */
    public void playLosingMusic() {
        if (!gameMusicDisabled) {
            losingMusic.play();
        }
    }
    /**
     * Adjusts the volume for all music tracks.
     */
    public void setVolume(float volume) {
        volume = MathUtils.clamp(volume, 0, 1);
        menuMusic.setVolume(volume);
        float finalVolume = volume;
        gameMusicList.forEach(music -> music.setVolume(finalVolume));
        winningMusic.setVolume(volume);
        losingMusic.setVolume(volume);
        lifeLostMusic.setVolume(volume);
        coinCollected.setVolume(volume);
        walkingSound.setVolume(volume);
    }

    /**
     * Increases the volume by a fixed step.
     */
    public void increaseVolume() {
        setVolume(menuMusic.getVolume() + 0.1f);
    }

    /**
     * Decreases the volume by a fixed step.
     */
    public void decreaseVolume() {
        setVolume(menuMusic.getVolume() - 0.1f);
    }
    /**
     * Plays the sound for losing a life.
     */
    public void playLifeLostSound() {
        if (!soundEffectsDisabled) {
            lifeLostMusic.play();
        }
    }

    /**
     * Plays the sound for collecting a coin.
     */
    public void playCoinCollectedSound() {
        if (!soundEffectsDisabled) {
            coinCollected.play();
        }
    }

    /**
     * Plays the walking sound.
     */
    public void playWalkingSound() {
        if (!soundEffectsDisabled) {
            walkingSound.play();
        }
    }

    // Setters for disabling/enabling audio
    public void setMenuMusicDisabled(boolean disabled) {
        this.menuMusicDisabled = disabled;
    }

    public void setGameMusicDisabled(boolean disabled) {
        this.gameMusicDisabled = disabled;
    }

    public void setSoundEffectsDisabled(boolean disabled) {
        this.soundEffectsDisabled = disabled;
    }
    /**
     * Releases all audio resources to prevent memory leaks.
     */
    public void dispose() {
        menuMusic.dispose();
        winningMusic.dispose();
        losingMusic.dispose();
        lifeLostMusic.dispose();
        coinCollected.dispose();
        walkingSound.dispose();
        gameMusicList.forEach(Music::dispose);
    }
}


