/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;
import com.micromic.projectbeta.Sprites.Door;
import com.micromic.projectbeta.Sprites.Tree;

/**
 *
 * @author mike
 */
public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            new Door(screen,rect);
        }
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            new Tree(screen,rect);
        }
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ ProjectBeta.PPM, (rect.getY() + rect.getHeight()/2)/ ProjectBeta.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox((rect.getWidth()/2)/ ProjectBeta.PPM,(rect.getHeight()/2)/ ProjectBeta.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = ProjectBeta.OBJECT_BIT;
            body.createFixture(fdef);
        }
       /* for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ ProjectBeta.PPM, (rect.getY() + rect.getHeight()/2)/ ProjectBeta.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox((rect.getWidth()/2)/ ProjectBeta.PPM,(rect.getHeight()/2)/ ProjectBeta.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }*/
        
    }
}
