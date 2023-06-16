package MainEditor;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Model.RawModel;
import Model.TexturedModel;
import RendererEngine.DataLoader;
import RendererEngine.DisplayManager;
import RendererEngine.ObjectLoader;
import RendererEngine.Renderer;
import ShaderEngine.StaticShader;
import Texture.ModelTexture;

public class MaineRendererLoop {

	public static void main(String[] args) {
		DisplayManager.CreateDisplay();
		DataLoader loader = new DataLoader() ;
		StaticShader Shader = new StaticShader() ;
		Renderer renderer = new Renderer(Shader) ;
		 
		RawModel Model = ObjectLoader.LoadObjectModel("dragon", loader) ; 
		TexturedModel texture = new TexturedModel(Model , new ModelTexture(loader.LoadTexture("red"))) ;
		
		ModelTexture modelTexture = texture.getModelTexture();
		modelTexture.setShineDamper(30);
		modelTexture.setReflectivity(1);
		
		Entity entity = new Entity(texture , new Vector3f(0,0,-25),0,0,0,1) ;
		Light light = new Light(new Vector3f(0,0,-10),new Vector3f(1,1,1)) ; 
		Camera camera = new Camera() ;
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0,1, 0);
			camera.CameraMove();
			renderer.PreparePixels();;
			Shader.StartShading();
			Shader.LoadLightSystem(light);;
			Shader.LoadCameraMatrix(camera);
			renderer.Render(entity, Shader);
			Shader.StopShading();
			DisplayManager.UpdateDisplay();
		}
		Shader.CleanMemory();
		loader.CleanMemory();
		DisplayManager.CloseDisplay();

	}

}
