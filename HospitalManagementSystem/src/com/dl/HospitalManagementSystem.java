package com.dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username ="root";
	private static final String password="P@vitra12";
	
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			Patient patient =new Patient(connection,scanner);
			Doctor doctor = new Doctor(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. view Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your Choice : ");
				int choice = scanner.nextInt();
				switch(choice) {
				case 1:
					//Add patient
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					//view patient
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					//view Doctors
					doctor.viewDoctors();
					break;
				case 4:
					//Book appointments
					bookAppointment(patient, doctor, connection, scanner);
					 System.out.println();
					 break;
				case 5:
					System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
					return;
				default:
					System.out.println("Enter Valid  Choice !!");
					break;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
	}
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner){
		System.out.println("Enter Patient Id : ");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor Id : ");
		int doctorId = scanner.nextInt();
		System.out.println("Enter Appointmnet Date (YYYY-MM-DD): ");
		String appointmentDate = scanner.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvailability(doctorId, appointmentDate,connection)){
				String appointmentQuery="insert into appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";
				try {
					PreparedStatement state = connection.prepareStatement(appointmentQuery);
					state.setInt(1, patientId);
					state.setInt(2, doctorId);
					state.setString(3,appointmentDate);
					 int rowsAffected = state.executeUpdate();
					 if(rowsAffected>0) {
						 System.out.println("Appointment Booked");
					 }else {
						 System.out.println("Failed to book appointment !! ");
					 }
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}else {
				System.out.println("Doctor Not Avaiable on this date !!");
			}
			
		}else {
			System.out.println("Either Patient or Doctor Doesn't Exist");
			
		}
	}
	public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection) {
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    
	}
}
