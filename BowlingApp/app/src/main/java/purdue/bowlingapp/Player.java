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

    public int addGame(Game game) { //TO DO: Adjust statistics when adding additional games
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
        int totalFirstBalls = 0;
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
                    totalFirstBalls++;
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
                    if(third == 10) { //Strike Strike Strike
                        numStrikes++;
                        totalFirstBalls++;
                    }
                    else numNonStrikes++;
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
                totalFirstBalls+=2;
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
                    totalFirstBalls+=2;
                }
                else { //Open
                    if(first == 9) totalSinglePins++;
                    numNonStrikes++;
                    totalFirstBalls++;
                }
            }
            totalFrames++;
            totalScore += games.get(i).getScore();
        }
        this.filledPc = ((double)numFilled/(double)totalFrames);
        this.strikePc = ((double)numStrikes/(double)totalFirstBalls);
        this.avgScore = ((double)totalScore/(double)numGames);
        this.singlePinPc = ((double)singlePinsMade/(double)totalSinglePins);
        this.sparePc = ((double)numSpares/(double)numNonStrikes);
    }

    private void printStats() {
        System.out.println("Average Score:                    " + this.avgScore);
        System.out.println("Percentage of Frames filled:      " + this.filledPc);
        System.out.println("Strike Percentage:                " + this.strikePc);
        System.out.println("Single Pin Conversion Percentage: " + this.singlePinPc);
        System.out.println("Spare Conversion Percentage:      " + this.sparePc);
        System.out.println("Total Number of Games:            " + this.numGames);
    }

    // TODO: create array of player's stats, with each index being a specific stat, and return
    public double[] getSelfStats() {
        return null;
    }


    public static void main(String[] args) {
        Frame f1 = new Frame('0','7');
        Frame f2 = new Frame('0','9');
        Frame f3 = new Frame('7','2');
        Frame f4 = new Frame('9','0');
        Frame f5 = new Frame('0','8');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('8','1');
        Frame f8 = new Frame('7','/');
        Frame f9 = new Frame('8','/');
        TenthFrame t1 = new TenthFrame('X','7','1');

        ArrayList<Frame> frames = new ArrayList<>();
        frames.add(f1);
        frames.add(f2);
        frames.add(f3);
        frames.add(f4);
        frames.add(f5);
        frames.add(f6);
        frames.add(f7);
        frames.add(f8);
        frames.add(f9);

        Game g = new Game(frames, t1);
        System.out.println("Game 1 Score: " + g.getScore());

        Frame f10 = new Frame('0','7');
        Frame f11 = new Frame('0','9');
        Frame f12 = new Frame('7','2');
        Frame f13 = new Frame('9','/');
        Frame f14 = new Frame('0','8');
        Frame f15 = new Frame('X',' ');
        Frame f16 = new Frame('8','1');
        Frame f17 = new Frame('7','/');
        Frame f18 = new Frame('8','/');
        TenthFrame t2 = new TenthFrame('X','7','1');

        ArrayList<Frame> frames2 = new ArrayList<>();
        frames2.add(f12);
        frames2.add(f10);
        frames2.add(f16);
        frames2.add(f14);
        frames2.add(f17);
        frames2.add(f18);
        frames2.add(f13);
        frames2.add(f11);
        frames2.add(f15);

        Game g2 = new Game(frames2, t2);
        System.out.println("Game 2 score: " + g2.getScore());

        ArrayList<Game> games = new ArrayList<>();
        games.add(g);
        games.add(g2);

        Player player = new Player("wow", "sick", "bet", games);

        player.printStats();

    }


}
