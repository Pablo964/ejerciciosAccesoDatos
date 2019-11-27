package com.IvanLazcano.ChuckNorris;

import java.util.List;

public class ObjetoAmigos 
{
	private List<Amigo> Amigo;

	public ObjetoAmigos(List<Amigo> amigos) {
		this.Amigo = amigos;
	}

	public List<Amigo> getAmigos() {
		return Amigo;
	}

	public void setAmigos(List<Amigo> amigos) {
		this.Amigo = amigos;
	}

	@Override
	public String toString() {
		return "ObjetoAmigos [amigos=" + Amigo+ "]";
	}
}
