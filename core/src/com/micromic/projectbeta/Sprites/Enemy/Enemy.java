/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.micromic.projectbeta.Screens.PlayScreen;

/**
 *
 * @author mike
 */
public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    protected int dmg;
    protected int healt;
    
    public Body b2body;
    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
    }
    
    public int getHealth(){
        return healt;
    }
    public abstract void kill();
    public abstract int getDmg();
    public abstract void gotHit();
    protected abstract void defineEnemy();
}
