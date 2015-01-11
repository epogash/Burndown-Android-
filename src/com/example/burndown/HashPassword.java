package com.example.burndown;

public class HashPassword {
	public String hash(String str){
		//The key is pi
		double pi = 3.14;
		String password  = "";
		char[] temp = str.toCharArray();
		for(int i = 0; i < temp.length; i++){
			password = password + (int)(temp[i] * pi);
		}
		return password;
	}
	
}
