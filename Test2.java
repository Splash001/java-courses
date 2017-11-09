//**
*Create classes, which describe employees with hourly wage and fixed payment. Give your suggestions
*about relations between classes. Implement method for calculating the average monthly salary. For
*employees with hourly wage use next formula: “average monthly salary= 20.8*8* hourly rate”, for employees
*with fixed payment – “average monthly salary= fixed monthly payment”. Write well commented code for
*solving next problems
*a) Sort the collection of employees in descending order by the average monthly salary. In the case of
*equal salary – by the name. Write ID, name and monthly salary for all employees from collection.
*b) Write information about first five employees from collection (problem a).
*c) Write ID of three last employees from collection (problem b).
*d) Write code for reading and writing collection of these objects from (into) file.
*e) Write code for handling the incorrect format of incoming file. 
*/


import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        List<Employee> employeeList = new LinkedList<>();

        employeeList.add(new EmployeeFixedPayment("Sue" ,2000));
        employeeList.add(new EmployeeHourlyWage("Terry",20));
        employeeList.add(new EmployeeHourlyWage("Frank", 5));
        employeeList.add(new EmployeeFixedPayment("Sofie", 2100));
        employeeList.add(new EmployeeFixedPayment("Andrew", 2000));
        employeeList.add(new EmployeeFixedPayment("Jack", 3000));
        employeeList.add(new EmployeeHourlyWage("Hue", 30));
        employeeList.add(new EmployeeHourlyWage("Larry", 30));
        
        Collections.sort(employeeList, new EmployeeComparator());

        System.out.println("problem b:");
        for (int i = 0; i < 5; i++) {
            System.out.println(employeeList.get(i));
        }

        System.out.println("\nproblem c:");

        for (int i = employeeList.size() - 1; i > employeeList.size() - 1 - 3; i--) {
            System.out.println(employeeList.get(i).getId());
        }

        // write collection to file
        writeEmployees(employeeList);

        // read collection from file
        System.out.println("\nreading files");

        List<Employee> list = readEmployees();
        for (Employee employee : list) {
            System.out.println(employee);
        }

    }
    // writing collection of employees to file
    public static void writeEmployees (List<Employee> employeeList) {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file.bin"))) {

            oos.writeObject(employeeList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // reading collection of employees from file
    public static List<Employee> readEmployees () {
        List<Employee> result = null;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("file.bin"))) {

            result = (List<Employee>) ois.readObject();
    // handling the incorrect format of incoming file with catch clause
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

abstract class Employee implements Serializable{
    static int idCounter;
    int id = 0;

    String name;

    public Employee() {
        idCounter++;
        id = idCounter;
    }
    public abstract double getMonthlySalary();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + ": " + getMonthlySalary();
    }
}

class EmployeeFixedPayment extends Employee{
    int fixedPayment;

    public EmployeeFixedPayment(String name, int fixedPayment) {
        this.name = name;
        this.fixedPayment = fixedPayment;
    }

    public double getMonthlySalary() {
        return fixedPayment;
    }
}

class EmployeeHourlyWage extends Employee{

    int hourlyRate;

    public EmployeeHourlyWage(String name, int hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    public double getMonthlySalary() {
        return 20.8 * 8 * hourlyRate;
    }
}

// sorts collection of employees in descending order by the average monthly salary
// in the case of equal salary - by the name
class EmployeeComparator implements Comparator<Employee> {

    public int compare(Employee e1, Employee e2) {
        if (e1.getMonthlySalary() > e2.getMonthlySalary()) {
            return -1;
        }
        else if (e1.getMonthlySalary() < e2.getMonthlySalary()) {
            return 1;
        }
        else
            return e1.getName().compareTo(e2.getName());
    }
}

0981488228