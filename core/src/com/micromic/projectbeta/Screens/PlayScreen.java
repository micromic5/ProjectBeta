/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.micromic.projectbeta.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.micromic.projectbeta.ProjectBeta;
import com.micromic.projectbeta.Sprites.Hero;


/**
 *
 * @author Mike
 */
public class PlayScreen implements Screen {    
    private ProjectBeta game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    
    private World world;
    private Box2DDebugRenderer b2dr;
    
    private Hero player;
    
    public PlayScreen(ProjectBeta game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(ProjectBeta.V_WIDTH / ProjectBeta.PPM,ProjectBeta.V_Height / ProjectBeta.PPM ,gamecam);
        hud = new Hud(game.batch);
        
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ ProjectBeta.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ ProjectBeta.PPM, (rect.getY() + rect.getHeight()/2)/ ProjectBeta.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox((rect.getWidth()/2)/ ProjectBeta.PPM,(rect.getHeight()/2)/ ProjectBeta.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ ProjectBeta.PPM, (rect.getY() + rect.getHeight()/2)/ ProjectBeta.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox((rect.getWidth()/2)/ ProjectBeta.PPM,(rect.getHeight()/2)/ ProjectBeta.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
        
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/ ProjectBeta.PPM, (rect.getY() + rect.getHeight()/2)/ ProjectBeta.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox((rect.getWidth()/2)/ ProjectBeta.PPM,(rect.getHeight()/2)/ ProjectBeta.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        
        player = new Hero(world);
    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <=2)
            player.b2body.applyLinearImpulse(new Vector2(0,0.1f),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.S) && player.b2body.getLinearVelocity().y >=-2)
            player.b2body.applyLinearImpulse(new Vector2(0,-0.1f),player.b2body.getWorldCenter(), true);
       if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >=-2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <=2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(), true);
    }
    
    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        renderer.setView(gamecam);
    }
    
    @Override
    public void show() {
    
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);        
        renderer.render();
        
        b2dr.render(world,gamecam.combined);
        
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {
    
    }

    @Override
    public void resume() {
    
    }

    @Override
    public void hide() {
    
    }

    @Override
    public void dispose() {
    
    }
    
}
