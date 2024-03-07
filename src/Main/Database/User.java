package Main.Database;


//This class is going to be moved to the API later on
public class User {

//    private int id;
    private String username;

    // necessary empty constructor
     // private User(){}
//    protected User(int id, String username){
//        this.id = id;
//        this.username = username;
//    }

    //Thread safety mechanism
    private static final class UserHolder {
        private static final User user = new User();
    }

    //Singleton getInstance method
    public static User getInstance(){
        //Thread safety reasons

        return UserHolder.user;
    }

    public void setUsername(String username){
        UserHolder.user.username = username;
    }

}
