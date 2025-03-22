package authentication;

public class users {
    private int userID; //Unique user ID
    private String name; //User's full name
    private String email; //Unique email for login
    private String password; //Hashed password
    private String phone; //Contact number
    private UserType userType; // Role of user

    //Method to Define user Role (CUSTOMER OR ADMIN)
    public enum UserType {
        CUSTOMER,
        ADMIN
    }
    //Constructor
    public users (int userID,String name, String email, String password, String phone, UserType userType) {
        this.userID =userID;
        this.name =  name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userType = userType;
    }
    //Getters
    public int getUserID() {
        return userID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getPhone() {
        return phone;
    }
    public UserType userType() {
        return userType;
    }

}
