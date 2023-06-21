package main.java.memoranda;

public class Bus {
    private final int ID;
    private final int seats;
    private String assignedDriverID = null;

    /**
     * Default constructor with ID input
     * Average number of seats on a city bus is 30
     * @param ID
     */
    public Bus (int ID) {
        this(ID, 30);
    }

    /**
     * Constructer for ID and number of seats input
     * @param ID
     * @param seats
     */
    public Bus (int ID, int seats) {
        this.ID = ID;
        this.seats = seats;
    }

    /**
     * Returns the ID of the bus
     * @return int ID
     */
    public int getId() {
        return ID;
    }

    /**
     * Returns the number of seats of the bus
     * @return int seats
     */
    public int getSeats() {
        return seats;
    }

    public void setAssignedDriver(String driverID) { this.assignedDriverID = driverID; }

    public String getAssignedDriverID() { return this.assignedDriverID; }

    public boolean hasAssignedDriver() { return this.assignedDriverID != null; }

    public void removeAssignedDriver() { this.assignedDriverID = null; }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer();
        info.append("Bus ID: "+ getId() + "\n");
        info.append("Bus seats: " + Integer.toString(getSeats())+ "\n\n");

        return info.toString();
    }

    public static void main (String [] args) {
        Bus dr = new Bus(1,55);
        System.out.println(dr.toString());
    }
}
