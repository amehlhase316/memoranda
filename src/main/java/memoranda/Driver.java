package main.java.memoranda;

public class Driver {
    private final int ID;
    private String name;
    private String phoneNumber;

    /**
     * Default constructor
     * @param ID
     * @param name
     * @param phoneNumber
     */
    public Driver(int ID, String name, String phoneNumber) {
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns Driver ID
     * @return int ID
     */
    public int getID() { return this.ID; }

    /**
     * Returns Driver name
     * @return String name
     */
    public String getName() { return this.name; }

    /**
     * Returns Driver phone number
     * @return String phoneNumber
     */
    public String getPhoneNumber() { return this.phoneNumber; }

    /**
     * Sets the name of the Driver if they have chosen a legal name change
     * @param newName
     */
    public void setName(String newName) { this.name = newName; }

    /**
     * Sets the phone number of the driver if they change phone numbers
     * @param newPhoneNumber
     */
    public void setPhoneNumber(String newPhoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        StringBuffer info = new StringBuffer();

        info.append("Driver: "+getName() + "\n");
        info.append("ID: "+ getID() + "\n");
        info.append("Phone Number: " + getPhoneNumber() + "\n");
        return info.toString();
    }

    public static void main (String [] args) {
        Driver dr = new Driver(1,"Andy","55555555");
        System.out.println(dr.toString());
    }
}
