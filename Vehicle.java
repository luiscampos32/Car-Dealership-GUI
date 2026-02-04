/**
 * The Vehicle class has information on cars such as its VIN, make, model, year,
 * mileage, and price.
 */
package assign5;

/**
 * A class that produces information on a Vehicle record
 */
public abstract class Vehicle 
{
    /**
     * Constructor for the Vehicle class. 
     * @param vin VIN of the vehicle
     * @param make Make of the vehicle
     * @param model Model of the vehicle
     * @param year Year of vehicle production
     * @param mileage Mileage of vehicle
     * @param price Price of vehicle
     */
    public Vehicle(String vin, String make, String model,
            Integer year, Integer mileage, Float price)
    {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }
    /**
     * Accesses the VIN of the vehicle 
     * @return Returns the VIN
     */
    public String getVin()
    {
        return this.vin;
    }
    /**
     * Accesses the Price of the vehicle 
     * @return Returns the price
     */
    public Float getPrice()
    {
        return this.price;
    }
    
    public String getMake()
    {
        return this.make;
    }
    
    public String getModel()
    {
        return this.model;
    }
    
    public Integer getYear()
    {
        return this.year;
    }
    
    public Integer mileage()
    {
        return this.mileage;
    }
    
    protected String vin;
    protected String make;
    protected String model;
    protected Integer year;
    protected Integer mileage;
    protected Float price;  
}
