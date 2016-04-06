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
    private enum State{STANDING, WALKING, UP, DOWN, ATTACKING};
    private State currentState;
    private State previousState;
    private float stateTime;
    private TextureRegion stand;
    private Animation walkUp;
    private Animation walkDown;
    private Animation walk;
    private Animation slash;
    private Array<TextureRegion> frames;
    private int dmg;
    private boolean setToDestroy;
    private boolean destroyed;
    private boolean walkingRight;
    
    public BlackKnight(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        walkingRight = true;
        dmg = 15;
        stand = new TextureRegion(screen.getAtlasBKnight().findRegion("StandSword"),0,0,36,72);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlasBKnight().findRegion("WalkFrontSword"),i*36,0,36,72));
        walkDown = new Animation(0.3f, frames);
        frames.clear();
        
        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlasBKnight().findRegion("WalkBackSword"),i*36,0,36,72));
        walkUp = new Animation(0.3f, frames);
        frames.clear();
        
        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlasBKnight().findRegion("WalkRightSword"),i*50,0,50,72));
        walk = new Animation(0.5f, frames);
        frames.clear();
        
        for(int i = 0; i < 3; i++)
            frames.add(new TextureRegion(screen.getAtlasBKnight().findRegion("SlashRight"),i*50,0,50,72));
        slash = new Animation(0.2f, frames);
        frames.clear();
        
        stateTime = 0;
        setBounds(x,y,36/ProjectBeta.PPM,72 /ProjectBeta.PPM);
        setToDestroy = false;
        destroyed = false;
        currentState = BlackKnight.State.STANDING;
        previousState = BlackKnight.State.STANDING;
    }
    
    public BlackKnight.State getState(){
        if(b2body.getLinearVelocity().x!=0)
            return BlackKnight.State.WALKING;
        else if(b2body.getLinearVelocity().y>0)
            return BlackKnight.State.UP;
        else if(b2body.getLinearVelocity().y<0)
            return BlackKnight.State.DOWN;
        else 
            return BlackKnight.State.STANDING;
    }
    
    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case WALKING:
                region = walk.getKeyFrame(stateTime, true);
                break;
            case UP:
                region = walkUp.getKeyFrame(stateTime, true);
                break;
            case DOWN:
                region = walkDown.getKeyFrame(stateTime,true);
                break;
            default:
                //region = stand;
                region = slash.getKeyFrame(stateTime,true);
                break;
        }
        if((b2body.getLinearVelocity().x<0 || !walkingRight) && !region.isFlipX()){
            region.flip(true,false);
            walkingRight = false;
        }
        else if((b2body.getLinearVelocity().x>0 || walkingRight) && region.isFlipX()){
            region.flip(true,false);
            walkingRight = true;
        }
        stateTime = currentState == previousState ? stateTime +dt : 0;
        previousState = currentState;
        return region;
    }
    
    public int getDmg(){
        return dmg;
    }
    
    public void attack(){
    
    }
    
    public void gotHit(){
        
    }
    
    public void kill(){
       setToDestroy = true; 
    }
    
    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            //Placeholder not created texture setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"),32,0,36,72));
        }
        else if(!destroyed){
            setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
            setRegion(getFrame(dt));
        }
    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = this.world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14/ ProjectBeta.PPM,18/ ProjectBeta.PPM,new Vector2(2 / ProjectBeta.PPM, -18 / ProjectBeta.PPM),0f);
        fdef.filter.categoryBits = ProjectBeta.ENEMY_BIT;
        fdef.filter.maskBits = ProjectBeta.GROUND_BIT | ProjectBeta.DOOR_BIT | ProjectBeta.TREE_BIT | ProjectBeta.ENEMY_BIT | ProjectBeta.OBJECT_BIT | ProjectBeta.HERO_BIT;
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
        shape.setAsBox(14/ProjectBeta.PPM,18/ProjectBeta.PPM,new Vector2(30 / ProjectBeta.PPM, -18 / ProjectBeta.PPM),0f);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = ProjectBeta.ATTACK_BIT;
        b2body.createFixture(fdef).setUserData(this);
        /*
        EdgeShape eShape = new EdgeShape();
        eShape.set(new Vector2(-18/ ProjectBeta.PPM,1/ ProjectBeta.PPM),new Vector2(18/ ProjectBeta.PPM,1/ ProjectBeta.PPM));
        fdef.shape = eShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("top");*/
    }
    
}
