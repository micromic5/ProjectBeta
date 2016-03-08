/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.micromic.projectbeta.ProjectBeta;

/**
 *
 * @author mike
 */
public class Hero extends Sprite{
    public World world;
    public Body b2body;
    
    public Hero(World world){
        this.world = world;
        defineHero();
    }
    
    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(16/ ProjectBeta.PPM,16/ ProjectBeta.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = this.world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/ ProjectBeta.PPM,8/ ProjectBeta.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
    }
}
