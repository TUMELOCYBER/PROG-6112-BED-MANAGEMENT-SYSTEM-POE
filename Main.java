package main;

import manager.PatientManager;
import manager.BedManager;
import model.Patient;
import model.Inpatient;
import model.Patient.PatientCategory;  
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
     Scanner input = new Scanner(System.in);
     PatientManager patientManager = new PatientManager();
     BedManager bedManager = new BedManager();
        
     int choice = 0;
        
        while (choice != 7) {
            System.out.println("========================================");
            System.out.println("   HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Bed Management");
            System.out.println("6. Reports");
            System.out.println("7. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            choice = input.nextInt();
            input.nextLine();
            
            // REGISTER PATIENT
            if (choice == 1) {
             System.out.println("\n--- REGISTER NEW PATIENT ---");
            System.out.println("Select Category:");
            System.out.println("1. Inpatient");
            System.out.println("2. Outpatient");
            System.out.println("3. Emergency");
            System.out.print("Enter choice: ");
             int catChoice = input.nextInt();
             input.nextLine();
                
                System.out.print("Enter Patient ID: ");
                String id = input.nextLine();
                
                System.out.print("Enter First Name: ");
                String firstName = input.nextLine();
                
                System.out.print("Enter Last Name: ");
                String lastName = input.nextLine();
                
                System.out.print("Enter Age: ");
                int age = input.nextInt();
                input.nextLine();
                
                System.out.print("Enter Gender: ");
                String gender = input.nextLine();
                
                System.out.print("Enter Medical Condition: ");
                String condition = input.nextLine();
                
                if (catChoice == 1) {
                    System.out.print("Enter Ward Number: ");
                    String ward = input.nextLine();
                    String bed = "Not Allocated";
                    
                    Inpatient in = new Inpatient(id, firstName, lastName, age, gender, condition, ward, bed);
                    boolean success = patientManager.registerPatient(in);
                    if (success == true) {
                        System.out.println("Inpatient registered successfully.");
                    } else {
                        System.out.println("Error: Patient ID already exists.");
                    }
                }
                else if (catChoice == 2) {
                    Patient p = new Patient(id, firstName, lastName, age, gender, condition, PatientCategory.OUTPATIENT);
                    boolean success = patientManager.registerPatient(p);
                    if (success == true) {
                        System.out.println("Outpatient registered successfully.");
                    } else {
                        System.out.println("Error: Patient ID already exists.");
                    }
                }
                   else if (catChoice == 3) {
                    Patient p = new Patient(id, firstName, lastName, age, gender, condition, PatientCategory.EMERGENCY);
                    boolean success = patientManager.registerPatient(p);
                    if (success == true) {
                        System.out.println("Emergency patient registered successfully.");
                    } else {
                        System.out.println("Error: Patient ID already exists.");
                    }
                }
                else {
                    System.out.println("Invalid category choice.");
                }
            }
            
            // SEARCH PATIENT
            else if (choice == 2) {
                System.out.print("\nEnter Patient ID to search: ");
                String searchId = input.nextLine();
                
                Patient found = patientManager.findPatientById(searchId);
                if (found != null) {
                    System.out.println("Patient Found:");
                    System.out.println(found.displayDetails());
                } else {
                    System.out.println("Patient not found.");
                }
            }
            
            // UPDATE PATIENT
            else if (choice == 3) {
                System.out.print("\nEnter Patient ID to update: ");
                String updateId = input.nextLine();
                
                Patient check = patientManager.findPatientById(updateId);
                if (check == null) {
                    System.out.println("Patient not found.");
                } else {
                    System.out.print("Enter First Name: ");
                    String newFirst = input.nextLine();
                    
                    System.out.print("Enter Last Name: ");
                    String newLast = input.nextLine();
                    
                    System.out.print("Enter Age: ");
                    int newAge = input.nextInt();
                    input.nextLine();
                    
                    System.out.print("Enter Gender: ");
                    String newGender = input.nextLine();
                    
                    System.out.print("Enter Medical Condition: ");
                    String newCondition = input.nextLine();
                    
                    PatientCategory currentCat = check.getCategory();
                    boolean updated = patientManager.updatePatient(updateId, newFirst, newLast, newAge, newGender, newCondition, currentCat);
                    
                    if (updated == true) {
                        System.out.println("Patient updated successfully.");
                    } else {
                        System.out.println("Update failed.");
                    }
                }
            }
            
            // DELETE PATIENT
                else if (choice == 4) {
                System.out.print("\nEnter Patient ID to delete: ");
                String deleteId = input.nextLine();
                
                Patient toDelete = patientManager.findPatientById(deleteId);
                if (toDelete == null) {
                    System.out.println("Patient not found.");
                } else {
                    if (toDelete instanceof Inpatient) {
                        Inpatient in = (Inpatient) toDelete;
                        if (in.getBedNumber().equals("Not Allocated") == false) {
                            bedManager.releaseBed(in.getBedNumber());
                        }
                    }
                    
                    boolean deleted = patientManager.deletePatient(deleteId);
                    if (deleted == true) {
                        System.out.println("Patient deleted successfully.");
                    }
                }
            }
            
            // BED MANAGEMENT
                else if (choice == 5) {
                int bedChoice = 0;
                while (bedChoice != 5) {
                    System.out.println("\n--- BED MANAGEMENT ---");
                    System.out.println("1. Allocate Bed to Inpatient");
                    System.out.println("2. Release Bed");
                    System.out.println("3. Display Ward Layout");
                    System.out.println("4. Display Available and Occupied Beds");
                    System.out.println("5. Back to Main Menu");
                    System.out.print("Enter choice: ");
                    
                    bedChoice = input.nextInt();
                    input.nextLine();
                    
                    if (bedChoice == 1) {
                        System.out.print("Enter Patient ID: ");
                        String pid = input.nextLine();
                        
                        Patient p = patientManager.findPatientById(pid);
                        if (p == null) {
                            System.out.println("Patient not found.");
                        } else if (p.getCategory() != PatientCategory.INPATIENT) {
                            System.out.println("Only inpatients can be allocated beds.");
                        } else {
                            String allocatedBed = bedManager.allocateBed(pid);
                            if (allocatedBed != null) {
                                Inpatient in = (Inpatient) p;
                                in.setBedNumber(allocatedBed);
                                System.out.println("Bed " + allocatedBed + " allocated successfully.");
                            } else {
                                System.out.println("No beds available.");
                            }
                        }
                    }
                    else if (bedChoice == 2) {
                        System.out.print("Enter Bed ID to release (e.g. B01): ");
                        String bedId = input.nextLine();
                        
                        ArrayList<Patient> all = patientManager.getAllPatients();
                        for (int i = 0; i < all.size(); i++) {
                            Patient p = all.get(i);
                            if (p instanceof Inpatient) {
                               Inpatient in = (Inpatient) p;
                            if (in.getBedNumber().equalsIgnoreCase(bedId)) {
                                in.setBedNumber("Not Allocated");
                                }
                            }
                        }
                        
                        boolean released = bedManager.releaseBed(bedId);
                        if (released == true) {
                            System.out.println("Bed released successfully.");
                        } else {
                            System.out.println("Bed not found or already empty.");
                        }
                    }
                    else if (bedChoice == 3) {
                        bedManager.displayWardLayout();
                    }
                    else if (bedChoice == 4) {
                        bedManager.displayAvailableBeds();
                        bedManager.displayOccupiedBeds();
                    }
                    else if (bedChoice == 5) {
                        System.out.println("Returning to main menu...");
                    }
                    else {
                        System.out.println("Invalid choice.");
                    }
                }
            }
            
            // REPORTS
            else if (choice == 6) {
                System.out.println("\n========== REPORTS ==========");
                
                System.out.println("\n1. All Registered Patients:");
                ArrayList<Patient> all = patientManager.getAllPatients();
                if (all.size() == 0) {
                    System.out.println("No patients registered.");
                } else {
                    for (int i = 0; i < all.size(); i++) {
                        System.out.println(all.get(i).displayDetails());
                    }
                }
                
                System.out.println("\n2. Available Beds:");
                bedManager.displayAvailableBeds();
                
                System.out.println("\n3. Occupied Beds:");
                bedManager.displayOccupiedBeds();
                
                System.out.println("\n4. Total Registered Patients: " + patientManager.getPatientCount());
                
                System.out.println("5. Total Occupied Beds: " + bedManager.getOccupiedCount());
                
                double percent = bedManager.getOccupancyPercentage();
                System.out.println("6. Ward Occupancy Percentage: " + percent + "%");
                
                System.out.println("==============================");
            }
            
            // EXIT
            else if (choice == 7) {
                System.out.println("Goodbye!");
            }
            
            // INVALID
            else {
                System.out.println("Invalid choice. Please enter 1 to 7.");
            }
            
            System.out.println();
        }
        
        input.close();
    }
} 