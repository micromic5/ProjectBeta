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
import com.micromic.projectbeta.Sprites.Door;
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
        if(fixA.getUserData() =="top" || fixB.getUserData() == "top"){
            Fixture top = fixA.getUserData() =="top" ?fixA : fixB;
            Fixture object = top == fixA ? fixB : fixA;
            
            if(object.getUserData() != null && Door.class.isAssignableFrom(object.getUserData().getClass())){
                ((Door) object.getUserData()).onTopHit();
            }
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
