package Entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	Vector3f Light_Position ;
	Vector3f Light_Colour ;
	
	public Light(Vector3f light_Position, Vector3f light_Colour) {
		Light_Position = light_Position;
		Light_Colour = light_Colour;
	}

	public Vector3f getLight_Position() {
		return Light_Position;
	}

	public void setLight_Position(Vector3f light_Position) {
		Light_Position = light_Position;
	}

	public Vector3f getLight_Colour() {
		return Light_Colour;
	}

	public void setLight_Colour(Vector3f light_Colour) {
		Light_Colour = light_Colour;
	}
	
	
}
