/**
 * The Employee subclass extends the User class and has extra information 
 * on a employee
 */
package assign5;

/**
 * A class that produces information on a Employee
 * 
 */
public class Employee extends User
{
    /**
     * Constructor of the Employee class
     * @param id id of employee
     * @param first first name of employee
     * @param last last name of employee
     * @param mSalary monthly salary of employee
     * @param accNumber bank account number of employee
     */
    public Employee(Integer id, String first, String last, Float mSalary,
            Integer accNumber)
    {
        super(id, first, last);
        this.mSalary = mSalary;
        this.accNumber = accNumber;
    }
    /**
     * Returns a String to allow output of object
     * @return 
     */
    @Override
    public String toString() 
    {
        String output = String.format("%-7d %-12s %-12s %-8.2f %-10d \n",
                id, first, last, mSalary, accNumber);
        return(output);
    }
    
    public Float getSalary()
    {
        return this.mSalary;
    }
    
    public Integer getAccount()
    {
        return this.accNumber;
    }
    /**
     * Sets the first name of employee
     * @param first 
     */
    public void setFirst(String first)
    {
        this.first = first;
    }
    /**
     * Sets the last name of employee
     * @param last 
     */
    public void setLast(String last)
    {
        this.last = last;
    }
    /**
     * Sets the monthly salary of employee
     * @param salary 
     */
    public void setSalary(Float salary)
    {
        this.mSalary = salary;
    }
    /**
     * Sets the account number of employee
     * @param accNumber 
     */
    public void setAccNumber(Integer accNumber)
    {
        this.accNumber = accNumber;
    }
    /**
     * Sets the ID of the employee
     * @return 
     */
    @Override
    public Integer getID()
    {
        return this.id;
    }
    
    private Float mSalary;
    private Integer accNumber;
}
