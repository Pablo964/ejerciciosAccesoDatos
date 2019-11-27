package com.IvanLazcano.ChuckNorris;

public class ObjetoGeneralAmigos 
{
	private ObjetoAmigos Amigos;

	public ObjetoGeneralAmigos(ObjetoAmigos a) {
		this.Amigos= a;
	}

	public ObjetoAmigos getA() {
		return Amigos;
	}

	public void setA(ObjetoAmigos a) {
		this.Amigos = a;
	}

	@Override
	public String toString() {
		return "ObjetoGeneralAmigos [a=" + Amigos + "]";
	}
	
	
}
