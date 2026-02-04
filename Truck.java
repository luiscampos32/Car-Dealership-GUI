/**
 * The Truck subclass extends the Vehicle class and has extra information 
 * on a truck
 */
package assign5;

/**
 * A class that produces information on a Truck
 * 
 */
public class Truck extends Vehicle
{
    /**
     * Constructor of the Truck class
     * @param vin VIN of the vehicle
     * @param make Make of the vehicle
     * @param model Model of the vehicle
     * @param year Year of vehicle production
     * @param mileage Mileage of vehicle
     * @param price Price of vehicle
     * @param maxLoad maximum load weight of truck
     * @param length length of truck
     */
    public Truck(String vin, String make, String model,
            Integer year, Integer mileage, Float price, String maxLoad, 
            String length)
    {
        super(vin, make, model, year, mileage, price);
        this.maxLoad = maxLoad;
        this.length = length;
    }
    /**
     * Returns String to allow output of object
     * @return 
     */
    @Override
    public String toString() 
    {
        String output = String.format(vin, make, model, year, mileage, price, 
                maxLoad, length);
        return(output);
    }

    private String maxLoad;
    private String length;
}
