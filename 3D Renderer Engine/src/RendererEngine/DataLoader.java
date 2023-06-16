package RendererEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Model.RawModel;

public class DataLoader {
	private List<Integer> VertexArrayObjects = new ArrayList<Integer>() ;
	private List<Integer> VertexBufferObjects = new ArrayList<Integer>() ;
	private List<Integer> Textures = new ArrayList<Integer>() ;
	
	public RawModel LoadDataToVertexArrayObject(float[] Positions , float[] Textures , float[] Normals , int[] indices) {
		int VertexArrayObject_ID = CreateVertexArrayObject();
		CreateElementBufferObject(indices) ;
		CreateVertexBufferObject(0 , 3 , Positions);
		CreateVertexBufferObject(1 , 2 , Textures);
		CreateVertexBufferObject(2 , 3 , Normals);
		UnBindVertexArrayObject();
		return new RawModel(VertexArrayObject_ID , indices.length);
	}
	private int CreateVertexArrayObject() {
		int VertexArrayObject_ID = GL30.glGenVertexArrays();
		VertexArrayObjects.add(VertexArrayObject_ID);
		GL30.glBindVertexArray(VertexArrayObject_ID);
		return VertexArrayObject_ID ; 
	}
	private void UnBindVertexArrayObject() {
		GL30.glBindVertexArray(0);
	}
	private void CreateVertexBufferObject(int AttributeNumber , int CoordinateSize , float[] DataType) {
		int VertexBufferObject_ID = GL15.glGenBuffers();
		VertexBufferObjects.add(VertexBufferObject_ID) ;
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VertexBufferObject_ID);
		FloatBuffer buffer = StoreDataInFloatBuffer(DataType) ;
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(AttributeNumber, CoordinateSize, GL11.GL_FLOAT, false, 0 , 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	private FloatBuffer StoreDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer ;
	}
	private void CreateElementBufferObject(int[] indices) {
		int ElementBufferObject_ID = GL15.glGenBuffers();
		VertexBufferObjects.add(ElementBufferObject_ID) ;
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ElementBufferObject_ID);
		IntBuffer buffer = StoreDataInIntBuffer(indices) ;
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	private IntBuffer StoreDataInIntBuffer(int[] indices) {
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		return buffer ;
	}
	public int LoadTexture(String FileName) {
		Texture texture = null ;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+FileName+".png")) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int Texture_ID = texture.getTextureID();
		Textures.add(Texture_ID);
		return Texture_ID;
	}
	
	public void CleanMemory() {
		for(int VAO:VertexArrayObjects) {
			GL30.glDeleteVertexArrays(VAO);
		}
		for(int VBO:VertexBufferObjects) {
			GL15.glDeleteBuffers(VBO);
		}
		for(int texture:Textures) {
			GL11.glDeleteTextures(texture);
		}
	}
}
