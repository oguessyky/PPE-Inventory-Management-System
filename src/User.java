

public class User {

    /* entity attributes */
    public enum Type {
        Admin,
        Staff;
    }    
    private String userID, name, password;
    private Type userType;

    /* constructors */
    public User(String userID, String name, String password, Type userType) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    /* getters */
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public Type getUserType() { return userType; }

    /* setters */
    public void setUserID(String userID) { this.userID = userID; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setUserType(Type userType) { this.userType = userType; }
}
