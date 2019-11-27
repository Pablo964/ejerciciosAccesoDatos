package com.IvanLazcano.ChuckNorris;

public class Amigo 
{

	private String Nombre;
	private String Edad;
	private String Telefono;
	
	public Amigo(String nombre, String edad, String telefono) {
		this.Nombre = nombre;
		this.Edad = edad;
		this.Telefono = telefono;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}
	public String getEdad() {
		return Edad;
	}
	public void setEdad(String edad) {
		this.Edad = edad;
	}
	public String getTelefono() {
		return Telefono;
	}
	public void setTelefono(String telefono) {
		this.Telefono = telefono;
	}
	@Override
	public String toString() 
	{
		return "Amigo [nombre=" + Nombre + ", edad=" + Edad + ", telefono=" + Telefono + "]";
	}
	
	
}
