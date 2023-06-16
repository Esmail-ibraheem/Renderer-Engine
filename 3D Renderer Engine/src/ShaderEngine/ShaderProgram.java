package ShaderEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	private int VertexShader_ID ;
	private int FragmentShader_ID ;
	private int ShaderProgram_ID ;
	private FloatBuffer MatrixBuffer = BufferUtils.createFloatBuffer(16) ;
	
	public ShaderProgram(String VertexShaderFile , String FragmentShaderFile) {
		VertexShader_ID = LoadShader(VertexShaderFile , GL20.GL_VERTEX_SHADER);
		FragmentShader_ID = LoadShader(FragmentShaderFile , GL20.GL_FRAGMENT_SHADER);
		ShaderProgram_ID  = GL20.glCreateProgram();
		GL20.glAttachShader(ShaderProgram_ID, VertexShader_ID);
		GL20.glAttachShader(ShaderProgram_ID, FragmentShader_ID);
		BindAttributes() ;
		GL20.glLinkProgram(ShaderProgram_ID);
		GL20.glValidateProgram(ShaderProgram_ID);
		GetAllUniformLocations() ;
	}
	public void StartShading() {
		GL20.glUseProgram(ShaderProgram_ID);
	}
	public void StopShading() {
		GL20.glUseProgram(0);
	}
	protected abstract void BindAttributes() ;
	protected abstract void GetAllUniformLocations() ;
	protected void BindAttribs(int AttributeNumber , String VaraibleName) {
		GL20.glBindAttribLocation(AttributeNumber, ShaderProgram_ID, VaraibleName);
	}
	protected int GetUniformLocation(String UniformName) {
		return GL20.glGetUniformLocation(ShaderProgram_ID, UniformName) ;
	}
	protected void LoadFloat(int Location , float Value) {
		GL20.glUniform1f(Location, Value);
	}
	protected void LoadVector(int Location , Vector3f vector) {
		GL20.glUniform3f(Location, vector.x, vector.y, vector.z);
	}
	protected void LoadMatrix(int Location , Matrix4f matrix) {
		matrix.store(MatrixBuffer) ;
		MatrixBuffer.flip();
		GL20.glUniformMatrix4(Location, false, MatrixBuffer);
	}
	private static int LoadShader(String file , int type) {
		StringBuilder shaderSource = new StringBuilder() ;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file)) ;
			String line;
			while((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n") ; 
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read the file");
			e.printStackTrace();
			System.exit(-1);
		}
		int ShaderID = GL20.glCreateShader(type) ;
		GL20.glShaderSource(ShaderID, shaderSource);
		GL20.glCompileShader(ShaderID);
		if(GL20.glGetShader(ShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(ShaderID, 500));
			System.out.println("Could not compile the shader.");
			System.exit(-1);
		}
		return ShaderID ;
	}
	public void CleanMemory() {
		StopShading() ;
		GL20.glDetachShader(ShaderProgram_ID, VertexShader_ID);
		GL20.glDetachShader(ShaderProgram_ID, FragmentShader_ID);
		GL20.glDeleteShader(VertexShader_ID);
		GL20.glDeleteShader(FragmentShader_ID);
		GL20.glDeleteProgram(ShaderProgram_ID);
	}
}
