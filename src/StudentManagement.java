import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentManagement extends Student {
    List<Student> array = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    Pattern patternMenuOption = Pattern.compile("^[abcdefgh]");
    Pattern patternABCOption = Pattern.compile("^[abc]");
    Pattern patternString = Pattern.compile("^[aA-zZ]+$");
    Pattern patternDay = Pattern.compile("\\b([1-9]|[12][0-9]|3[01])\\b");
    Pattern patternNumber = Pattern.compile("\\d+");
    private int age;
    private char sex, confirm;

    public void menuShow() {
        System.out.println("""
                Welcome to java student management demo
                Choose option below
                a: Add Student
                b: Update Student
                c: Delete Student
                d: View all Students
                e: Search Student
                f: Sort Student
                g: Save Data
                h: Load Data (not work for now)
                exit: to go to previous page""");
        menuOption();
    }

    private void menuOption() {
        String menuOpt;
        menuOpt = sc.next();
        Matcher matcherMenuOpt = patternMenuOption.matcher(menuOpt);
        while (!matcherMenuOpt.find() && !menuOpt.equals("exit")) {
            System.out.print("Please select from a to h or exit only: ");
            menuOpt = sc.next();
            matcherMenuOpt = patternMenuOption.matcher(menuOpt);
        }
        switch (menuOpt) {
            case "a":
                addStudent(array);
                break;
            case "b":
                updatestudent(array);
                break;
            case "c":
                deleteStudent(array);
                break;
            case "d":
                viewStudent(array);
                break;
            case "e":
                searchStudent(array);
                break;
            case "f":
                sortStudent(array);
                break;
            case "g":
                saveFile(array);
                break;
            case "h":
                readFile(array);
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }

    private void addStudent(List<Student> array) {
        String firstName;
        String lastName;
        String address;
        String ID = null;
        StringMessage stringMessage = new StringMessage();
        NumberMessage numberMessage = new NumberMessage();


        do {
            System.out.print("Enter Student First Name: ");
            firstName = sc.next();
            Matcher matcherFirstName = patternString.matcher(firstName);
            stringMessage.methodTry(firstName, matcherFirstName);
        } while (stringMessage.isBool());

        do {
            System.out.print("Enter Student Last Name: ");
            lastName = sc.next();
            Matcher matcherLastName = patternString.matcher(lastName);
            stringMessage.methodTry(lastName, matcherLastName);
        } while (stringMessage.isBool());

        System.out.print("Enter Student age: ");
        age = sc.nextInt();

        do {
            System.out.print("Enter Student Address: ");
            address = sc.next();
            Matcher matcherAddress = patternString.matcher(address);
            stringMessage.methodTry(address, matcherAddress);
        } while (stringMessage.isBool());

        while (true) {
            do {
                System.out.print("Enter Student ID: ");
                ID = sc.next();
                Matcher matcherID = patternNumber.matcher(ID);
                numberMessage.methodTry(ID, matcherID);
            } while (stringMessage.isBool());
            if (checkID(array, ID)) {
                System.out.println("The ID you entered already in used please try another ID");
            } else {
                break;
            }
        }


        System.out.print("Enter Student Sex: ");
        sex = sc.next().charAt(0);
        while (sex != 'f' && sex != 'F' && sex != 'm' && sex != 'M') {
            System.out.print("Input only m or f: ");
            sex = sc.next().charAt(0);
        }

        System.out.println("\nSuccess add this student\n");
        System.out.println("Student first name: " + firstName);
        System.out.println("Student last name: " + lastName);
        System.out.println("Student age: " + age);
        System.out.println("Student address: " + address);
        System.out.println("Student ID: " + ID);
        System.out.println("Student sex: " + sex);
        System.out.println("\n");
        Student student = new Student(firstName, lastName, ID, age, sex, address);
        array.add(student);
        System.out.print("Do you want to add more or go back to main menu?'Y'es and 'N'o: ");
        confirm = sc.next().charAt(0);
        while (confirm != 'y' && confirm != 'Y' && confirm != 'n' && confirm != 'N') {
            System.out.print("Input only Y'es or 'N'o: ");
            confirm = sc.next().charAt(0);
        }
        if (confirm == 'y' || confirm == 'Y') {
            addStudent(array);
        }
        menuShow();
    }

    private void displayStudentInfo(List<Student> array) {
        if (array.isEmpty()) {
            System.out.println("There is no student in data.");
            System.out.print("Enter to continues... ");
            sc.nextLine();
            sc.nextLine();
            menuShow();
        } else {
            for (int i = 0; i < array.size(); i++) {
                Student student = array.get(i);
                System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
                System.out.println("ID: " + student.getID());
                System.out.println("Age: " + student.getAge());
                System.out.println("Sex: " + student.getSex());
                System.out.println("Address: " + student.getAddress());
                System.out.println("\n");
            }
        }
    }

    private void deleteStudent(List<Student> array) {
        displayStudentInfo(array);
        String ID;

        while (true) {
            System.out.print("Please enter the student ID to delete: ");
            ID = sc.next();
            if (!checkID(array, ID)) {
                System.out.println("The ID you entered is not exist in data.");
            } else {
                break;
            }
        }
        for (int i = 0; i < array.size(); i++) {
            if (ID.equals(array.get(i).getID())) {
                array.remove(i);
            }
        }
        System.out.println("Succeed remove");
        System.out.println("Do you want to remove another student? 'Y'es and 'N'o: ");
        confirm = sc.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            deleteStudent(array);
        }
        menuShow();
    }

    private void updatestudent(List<Student> array) {
        String firstName;
        String lastName;
        String address;
        String ID;
        StringMessage stringMessage = new StringMessage();
        displayStudentInfo(array);

        while (true) {
            System.out.println("Please enter the student ID to modify");
            ID = sc.next();
            if (!checkID(array, ID)) {
                System.out.println("The ID you entered is not exist in data.");
            } else if (ID == "exit") {
                menuShow();
            } else {
                break;
            }
        }
        do {
            System.out.print("Enter Student First Name: ");
            firstName = sc.next();
            Matcher matcherFirstName = patternString.matcher(firstName);
            stringMessage.methodTry(firstName, matcherFirstName);
        } while (stringMessage.isBool());

        do {
            System.out.print("Enter Student Last Name: ");
            lastName = sc.next();
            Matcher matcherLastName = patternString.matcher(lastName);
            stringMessage.methodTry(lastName, matcherLastName);
        } while (stringMessage.isBool());

        System.out.print("Enter Student age: ");
        age = sc.nextInt();

        do {
            System.out.print("Enter Student Address: ");
            address = sc.next();
            Matcher matcherAddress = patternString.matcher(address);
            stringMessage.methodTry(address, matcherAddress);
        } while (stringMessage.isBool());

        System.out.print("Enter Student Sex: ");
        sex = sc.next().charAt(0);
        while (sex != 'f' && sex != 'F' && sex != 'm' && sex != 'M') {
            System.out.print("Input only m or f: ");
            sex = sc.next().charAt(0);
        }
        Student student = new Student(firstName, lastName, ID, age, sex, address);
        for (int i = 0; i < array.size(); i++) {
            if (ID.equals(array.get(i).getID())) {
                array.set(i, student);
            }
        }
        System.out.println("Succeed remove");
        System.out.println("Do you want to update another student? 'Y'es and 'N'o: ");
        confirm = sc.next().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            updatestudent(array);
        }
        menuShow();
    }

    private void viewStudent(List<Student> array) {
        displayStudentInfo(array);
        System.out.print("Enter to continues... ");
        sc.nextLine();
        sc.nextLine();
        menuShow();
    }

    private boolean checkID(List<Student> array, String ID) {
        for (int i = 0; i < array.size(); i++) {
            if (ID.equals(array.get(i).getID())) {
                return true;
            }
        }
        return false;
    }

    private void searchStudent(List<Student> array) {
        String ID;
        String firstName;
        boolean bool = true;
        String searchOpt;
        System.out.println("Search Student:::");
        System.out.println("""
                a: Search By First Name
                b: Search By ID
                c: Go Back To Main Menu""");
        searchOpt = sc.next();
        Matcher matcherABCOpt=patternABCOption.matcher(searchOpt);
        while (!matcherABCOpt.find()) {
            System.out.print("Please select from a to c only: ");
            searchOpt = sc.next();
            matcherABCOpt = patternMenuOption.matcher(searchOpt);
        }

        switch (searchOpt) {
            case "a":
                while (bool) {
                    System.out.println("Please enter the student first name to search");
                    firstName = sc.next();
                    for (int i = 0; i < array.size(); i++) {
                        if (!firstName.equals(array.get(i).getFirstName())) {
                            System.out.println("The ID you entered is not exist in data.");

                        } else if (firstName.equals("exit")) {
                            searchStudent(array);
                        } else {
                            break;
                        }
                    }

                    for (int i = 0; i < array.size(); i++) {
                        if (firstName.equals(array.get(i).getID())) {
                            displayStudentInfo(array);
                        }
                    }
                    if (confirm == 'y' || confirm == 'Y') {
                        searchStudent(array);
                    }
                    menuShow();
                }
                break;
            case "b":
                while (true) {
                    System.out.println("Please enter the student ID to search");
                    ID = sc.next();
                    if (!checkID(array, ID)) {
                        System.out.println("The ID you entered is not exist in data.");
                    } else if (ID.equals("exit")) {
                        searchStudent(array);
                    } else {
                        break;
                    }
                    for (int i = 0; i < array.size(); i++) {
                        if (ID.equals(array.get(i).getID())) {
                            displayStudentInfo(array);
                        }
                    }
                    System.out.println("Do you want to search another student? 'Y'es and 'N'o: ");
                    confirm = sc.next().charAt(0);
                    if (confirm == 'y' || confirm == 'Y') {
                        searchStudent(array);
                    }
                    menuShow();
                }
                break;
            case "c":
                menuShow();
        }
    }

    public void sortStudent (List<Student> array) {
        String sortOpt, sortOrder;
        Student student=new Student();
        System.out.println("Sort Student:::");
        System.out.println("""
                a: Sort by ID
                b: Sort by First Name
                c: Go Back To Main Menu
                """);
        sortOpt=sc.next();
        Matcher matcherABCOpt=patternABCOption.matcher(sortOpt);
        while (!matcherABCOpt.find()) {
            System.out.print("Please select from a to c only: ");
            sortOpt = sc.next();
            matcherABCOpt = patternMenuOption.matcher(sortOpt);
        }
        switch (sortOpt){
            case "a":
                System.out.println("""
                a: Ascending order (lowest to highest)
                b: Descending order (highest to lowest)
                c: Go Back To Main Menu
                """);
                sortOrder=sc.next();
                matcherABCOpt=patternABCOption.matcher(sortOrder);
                while (!matcherABCOpt.find()) {
                    System.out.print("Please select from a to c only: ");
                    sortOrder = sc.next();
                    matcherABCOpt = patternMenuOption.matcher(sortOrder);
                }
                switch (sortOrder){
                    case "a":
                        Collections.sort(array, Comparator.comparing(Student::getID));
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent(array);
                        break;
                    case "b":
                        Collections.sort(array, Comparator.comparing(Student::getID));
                        Collections.reverse(array);
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent(array);
                        break;
                    case "c":
                        menuShow();
                        break;
                }
                break;
            case "b":
                System.out.println("""
                a: Ascending order (A-Z)
                b: Descending order (Z-A)
                c: Go Back To Main Menu
                """);
                sortOrder=sc.next();
                matcherABCOpt=patternABCOption.matcher(sortOrder);
                while (!matcherABCOpt.find()) {
                    System.out.print("Please select from a to c only: ");
                    sortOrder = sc.next();
                    matcherABCOpt = patternMenuOption.matcher(sortOrder);
                }
                switch (sortOrder){
                    case "a":
                        Collections.sort(array, Comparator.comparing(Student::getFirstName));
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent(array);
                        break;
                    case "b":
                        Collections.sort(array, Comparator.comparing(Student::getFirstName));
                        Collections.reverse(array);
                        array.forEach(System.out::println);

                        System.out.print("Enter to continues... ");
                        sc.nextLine();
                        sc.nextLine();
                        sortStudent(array);
                        break;
                    case "c":
                        menuShow();
                        break;
                }
                break;
            case "c":
                menuShow();
                break;
        }
    }
    public void saveFile(List<Student>array){

        try {
            FileWriter writer = new FileWriter("output.txt");
            for(Student str: array) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("File Save success");
        System.out.print("Enter to continues... ");
        sc.nextLine();
        sc.nextLine();
        menuShow();
    }
    public void readFile(List<Student>array){
        String line=sc.next();
        while (sc.hasNext()){
            String[] spit=line.split(",");
            array.add(new Student());
            menuShow();
        }

    }
}
