/**
 * The Car subclass extends the Vehicle class and has extra information 
 * on a car
 */
package assign5;

/**
 * A class that produces information on a Car
 * 
 */
public class Car extends Vehicle 
{
    /**
     * Constructor of the Car class
     * @param vin VIN of car
     * @param make make of car
     * @param model model of car
     * @param year year of car
     * @param mileage mileage of car
     * @param price price of car 
     * @param style style of car
     */
    public Car(String vin, String make, String model,
            Integer year, Integer mileage, Float price, String style)
    {
        super(vin, make, model, year, mileage, price);
        this.style = style;
    }
    /**
     * Returns a String to allow output of a Car object
     * @return Returns the "output" String 
     */
    @Override
    public String toString() 
    {
        String output = String.format(vin, make, model, year, mileage, 
                price, style);
        return(output);
    }

    private String style;
}
