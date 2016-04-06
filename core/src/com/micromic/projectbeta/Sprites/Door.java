/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;

/**
 *
 * @author mike
 */
public class Door extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private int textureNumber = 16;
    public Door(PlayScreen screen, Rectangle bounds){
        super(screen,bounds);    
        tileSet = map.getTileSets().getTileSet("Tildes");
        fixture.setUserData(this);
        setCategoryFilter(ProjectBeta.DOOR_BIT);
    }
    
    //Activate Door when character hits the door with his head
    public void onTopHit(){
        textureNumber=textureNumber==6?38:16;
        for(Cell cell : getDoorCells()){
            cell.setTile(tileSet.getTile(textureNumber)); 
            if(textureNumber == 16 || textureNumber == 38){
                textureNumber-=10;
            }
        }
    }
}
