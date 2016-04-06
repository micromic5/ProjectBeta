package com.micromic.projectbeta;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.micromic.projectbeta.Screens.PlayScreen;

public class ProjectBeta extends Game {
        public static final int V_WIDTH = 400;
        public static final int V_Height = 200;
        public static final float PPM = 100;
        
        public static final short GROUND_BIT = 1;
        public static final short HERO_BIT = 2;
        public static final short DOOR_BIT = 4;
        public static final short TREE_BIT = 8;
        public static final short DESTROYED_BIT =16;
        public static final short OBJECT_BIT =32;
        public static final short ENEMY_BIT =64;
        
        
	public SpriteBatch batch;	
        public AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
                manager = new AssetManager();
                //Sounds video 15
                manager.load("audio/music/background-sound.wav",Music.class);
                //loads all the assets vefore moving on
                manager.finishLoading();
                setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}        
        
        public void dispose(){
            super.dispose();
            manager.dispose();
            batch.dispose();
        }
}
