import java.util.Comparator;

public class Student{
    private String firstName;
    private String lastName;
    private String ID;
    private int age;
    private String address;
    private char sex;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Student(){

    }

    public Student(String firstName, String lastName, String ID, int age, char sex, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.address = address;
        this.age=age;
        this.sex=sex;
    }

    @Override
    public String toString() {
        return "ID: " + ID  +
                ", firstName: " + firstName  +
                ", lastName: " + lastName  +
                ", age: " + age +
                ", address: " + address +
                ", sex: " + sex;
    }
}
