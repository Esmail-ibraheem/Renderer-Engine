package ShaderEngine;

import org.lwjgl.util.vector.Matrix4f;

import Entities.Camera;
import Entities.Light;
import ToolBox.Maths;

public class StaticShader extends ShaderProgram{
	
	private static final String VertexShaderFile = "src/ShaderEngine/VertexShaderFile(GLSL).txt" ;
	private static final String FragmentShaderFile = "src/ShaderEngine/FragmentShaderFile(GLSL).txt" ;
	private int LocationTransformationMatrix ;
	private int LocationProjectionMatrix ;
	private int LocationCameraMatrix ;
	private int LocationLight_Positon;
	private int LocationLight_Colour;
	private int LocationShineDamper;
	private int LocationReflectivity;
	
	public StaticShader() {
		super(VertexShaderFile, FragmentShaderFile);
		
	}

	@Override
	protected void BindAttributes() {
		super.BindAttribs(0, "Positions");
		super.BindAttribs(1, "TextureCoordinate");
		super.BindAttribs(2, "normals");
	}

	@Override
	protected void GetAllUniformLocations() {
		LocationTransformationMatrix = super.GetUniformLocation("TransformationMatrix");
		LocationProjectionMatrix  = super.GetUniformLocation("ProjectionMatrix") ;
		LocationCameraMatrix = super.GetUniformLocation("CameraMatrix") ;
		LocationLight_Positon = super.GetUniformLocation("LightPositions") ;
		LocationLight_Colour = super.GetUniformLocation("lightColour") ;
		LocationShineDamper = super.GetUniformLocation("shineDamper") ;
		LocationReflectivity = super.GetUniformLocation("reflectivity") ;
	}
	public void LoadShineVaraibles(float ShineDamper , float Reflectivity) {
		super.LoadFloat(LocationShineDamper, ShineDamper);
		super.LoadFloat(LocationReflectivity, Reflectivity);
	}
	public void LoadTransformationMatrix(Matrix4f matrix) {
		super.LoadMatrix(LocationTransformationMatrix, matrix);
	}
	public void LoadLightSystem(Light light) {
		super.LoadVector(LocationLight_Positon, light.getLight_Position());
		super.LoadVector(LocationLight_Colour, light.getLight_Colour());
	}
	public void LoadCameraMatrix(Camera camera) {
		Matrix4f matrix = Maths.CreateCameraMatrix(camera);
		super.LoadMatrix(LocationCameraMatrix, matrix);
	}
	public void LoadProjectionMatrix(Matrix4f matrix) {
		super.LoadMatrix(LocationProjectionMatrix, matrix);
	}

}
