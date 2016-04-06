/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;

/**
 *
 * @author mike
 */
public class Hero extends Sprite{
    public enum State{STANDING, RUNNING, UP, DOWN};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion heroStand;
    private Animation heroWalkUp;
    private Animation heroWalkDown;
    private Animation heroWalk;
    private float stateTimer;
    private boolean walkingRight;
    
    
    public Hero(PlayScreen screen){
        super(screen.getAtlasHero().findRegion("Stand"));
        heroStand = new TextureRegion(getTexture(),288,0,36,72);
        setBounds(0,0,36/ProjectBeta.PPM,72/ProjectBeta.PPM);
        setRegion(heroStand);
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        walkingRight = true;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 4; i<6; i++)
            frames.add(new TextureRegion(getTexture(), i * 36,0,36,72));
        heroWalkUp = new Animation(0.1f, frames);
        frames.clear();
        
        for(int i = 1; i<4; i++)
            frames.add(new TextureRegion(getTexture(), i * 36,0,36,72));
        heroWalk = new Animation(0.1f, frames);
        frames.clear();
        
        for(int i = 6; i<8; i++)
            frames.add(new TextureRegion(getTexture(), i * 36,0,36,72));
        heroWalkDown = new Animation(0.1f, frames);
        frames.clear();
        
        defineHero();
    }
    
    public State getState(){
        if(b2body.getLinearVelocity().x!=0)
            return State.RUNNING;
        else if(b2body.getLinearVelocity().y>0)
            return State.UP;
        else if(b2body.getLinearVelocity().y<0)
            return State.DOWN;
        else 
            return State.STANDING;
        }
    
    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case RUNNING:
                region = heroWalk.getKeyFrame(stateTimer, true);
                break;
            case UP:
                region = heroWalkUp.getKeyFrame(stateTimer, true);
                break;
            case DOWN:
                region = heroWalkDown.getKeyFrame(stateTimer,true);
                break;
            default:
                region = heroStand;
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
        stateTimer = currentState == previousState ? stateTimer +dt : 0;
        previousState = currentState;
        return region;
    }
    
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2);
        setRegion(getFrame(dt));
    }
    
    public void defineHero(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(580/ ProjectBeta.PPM,30/ ProjectBeta.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = this.world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(14/ ProjectBeta.PPM,18/ ProjectBeta.PPM,new Vector2(2 / ProjectBeta.PPM, -18 / ProjectBeta.PPM),0f);
        fdef.filter.categoryBits = ProjectBeta.HERO_BIT;
        fdef.filter.maskBits = ProjectBeta.GROUND_BIT | ProjectBeta.DOOR_BIT | ProjectBeta.TREE_BIT | ProjectBeta.OBJECT_BIT | ProjectBeta.ENEMY_BIT;
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
        
       /* shape.setAsBox(14/ProjectBeta.PPM,18/ProjectBeta.PPM,new Vector2(30 / ProjectBeta.PPM, -18 / ProjectBeta.PPM),0f);
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("attack");
        */
        EdgeShape eShape = new EdgeShape();
        eShape.set(new Vector2(-18/ ProjectBeta.PPM,1/ ProjectBeta.PPM),new Vector2(18/ ProjectBeta.PPM,1/ ProjectBeta.PPM));
        fdef.shape = eShape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("top");
    }
}
