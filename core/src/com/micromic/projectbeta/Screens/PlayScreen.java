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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayers;
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
import com.micromic.projectbeta.Sceenes.Hud;
import com.micromic.projectbeta.Sprites.Hero;
import com.micromic.projectbeta.Tools.B2WorldCreator;


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
    private OrthogonalTiledMapRenderer backgroundRenderer;
    private OrthogonalTiledMapRenderer foregroundRenderer;
    private MapLayers layers;
    private World world;
    private Box2DDebugRenderer b2dr;
    
    private Hero player;
    private float heroSpeedModificator;
    
    private TextureAtlas atlas;
    
    public PlayScreen(ProjectBeta game){
        atlas = new TextureAtlas("self_made/character/character.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(ProjectBeta.V_WIDTH / ProjectBeta.PPM,ProjectBeta.V_Height / ProjectBeta.PPM ,gamecam);
        hud = new Hud(game.batch);
        
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();
        
        heroSpeedModificator = 1;
        
        new B2WorldCreator(world, map);
        
        player = new Hero(world,this);
        //Back and Foreground Layers
        map.getLayers().remove(map.getLayers().get("graphics2"));
        map.getLayers().remove(map.getLayers().get("graphics3"));
        backgroundRenderer = new OrthogonalTiledMapRenderer(map, 1/ ProjectBeta.PPM);
        map = maploader.load("level1.tmx");
        map.getLayers().remove(map.getLayers().get("graphics"));
        map.getLayers().remove(map.getLayers().get("doors"));
        map.getLayers().remove(map.getLayers().get("vegetation"));
        map.getLayers().remove(map.getLayers().get("background"));
        foregroundRenderer = new OrthogonalTiledMapRenderer(map, 1/ ProjectBeta.PPM);
    }
    public void handleInput(float dt){
        //Speed Modification
        if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)){
            if(heroSpeedModificator == 1){
                heroSpeedModificator= 0.1f;
                if(Math.abs(player.b2body.getLinearVelocity().x)>0.2)
                    player.b2body.setLinearVelocity(new Vector2(0,player.b2body.getLinearVelocity().y));
                if(Math.abs(player.b2body.getLinearVelocity().y)>0.2)
                    player.b2body.setLinearVelocity(new Vector2(player.b2body.getLinearVelocity().x,0));
        }else
                heroSpeedModificator =1;
                        
        }
        //Hero Movement
        if(Gdx.input.isKeyPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <=2*heroSpeedModificator)
            player.b2body.applyLinearImpulse(new Vector2(0,0.1f),player.b2body.getWorldCenter(),true); 
        if(Gdx.input.isKeyPressed(Input.Keys.S) && player.b2body.getLinearVelocity().y >=-2*heroSpeedModificator)
            player.b2body.applyLinearImpulse(new Vector2(0,-0.1f),player.b2body.getWorldCenter(), true);
        if(!(Gdx.input.isKeyPressed(Input.Keys.W)) && 
                !(Gdx.input.isKeyPressed(Input.Keys.S)) && 
                player.b2body.getLinearVelocity().y !=0){
            player.b2body.setLinearVelocity(new Vector2(player.b2body.getLinearVelocity().x,0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >=-2*heroSpeedModificator)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true);
        else if(!(Gdx.input.isKeyPressed(Input.Keys.A)) && 
                !(Gdx.input.isKeyPressed(Input.Keys.D)) && 
                player.b2body.getLinearVelocity().x !=0){
            player.b2body.setLinearVelocity(new Vector2(0,player.b2body.getLinearVelocity().y));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <=2*heroSpeedModificator)
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(), true);
        else if(!(Gdx.input.isKeyPressed(Input.Keys.A)) && 
                !(Gdx.input.isKeyPressed(Input.Keys.D)) && 
                player.b2body.getLinearVelocity().x !=0){
            player.b2body.setLinearVelocity(new Vector2(0,player.b2body.getLinearVelocity().y));
        }
    }
    
    public void update(float dt){
        player.update(dt);
        handleInput(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.position.y = player.b2body.getPosition().y;
        gamecam.update();
        world.step(1/60f, 1, 2);       
        backgroundRenderer.setView(gamecam);
        foregroundRenderer.setView(gamecam);        
    }
    
    @Override
    public void show() {
    
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);        
        backgroundRenderer.render();       
        
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        foregroundRenderer.render();
        //can create graphical bugs
        b2dr.render(world,gamecam.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public TextureAtlas getAtlas(){
        return atlas;
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
        map.dispose();
        backgroundRenderer.dispose();
        foregroundRenderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
    
}
