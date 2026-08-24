package manager;

import model.Bed;

public class BedManager {
    private Bed[] beds;

    public BedManager() {
        beds = new Bed[20];
        for (int i = 0; i < 20; i++) {
            String bedId;
            if (i + 1 < 10) {
                bedId = "B0" + (i + 1);
            } else {
                bedId = "B" + (i + 1);
            }
            beds[i] = new Bed(bedId);
        }
    }

    public String allocateBed(String patientId) {
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied() == false) {
                beds[i].setOccupied(true);
                beds[i].setPatientId(patientId);
                return beds[i].getBedId();
            }
        }
        return null;
    }

    public boolean releaseBed(String bedId) {
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].getBedId().equalsIgnoreCase(bedId)) {
                if (beds[i].isOccupied() == true) {
                    beds[i].setOccupied(false);
                    beds[i].setPatientId(null);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBedOccupied(String bedId) {
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].getBedId().equalsIgnoreCase(bedId)) {
                return beds[i].isOccupied();
            }
        }
        return false;
    }

    public void displayWardLayout() {
        System.out.println("\n--- WARD LAYOUT ---");
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied() == true) {
                System.out.print("[X] " + beds[i].getBedId() + "  ");
            } else {
                System.out.print("[ ] " + beds[i].getBedId() + "  ");
            }
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    public void displayAvailableBeds() {
        System.out.println("\n--- AVAILABLE BEDS ---");
        boolean found = false;
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied() == false) {
                System.out.println(beds[i].getBedId());
                found = true;
            }
        }
        if (found == false) {
            System.out.println("No beds available.");
        }
    }

    public void displayOccupiedBeds() {
        System.out.println("\n--- OCCUPIED BEDS ---");
        boolean found = false;
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied() == true) {
                System.out.println(beds[i].getBedId() + " - Patient: " + beds[i].getPatientId());
                found = true;
            }
        }
        if (found == false) {
            System.out.println("No occupied beds.");
        }
    }

    public int getOccupiedCount() {
        int count = 0;
        for (int i = 0; i < beds.length; i++) {
            if (beds[i].isOccupied() == true) {
                count = count + 1;
            }
        }
        return count;
    }

    public double getOccupancyPercentage() {
        int occupied = getOccupiedCount();
        double percent = (occupied * 100.0) / 20.0;
        return percent;
    }
}