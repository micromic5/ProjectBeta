/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sceenes;

import com.micromic.projectbeta.Screens.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.micromic.projectbeta.ProjectBeta;

/**
 *
 * @author mike
 */
public class Hud {
    public Stage stage;
    private Viewport viewport;
    
    private Float worldTimer;
    private Integer hour;
    private Integer minute;
    private float timeCount;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    
    public Hud(SpriteBatch sb){
        //24h 86400 7h 25200 time should be saved somewhere
        worldTimer = 25200f;
        hour = (int)Math.floor(worldTimer/3600);
        minute = (int)Math.floor((worldTimer%3600)/60);
        timeCount=0;
        viewport = new FitViewport(ProjectBeta.V_WIDTH, ProjectBeta.V_Height, new OrthographicCamera());
        stage = new Stage(viewport,sb);       
        
        Table table = new Table();
        table.top();        
        table.setFillParent(true);
        //Calculation time
        timeLabel = new Label((hour>9?Integer.toString(hour):"0"+Integer.toString(hour))+":"+(minute>9?Integer.toString(minute):"0"+Integer.toString(minute)), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        levelLabel = new Label("level", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        worldLabel = new Label("world", new Label.LabelStyle(new BitmapFont(),Color.WHITE));

        table.add(worldLabel).align(Align.left).expandX().padLeft(5);
        table.row();
        table.add(levelLabel).align(Align.left).expandX().padLeft(5);
        table.row();
        table.add(timeLabel).align(Align.left).expandX().padLeft(5);
        
        stage.addActor(table);
    }
    
    public void update(float dt){
        timeCount += dt;
        if(timeCount >=1){            
            hour = (int)Math.floor(worldTimer/3600);
            minute = (int)Math.floor((worldTimer%3600)/60);
            timeLabel.setText((hour>9?Integer.toString(hour):"0"+Integer.toString(hour))+":"+(minute>9?Integer.toString(minute):"0"+Integer.toString(minute)));
            timeCount = 0;
            worldTimer=(worldTimer>=86400f?0:worldTimer+60);
        }
    }
    
    public void dispose(){
        stage.dispose();
    }
}
