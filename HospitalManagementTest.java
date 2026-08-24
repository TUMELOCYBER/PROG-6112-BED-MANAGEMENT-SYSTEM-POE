package manager;

import model.Patient;
import model.Inpatient;
import model.Patient.PatientCategory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class HospitalManagementTest {

    private PatientManager patientManager;
    private BedManager bedManager;

    @Before
    public void setUp() {
        patientManager = new PatientManager();
        bedManager = new BedManager();
    }

    @Test
    public void testRegisterPatient() {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        boolean result = patientManager.registerPatient(p);
        assertTrue(result);
        assertEquals(1, patientManager.getPatientCount());
    }

    @Test
    public void testSearchPatient() {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        
        Patient found = patientManager.findPatientById("P001");
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
    }

    @Test
    public void testUpdatePatientDetails() {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        
        boolean result = patientManager.updatePatient("P001", "Johnny", "Doe", 31, "Male", "Recovered", PatientCategory.OUTPATIENT);
        
        assertTrue(result);
        assertEquals("Johnny", patientManager.findPatientById("P001").getFirstName());
        assertEquals(31, patientManager.findPatientById("P001").getAge());
    }

    @Test
    public void testDeletePatient() {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        patientManager.registerPatient(p);
        
        boolean result = patientManager.deletePatient("P001");
        assertTrue(result);
        assertEquals(0, patientManager.getPatientCount());
    }

    @Test
    public void testPreventDuplicatePatientIds() {
        Patient p1 = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P001", "Jane", "Smith", 25, "Female", "Cold", PatientCategory.INPATIENT);
        
        boolean first = patientManager.registerPatient(p1);
        boolean second = patientManager.registerPatient(p2);
        
        assertTrue(first);
        assertFalse(second);
        assertEquals(1, patientManager.getPatientCount());
    }

    @Test
    public void testAllocateBed() {
        String bedId = bedManager.allocateBed("P001");
        assertNotNull(bedId);
        assertEquals("B01", bedId);
    }

    @Test
    public void testReleaseBed() {
        bedManager.allocateBed("P001");
        boolean released = bedManager.releaseBed("B01");
        assertTrue(released);
    }

    @Test
    public void testPreventAllocateOccupiedBed() {
        String bed1 = bedManager.allocateBed("P001");
        String bed2 = bedManager.allocateBed("P002");
        
        assertNotNull(bed1);
        assertNotNull(bed2);
        
        boolean sameBed = bed1.equalsIgnoreCase(bed2);
        assertFalse(sameBed);
        
        assertTrue(bedManager.isBedOccupied(bed1));
        assertTrue(bedManager.isBedOccupied(bed2));
    }

    @Test
    public void testPreventAllocateWhenAllBedsOccupied() {
        for (int i = 0; i < 20; i++) {
            String bedId = bedManager.allocateBed("P" + i);
            assertNotNull(bedId);
        }
        String extra = bedManager.allocateBed("P999");
        assertNull(extra);
    }

    @Test
    public void testSortPatientsBySurname() {
        patientManager.registerPatient(new Patient("P003", "Charlie", "Alpha", 25, "M", "X", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("P001", "John", "Bravo", 30, "M", "Y", PatientCategory.INPATIENT));
        patientManager.registerPatient(new Patient("P002", "Jane", "Charlie", 28, "F", "Z", PatientCategory.EMERGENCY));
        
        ArrayList<Patient> sorted = patientManager.sortBySurname();
        
        assertEquals("Alpha", sorted.get(0).getLastName());
        assertEquals("Bravo", sorted.get(1).getLastName());
        assertEquals("Charlie", sorted.get(2).getLastName());
    }

    @Test
    public void testSortPatientsByPatientId() {
        patientManager.registerPatient(new Patient("P003", "C", "C", 25, "M", "X", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("P001", "A", "A", 30, "M", "Y", PatientCategory.INPATIENT));
        patientManager.registerPatient(new Patient("P002", "B", "B", 28, "F", "Z", PatientCategory.EMERGENCY));
        
        ArrayList<Patient> sorted = patientManager.sortByPatientId();
        
        assertEquals("P001", sorted.get(0).getPatientId());
        assertEquals("P002", sorted.get(1).getPatientId());
        assertEquals("P003", sorted.get(2).getPatientId());
    }
}