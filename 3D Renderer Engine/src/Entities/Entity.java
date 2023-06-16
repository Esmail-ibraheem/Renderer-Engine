package Entities;

import org.lwjgl.util.vector.Vector3f;

import Model.TexturedModel;

public class Entity {
	private TexturedModel texturedModel ;
	private Vector3f position ;
	private float rotx , roty , rotz ;
	private float scale ;
	
	public Entity(TexturedModel texturedModel, Vector3f position, float rotx, float roty, float rotz, float scale) {
		this.texturedModel = texturedModel;
		this.position = position;
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
		this.scale = scale;
	}
	public void increaseRotation(float dx , float dy , float dz) {
		this.rotx+=dx ;
		this.roty+=dy ;
		this.rotz+=dz ;
	}
	public TexturedModel getTexturedModel() {
		return texturedModel;
	}

	public void setTexturedModel(TexturedModel texturedModel) {
		this.texturedModel = texturedModel;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotx() {
		return rotx;
	}

	public void setRotx(float rotx) {
		this.rotx = rotx;
	}

	public float getRoty() {
		return roty;
	}

	public void setRoty(float roty) {
		this.roty = roty;
	}

	public float getRotz() {
		return rotz;
	}

	public void setRotz(float rotz) {
		this.rotz = rotz;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
}
