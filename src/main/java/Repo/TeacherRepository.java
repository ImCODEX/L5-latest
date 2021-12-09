package Repo;

import CustomExceptions.CustomExceptions;
import Model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TeacherRepository extends SQLRepository<Teacher>{

    public TeacherRepository() throws SQLException {
        super();
    }

    @Override
    public Teacher add(Teacher obj) throws SQLException {
        String SQLQuery = "insert into teacher values(" + obj.getTeacherId() +
                ", '" + obj.getFirstName() + "', '" + obj.getLastName() + "')";
        statement.executeQuery(SQLQuery);
        return obj;
    }

    @Override
    public List<Teacher> getAll() throws SQLException {
        List<Teacher> teachers = new ArrayList<>();
        String SQLQuery = "select * from teacher";
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        String SQLQuery2 = "";
        ResultSet resultSet2;
        while (resultSet.next()){
            Teacher tempTeacher = new Teacher();
            tempTeacher.setTeacherId(resultSet.getInt("teacherId"));
            tempTeacher.setFirstName(resultSet.getString("firstName"));
            tempTeacher.setLastName(resultSet.getString("lastName"));
            SQLQuery2 = "select courseId from course where teacherId = " +tempTeacher.getTeacherId();
            resultSet2 = secondStatement.executeQuery(SQLQuery2);
            List<Integer> courses = new ArrayList<>();
            while(resultSet2.next()){
                courses.add(resultSet2.getInt("courseId"));
            }
            tempTeacher.setCourses(courses);
            teachers.add(tempTeacher);
        }
        return teachers;
    }

    @Override
    public Teacher find(Integer id) throws CustomExceptions, SQLException {
        Teacher tempTeacher = new Teacher();
        String SQLQuery = "select * from teacher where teacherId = " + id;
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        resultSet.next();
        tempTeacher.setTeacherId(resultSet.getInt("teacherId"));
        tempTeacher.setFirstName(resultSet.getString("firstName"));
        tempTeacher.setLastName(resultSet.getString("lastName"));
        String SQLQuery2 = "select courseId from course where teacherId = " +tempTeacher.getTeacherId();
        ResultSet resultSet2 = secondStatement.executeQuery(SQLQuery2);
        List<Integer> courses = new ArrayList<>();
        while(resultSet2.next()){
            courses.add(resultSet2.getInt("courseId"));
        }
        tempTeacher.setCourses(courses);
        return tempTeacher;
    }

    @Override
    public Teacher update(Teacher oldObject, Teacher newObject) throws SQLException {
        if(!oldObject.getCourses().isEmpty()){
            String SQLQuery2 = "update course set teacherId = -1 where teacherId = " + oldObject.getTeacherId();
            statement.execute(SQLQuery2);
        }

        String SQLQuery = "select * from teacher where teacherId = " + oldObject.getTeacherId();
        ResultSet resultSet = statement.executeQuery(SQLQuery);
        resultSet.next();

        if(!Objects.equals(oldObject.getTeacherId(), newObject.getTeacherId())){
            String SQLQuery2 = "update teacher set teacherId = " + newObject.getTeacherId() + " where teacherId = " + oldObject.getTeacherId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getFirstName(), newObject.getFirstName())){
            String SQLQuery2 = "update teacher set firstName = '" + newObject.getFirstName() + "' where teacherId = " + newObject.getTeacherId();
            statement.execute(SQLQuery2);
        }

        if(!Objects.equals(oldObject.getLastName(), newObject.getLastName())){
            String SQLQuery2 = "update teacher set lastName = '" + newObject.getLastName() + "' where teacherId = " + newObject.getTeacherId();
            statement.execute(SQLQuery2);
        }

        if(!newObject.getCourses().isEmpty()){
            for (Integer courseId:
                    newObject.getCourses()) {
                String SQLQuery2 = "update course set teacherId = " + newObject.getTeacherId() + " where courseId = " + courseId;
                statement.execute(SQLQuery2);
            }
        }

        return newObject;
    }

    @Override
    public void delete(Teacher obj) throws SQLException {
        String SQLQuery = "delete from teacher where teacherId = " + obj.getTeacherId();
        statement.execute(SQLQuery);
        String SQLQuery2 = "update course set teacherId = -1 where teacherId = " + obj.getTeacherId();
        statement.execute(SQLQuery2);
    }
}

