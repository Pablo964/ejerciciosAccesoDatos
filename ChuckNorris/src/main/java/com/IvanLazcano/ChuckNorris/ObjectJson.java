package com.IvanLazcano.ChuckNorris;

import java.io.Serializable;
import java.util.List;

public class ObjectJson implements Serializable
{
	private static final long serialVersionUID = 1705815914900036131L;
	private String type;
	private List<Value> value;
	public ObjectJson(String type, List<Value> values) {
		super();
		this.type = type;
		this.value = values;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Value> getValue() {
		return value;
	}
	public void setValue(List<Value> value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "ObjectJson [type=" + type + ", value=" + value + "]";
	}

	
}
