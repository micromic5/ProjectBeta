/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Screens.PlayScreen;

/**
 *
 * @author mike
 */
public class Tree extends InteractiveTileObject{
     public Tree(PlayScreen screen, Rectangle bounds){
        super(screen,bounds);     
        fixture.setUserData(this);
        setCategoryFilter(ProjectBeta.TREE_BIT);
    }
}
