/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bonfire.game;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import bonfire.game.renderEngine.DisplayManager;
import bonfire.game.renderEngine.Loader;
import bonfire.game.renderEngine.MasterRenderer;
import bonfire.game.renderEngine.ObjLoader;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import terrains.Terrain;

public class Game{

    public static void main(String[] args) {

	DisplayManager.createDisplay();
	Loader loader = new Loader();
		
	RawModel model = ObjLoader.loadObjModel("stall", loader);
	TexturedModel grass = new TexturedModel(ObjLoader.loadObjModel("Grass_01", loader),
                new ModelTexture(loader.loadTexture("grass_diff")));	
	//TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("stallTexture")));
	//ModelTexture texture = staticModel.getTexture();
        TexturedModel fern = new TexturedModel(ObjLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("leaf")));
        TexturedModel tree = new TexturedModel(ObjLoader.loadObjModel("tree", loader),
                new ModelTexture(loader.loadTexture("tree")));
	grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);
        //tree.getTexture().setUseFakeLighting(true);
        //Entity entity = new Entity(staticModel, new Vector3f(0,10,0),0,0,0,1);
	Light light = new Light(new Vector3f(3000,2000,2000), new Vector3f(1,1,1));
        //entity.setRotY(180);
        
        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));
        //Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass")));        
	Camera camera = new Camera();
        //camera.setYaw(180);        
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i = 0; i < 50; i++) {
            entities.add(new Entity(tree, new Vector3f(random.nextFloat() * 400, 0,
                random.nextFloat() * 400), 0, 0, 0, 7));
            entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 400, 0,
                random.nextFloat() * 400), 0, 0, 0, 5));
            entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 400, 0,
                random.nextFloat() * 400), 0, 0, 0, 0.8f));
        }
        //Entity fernModel = new Entity(fern, new Vector3f(0, 0, 0), 0, 0, 0, 2);
                
                
	MasterRenderer renderer = new MasterRenderer();
	while(!Display.isCloseRequested()){
            camera.move();
            renderer.processTerrain(terrain);
            //renderer.processTerrain(terrain2);
            //renderer.processEntity(fernModel);
            for(Entity modes : entities) {
                renderer.processEntity(modes);
            }
            //renderer.processEntity(entity);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
	}
        renderer.cleanUp();
	loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
