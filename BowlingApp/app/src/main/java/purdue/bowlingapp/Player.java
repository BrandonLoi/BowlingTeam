package purdue.bowlingapp;

import java.util.ArrayList;

/**
 * Created by Jimmy on 10/3/2017.
 */

public class Player extends User {

    private ArrayList<Game> games;
    private int numGames;
    private double avgScore;
    private double strikePc;
    private double pocketPc;
    private double makeablePc;
    private double singlePinPc;
    private double sparePc;
    private double filledPc;

    public Player(String username, String password, String email, ArrayList<Game> games){
        super(username, password, email);
        this.games = games;
        getPercents();
    }

    public int addGame(Game game) {
        if (games.add(game)) {
            return 1;
        }
        return 0;
    }

    public Game getGame(int index) {
        return games.get(index);
    }

    private void getPercents() {
        int numFilled = 0;
        int totalFrames = 0;
        for(int i = 0; i < numGames; i++) {
            for(int j = 0; j < 9; j++) {
                Frame curr = games.get(i).getFrame(j);
                if (curr != null) {
                    int score = curr.getBothThrows();
                    if(score == 10 || score == 11) numFilled++;
                    totalFrames++;
                }
            }
            TenthFrame tenth = games.get(i).getTenth();
            if(tenth.getFirstThrow() == 'X' || tenth.getSecondThrow() == '/')numFilled++;
            totalFrames++;
        }
        this.filledPc = ((double)numFilled/(double)totalFrames);
    }

    // TODO: create array of player's stats, with each index being a specific stat, and return
    public double[] getSelfStats() {
        return null;
    }
}
