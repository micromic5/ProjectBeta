/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;

/**
 *
 * @author mike
 */
public class Hero extends Sprite{
    public World world;
    public Body b2body;
    private TextureRegion heroStand;
    
    public Hero(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Lonely"));
        heroStand = new TextureRegion(getTexture(),902,895,16,32);
        setBounds(0,0,16/ProjectBeta.PPM,32/ProjectBeta.PPM);
        setRegion(heroStand);
        this.world = world;
        defineHero();
    }
    
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2.5f);
    }
    
    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(16/ ProjectBeta.PPM,16/ ProjectBeta.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = this.world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6/ ProjectBeta.PPM,4/ ProjectBeta.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
    }
}
