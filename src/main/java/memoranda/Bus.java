package main.java.memoranda;

public class Bus {
    private String ID;
    private int seats;
    private String assignedDriverID = null;

    private Driver driver;

    /**
     * Default constructor with ID input.
     * Average number of seats on a city bus is 30
     * @param ID ID of the bus
     */
    public Bus (String ID) {
        this(ID, 30);
        this.driver = null;
    }

    /**
     * Constructer for ID and number of seats input.
     * @param ID ID of the bus
     * @param seats number of seats on the bus
     */
    public Bus (int ID, int seats) {
        this.driver = null;
    }

    public Bus (String ID, int seats) {
        this.ID = ID;
        this.seats = seats;
    }
    /**
     * Returns the ID of the bus.
     * @return int ID
     */
    public String getId() {
        return ID;
    }

    /**
     * Returns the number of seats of the bus.
     * @return int seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the assignedDriver with driverID.
     * @param driverID String ID
     */
    public void setAssignedDriver(String driverID) { this.assignedDriverID = driverID; }

    /**
     * Gets the driver ID of the assigned driver if there exists one.
     * @return String driverID
     */
    public String getAssignedDriverID() { return this.assignedDriverID; }

    /**
     * Returns true or false if there is an assigned driver.
     * @return true or false
     */
    public boolean hasAssignedDriver() { return this.assignedDriverID != null; }

    /**
     * Removes the assignment of a driver to this bus.
     */
    public void removeAssignedDriver() { this.assignedDriverID = null; }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer();
        info.append("Bus ID: "+ getId() + "\n");
        info.append("Bus seats: " + Integer.toString(getSeats())+ "\n\n");

        return info.toString();
    }

    /**
     * Gets the driver's reference assigned to the Bus
     * @return the Driver object or null if no assigned driver
     */
    public Driver getDriver(){
        return driver;
    }

    /**
     * Setter for the driver variable
     * @param d the Driver reference
     */
    public void setDriver(Driver d){
        driver = d;
    }

    public static void main (String [] args) {
    //testing purposes
    /*public static void main (String [] args) {
        Bus dr = new Bus(1,55);
        System.out.println(dr.toString());*/
    }
}
