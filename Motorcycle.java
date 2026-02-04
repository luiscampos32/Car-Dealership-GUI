/**
 * The Motorcycle subclass extends the Vehicle class and has extra information 
 * on a motorcycle
 */
package assign5;

/**
 * A class that produces information on a Motorcycle
 * 
 */
public class Motorcycle extends Vehicle 
{
    /**
     * Constructor of the Motorcycle class
     * @param vin VIN of the vehicle
     * @param make Make of the vehicle
     * @param model Model of the vehicle
     * @param year Year of vehicle production
     * @param mileage Mileage of vehicle
     * @param price Price of vehicle
     * @param type type of motorcycle
     * @param engineDisp engine displacement of motorcycle
     */
    public Motorcycle(String vin, String make, String model,
            Integer year, Integer mileage, Float price, String type, 
            String engineDisp)
    {
        super(vin, make, model, year, mileage, price);
        this.type = type;
        this.engineDisp = engineDisp;
    }
    /**
     * Returns String to allow output of object
     * @return 
     */
    @Override
    public String toString() 
    {
        String output = String.format(vin, make, model, year, mileage, price, 
                type, engineDisp);
        return(output);
    }
    private String type;
    private String engineDisp;
    
}
