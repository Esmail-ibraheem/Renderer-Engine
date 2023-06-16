package RendererEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Entity;
import Model.RawModel;
import Model.TexturedModel;
import ShaderEngine.StaticShader;
import Texture.ModelTexture;
import ToolBox.Maths;

public class Renderer {
	private static final float FOV = 70; 
	private static final float Near_Plane = 0.1f; 
	private static final float Far_Plane = 1000 ;
	private Matrix4f ProjectionMatrix ;
	
	public Renderer(StaticShader Shader) {
		CreatePorjectionMatrix();
		Shader.StartShading();
		Shader.LoadProjectionMatrix(ProjectionMatrix);
		Shader.StopShading();
	}
	public void PreparePixels() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(1, 0 , 0 ,0 );
	}
	public void Render(Entity entity , StaticShader Shader) {
		TexturedModel texturedModel = entity.getTexturedModel();
		RawModel rawModel = texturedModel.getRawModel();
		GL30.glBindVertexArray(rawModel.getVertexArrayObject_ID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f matrix = Maths.CreateTransformationMatrix(entity.getPosition(), entity.getRotx(), entity.getRoty(), entity.getRotz(), entity.getScale());
		Shader.LoadTransformationMatrix(matrix);
		ModelTexture texture = texturedModel.getModelTexture();
		Shader.LoadShineVaraibles(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getModelTexture().getTexture_ID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	private void CreatePorjectionMatrix() {
		float aspectRatio = (float) Display.getWidth()/(float) Display.getHeight() ; 
		float y_Scale = (float) ((1f/Math.tan(Math.toRadians(FOV)/2)) *aspectRatio) ;
		float x_Scale = y_Scale/aspectRatio ;
		float frustum_length = Far_Plane - Near_Plane ;
		
		ProjectionMatrix = new Matrix4f() ;
		ProjectionMatrix.m00 = x_Scale ;
		ProjectionMatrix.m11 = y_Scale ;
		ProjectionMatrix.m22 = -((Far_Plane +Near_Plane)/frustum_length) ;
		ProjectionMatrix.m23 = -1 ;
		ProjectionMatrix.m32 = -((2*Near_Plane * Far_Plane)/frustum_length) ;
		ProjectionMatrix.m33 = 0 ;
	}
}
