public class Amigo 
{
	String nombre;
	String edad;
	String telefono;
	
	
	public Amigo(String nombre, String edad, String telefono)
	{
		this.nombre = nombre;
		this.edad = edad;
		this.telefono = telefono;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	@Override
	public String toString() {
		return "Amigo [nombre=" + nombre + ", edad=" + edad +
				", telefono=" + telefono + "]";
	}
	
	@Override
	public boolean equals(Object o) 
	{
		Amigo a = (Amigo)o;
	    if (a.edad.equals(this.edad) && a.nombre.equals(this.nombre) 
	    		&& a.telefono.equals(this.telefono))
	    {
	        return true;
	    }
	    return false;
	}
}
