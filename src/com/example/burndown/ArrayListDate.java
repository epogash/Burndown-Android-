package com.example.burndown;

public class ArrayListDate {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	private String date;
	public ArrayListDate(){
		
	}
	public ArrayListDate(String name, String date){
		this.name = name;
		this.date = date;
	}
}