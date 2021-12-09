package Controller;

import CustomExceptions.CustomExceptions;
import Model.Course;
import Model.Student;
import Model.Teacher;
import Repo.CourseRepository;
import Repo.StudentRepository;
import Repo.TeacherRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Controller Class
 */

public class Controller {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;


    /**
     * Controller makes use of 3 FileRepositories
     *
     * @param courseFileRepository:
     * @param studentFileRepository:
     * @param teacherFileRepository:
     */

    public Controller(CourseRepository courseFileRepository, StudentRepository studentFileRepository, TeacherRepository teacherFileRepository) {
        this.courseRepository = courseFileRepository;
        this.teacherRepository = teacherFileRepository;
        this.studentRepository = studentFileRepository;

    }


    /**
     * Getter for all courses
     *
     * @return: List<Course>
     */

    public List<Course> getCourses() throws SQLException {
        return courseRepository.getAll();
    }


    /**
     * Getter for all students
     *
     * @return: List<Student>
     */

    public List<Student> getStudents() throws SQLException {
        return studentRepository.getAll();
    }


    /**
     * Getter for all teachers
     *
     * @return: List<Teacher>
     */

    public List<Teacher> getTeacher() throws SQLException {
        return teacherRepository.getAll();
    }

    public Course addCourse(int id, String name, int maxEnrollment, int credits) throws SQLException {
        return courseRepository.add(new Course(id, name, maxEnrollment, credits));
    }


    /**
     * Student add Method
     *
     * @param firstName:     String
     * @param lastName:      String
     * @param studentId_:    Int
     * @param totalCredits_: Int
     * @param coursesId:     List<Integer>
     * @return Student
     */

    public Student addStudent(String firstName, String lastName, int studentId_, int totalCredits_, List<Integer> coursesId) throws SQLException {
        /*List<Course> tempCourses = new ArrayList<>();
        for (Integer id :
                coursesId) {
            for (Course c :
                    courseRepository.getAll()) {
                if (c.getId() == id)
                    tempCourses.add(c);
            }
        }*/
        return studentRepository.add(new Student(firstName, lastName, studentId_, totalCredits_, coursesId));
    }


    /**
     * Teacher add Method
     *
     * @param firstName:  String
     * @param lastName:   String
     * @param teacherId_: Int
     * @param courses_:   List<Integer>
     * @return Teacher
     */

    public Teacher addTeacher(String firstName, String lastName, int teacherId_, List<Integer> courses_) throws SQLException {
        /*List<Course> tempCourses = new ArrayList<>();
        for (Integer id :
                courses_) {
            for (Course c :
                    courseRepository.getAll()) {
                if (c.getId() == id)
                    tempCourses.add(c);
            }
        }*/
        return teacherRepository.add(new Teacher(firstName, lastName, teacherId_, courses_));
    }


    /**
     * Find by id Method for Course
     *
     * @param id: Int
     * @return Course
     * @throws CustomExceptions in case of not found
     */

    public Course findCourseById(int id) throws CustomExceptions, SQLException {
        return courseRepository.find(id);
    }


    /**
     * Find Student by Id
     *
     * @param id: Int
     * @return Student
     * @throws CustomExceptions in case of not found
     */

    public Student findStudentById(int id) throws CustomExceptions, SQLException {
        return studentRepository.find(id);
    }


    /**
     * Find Teacher by Id
     *
     * @param id: Int
     * @return Teacher
     * @throws CustomExceptions in case of not found
     */

    public Teacher findTeacherById(int id) throws CustomExceptions, SQLException {
        return teacherRepository.find(id);
    }


