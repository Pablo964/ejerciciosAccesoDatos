package com.IvanLazcano.ChuckNorris;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public class App 
{
	static Scanner sc = new Scanner(System.in);
	
	public static void menu() throws Exception
	{
		Gson gson = new Gson(); 
		String jsonJokes = readUrl("http://api.icndb.com/jokes");
		ObjectJson post = gson.fromJson(jsonJokes, ObjectJson.class);
		System.out.println("Elige una opción");
		String opcion = sc.nextLine();
		switch (opcion) 
		{
			case "1":
				leerJsonArray();
			break;
			case "2":
				leerJsonObjeto();
			break;
			case "3":
				serializarObjetoJson(post);;
			break;
			case "4":
				deserializarObjetoJson(post);
				break;
			case "5":
				crearFicheroJson(post);
				break;
			case "6":
				crearFicheroJson(post);
				break;
			case "7":
				leerJsonComplejo();
				break;
			case "8":
				leerDeApiCrearsql();
				break;
		default:
			break;
		}
	}
	
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	public static void leerDeApiCrearsql() throws Exception
	{
		//si el json es un objeto
		Gson gson = new Gson(); 
		String jsonJokes = readUrl("http://api.icndb.com/jokes");
		ObjectJson post = gson.fromJson(jsonJokes, ObjectJson.class);
		
		System.out.println("Creando base de datos jsonprueba");
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/";
		String usuario = "postgres";
		String password = "1234";
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Statement statement = con.createStatement();
		
		String sentenciaSQL = "DROP DATABASE IF EXISTS jsonprueba; "
				+ "CREATE DATABASE jsonprueba;";
		try {
			int errorCode = statement.executeUpdate(sentenciaSQL);
			
			if (errorCode == 0) {
				System.out.println("Se ha creado con exito la base de datos");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			con.close();
		}
		
		//creacion de tablas
		
		url+="jsonprueba";
		con = DriverManager.getConnection(url, usuario, password);
		statement = con.createStatement();
		
		sentenciaSQL = "drop table if exists value; "
				+ "create table value"
				+ "("
				+ 	" id numeric primary key,"
				+ 	" jokes varchar(1000),"
				+ 	" categories text[]"
				+ ");";
		statement.executeUpdate(sentenciaSQL);
		sentenciaSQL = "";
		for (int i = 0; i < post.getValue().size(); i++) 
		{
			Value v = post.getValue().get(i);
			String categories = "";
			String joke = v.getJoke();
			
			for (int j = 0; j < v.getCategories().size(); j++) 
			{
				if (j == v.getCategories().size()-1) 
				{
					categories += "'"+v.getCategories().get(j)+"'"; 
				}
				else
					categories += "\""+v.getCategories().get(j)+"\","; 
			}

			joke = joke.replaceAll("'", " ");
			
			sentenciaSQL = "insert into value"
							+ "(id, jokes, categories)"
							+ "values"
							+ "('"+v.getId()+"','"+joke+"',ARRAY["+categories+"]::varchar[]);";
			System.out.println(sentenciaSQL);
			statement.executeUpdate(sentenciaSQL);
			
		}
		
		sentenciaSQL = "drop function if exists verdatos(integer);"
				+ "drop type if exists tipo_value;"
				+ "CREATE TYPE tipo_value as (id integer, joke character varying, categorie text[]);\r\n" + 
				"CREATE OR REPLACE FUNCTION verdatos(id_buscar integer) RETURNS SETOF tipo_value\r\n" + 
				"AS\r\n" + 
				"$$\r\n" + 
				"DECLARE\r\n" + 
				"	reg tipo_value%ROWTYPE;\r\n" + 
				"BEGIN\r\n" + 
				"	FOR reg IN SELECT value.id, value.jokes, value.categories\r\n" + 
				"		FROM value\r\n" + 
				"		WHERE value.id = id_buscar\r\n" + 
				"	LOOP\r\n" + 
				"		RETURN NEXT reg;\r\n" + 
				"	END LOOP;\r\n" + 
				"RETURN;\r\n" + 
				"END;\r\n" + 
				"$$\r\n" + 
				"LANGUAGE plpgsql;";
		statement.executeUpdate(sentenciaSQL);
		
		CallableStatement cStmt = con.prepareCall("{call verdatos(?)}");
		 cStmt.setInt(1, (1));
		 ResultSet rs = cStmt.executeQuery();
		 while (rs.next()) 
		 {
			 System.out.println("Código: " + rs.getInt(1) + "\n" +
					 "joke: " + rs.getString(2) + "\n" + 
					 "categories: " + rs.getString(3));
		 }

		con.close();
	}
	public static void leerJsonArray() throws Exception
	{
		//Si todo el json es un array
		Gson gson = new Gson(); 
		String jsonTasks = readUrl("https://jsonplaceholder.typicode.com/todos");
		Task[] tasks = gson.fromJson(jsonTasks, Task[].class);
		for (Task task : tasks) 
		{
			//System.out.println(task.toString());
		}
	}
	public static void leerJsonObjeto() throws Exception
	{
		//si el json es un objeto
		Gson gson = new Gson(); 
		String jsonJokes = readUrl("http://api.icndb.com/jokes");
		ObjectJson post = gson.fromJson(jsonJokes, ObjectJson.class);
		for (int i = 0; i < post.getValue().size(); i++) 
		{
			Value v = post.getValue().get(i);
			System.out.println(v.toString());
		}
	}
	public static void serializarObjetoJson(ObjectJson post) throws IOException
	{
		//SERIALIZAR EL OBJETO OBJECTJSON
		File ficheroSerializar = new File("jokes.dat");
		FileOutputStream ficheroSalida =
		new FileOutputStream(ficheroSerializar);
		ObjectOutputStream ficheroObjetos =
		new ObjectOutputStream(ficheroSalida);
		ficheroObjetos.writeObject(post);//Serializa el objeto resultado del json
		ficheroObjetos.close();
	}
	public static void deserializarObjetoJson(ObjectJson post) throws IOException, ClassNotFoundException
	{
		File ficheroSerializar = new File("jokes.dat");
		FileInputStream ficheroEntrada =
				new FileInputStream(ficheroSerializar);
				ObjectInputStream ficheroObjetosEntrada =
				new ObjectInputStream(ficheroEntrada);
		ObjectJson c = (ObjectJson) ficheroObjetosEntrada.readObject();
		System.out.println(c.toString());
		ficheroObjetosEntrada.close();
	}
	public static void crearFicheroJson(ObjectJson post) throws IOException
	{
		Gson gson = new Gson();
		String sFichero = "fichero.json"; 
    	File fichero = new File(sFichero);
    	BufferedWriter bw = new BufferedWriter(new FileWriter(sFichero));	    	 
    	String json = gson.toJson(post);
    	bw.write(json);
	}
	public static void leerJsonComplejo() throws IOException
	{
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("amigos.json"));
    	ObjetoGeneralAmigos o = gson.fromJson(reader, ObjetoGeneralAmigos.class);
    	System.out.println(o.toString());
    	reader.close();
	}
    public static void main( String[] args ) throws IOException
    {
    	try {
			
			menu();
		} 
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
