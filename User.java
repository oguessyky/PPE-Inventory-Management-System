

public class User {

    /* entity attributes */
    private String userID, name, password, userType;

    /* constructors */
    public User(String userID, String name, String password, String userType) {   
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    /* getters */
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getUserType() { return userType; }

    /* setters */
    public void setUserID(String userID) { this.userID = userID; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setUserType(String userType) { this.userType = userType; }
}
