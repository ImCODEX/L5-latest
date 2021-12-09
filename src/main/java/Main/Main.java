package Main;

import Controller.Controller;
import CustomExceptions.CustomExceptions;
import Repo.CourseRepository;
import Repo.StudentRepository;
import Repo.TeacherRepository;
import View.Console;

import javax.swing.plaf.nimbus.State;

import java.io.IOException;
import java.sql.*;


public class Main {


    public static void main(String[] args) throws SQLException, IOException, CustomExceptions, ClassNotFoundException {
        CourseRepository courseRepository = new CourseRepository();
        StudentRepository studentRepository = new StudentRepository();
        TeacherRepository teacherRepository = new TeacherRepository();

        Controller controller = new Controller(courseRepository, studentRepository, teacherRepository);
        //controller.startInput();
        Console console = new Console(controller);
        console.run();
        //controller.saveInput();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/labmap", "root", "961688");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from student");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("studentId"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


