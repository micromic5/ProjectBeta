/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;

/**
 *
 * @author mike
 */
public class BlackKnight extends Enemy{
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    
    public BlackKnight(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlasBKnight().findRegion("WalkFrontSword"),i*36,0,36,72));
        walkAnimation = new Animation(0.2f, frames);
        stateTime = 0;
        setBounds(getX(),getY(),36/ProjectBeta.PPM,72 /ProjectBeta.PPM);
    }
    
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(walkAnimation.getKeyFrame(stateTime,true));
    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(380/ ProjectBeta.PPM,30/ ProjectBeta.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = this.world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14/ ProjectBeta.PPM,18/ ProjectBeta.PPM,new Vector2(2 / ProjectBeta.PPM, -18 / ProjectBeta.PPM),0f);
        fdef.filter.categoryBits = ProjectBeta.ENEMY_BIT;
        fdef.filter.maskBits = ProjectBeta.GROUND_BIT | ProjectBeta.DOOR_BIT | ProjectBeta.TREE_BIT | ProjectBeta.ENEMY_BIT | ProjectBeta.OBJECT_BIT | ProjectBeta.HERO_BIT;
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
       /* shape.setAsBox(14/ProjectBeta.PPM,18/ProjectBeta.PPM,new Vector2(30 / ProjectBeta.PPM, -18 / ProjectBeta.PPM),0f);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("attack");
        
        EdgeShape eShape = new EdgeShape();
        eShape.set(new Vector2(-18/ ProjectBeta.PPM,1/ ProjectBeta.PPM),new Vector2(18/ ProjectBeta.PPM,1/ ProjectBeta.PPM));
        fdef.shape = eShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("top");*/
    }
    
}
