/**
 * The User class has information on users such as ID, first name,
 * and last name.
 */
package assign5;

/**
 * A class that produces information on a User
 * 
 */
public abstract class User 
{
    /**
     * Constructor for the User class
     * @param id ID of  a user
     * @param first first name of a user
     * @param last  last name of a user 
     */
    public User(Integer id, String first, String last)
    {
        this.id = id;
        this.first = first;
        this.last = last;
    }
    /**
     * Accesses the ID of a user
     * @return Returns the ID 
     */
    public Integer getID()
    {
        return this.id;
    }
    
    public String getFirst()
    {
        return this.first;
    }
    
    public String getLast()
    {
        return this.last;
    }
    
    protected Integer id;
    protected String first;
    protected String last;


}
