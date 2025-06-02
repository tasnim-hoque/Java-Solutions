import java.util.ArrayList;
import java.util.List;

// Base Class: Person
class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

// Doctor Class (inherits Person)
class Doctor extends Person {
    private String specialization;

    public Doctor(String name, int age, String specialization) {
        super(name, age);
        this.specialization = specialization;
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
    }
}

// Patient Class (inherits Person)
class Patient extends Person {
    private String disease;

    public Patient(String name, int age, String disease) {
        super(name, age);
        this.disease = disease;
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Disease: " + disease);
    }
}

// Hospital Management
class Hospital {
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void showDoctors() {
        System.out.println("\nList of Doctors:");
        for (Doctor d : doctors) {
            d.displayInfo();
        }
    }

    public void showPatients() {
        System.out.println("\nList of Patients:");
        for (Patient p : patients) {
            p.displayInfo();
        }
    }
}

// Main Method
public class HospitalManagementSystem {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();

        Doctor d1 = new Doctor("Dr. Arnob", 22, "Cardiologist");
        Doctor d2 = new Doctor("Dr. Mahua", 21, "Neurologist");

        Patient p1 = new Patient("Tinni", 30, "Flu");
        Patient p2 = new Patient("Tabib", 27, "Mental Disorder");

        hospital.addDoctor(d1);
        hospital.addDoctor(d2);

        hospital.addPatient(p1);
        hospital.addPatient(p2);

        hospital.showDoctors();
        hospital.showPatients();
    }
}