    /**
     * Delete Course Method
     *
     * @param course: Course
     */

    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }


    /**
     * Delete Student Method
     *
     * @param student: Student
     */

    public void deleteStudent(Student student) throws SQLException {
        studentRepository.delete(student);
    }


    /**
     * Delete Teacher Method
     *
     * @param teacher: Teacher
     */

    public void deleteTeacher(Teacher teacher) throws SQLException {
        teacherRepository.delete(teacher);
    }


    /**
     * Update Course Method
     *
     * @param oldCourseId:   Id of the Course that needs to be updated
     * @param id:            New Id
     * @param name:          String
     * @param maxEnrollment: Int
     * @param credits:       Int
     * @throws CustomExceptions in case of Course not found
     */

    public void updateCourse(int oldCourseId, int id, String name, int maxEnrollment, int credits) throws CustomExceptions, SQLException {
        Course tempCourse = findCourseById(oldCourseId);
        if (Objects.equals(name, ""))
            name = tempCourse.getName();

        courseRepository.update(tempCourse, new Course(id, name, maxEnrollment, credits));
    }


    /**
     * Update Student Method
     *
     * @param oldStudentId:  Id of Student that needs to be updated
     * @param firstName:     String
     * @param lastName:      String
     * @param studentId_:    Int
     * @param totalCredits_: Int
     * @param coursesId:     List<Integer>
     * @throws CustomExceptions in case of Student not found
     */

    public void updateStudent(long oldStudentId, String firstName, String lastName, int studentId_, int totalCredits_, List<Integer> coursesId) throws CustomExceptions, SQLException {
        Student tempStudent = findStudentById((int) oldStudentId);
        if (Objects.equals(firstName, ""))
            firstName = tempStudent.getFirstName();
        if (Objects.equals(lastName, ""))
            lastName = tempStudent.getLastName();

        /*List<Course> tempCourses = new ArrayList<>();
        for (Integer id :
                coursesId) {
            for (Course c :
                    courseRepository.getAll()) {
                if (c.getId() == id)
                    tempCourses.add(c);
            }
        }*/

        studentRepository.update(tempStudent, new Student(firstName, lastName, studentId_, totalCredits_, coursesId));
    }


    /**
     * Update Teacher Method
     *
     * @param oldId:      Id of Teacher that needs to be updated
     * @param firstName:  String
     * @param lastName:   String
     * @param teacherId_: Int
     * @param courses_:   List<Integer>
     * @throws CustomExceptions in case of Teacher not found
     */

    public void updateTeacher(int oldId, String firstName, String lastName, int teacherId_, List<Integer> courses_) throws CustomExceptions, SQLException {
        Teacher tempTeacher = findTeacherById(oldId);
        if (Objects.equals(firstName, ""))
            firstName = tempTeacher.getFirstName();
        if (Objects.equals(lastName, ""))
            lastName = tempTeacher.getLastName();

        /*List<Course> tempCourses = new ArrayList<>();
        for (Integer id :
                courses_) {
            for (Course c :
                    courseRepository.getAll()) {
                if (c.getId() == id)
                    tempCourses.add(c);
            }*/
            teacherRepository.update(tempTeacher, new Teacher(firstName, lastName, teacherId_, courses_));


    }


    /**
     * Sort students descending by
     * ammount of enrolled courses
     *
     * @return: List<Student>
     */

    public List<Student> sortStudentsByEnrolledCourses() throws SQLException {
        return studentRepository.getAll().stream().sorted((x, y) -> (y.getEnrolledCourses().size() - x.getEnrolledCourses().size())).collect(Collectors.toList());
    }


    /**
     * Sort courses descending by
     * amount of credits
     *
     * @return: List<Course>
     */

    public List<Course> sortCourseByCredits() throws SQLException {
        return courseRepository.getAll().stream().sorted((x, y) -> (y.getCredits() - x.getCredits())).collect(Collectors.toList());
    }


    /**
     * Filter students by
     * max amount of enrolled Courses
     *
     * @param coursesCount: user given integer for filter
     * @return: List<Student>
     */

    public List<Student> filterStudentsByLessThenXCourses(Integer coursesCount) throws SQLException {
        return studentRepository.getAll().stream().filter(x -> x.getEnrolledCourses().size() <= coursesCount).collect(Collectors.toList());
    }


    /**
     * Filter courses by
     * max amount of enrollment
     *
     * @param enrollmentCount: user given integer for filter
     * @return: List<Course>
     */

    public List<Course> filterCourseByMaxEnrollment(Integer enrollmentCount) throws SQLException {
        return courseRepository.getAll().stream().filter(x -> x.getMaxEnrollment() <= enrollmentCount).collect(Collectors.toList());
    }

}

