//Pablo Sánchez Alonso
package com.profesor.tema3_exercise10esqueleto;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ejercicio0310 
{
	public static Scanner sc = new Scanner(System.in);
	public static Pattern fecha = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");
	public static Pattern id = Pattern.compile("^\\d+$");;
	
	public static void main (String[] args) throws ClassNotFoundException, SQLException
	{
		ManejadorBaseDatos manejador = new ManejadorBaseDatos();
		
		boolean exit = false;
		
		do
		{
			ShowMenu();
			switch (getOption()) 
			{
				case 1:
					addPersona(manejador);
					break;
				case 2:
					addCliente(manejador);		
					break;
				case 3:
					addFuncionario(manejador);			
					break;
				case 4:
					modifyPersona(manejador);			
					break;
				case 5:
					modifyCliente(manejador);			
					break;
				case 6:
					modifyFuncionario(manejador);			
					break;
				case 7:
					showPersonas(manejador);			
					break;
				case 8:
					showClientes(manejador);			
					break;
				case 9:
					showFuncionarios(manejador);			
					break;
				case 0:
					exit = true;
					break;
				default:
					break;
			}
		}
		while(!exit);
	}
	
	private static int getOption() 
	{
		System.out.println("Option:");
		int option = sc.nextInt();
		sc.nextLine();
		return option;
	}

	private static void ShowMenu()
	{
		System.out.println("1.-Añadir persona");
		System.out.println("2.-Añadir cliente");
		System.out.println("3.-Añadir funcionario");
		System.out.println("4.-Modificar persona");
		System.out.println("5.-Modificar cliente");
		System.out.println("6.-Modificar funcionario");
		System.out.println("7.-Ver personas");
		System.out.println("8.-Ver clientes");
		System.out.println("9.-Ver funcionarios");
		System.out.println("0.-Salir");
	}

	private static String[] datosPersona()
	{
		Pattern regex;
		String tlf;
		String nacido;
		
		System.out.println("Introduce el nombre de la persona: ");
		String nombre = sc.nextLine();
		System.out.println("Introduce los apellidos de la persona: ");
		String apellidos = sc.nextLine();
		System.out.println("Introduce la direccion de la persona: ");
		String direccion = sc.nextLine();
		do 
		{
			regex = Pattern.compile("^[0-9]{9}$");
			System.out.println("Introduce un teléfono(Se debe componer"
					+ " de un número de 9 dígitos)");
			tlf = sc.nextLine();
		} 
		while (!regex.matcher(tlf).find() && !tlf.equals(""));
		
		do 
		{
			regex = fecha;
			System.out.println("Introduce una fecha de nacimiento"
					+ "(Debe de tener el formato: dd/mm/yyyy)");
			nacido = sc.nextLine();
		} 
		while (!regex.matcher(nacido).find() && !nacido.equals(""));

		String[] datos = {nombre, apellidos, direccion, tlf, nacido};

		return datos;
	}
	
	private static String[] datosFuncionario(ManejadorBaseDatos manejador)
	{		
		System.out.println("Introduce el grupo del cargo\n A1: 1\n A2: 2\n"
				+ " C1: 3\n C2: 4\n AP: 5");
		
		String opcion = sc.nextLine();
		String grupo = "";
		do {
			switch (opcion) 
			{
				case "1":
					grupo = "A1";
					break;
				case "2":
					grupo = "A2";
					break;
				case "3":
					grupo = "C1";
					break;
				case "4":
					grupo = "C2";
					break;
				case "5":
					grupo = "AP";
					break;
				case "":
					grupo = "";
				default:
					if(!grupo.equals(""))
						System.out.println("Error: introduce 1, 2, 3, 4 o 5");
					opcion = "0";
					break;
			}
			
		} while (opcion.equals("0") && opcion.equals(""));
		
		String codigoCargo = "";
		do
		{
		System.out.println("Introduce el código del cargo(Sólo 5 dígitos)");
		codigoCargo = sc.nextLine();
		}
		while(codigoCargo.length() > 5);
		
		System.out.println("Introduce el departamento");
		String departamento = sc.nextLine();
		String fechaIngreso;
		do 
		{
			System.out.println("Introduce la fecha de ingreso"
					+ "(Debe de tener el formato: dd/mm/yyyy)");
			fechaIngreso = sc.nextLine();
		} 
		while (!fecha.matcher(fechaIngreso).find() && !fechaIngreso.equals(""));

		String[] datosFuncionario = {grupo, codigoCargo, departamento,
				fechaIngreso};
		return datosFuncionario;
	}
	
	private static String[] datosCliente()
	{
		System.out.println("Introduce el IBAN de la cuenta");
		String nroCuenta = sc.nextLine();
		System.out.println("Introduce el estado de la cuenta \nactivo: 1\n"
				+ "pendiente: 2 \ninactivo: 3");
		
		String opcion = sc.nextLine();
		String estado = "";
		do {
			switch (opcion) 
			{
				case "1":
					estado = "activo";
					break;
				case "2":
					estado = "pendiente";
					break;
				case "3":
					estado = "inactivo";
					break;
				case "":
					estado = "";
				default:
					if (!estado.equals("")) 
					{
						System.out.println("Error: introduce 1, 2 o 3");
					}
					opcion = "0";
					break;
			}
			
		} while (opcion.equals("0") && opcion.equals(""));
		
		System.out.println("Introduce el tipo de cliente \nnormal: 1 \n"
				+ "premium: 2");
		opcion = sc.nextLine();
		String tipoCliente = "";
		do {
			if(opcion.equals("1"))
			{
				tipoCliente = "normal";
			}
			else if(opcion.equals("2"))
			{
				tipoCliente = "premium";
			}
			else if(opcion.equals(""))
			{
				tipoCliente = "";
			}
			else
			{
				opcion = "0";
			}
			
		} while (opcion.equals("0") && opcion.equals(""));
		
		String[] datos = {nroCuenta, estado, tipoCliente};
		return datos;
	}
	
	private static void addPersona(ManejadorBaseDatos manejador) 
			throws ClassNotFoundException, SQLException 
	{
		
		String[] datos = datosPersona();
		if(datos[3].equals(""))
			datos[3] = "0";
		if(datos[4].equals(""))
			datos[4] = "01/01/1001";
		
		
		String sentenciaSQL = 
				"insert into persona "
				+ "(nombre, apellidos, direccion, telefono, fecha_nacim)"
				+ "values"
				+ "('"+datos[0]+"','"+datos[1]+"','"
				+datos[2]+"',"+Integer.parseInt(datos[3])+
				",'"+datos[4]+"');";
		
		manejador.update(sentenciaSQL);
	}
	

	private static void addCliente(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		String[] datos = datosPersona();
		
		String[] datosCliente = datosCliente();
		if(datos[3].equals(""))
			datos[3] = "0";
		if(datos[4].equals(""))
			datos[4] = "01/01/1001";
		
		if(datosCliente[1].equals(""))
			datosCliente[1] = "inactivo";
		if(datosCliente[2].equals(""))
			datosCliente[2] = "normal";
		
		String sentenciaSQL = 
				"insert into cliente "
				+ "(nombre, apellidos, direccion, telefono, fecha_nacim,"
				+ " nro_cuenta, estado, tipo_cliente)"
				+ "values"
				+ "('"+datos[0]+"','"+datos[1]+"','"
				+datos[2]+"',"+Integer.parseInt(datos[3])+
				",'"+datos[4]+"','"+datosCliente[0]+"','"+datosCliente[1]+"',"
						+ "'"+datosCliente[2]+"');";
		
		manejador.update(sentenciaSQL);
	}
	
	
	private static void addFuncionario(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		String[] datos = datosPersona();
		String[] datosFunci = datosFuncionario(manejador);
		
		if(datos[3].equals(""))
			datos[3] = "0";
		if(datos[4].equals(""))
			datos[4] = "01/01/1001";
		
		if(datosFunci[2].equals(""))
			datosFunci[2] = "A1";
		if(datosFunci[3].equals(""))
			datosFunci[3] = "AS213";
		
		if(datosFunci[1].equals(""))
			datosFunci[1] = "01/01/2019";
		
		String sentenciaSQL = 
				"insert into funcionario "
				+ "(nombre, apellidos, direccion, telefono, fecha_nacim, cargo,"
				+ " departamento, fecha_ingreso)"
				+ "values"
				+ "('"+datos[0]+"','"+datos[1]+"','"
				+datos[2]+"',"+Integer.parseInt(datos[3])+
				",'"+datos[4]+"',('"+datosFunci[2]+"','"+datosFunci[3]+"'),"
						+ "'"+datosFunci[0]+"','"+datosFunci[1]+"');";
		
		manejador.update(sentenciaSQL);
	}
	
	private static void modificarGeneral(ManejadorBaseDatos manejador,
			String tipoUsu)
	{
		boolean esFuncionario = false;
		boolean esCliente = false;
		
		if(tipoUsu.equals("cliente"))
			esCliente = true;
		if(tipoUsu.equals("funcionario"))
			esFuncionario = true;
		
		String idBuscar = "";
		do 
		{
			System.out.println("Introduce el id de "+tipoUsu+" a modificar(Sólo"
					+ " puede ser un número)");
			idBuscar = sc.nextLine();
		} while (!id.matcher(idBuscar).find());
		
		String sentenciaSQL = "select * from "+tipoUsu+" where numero = "+idBuscar+";";
		
		ResultSet rs = null;
		try {
			rs = manejador.select(sentenciaSQL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String nombreUsuMod = "";
		String apellidosUsuMod = "";
		String direccionUsuMod = "";
		String telefonoUsuMod = "";
		String fechaNacimUsuMod = "";
		String nroCuentaUsuMod = "";
		String estadoUsuMod = "";
		String tipoClienteUsuMod = "";	
		String cargoUsuMod = "";
		String depUsuMod ="";
		String fechaIngresoUsuMod = "";
		
		try 
		{
			while(rs.next())
			{
				System.out.println("/-----------------------");
				nombreUsuMod = rs.getString("nombre");
				System.out.println("Nombre: "+ nombreUsuMod);
				apellidosUsuMod = rs.getString("apellidos");
				System.out.println("Apellidos: "+ apellidosUsuMod);	
				direccionUsuMod = rs.getString("direccion");
				System.out.println("Direccion: "+ direccionUsuMod);
				telefonoUsuMod = rs.getString("telefono");
				System.out.println("Teléfono: "+ telefonoUsuMod);
				fechaNacimUsuMod = rs.getString("fecha_nacim");
				System.out.println("Fecha nacimiento: "+ fechaNacimUsuMod);
				
				if(esCliente)
				{
					nroCuentaUsuMod = rs.getString("nro_cuenta");
					System.out.println("Numero de cuenta: "+ nroCuentaUsuMod);
					estadoUsuMod = rs.getString("estado");
					System.out.println("Estado de cuenta: "+ estadoUsuMod);
					tipoClienteUsuMod = rs.getString("tipo_cliente");
					System.out.println("Tipo de cliente: " + tipoClienteUsuMod);
				}
				if(esFuncionario)
				{
					cargoUsuMod = rs.getString("cargo");
					System.out.println("Cargo: "+ cargoUsuMod);
					depUsuMod = rs.getString("departamento");
					System.out.println("Departamento: "+ depUsuMod);
					fechaIngresoUsuMod = rs.getString("fecha_ingreso");
					System.out.println("Fecha ingreso: " + fechaIngresoUsuMod);
				}
				System.out.println("/------------------------");
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		String respuesta;
		boolean modificar = false;
		do 
		{
			System.out.println("¿Lo desea modificar? s/n");
			respuesta = sc.nextLine();
			if (respuesta.toLowerCase().equals("s")) 
			{
				modificar = true;
			}
			else if(respuesta.toLowerCase().equals("n") || respuesta.equals(""))
			{
				return;
			}
			else
			{
				respuesta = "n/a";
			}
		} 
		while (respuesta.equals("n/a") || !modificar);
		
		String[] datos = datosPersona();
		String[] datosFunc;
		String[] datosCliente;
		
		String sqlFunc = "";
		String sqlCliente = "";
		
		if (datos[0].equals("")) 
			datos[0] = nombreUsuMod;
		if (datos[1].equals("")) 
			datos[1] = apellidosUsuMod;
		if (datos[2].equals("")) 
			datos[2] = direccionUsuMod;
		if (datos[3].equals("")) 
			datos[3] = telefonoUsuMod;
		if (datos[4].equals("")) 
			datos[4] = fechaNacimUsuMod;
		
		
		if(esCliente)
		{
			datosCliente = datosCliente();
			if (datosCliente[0].equals("")) 
				datosCliente[0] = nroCuentaUsuMod;
			if (datosCliente[1].equals("")) 
				datosCliente[1] = estadoUsuMod;
			if (datosCliente[2].equals("")) 
				datosCliente[2] = tipoClienteUsuMod;
			
			sqlCliente = ", nro_cuenta = '"+datosCliente[0]+"', "
					+ "estado = '"+datosCliente[1]+"', "
					+ "tipo_cliente = '"+datosCliente[2]+"'";
		}
			
		if (esFuncionario) 
		{
			datosFunc = datosFuncionario(manejador);
			
			if (datosFunc[0].equals("")) 
				datosFunc[0] = cargoUsuMod.substring(
						1, cargoUsuMod.length()).split(",")[0];
			if (datosFunc[1].equals("")) 
				datosFunc[1] = cargoUsuMod.substring(
						1, cargoUsuMod.length()-1).split(",")[1];
			if (datosFunc[2].equals("")) 
				datosFunc[2] = depUsuMod;
			if (datosFunc[3].equals("")) 
				datosFunc[3] = fechaIngresoUsuMod;	
			
			sqlFunc = ", cargo = ('"+datosFunc[0]+"', '"+datosFunc[1]+"'), "
					+ "departamento = '"+datosFunc[2]+"',"
					+ " fecha_ingreso = '"+datosFunc[3]+"'";
		}
		
		String updateSQL = 
				"update "+tipoUsu
				+ " set nombre = '"+datos[0]+"', apellidos = '"+datos[1]+"',"
				+ "	direccion = '"+datos[2]+"', telefono = '"+datos[3]+"',"
				+"fecha_nacim = '"+datos[4]+"'"+sqlFunc+sqlCliente
				+" where numero = "+idBuscar+";";
		try {
			manejador.update(updateSQL);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	private static void modifyPersona(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		modificarGeneral(manejador, "persona");
	}
	
	private static void modifyCliente(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		modificarGeneral(manejador, "cliente");
	}
	
	private static void modifyFuncionario(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		modificarGeneral(manejador, "funcionario");
	}
	
	private static void showPersonas(ManejadorBaseDatos manejador) 
		throws SQLException, ClassNotFoundException 
	{
		String sentenciaSQL = "select * from persona;";
		
		ResultSet rs = null;
		try {
			rs = manejador.select(sentenciaSQL);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("--------PERSONAS--------");
		while(rs.next())
		{
			System.out.println("Codigo: "+rs.getString("numero"));
			System.out.println("Nombre: "+rs.getString("nombre"));
			System.out.println("Apellidos: "+rs.getString("apellidos"));
			System.out.println("Dirección: "+rs.getString("direccion"));
			System.out.println("Teléfono: "+rs.getString("telefono"));
			System.out.println("Fecha nacimiento: "+rs.getString("fecha_nacim"));
			System.out.println("//-----------");
		}
		System.out.println();
	}
	
	private static void showClientes(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		String sentenciaSQL = "select * from cliente;";
		
		ResultSet rs = null;
		try {
			rs = manejador.select(sentenciaSQL);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("---------CLIENTES---------");
		while(rs.next())
		{
			System.out.println("Codigo: "+rs.getString("numero"));
			System.out.println("Nombre: "+rs.getString("nombre"));
			System.out.println("Apellidos: "+rs.getString("apellidos"));
			System.out.println("Dirección: "+rs.getString("direccion"));
			System.out.println("Teléfono: "+rs.getString("telefono"));
			System.out.println("Fecha nacimiento: "+rs.getString("fecha_nacim"));
			System.out.println("Número cuenta: "+rs.getString("nro_cuenta"));
			System.out.println("Estado: "+rs.getString("estado"));
			System.out.println("Tipo cliente: "+rs.getString("tipo_cliente"));
			System.out.println("//------------");
		}
		System.out.println();
	}
	
	private static void showFuncionarios(ManejadorBaseDatos manejador) 
		throws ClassNotFoundException, SQLException 
	{
		String sentenciaSQL = "select * from funcionario;";
		
		ResultSet rs = null;
		try {
			rs = manejador.select(sentenciaSQL);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("---------FUNCIONARIO---------");
		while(rs.next())
		{
			System.out.println("Codigo: "+rs.getString("numero"));
			System.out.println("Nombre: "+rs.getString("nombre"));
			System.out.println("Apellidos: "+rs.getString("apellidos"));
			System.out.println("Dirección: "+rs.getString("direccion"));
			System.out.println("Teléfono: "+rs.getString("telefono"));
			System.out.println("Fecha nacimiento: "+rs.getString("fecha_nacim"));
			System.out.println("Cargo: "+rs.getString("cargo"));
			System.out.println("Departamento: "+rs.getString("departamento"));
			System.out.println("Fecha ingreso: "+rs.getString("fecha_ingreso"));
			System.out.println("//------------");
		}
		System.out.println();
	}
}