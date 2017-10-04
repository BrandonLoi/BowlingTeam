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
        this.numGames = this.games.size();
        int totalScore = 0;
        //For filled percentage
        int numFilled = 0;
        int totalFrames = 0;
        //For strike percentage, as is totalFrames
        int numStrikes = 0;
        //For single pins
        int singlePinsMade = 0;
        int totalSinglePins = 0;
        //For spare percentage
        int numSpares = 0;
        int numNonStrikes = 0;
        for(int i = 0; i < this.numGames; i++) {
            for(int j = 0; j < 9; j++) {
                Frame curr = games.get(i).getFrame(j);
                if (curr != null) {
                    int score = curr.getBothThrows();
                    if(score == 11) {
                        numStrikes++;
                        numFilled++;
                    }
                    else if (score == 10) {
                        if(curr.getFirstThrow() == 9){
                            singlePinsMade++;
                            totalSinglePins++;
                        }
                        numFilled++;
                        numSpares++;
                        numNonStrikes++;
                    }
                    else{
                        if(curr.getFirstThrow() == 9) totalSinglePins++;
                        numNonStrikes++;
                    }
                    totalFrames++;
                }
            }
            TenthFrame tenth = games.get(i).getTenth();
            int first = tenth.getFirstThrow();
            int second = tenth.getSecondThrow();
            int third = tenth.getThirdThrow();
            if(first == 10){
                numStrikes++;
                numFilled++;
                if(second == 10){ //Strike Strike Fill
                    numStrikes++;
                    if(third == 10) numStrikes++; //Strike Strike Strike
                }
                else if (third == 11){ //Strike Spare
                    numNonStrikes++;
                    numSpares++;
                    if(second == 9) {
                        singlePinsMade++;
                        totalSinglePins++;
                    }
                }
                else { //Strike Open
                    if (second == 9) totalSinglePins++;
                    numNonStrikes++;
                }
            }
            else {
                if (second == 11) {
                    numFilled++;
                    numNonStrikes++;
                    numSpares++;
                    if(first == 9) {
                        singlePinsMade++;
                        totalSinglePins++;
                    }
                    if(third == 10) numStrikes++; //Spare Strike
                    else numNonStrikes++; //Spare Fill
                }
                else { //Open
                    if(first == 9) totalSinglePins++;
                    numNonStrikes++;
                }
            }
            totalFrames++;
            totalScore += games.get(i).getScore();
        }
        this.filledPc = ((double)numFilled/(double)totalFrames);
        this.strikePc = ((double)numStrikes/(double)totalFrames);
        this.avgScore = ((double)totalScore/(double)numGames);
        this.singlePinPc = ((double)singlePinsMade/(double)totalSinglePins);
        this.sparePc = ((double)numSpares/(double)numNonStrikes);
    }

    // TODO: create array of player's stats, with each index being a specific stat, and return
    public double[] getSelfStats() {
        return null;
    }
}
