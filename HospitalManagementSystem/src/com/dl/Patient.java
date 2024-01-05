package com.dl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Patient {
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection, Scanner scanner) {
		this.connection= connection;
		this.scanner =scanner;
		
	}
	public void addPatient() {
		System.out.println("Enter Patient Name : ");
		String name = scanner.next();
		System.out.println("Enter Patient Age : ");
		int age = scanner.nextInt();
		System.out.println("Enter Patient Gender : ");
		String gender = scanner.next();
		try {
			String query ="insert into patients(name,age,gender)values(?,?,?)";
			PreparedStatement state = connection.prepareStatement(query);
			state.setString(1,name);
			state.setInt(2, age);
			state.setString(3, gender);
			int affectedRows = state.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Patient data added Successfully !!");
			}else {
				System.out.println("Failed to add patient data !!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public void viewPatients() {
		String query ="select * from patients";
		try {
			
			PreparedStatement state = connection.prepareStatement(query);
			ResultSet result = state.executeQuery();
			System.out.println("Patients:");
			System.out.println("+------------+-----------------+---------+--------------");
			System.out.println("| patinet id | Name            | Age     | Gender       |");
			System.out.println("+------------+-----------------+---------+--------------");
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int age = result.getInt("age");
				String gender = result.getString("gender");
				System.out.printf("|%-12s|%-17s|%-10s|%-14s|\n",id,name,age,gender);
				System.out.println("+------------+-----------------+---------+--------------");
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
	}
	
    public boolean getPatientById(int id) {
    	String query="select * from patients where id = ?";
		
		try {
			PreparedStatement state = connection.prepareStatement(query);
			state.setInt(1, id);
			ResultSet result = state.executeQuery();
			if(result.next()) {
				return true;
			}else {
		return false;
	}
			
		}catch(SQLException e){
			e.printStackTrace();
			
          return false;
		}
		
	 
 }
	

}
