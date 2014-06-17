package com.xentime.entities;

public class ExampleEntity{
	Integer id;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return (this.id == null);
	}

	public String getSerial(){
		return String.format("%06d", this.id);
	}

}