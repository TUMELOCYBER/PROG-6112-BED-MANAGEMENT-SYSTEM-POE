package manager;

import model.Patient;
import model.Patient.PatientCategory;
import java.util.ArrayList;

public class PatientManager {
    private ArrayList<Patient> patients;

    public PatientManager() {
        patients = new ArrayList<Patient>();
    }

    public boolean registerPatient(Patient patient) {
        if (patient == null) {
            return false;
        }
        if (findPatientById(patient.getPatientId()) != null) {
            return false;
        }
        patients.add(patient);
        return true;
    }

    public Patient findPatientById(String patientId) {
        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String patientId, String firstName, String lastName,
                                  int age, String gender, String medicalCondition, 
                                  PatientCategory category) {
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            return false;
        }
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        patient.setCategory(category);
        return true;
    }

    public boolean deletePatient(String patientId) {
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            return false;
        }
        patients.remove(patient);
        return true;
    }

    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> copy = new ArrayList<Patient>();
        for (int i = 0; i < patients.size(); i++) {
            copy.add(patients.get(i));
        }
        return copy;
    }

    public int getPatientCount() {
        return patients.size();
    }

    public ArrayList<Patient> sortBySurname() {
        ArrayList<Patient> sorted = getAllPatients();
        for (int i = 0; i < sorted.size(); i++) {
            for (int j = 0; j < sorted.size() - 1; j++) {
                Patient p1 = sorted.get(j);
                Patient p2 = sorted.get(j + 1);
                String name1 = p1.getLastName();
                String name2 = p2.getLastName();
                if (name1.compareToIgnoreCase(name2) > 0) {
                    sorted.set(j, p2);
                    sorted.set(j + 1, p1);
                }
            }
        }
        return sorted;
    }

    public ArrayList<Patient> sortByPatientId() {
        ArrayList<Patient> sorted = getAllPatients();
        for (int i = 0; i < sorted.size(); i++) {
            for (int j = 0; j < sorted.size() - 1; j++) {
                Patient p1 = sorted.get(j);
                Patient p2 = sorted.get(j + 1);
                String id1 = p1.getPatientId();
                String id2 = p2.getPatientId();
                if (id1.compareToIgnoreCase(id2) > 0) {
                    sorted.set(j, p2);
                    sorted.set(j + 1, p1);
                }
            }
        }
        return sorted;
    }
}