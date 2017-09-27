package purdue.bowlingapp;
import java.util.ArrayList;

public class Player {

    private String username;
    private String password;
    private String email;

    private ArrayList<Game> games;
    private double avgScore;
    private double avgStrikes;
    private double avgSpares;
    // etc...

    Player(String username, String password, String email) {
        games = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.email = email;
    }

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
     * Gets an array of the Player's average score, average strikes, average spares, etc.
     *
     * @return double[] containing an array of the player's statistics
     */

    public int addGame(Game game) {
        if (games.add(game)) {
            return 1;
        }
        return 0;
    }

    public Game getGame(int index) {
        return games.get(index);
    }

    public int editGame() {
        return 0;
    }

    // TODO: create array of player's stats, with each index being a specific stat, and return
    public double[] getSelfStats() {
        return null;
    }
}
