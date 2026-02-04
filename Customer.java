/**
 * The Customer subclass extends the User class and has extra information 
 * on a customer
 */
package assign5;

/**
 * A class that produces information on a Customer
 * 
 */
public class Customer extends User
{
    /**
     * Constructor of the Customer class
     * @param id id of a customer
     * @param first first name of a customer
     * @param last last name of a customer
     * @param phNumber phone number of a customer
     * @param dLicense driver's license of a customer
     */
    public Customer(Integer id, String first, String last, String phNumber,
            Integer dLicense)
    {
        super(id, first, last);
        this.phNumber = phNumber;
        this.dLicense = dLicense;
    }
    
    public String getPhone()
    {
        return this.phNumber;
    }
    
    public Integer getLicense()
    {
        return this.dLicense;
    }
    /**
     * Sets the first name of a customer
     * @param first 
     */
    public void setFirst(String first)
    {
        this.first = first;
    }
    /**
     * Sets the last name of a customer
     * @param last 
     */
    public void setLast(String last)
    {
        this.last = last;
    }
    /**
     * Sets the phone number of a customer
     * @param number 
     */
    public void setPhoneNumber(String number)
    {
        this.phNumber = number;
    }
    /**
     * sets the Driver's license of a customer
     * @param license 
     */
    public void setDLicense(Integer license)
    {
        this.dLicense = license;
    }
    /**
     * Accesses the ID of a customer
     * @return 
     */
    @Override
    public Integer getID()
    {
        return this.id;
    }
    /**
     * Returns a String to allow output of object
     * @return 
     */
    @Override
    public String toString() 
    {
        String output = String.format("%-7d %-12s %-12s %-12s %-10d \n",
                id, first, last, phNumber, dLicense);
        return(output);
    }
    
    private String phNumber;
    private Integer dLicense;
}
