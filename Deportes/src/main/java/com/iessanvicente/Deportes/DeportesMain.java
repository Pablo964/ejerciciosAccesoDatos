package com.iessanvicente.Deportes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeportesMain 
{

	static String url = "jdbc:postgresql://localhost:5432/Deporte";
	static String usuario = "postgres";
	static String passw = "1234";
	static Scanner sc = new Scanner(System.in);
	
	public static void addDeporte(Connection con, Statement statement) 
			throws SQLException 
	{
		System.out.println("Introduce el nombre del deporte");
		String nombreDeporte = sc.nextLine();


			statement.executeUpdate(
					"insert into deporte " +
					"(nombre)"+
					"values "
					+ "('"+ nombreDeporte + "')");

	}
	
	public static void addDeportista(Connection con) throws SQLException 
	{	
		System.out.println("Introduce el nombre del deportista");
		String nombreDeportista = sc.nextLine();
		System.out.println("Introduce el código del deporte que practica el deportista");
		int codDeporte = sc.nextInt();
		sc.nextLine();
		 PreparedStatement pStmt = con.prepareStatement(
				 "insert into deportista "
				 +"(nombre, codDeporte)"
				 + "values"
				 + "(?, ?)");
		 pStmt.setString(1, nombreDeportista);
		 pStmt.setInt(2, codDeporte);
		 pStmt.executeUpdate();
		
	}
	
	private static void buscarPorNombreDeDeportista() throws SQLException 
	{
		System.out.println("Introduce el nombre del deportista a buscar");
		String nombreDeportista = sc.nextLine();
		
		 Connection con = DriverManager.getConnection(url, usuario, passw);
		 CallableStatement cStmt = con.prepareCall("{call verdatosdeportista(?)}");
		 cStmt.setString(1, ("%" + nombreDeportista + "%"));
		 ResultSet rs = cStmt.executeQuery();
		 while (rs.next()) 
		 {
			 System.out.println("Código deportista: " + rs.getInt(1) + "\n" +
					 "Nombre deportista: " + rs.getString(2) + "\n" + 
					 "Nombre deporte: " + rs.getString(3));
		 }

	}
	
	private static void deporteNoAsignado() throws SQLException 
	{

		 Connection con = DriverManager.getConnection(url, usuario, passw);
		 CallableStatement cStmt = con.prepareCall("{call deporteNoAsignado()}");
		 ResultSet rs = cStmt.executeQuery();
		 while (rs.next()) 
		 {
			 System.out.println("Nombre deporte: " + rs.getString(1));
		 }

	}
	
	public static void Menu(Connection con) throws SQLException
	{
		Statement st;
		st = con.createStatement(); 
		String opcion = "0";
		do 
		{
			System.out.println("1: Añadir deporte.");
			System.out.println("2: Añadir deportista.");
			System.out.println("3: Buscar deportista.");
			System.out.println("4: Deportes vacíos.");
			System.out.println("0: Salir");
			opcion = sc.nextLine();
			
			switch (opcion) {
			case "1":
				addDeporte(con, st);
				break;
			case "2":
				addDeportista(con);
				break;
			case "3":
				buscarPorNombreDeDeportista();
				break;
			case "4":
				deporteNoAsignado();
				break;
			case "0":
				System.out.println("Adiós!");
				break;
			default:
				System.out.println("No se ha encontrado la opción");
				break;
			}
		}
		while (!opcion.equals("0"));
	}
	
	public static void main(String[] args) throws ClassNotFoundException,
		SQLException 
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(url, usuario, passw);
		Menu(con);
	}

}

