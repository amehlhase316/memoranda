package main.java.memoranda;

public class Bus {
    private final int ID;
    private final int seats;

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
    public int getID() {
        return ID;
    }

    /**
     * Returns the number of seats of the bus
     * @return int seats
     */
    public int getSeats() {
        return seats;
    }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer();
        info.append("Bus ID: "+ getID() + "\n");
        info.append("Bus seats: " + Integer.toString(getSeats())+ "\n");

        return info.toString();
    }

    public static void main (String [] args) {
        Bus dr = new Bus(1,55);
        System.out.println(dr.toString());
    }
}
