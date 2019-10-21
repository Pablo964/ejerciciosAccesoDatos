package com.iessanvicente.conectar1;

import java.sql.*;
import java.util.Scanner;

public class App 
{
	static String url = "jdbc:postgresql://localhost:5432/dia01";
	static String usuario ="postgres";
	static String password = "1234";
	static String sql = null;
	
	public static void insertar() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(
				url, usuario, password);
		Statement statement = con.createStatement();
		sql = "insert into gente values('3', 'Juan')";
		int registros = statement.executeUpdate(sql);
		System.out.println("Registros afectados: "+registros);
		con.close();
	}
	
	public static void crearTabla() throws SQLException, ClassNotFoundException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(
				url, usuario, password);
		Statement statement = con.createStatement();
		sql = "drop table gente";
		statement.executeUpdate(sql);
		sql = "create table gente(id varchar(50) primary key, nombre varchar(50))";
		statement.executeUpdate(sql);
		con.close();
	}
	
	public static void consulta() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(
				url, usuario, password);
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(
				"select * from articulos where upper(nombre) like 'A%'");
		
		while (rs.next()) 
		{
			String nombre = rs.getString("nombre");
			String id = rs.getString("id");
			double precio = rs.getDouble("precio");
			
			System.out.println("ARTÍCULO");
			System.out.println("id: "+ id + "\nnombre: "+nombre + "\nprecio: "+precio);
			System.out.println();
			
		}
		
		rs.close();
		con.close();
	}
	
	public static void anyadirYbuscar() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(
				url, usuario, password);
		Statement statement = con.createStatement();
		Scanner sc = new Scanner(System.in);
		int opcion = 1;
		
		
		do 
		{
			System.out.println("1.Buscar\n2.Añadir\n3.Modificar\n0.Salir");
			System.out.print("Introduce una opción: ");
			opcion = sc.nextInt();
			sc.nextLine();
			switch (opcion) 
			{
				case 2:
					System.out.println("Introduce el nombre del artículo");
					String nombre = sc.nextLine();

					System.out.println("Introduce el id del artículo");
					String id = sc.nextLine();
					
					System.out.println("Introduce el precio del artículo");
					double precio = sc.nextDouble();
					
					sql="insert into articulos values("
							+""+id+",'"+nombre+"',"+precio+")";
					System.out.println(sql);
					statement.executeUpdate(sql);
					break;
				case 1:
					System.out.println("Introduce el texto a buscar");
					String buscar = sc.nextLine();
					sql="select * from articulos "
							+ "where nombre like '%"+buscar+"%'";
					ResultSet rs = statement.executeQuery(sql);
					
					
					while (rs.next()) 
					{
						String nombre1 = rs.getString("nombre");
						String id1 = rs.getString("id");
						double precio1 = rs.getDouble("precio");
						
						System.out.println("ARTÍCULO");
						System.out.println("id: "+ id1 + "\nnombre: "+nombre1 + "\nprecio: "+precio1);
						System.out.println();
						
					}
					rs.close();
					break;
					
				case 3:
					System.out.print("Introduce el id a buscar: ");
					String idModificar = sc.nextLine();
					System.out.print("Introduce el nuevo nombre a asignar: ");
					String nuevoNombre = sc.nextLine();
					
					sql="update articulos set nombre ='"+nuevoNombre+"'"
							+ " where id ="+idModificar+";";
					System.out.println(sql);
					statement.executeUpdate(sql);
					
					break;
			default:
				break;
			}
			
		} while (opcion != 0);
		
		con.close();
	}
	
    public static void main( String[] args ) throws ClassNotFoundException,SQLException
    {    	
		
		/*consulta();
		crearTabla();
		insertar();*/
    	
    	anyadirYbuscar();
	}
    
}
