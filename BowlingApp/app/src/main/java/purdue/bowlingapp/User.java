package purdue.bowlingapp;
import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private String email;

    // etc...

    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    User() {}

    public String getUsername() {
        return username;
    }

    public int setUsername(String newUsername) {
        username = newUsername;
        return 1;
    }

    // TODO: create legit password storing/functionality
    public String getPassword() {
        return password;
    }

    public int setPassword(String newPassword) {
        password = newPassword;
        return 1;
    }

    public String getEmail() {
        return email;
    }

    public int setEmail(String newEmail) {
        email = newEmail;
        return 1;
    }

    /**
     * Gets an array of the User's average score, average strikes, average spares, etc.
     *
     * @return double[] containing an array of the player's statistics
     */

    public int editGame() {
        return 0;
    }


}
