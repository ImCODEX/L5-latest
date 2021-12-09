
package Repo;

import CustomExceptions.CustomExceptions;
import Model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CourseRepository extends SQLRepository<Course>{

    public CourseRepository() throws SQLException, ClassNotFoundException {
        super();
    }

    @Override
    public Course add(Course obj) throws SQLException {
        String SQLQuery = "insert into course values(" + obj.getId() + ", '" + obj.getName() +
                "', " + obj.getTeacher() + ", " + obj.getMaxEnrollment() + ", " + obj.getCredits() + ") ";
        statement.execute(SQLQuery);
        return obj;
    }

    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String SQLQuery = "select * from course";
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        String SQLQuery2 = "";
        ResultSet resultSet2;
        while(resultSet.next()){
            Course tempCourse = new Course();
            tempCourse.setId(resultSet.getInt("courseId"));
            tempCourse.setName(resultSet.getString("name"));
            tempCourse.setTeacher(resultSet.getInt("teacherId"));
            tempCourse.setMaxEnrollment(resultSet.getInt("maxEnrollment"));
            tempCourse.setCredits(resultSet.getInt("credits"));
            SQLQuery2 = "select * from students_courses where courseId = " + tempCourse.getId();
            resultSet2 = secondStatement.executeQuery(SQLQuery2);
            List<Integer> students = new ArrayList<>();
            while(resultSet2.next()){
                students.add(resultSet2.getInt("studentId"));
            }
            tempCourse.setStudentsEnrolled(students);
            courses.add(tempCourse);
        }
        return courses;
    }

    @Override
    public Course find(Integer id) throws CustomExceptions, SQLException {
        Course tempCourse = new Course();
        String SQLQuery = "select * from course where courseId = " + id;
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        resultSet.next();
        tempCourse.setId(resultSet.getInt("courseId"));
        tempCourse.setName(resultSet.getString("name"));
        tempCourse.setTeacher(resultSet.getInt("teacherId"));
        tempCourse.setMaxEnrollment(resultSet.getInt("maxEnrollment"));
        tempCourse.setCredits(resultSet.getInt("credits"));
        String SQLQuery2 = "select * from students_courses where courseId = " + tempCourse.getId();
        ResultSet resultSet2 = secondStatement.executeQuery(SQLQuery2);
        List<Integer> students = new ArrayList<>();
        while(resultSet2.next()){
            students.add(resultSet2.getInt("studentId"));
        }
        tempCourse.setStudentsEnrolled(students);
        return tempCourse;
    }

    @Override
    public Course update(Course oldObject, Course newObject) throws SQLException {
        if(!oldObject.getStudentsEnrolled().isEmpty()){
            String SQLQuery2 = "delete from students_courses where courseId = " + oldObject.getId();
            statement.execute(SQLQuery2);
        }

        String SQLQuery = "select * from course where courseId = " + oldObject.getId();
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        resultSet.next();

        if(!Objects.equals(oldObject.getId(), newObject.getId())){
            String SQLQuery2 = "update course set courseId = " + newObject.getId() + " where courseId = " + oldObject.getId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getName(), newObject.getName())){
            String SQLQuery2 = "update course set name = " + newObject.getName() + " where courseId = " + newObject.getId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getMaxEnrollment(), newObject.getMaxEnrollment())){
            String SQLQuery2 = "update course set maxEnrollment = " + newObject.getMaxEnrollment() + " where courseId = " + newObject.getId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getCredits(), newObject.getCredits())){
            String SQLQuery2 = "update course set credits = " + newObject.getCredits() + " where courseId = " + newObject.getId();
            statement.execute(SQLQuery2);
        }

        if(!newObject.getStudentsEnrolled().isEmpty()){
            for (Integer id:
                    newObject.getStudentsEnrolled()) {
                String SQLQuery2 = "insert into students_courses values (" + id + ", " + newObject.getId() + ")";
                statement.execute(SQLQuery2);
             }
        }

        return newObject;
    }

    @Override
    public void delete(Course obj) {

    }
}

