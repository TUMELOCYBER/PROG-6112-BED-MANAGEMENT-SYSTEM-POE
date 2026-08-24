package model;

public class Bed {
    private String bedId;
    private boolean isOccupied;
    private String patientId;

    public Bed(String bedId) {
        this.bedId = bedId;
        this.isOccupied = false;
        this.patientId = null;
    }

    public String getBedId() { return bedId; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { this.isOccupied = occupied; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
}