/**
 * The Sales Class has information on a sale of a vehicle made
 * between a customer and employee
 */
package assign5;

import java.util.Date;

/**
 * A class that produces information on a Sale
 * 
 */
public class Sales 
{
    /**
     * Constructor of the Sales class
     * @param custID customer ID that bought a vehicle
     * @param vehicleVIN VIN of a vehicle that was sold
     * @param saleDate Date that vehicle was sold
     * @param salePrice Price that vehicle was sold for
     * @param empID employee ID that sold the vehicle
     */
    public Sales(Integer custID, String vehicleVIN, String saleDate,
            Float salePrice, Integer empID)
    {
        this.custID = custID;
        this.vehicleVIN = vehicleVIN;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.empID = empID;
    }
    
    public Integer getCustID()
    {
        return this.custID;
    }
    
    public String getVehicleVIN()
    {
        return this.vehicleVIN;
    }
    
    public String getDate()
    {
        return this.saleDate;
    }
    
    public Float getSalePrice()
    {
        return this.salePrice;
    }
    
    public Integer getEmpID()
    {
        return this.empID;
    }
    /**
     * Returns String to allow output of object
     * @return 
     */
    @Override
    public String toString() 
    {
        String output = String.format("%-9d %-12s %-12tD %-8.2f %-9d \n",
                custID, vehicleVIN, saleDate, salePrice, empID);
        return(output);
    }

    private Integer custID;
    private String vehicleVIN;
    private String saleDate;
    private Float salePrice;
    private Integer empID;
}
