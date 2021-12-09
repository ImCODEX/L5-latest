package Model;

/**
 * Abstract class for Teacher, Student
 */
public abstract class Person {
    private String firstName;
    private String lastName;

    public Person() {
    }

    /**
     * Teacher Constructor
     * @param firstName: String
     * @param lastName: String
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
