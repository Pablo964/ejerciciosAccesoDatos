package com.profesor.tema3_exercise10esqueleto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ManejadorBaseDatos 
{
	Scanner sc = new Scanner(System.in);
	String url = "jdbc:postgresql://localhost:5432/gestionpersonal";
	String usuario = "postgres";
	String password = "1234";
	
	public ManejadorBaseDatos() 
		throws ClassNotFoundException, SQLException
	{
		checkDataBaseIsCreated();
	}
	
	private void checkDataBaseIsCreated() throws 
		ClassNotFoundException, SQLException 
	{
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/";
		String usuario = "postgres";
		String password = "1234";
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Statement statement = con.createStatement();
		
		String sentenciaSQL = 
				"SELECT * FROM pg_database WHERE datname LIKE 'gestionpersonal';";
		
		ResultSet rs = statement.executeQuery(sentenciaSQL);
		
		if (!rs.next()) 
		{
			boolean exit = false;
			System.out.println("No se ha encontrado base de datos ");
			do
			{
				System.out.println("Crear base de datos automaticamente? (si/no)");
				String answer = sc.nextLine();
				
				if (answer.equals("si")) 
				{
					createDataBase();
					exit = true;
				}
				else if(answer.equals("no"))
				{
					System.out.println("No se creó la base de datos");
					System.exit(0);
				}
				else
				{
					System.out.println("Error de escritura prueba con (si/no)");
				}
			}
			while(!exit);
		}
		else
		{
			System.out.println("Se ha encontrado la base de datos ");
			boolean exit = false;
			do
			{
				System.out.println("Borrar y crear un nueva base de datos "
						+ "automaticamente? (si/no)");
				String answer = sc.nextLine();
				
				if (answer.equals("si")) 
				{
					createDataBase();
					exit = true;
				}
				else if(answer.equals("no"))
				{
					checkTablesAreCreated();
					exit = true;
				}
				else
				{
					System.out.println("Error de escritura prueba con (si/no)");
				}
			}
			while(!exit);
		}
	}
	
	private void checkTablesAreCreated() 
		throws ClassNotFoundException, SQLException 
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		String sentenciaSQL = 
				"SELECT count(*) " + 
				"FROM information_schema.tables " + 
				"WHERE table_type = 'BASE TABLE' " + 
				"AND table_name = 'persona' " + 
				"OR table_name = 'cliente' " + 
				"OR table_name = 'funcionario';";
		
		ResultSet rs = statement.executeQuery(sentenciaSQL);
		rs.next();
		if(rs.getInt(1) != 3)
		{
			System.out.println("No se ha encontrado tablas ");
			System.out.println("Crear tablas automaticamente? (si/no)");
			String answer = sc.nextLine();
			
			if (answer.equals("si")) {
				createTables();
			}
			else
			{
				System.out.println("No se crearon las tablas");
				System.exit(0);
			}
		}
		else {
			System.out.println("Tablas cargadas con exito");
		}
		
	}
	
	public void createTables() 
			throws ClassNotFoundException, SQLException
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		/* En el siguiente String tendremos que concatenar
		 * las diferentes instrucciones de borrado de tablas y su creación
		 * los tipos de datos que queramos hacer y las tablas heredadas
		 * Es el Script de creación de nuestra base de datos
		 */
		String sentenciaSQL = "DROP TYPE IF EXISTS valorcliente;\r\n"
				+ "DROP TYPE IF EXISTS tipoestado;\r\n"
				+ "DROP TYPE IF EXISTS tipo_trabajo;\r\n"
				+ "DROP TYPE IF EXISTS grupo_trabajo;\r\n"
				+"drop table if exists cliente;\r\n" + 
				"drop table if exists funcionario;\r\n" + 
				"drop table if exists persona;\r\n" + 
				"\r\n"
				+ "CREATE TYPE public.valorcliente AS ENUM\r\n" + 
				"    ('normal', 'premium');"
				+ "CREATE TYPE public.tipoestado AS ENUM\r\n" + 
				"    ('activo', 'pendiente', 'inactivo');"
				+ "CREATE TYPE public.grupo_trabajo AS ENUM\r\n" + 
				"    ('C2', 'C1', 'A2', 'A1', 'AP');"
				+ "CREATE TYPE public.tipo_trabajo AS\r\n" + 
				"(\r\n" + 
				"	grupo grupo_trabajo,\r\n" + 
				"	cod_cuerpo character varying(5) \r\n" + 
				");"+
				"CREATE TABLE persona\r\n" + 
				"(\r\n" + 
				"	numero serial primary key,\r\n" + 
				"	nombre varchar(30),\r\n" + 
				"	apellidos varchar(30),\r\n" + 
				"	direccion varchar(30),\r\n" + 
				"	telefono numeric,\r\n" + 
				"	fecha_nacim date\r\n" + 
				");\r\n" + 
				"\r\n" + 
				"create table cliente\r\n" + 
				"(\r\n" + 
				"	nro_cuenta varchar(24),\r\n" + 
				"	estado tipoestado,\r\n" + 
				"	tipo_cliente valorcliente\r\n" + 
				")INHERITS (persona);\r\n" + 
				"\r\n" + 
				"ALTER TABLE cliente\r\n" + 
				"ADD CONSTRAINT cp_cliente PRIMARY KEY(numero);\r\n" + 
				"\r\n" +
				"create table funcionario\r\n" + 
				"(\r\n" + 
				"	cargo tipo_trabajo,\r\n" + 
				"	departamento varchar(30),\r\n" + 
				"	fecha_ingreso date\r\n" + 
				")INHERITS (persona);\r\n" + 
				"\r\n" + 
				"ALTER TABLE funcionario\r\n" + 
				"ADD CONSTRAINT cp_funcionario PRIMARY KEY(numero);"; 

		try {
			statement.executeUpdate(sentenciaSQL);
			
		} catch (Exception e) {
			System.out.println("Problemas creando las tablas");
		} finally {
			con.close();
		}
		
		/*
		 * Aquí crearemos los datos de prueba iniciales que queramos insertar
		 * en nuestra base de datos
		 */
		String pruebaPersonas = "insert into persona\r\n" + 
				"(nombre, apellidos, direccion, telefono, fecha_nacim)\r\n" + 
				"values\r\n" + 
				"('Pedro', 'Antonio', 'Calle Dolores', 615050412, '01/01/2000'),\r\n" + 
				"('María', 'Rivera', 'Calle Gato', 615394000, '15/12/1992'),\r\n" + 
				"('Carlos', 'Terol', 'Calle Perro', 675429555, '18/07/1980');";
		
		String pruebaCliente = "insert into cliente\r\n" + 
				"(nombre, apellidos, direccion, telefono, fecha_nacim, nro_cuenta, estado, tipo_cliente)\r\n" + 
				"values\r\n" + 
				"('Jose', 'Rojas', 'Calle wiwi', 675432493, '22/03/1999', 'ES2334932829483', 'activo','normal'),\r\n" + 
				"('Pedro', 'Alonso', 'Calle los pueblos', 675423154, '22/01/1996', 'ES2331112829483', 'pendiente','premium'),\r\n" + 
				"('Alicia', 'Prada', 'Calle miranda', 675429232, '02/07/1998', 'ES233112243242', 'inactivo','normal');";
		
		String pruebaFuncionario = "insert into funcionario\r\n" + 
				"(nombre, apellidos, direccion, telefono, fecha_nacim, cargo, departamento, fecha_ingreso)\r\n" + 
				"values\r\n" + 
				"('Sandra', 'Camero', 'Calle Mutxamel', 615050434, '01/02/2001', ('A1', 'SDE21'), 'Recursos humanos','22/05/2017'),\r\n" + 
				"('María', 'Rivera', 'Calle del pecado', 615394242, '05/04/1991', ('A2', 'SDA11'), 'Marketing','10/01/2012'),\r\n" + 
				"('Carlos', 'Terol', 'Calle Miguel Hernandez', 675429299, '08/07/1990', ('C2', 'SQA81'), 'Finanzas','23/05/1990');";
		
		update(pruebaPersonas);
		update(pruebaCliente);
		update(pruebaFuncionario);
	}

	private void createDataBase() throws 
	ClassNotFoundException, SQLException 
	{
		System.out.println("Creando base de datos GestionPersonal");
		
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/";
		String usuario = "postgres";
		String password = "1234";
		
		Connection con = DriverManager.getConnection(url, usuario, password);
		Statement statement = con.createStatement();
		
		String sentenciaSQL = "DROP DATABASE IF EXISTS gestionpersonal; "
				+ "CREATE DATABASE gestionpersonal;";
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
		
		createTables();
	}
	
	private Connection conection() throws
	ClassNotFoundException, SQLException
	{
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(url, usuario, password);
	}
	
	
	public void update(String sentenceSQL) 
		throws ClassNotFoundException, SQLException
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		try 
		{
			int resultado = statement.executeUpdate(sentenceSQL);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			con.close();
		}
	}
	
	public ResultSet select(String sentenciaSQL) 
		throws ClassNotFoundException, SQLException
	{
		Connection con = conection();
		Statement statement = con.createStatement();
		
		ResultSet rs = statement.executeQuery(sentenciaSQL);
		
		con.close();
		
		return rs;
	}
}
                           
                           
  