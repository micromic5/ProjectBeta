/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;
import java.util.ArrayList;

/**
 *
 * @author mike
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected ArrayList<Cell> cells;
    
    public InteractiveTileObject(PlayScreen screen, Rectangle bounds){
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;
        
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth()/2)/ ProjectBeta.PPM, (bounds.getY() + bounds.getHeight()/2)/ ProjectBeta.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth()/2)/ ProjectBeta.PPM,(bounds.getHeight()/2)/ ProjectBeta.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);        
    }
    
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    
    public TiledMapTileLayer.Cell getCell(){
        System.out.println(body.getPosition().x* ProjectBeta.PPM * 36);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x* ProjectBeta.PPM/36), (int)((body.getPosition().y*ProjectBeta.PPM) /36));
    }
    
    public ArrayList<Cell> getDoorCells(){
        cells = new ArrayList<Cell>();
        System.out.println(body.getPosition().x* ProjectBeta.PPM * 36);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        cells.add(layer.getCell((int)(body.getPosition().x* ProjectBeta.PPM/36), (int)((body.getPosition().y*ProjectBeta.PPM-1) /36)));
        cells.add(layer.getCell((int)(body.getPosition().x* ProjectBeta.PPM/36), (int)((body.getPosition().y*ProjectBeta.PPM+1) /36)));
        return cells;
    }
}
