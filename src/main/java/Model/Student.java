package Model;


import java.util.List;

/**
 * Student Class
 */

public class Student extends Person{
    private int studentId;
    private int totalCredits;
    private List<Integer> enrolledCourses;

    public Student() {
        super();
    }

    /**
     * Student Constructor
     * @param firstName: String
     * @param lastName: String
     * @param studentId_: int - unique identifier for Student
     * @param totalCredits_: sum of all Courses credits
     */

    public Student(String firstName, String lastName, int studentId_, int totalCredits_) {
        super(firstName, lastName);
        studentId = studentId_;
        totalCredits = totalCredits_;
    }

    public Student(String firstName, String lastName, int studentId_, int totalCredits_, List<Integer> enrolledCourses_) {
        super(firstName, lastName);
        studentId = studentId_;
        totalCredits = totalCredits_;
        enrolledCourses = enrolledCourses_;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Integer> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Integer> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }


    /**
     * Equals method for comparison
     * Used especially in update() method from InMemoryRepository.java
     * @param o: another Student Object
     * @return: boolean
     */
    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }
}
