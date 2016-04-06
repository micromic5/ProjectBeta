/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Sprites.Door;
import com.micromic.projectbeta.Sprites.Enemy.Enemy;
import com.micromic.projectbeta.Sprites.Hero;
import com.micromic.projectbeta.Sprites.InteractiveTileObject;

/**
 *
 * @author mike
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if(fixA.getUserData() =="top" || fixB.getUserData() == "top"){
            Fixture top = fixA.getUserData() =="top" ?fixA : fixB;
            Fixture object = top == fixA ? fixB : fixA;
            
            if(object.getUserData() != null && Door.class.isAssignableFrom(object.getUserData().getClass())){
                ((Door) object.getUserData()).onTopHit();
            }
        }
        
        switch(cdef){
            case ProjectBeta.ATTACK_BIT | ProjectBeta.HERO_BIT:
                if(fixA.getFilterData().categoryBits == ProjectBeta.ATTACK_BIT && fixB.getUserData() !="top")
                    ((Hero)fixB.getUserData()).decreaseHealth(((Enemy)fixA.getUserData()).getDmg());
                else if(fixB.getFilterData().categoryBits == ProjectBeta.ATTACK_BIT && fixA.getUserData() !="top")
                    ((Hero)fixA.getUserData()).decreaseHealth(((Enemy)fixB.getUserData()).getDmg());
                break;
        }
    }

    @Override
    public void endContact(Contact cntct) {
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
    
}
