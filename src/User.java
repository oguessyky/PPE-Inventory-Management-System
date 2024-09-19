
import java.util.Comparator;
import java.util.function.Predicate;
public class User {

    /* entity attributes */
    public enum Type {
        Admin,
        Staff;
    }
    private String userID, name, password;
    private Type userType;

    /* Comparators for sorting */
    public static final Comparator<User> IDComparator = Comparator.comparing(User::getUserID);
    public static final Comparator<User> NameComparator = Comparator.comparing(User::getName);

    /* Predicates for filtering */
    public static final Predicate<User> NameContains(String s) { return (user -> user.name.contains(s)); }

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

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s", userID, name, password, userType);
    }
}
