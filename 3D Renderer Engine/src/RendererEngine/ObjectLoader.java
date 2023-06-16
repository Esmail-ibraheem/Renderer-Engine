package RendererEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Model.RawModel;

public class ObjectLoader {
	public static RawModel LoadObjectModel(String FileName , DataLoader loader) {
		FileReader fr = null ;
		try {
			fr = new FileReader(new File("res/"+FileName+".obj")) ;
		} catch (FileNotFoundException e) {
			System.err.println("Could not read the object file");
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(fr) ;
		String Line ;
		List<Vector3f> Vertices = new ArrayList<Vector3f>() ;
		List<Vector3f> Normals = new ArrayList<Vector3f>() ;
		List<Vector2f> Textures = new ArrayList<Vector2f>() ;
		List<Integer> Indices = new ArrayList<Integer>() ;
		float[] VertexArray = null ;
		float[] NormalsArray = null ;
		float[] TextureArray = null ;
		int[] IndicesArray = null ;
		
		try {
			while(true) {
				Line = reader.readLine();
				String[] CurrentLine = Line.split(" ");
				if(Line.startsWith("v ")) {
					Vector3f Vertex = new Vector3f(Float.parseFloat(CurrentLine[1]) , Float.parseFloat(CurrentLine[2]) , Float.parseFloat(CurrentLine[3]));
					Vertices.add(Vertex);
				}else if(Line.startsWith("vt ")) {
					Vector2f Texture = new Vector2f(Float.parseFloat(CurrentLine[1]) , Float.parseFloat(CurrentLine[2]));
					Textures.add(Texture);
				}else if(Line.startsWith("vn ")) {
					Vector3f Normal = new Vector3f(Float.parseFloat(CurrentLine[1]) , Float.parseFloat(CurrentLine[2]) , Float.parseFloat(CurrentLine[3]));
					Normals.add(Normal);
				}else if(Line.startsWith("f")) {
					TextureArray = new float[Vertices.size()*2];
					NormalsArray = new float[Vertices.size() *3] ;
					break ;
				}
			}
			while(Line!=null) {
				if(!Line.startsWith("f ")) {
					Line = reader.readLine();
					continue ;
				}
				String[] CurrentLine = Line.split(" ") ;
				String[] Vertex1 = CurrentLine[1].split("/") ;
				String[] Vertex2 = CurrentLine[2].split("/") ;
				String[] Vertex3 = CurrentLine[3].split("/") ;
				ProcessVertex(Vertex1 , Indices , Textures , Normals , TextureArray , NormalsArray) ;
				ProcessVertex(Vertex2 , Indices , Textures , Normals , TextureArray , NormalsArray) ;
				ProcessVertex(Vertex3 , Indices , Textures , Normals , TextureArray , NormalsArray) ;
				Line = reader.readLine();
			}
			reader.close() ;
		}catch(Exception e) {
			e.printStackTrace();
		}
		VertexArray = new float[Vertices.size() * 3] ;
		IndicesArray = new int[Indices.size()] ;
		
		int VertexPointer = 0 ;
		for(Vector3f vertex:Vertices) {
			VertexArray[VertexPointer++] = vertex.x;
			VertexArray[VertexPointer++] = vertex.y;
			VertexArray[VertexPointer++] = vertex.z;
		}
		for(int i =0 ; i<Indices.size() ; i++) {
			IndicesArray[i] = Indices.get(i);
		}
		return loader.LoadDataToVertexArrayObject(VertexArray, TextureArray, NormalsArray, IndicesArray);
	}
	private static void ProcessVertex(String[] VertexData , List<Integer> indices , List<Vector2f> textures , List<Vector3f> normals , float[] textureArray , float[] normalsArray) {
		int CurrentVertexPointer = Integer.parseInt(VertexData[0])-1 ;
		indices.add(CurrentVertexPointer) ;
		Vector2f CurrentTexture = textures.get(Integer.parseInt(VertexData[1])-1) ;
		textureArray[CurrentVertexPointer * 2] = CurrentTexture.x ;
		textureArray[CurrentVertexPointer * 2 + 1] = 1 - CurrentTexture.y ;
		Vector3f CurrentNormal = normals.get(Integer.parseInt(VertexData[2])-1) ;
		normalsArray[CurrentVertexPointer  *3] = CurrentNormal.x ;
		normalsArray[CurrentVertexPointer  *3 + 1] = CurrentNormal.y ;
		normalsArray[CurrentVertexPointer  *3 + 2] = CurrentNormal.z ;
	}
}
