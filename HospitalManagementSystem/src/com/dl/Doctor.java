package com.dl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
	private Connection connection;
	
	
	public Doctor(Connection connection) {
		this.connection= connection;
		
	}
	
	public void viewDoctors() {
		String query ="select * from doctors";
		try {
			
			PreparedStatement state = connection.prepareStatement(query);
			ResultSet result = state.executeQuery();
			System.out.println("Doctors:");
			System.out.println("+------------+-----------------+------------------------");
			System.out.println("| Doctor id  | Name            | Specialization         |");
			System.out.println("+------------+-----------------+------------------------");
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				String specialization = result.getString("specialization");
				System.out.printf("|%-12s|%-18s|%-24s|\n",id,name,specialization);
				System.out.println("+------------+-----------------+------------------------");
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		
	}
	
    public boolean getDoctorById(int id) {
    	String query="select * from doctors where id = ?";
		
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
