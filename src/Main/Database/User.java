package Main.Database;


/**
 * Class that represents the User made with Singleton pattern.
 */
public class User {

//    private int id;
    private String username;

    // necessary empty constructor
     // private User(){}
//    protected User(int id, String username){
//        this.id = id;
//        this.username = username;
//    }

    /**
     * static final class that ensures thread safety. Java ClassLoader makes sure that it will be loaded only once
     * when getInstance method is called
     */
    private static final class UserHolder {
        private static final User user = new User();
    }

    /**
     * Singleton getInstance method
     * @return returns the instance of User.
     */
    public static User getInstance(){
        //Thread safety reasons

        return UserHolder.user;
    }

    /**
     * Sets the username of User instance
     * @param username - String to which the username of user should be changed.
     */
    public void setUsername(String username){
        UserHolder.user.username = username;
    }
    public String getUsername() {return UserHolder.user.username;}

}
