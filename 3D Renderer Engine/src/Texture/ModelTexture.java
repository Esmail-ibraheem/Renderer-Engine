package Texture;

public class ModelTexture {
	private int Texture_ID ;
	private float ShineDamper = 1;
	private float Reflectivity = 0;
	
	public ModelTexture(int ID) {
		this.Texture_ID = ID ;
	}

	public int getTexture_ID() {
		return this.Texture_ID;
	}
	
	public float getShineDamper() {
		return ShineDamper;
	}

	public void setShineDamper(float shineDamper) {
		ShineDamper = shineDamper;
	}

	public float getReflectivity() {
		return Reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		Reflectivity = reflectivity;
	}
	
}
