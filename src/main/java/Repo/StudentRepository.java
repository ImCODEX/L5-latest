package Repo;

import CustomExceptions.CustomExceptions;
import Model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StudentRepository extends SQLRepository<Student>{

    public StudentRepository() throws SQLException {
        super();
    }


    @Override
    public Student add(Student obj) throws SQLException {
        String SQLQuery = "insert into student values (" + obj.getStudentId() +
                ", '" + obj.getFirstName() + "', '" + obj.getLastName() + "', " + obj.getTotalCredits() + ")";
        String SQLQuery2 = "";
        statement.execute(SQLQuery);
        for (Integer id:
             obj.getEnrolledCourses()) {
             SQLQuery2 = "insert into students_courses values (" + obj.getStudentId() + ", " + id + ")";
             statement.execute(SQLQuery2);
        }
        return obj;
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String SQLQuery = "select * from student";
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        String SQLQuery2 = "";
        ResultSet resultSet2;
        while(resultSet.next()){
            Student tempStudent = new Student();
            tempStudent.setStudentId(resultSet.getInt("studentId"));
            tempStudent.setFirstName(resultSet.getString("firstName"));
            tempStudent.setLastName(resultSet.getString("lastName"));
            tempStudent.setTotalCredits(resultSet.getInt("totalCredits"));
            SQLQuery2 = "select courseId from students_courses where studentId = " + tempStudent.getStudentId();
            resultSet2 = secondStatement.executeQuery(SQLQuery2);
            List<Integer> courses = new ArrayList<>();
            while(resultSet2.next()){
                courses.add(resultSet2.getInt("courseId"));
            }
            tempStudent.setEnrolledCourses(courses);
            students.add(tempStudent);
        }
        return students;
    }

    @Override
    public Student find(Integer id) throws CustomExceptions, SQLException {
        Student tempStudent = new Student();
        String SQLQuery = "select * from student where studentId=" + id;
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        resultSet.next();
        tempStudent.setStudentId(resultSet.getInt("studentID"));
        tempStudent.setFirstName(resultSet.getString("firstName"));
        tempStudent.setLastName(resultSet.getString("studentID"));
        tempStudent.setTotalCredits(resultSet.getInt("totalCredits"));
        String SQLQuery2 = "select courseId from students_courses where studentId = " + tempStudent.getStudentId();
        ResultSet resultSet2 = secondStatement.executeQuery(SQLQuery2);
        List<Integer> courses = new ArrayList<>();
        while(resultSet2.next()){
            courses.add(resultSet2.getInt("courseId"));
        }
        tempStudent.setEnrolledCourses(courses);
        return tempStudent;
    }

    @Override
    public Student update(Student oldObject, Student newObject) throws SQLException {
        if(!oldObject.getEnrolledCourses().isEmpty()) {
            String SQLQuery2 = "delete from students_courses where studentId=" + oldObject.getStudentId();
            statement.execute(SQLQuery2);
        }

        String SQLQuery = "select * from student where studentId=" + oldObject.getStudentId();
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        resultSet.next();

        if(!Objects.equals(oldObject.getStudentId(), newObject.getStudentId())){
            String SQLQuery2 = "update student set studentId=" + newObject.getStudentId() + " where studentId=" + oldObject.getStudentId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getFirstName(), newObject.getFirstName())) {
            String SQLQuery2 = "update student set firstName='" + newObject.getFirstName() + "' where studentId=" + newObject.getStudentId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getLastName(), newObject.getLastName())){
            String SQLQuery2 = "update student set lastName='" + newObject.getLastName() + "' where studentId=" + newObject.getStudentId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getTotalCredits(), newObject.getTotalCredits())){
            String SQLQuery2 = "update student set totalCredits=" + newObject.getTotalCredits() + " where studentId=" + newObject.getStudentId();
            statement.execute(SQLQuery2);
        }

        if(!newObject.getEnrolledCourses().isEmpty()){
            for (Integer id:
                    newObject.getEnrolledCourses()) {
                String SQLQuery2 = "insert into students_courses values (" + newObject.getStudentId() + ", " + id + ")";
                statement.execute(SQLQuery2);
            }
        }

        return newObject;
    }

    @Override
    public void delete(Student obj) throws SQLException {
        String SQLQuery = "delete from student where studentId=" + obj.getStudentId();
        statement.execute(SQLQuery);
        String SQLQuery2 = "delete from students_courses where studentId=" + obj.getStudentId();
        statement.execute(SQLQuery2);
    }

}

