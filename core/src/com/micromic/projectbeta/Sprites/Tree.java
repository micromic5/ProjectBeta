/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.micromic.projectbeta.ProjectBeta;

/**
 *
 * @author mike
 */
public class Tree extends InteractiveTileObject{
     public Tree(World world, TiledMap map, Rectangle bounds){
        super(world,map,bounds);     
        fixture.setUserData(this);
        setCategoryFilter(ProjectBeta.TREE_BIT);
    }
}
