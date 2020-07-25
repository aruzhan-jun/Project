package com.company;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {
    public static ListStudents listStudents;
    public static Connection connection;
    public static void main(String[] args) {
        connectToDB();
        try {
            ServerSocket server = new ServerSocket(1984);
            System.out.println("WAITING FOR CLIENT");
            Socket socket = server.accept();
            System.out.println("CLIENT CONNECTED");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            PackageData packageData;
            while((packageData = (PackageData) inputStream.readObject()) != null){
                if (packageData.getOperationType().equals("ADD STUDENT")){
                    addStudentToDB(packageData.getStudent());
                }else if (packageData.getOperationType().equals("LIST STUDENTS")){
                    packageData.setStudents(getAllStudents());
                    for (Student s : packageData.getStudents()){
                        listStudents.textArea.append(s.toString());
                    }
                }else if (packageData.getOperationType().equals("EXIT")){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void connectToDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/students?useUnicode=true&serverTimezone=UTC", "root", "");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void addStudentToDB(Student student){
        try{
            PreparedStatement statement = connection.prepareStatement("" +
                    "INSERT INTO students1 (name, surname, age) " +
                    "VALUES (?, ?, ?)");
            statement.setString(1, student.getName());
            statement.setString(2, student.getSurname());
            statement.setInt(3, student.getAge());
            statement.executeUpdate();
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Student> getAllStudents(){
        ArrayList<Student> studentList = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students1");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                studentList.add(new Student(id, name, surname, age));
            }
            statement.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return studentList;
    }
}
