package com.IvanLazcano.ChuckNorris;

import java.io.Serializable;
import java.util.List;

public class Value implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9129598999114711741L;
	private int id;
	private String joke;
	private List<String> categories;
	public Value(int id, String joke, List<String> categories) 
	{
		this.id = id;
		this.joke = joke;
		this.categories = categories;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJoke() {
		return joke;
	}
	public void setJoke(String joke) {
		this.joke = joke;
	}
	
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	@Override
	public String toString() {
		return "Values [id=" + id + ", joke=" + joke + "categories"+categories+"]";
	}
	
	
}
