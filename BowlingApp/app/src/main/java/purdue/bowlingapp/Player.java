package purdue.bowlingapp;

import java.util.ArrayList;

/**
 * Created by Jimmy on 10/3/2017.
 */

public class Player {

    private String username;

    private ArrayList<Game> games;
    private int highGame = -1;
    private int numGames;
    private double avgScore;
    private double strikePc;
    private double pocketPc;
    private double makeablePc;
    private double singlePinPc;
    private double sparePc;
    private double filledPc;

    //Helper Variables
    private int totalScore;
    private int numFilled;
    private int totalFrames;
    private int numStrikes;
    private int totalFirstBalls;

    Integer prevSingleMade;
    Integer prevSingleLeft;
    Integer prevSplitMade;
    Integer prevSplitLeft;
    Integer prevMultiMade;
    Integer prevMultiLeft;
    Integer prevStrikes;
    Integer prevTotal;
    Integer numberGames;
    Integer ballsThrown;
    Integer highestScore;
    Integer prevFilled;

//    public int multiPinsLeft;
 //   public int multiPinsMade;
 //   public int splitsLeft;
 //   public int splitsMade;
 //   public int singlePinMade;
 //   public int singlePinLeft;
 //   public int newBallsThrown;
 //   public int strikes;
//    public int filledFramesCount;
//    public int scoreTemp;
 //   public int tempHigh;

    private int singlePinsMade;
    private int totalSinglePins;

    private int numSpares;
    private int numNonStrikes;

    public Player() {
        //default constructor to store in database
    }

    public Player(String username, ArrayList<Game> games){
        this.username = username;
        this.games = games;
        if(this.games != null) {
            getPercents();
        }
    }

    public void addFrame(Frame frame) {
        int firstThrow = frame.getValFirstThrow();
        if(firstThrow == -1) {
            return;
        }
        totalFrames++;
        totalFirstBalls++;
        if(firstThrow == 10) {
            numStrikes++;
            numFilled++;
        }
        else {
            numNonStrikes++;
            if (firstThrow == 9) {
                    totalSinglePins++;
                    if (frame.getSecondThrow() == '/') {
                        singlePinsMade++;
                        numFilled++;
                        numSpares++;
                    }
            }
            else if (frame.getSecondThrow() == '/') {
                    numFilled++;
                    numSpares++;
            }
        }
    }

    public void addFrame(TenthFrame tenthFrame) {
        int first = tenthFrame.getFirstThrow();
        int second = tenthFrame.getSecondThrow();
        int third = tenthFrame.getThirdThrow();
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
    }

    public int addGame(Game game) { //TODO: Adjust statistics when adding additional games
        if (games.add(game)) {
            this.numGames++;
            for(int i = 0; i < 9; i++) {
                Frame curr = game.getFrame(i);
                if (curr != null) {
                    int score = curr.getBothThrows();
                    if(score == 11) {
                        this.numStrikes++;
                        this.numFilled++;
                    }
                    else if (score == 10) {
                        if(curr.getValFirstThrow() == 9){
                            this.singlePinsMade++;
                            this.totalSinglePins++;
                        }
                        this.numFilled++;
                        this.numSpares++;
                        this.numNonStrikes++;
                    }
                    else{
                        if(curr.getValFirstThrow() == 9) totalSinglePins++;
                        this.numNonStrikes++;
                    }
                    this.totalFrames++;
                    this.totalFirstBalls++;
                }
            }
            TenthFrame tenth = game.getTenth();
            addFrame(tenth);
            this.totalScore += game.getScore();
            if(game.getScore() > this.highGame) this.highGame = game.getScore();

            this.filledPc = ((double)this.numFilled/(double)this.totalFrames);
            this.strikePc = ((double)this.numStrikes/(double)this.totalFirstBalls);
            this.avgScore = ((double)this.totalScore/(double)this.numGames);
            this.singlePinPc = ((double)this.singlePinsMade/(double)this.totalSinglePins);
            this.sparePc = ((double)this.numSpares/(double)this.numNonStrikes);
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
                        if(curr.getValFirstThrow() == 9){
                            singlePinsMade++;
                            totalSinglePins++;
                        }
                        numFilled++;
                        numSpares++;
                        numNonStrikes++;
                    }
                    else{
                        if(curr.getValFirstThrow() == 9) totalSinglePins++;
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
            if (games.get(i).getScore() > this.highGame)
                this.highGame = games.get(i).getScore();
        }
        this.filledPc = ((double)numFilled/(double)totalFrames);
        this.strikePc = ((double)numStrikes/(double)totalFirstBalls);
        this.avgScore = ((double)totalScore/(double)numGames);
        this.singlePinPc = ((double)singlePinsMade/(double)totalSinglePins);
        this.sparePc = ((double)numSpares/(double)numNonStrikes);
        this.totalScore = totalScore;
        this.numFilled = numFilled;
        this.totalFrames = totalFrames;
        this.numStrikes = numStrikes;
        this.totalFirstBalls = totalFirstBalls;
        this.singlePinsMade = singlePinsMade;
        this.totalSinglePins = totalSinglePins;
        this.numSpares = numSpares;
        this.numNonStrikes = numNonStrikes;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public int getHighGame() {
        return highGame;
    }

    public int getNumGames() {
        return numGames;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public double getStrikePc() {
        return strikePc;
    }

    public double getPocketPc() {
        return pocketPc;
    }

    public double getMakeablePc() {
        return makeablePc;
    }

    public double getSinglePinPc() {
        return singlePinPc;
    }

    public double getSparePc() {
        return sparePc;
    }

    public double getFilledPc() {
        return filledPc;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getNumFilled() {
        return numFilled;
    }

    public int getTotalFrames() {
        return totalFrames;
    }

    public int getNumStrikes() {
        return numStrikes;
    }

    public int getTotalFirstBalls() {
        return totalFirstBalls;
    }

    public int getSinglePinsMade() {
        return singlePinsMade;
    }

    public int getTotalSinglePins() {
        return totalSinglePins;
    }

    public int getNumSpares() {
        return numSpares;
    }

    public int getNumNonStrikes() {
        return numNonStrikes;
    }

    private void printStats() {
        System.out.println("High Game:                        " + this.highGame);
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

        Frame f20 = new Frame('7','/');
        Frame f21 = new Frame('9','0');
        Frame f22 = new Frame('X',' ');
        Frame f23 = new Frame('X',' ');
        Frame f24 = new Frame('X',' ');
        Frame f25 = new Frame('X',' ');
        Frame f26 = new Frame('8','1');
        Frame f27 = new Frame('7','/');
        Frame f28 = new Frame('8','/');
        TenthFrame t3 = new TenthFrame('X','7','1');

        ArrayList<Frame> frames3 = new ArrayList<>();
        frames3.add(f20);
        frames3.add(f21);
        frames3.add(f22);
        frames3.add(f23);
        frames3.add(f24);
        frames3.add(f25);
        frames3.add(f26);
        frames3.add(f27);
        frames3.add(f28);

        Game g3 = new Game(frames3, t3);
        System.out.println("Game 3 score: " + g3.getScore());

        Frame f30 = new Frame('X','4');
        Frame f31 = new Frame('X','8');
        Frame f32 = new Frame('X',' ');
        Frame f33 = new Frame('X',' ');
        Frame f34 = new Frame('X','a');
        Frame f35 = new Frame('X',' ');
        Frame f36 = new Frame('X',' ');
        Frame f37 = new Frame('X',' ');
        Frame f38 = new Frame('X',' ');
        TenthFrame t4 = new TenthFrame('X','X','X');

        ArrayList<Frame> frames4 = new ArrayList<>();
        frames4.add(f30);
        frames4.add(f31);
        frames4.add(f32);
        frames4.add(f33);
        frames4.add(f34);
        frames4.add(f35);
        frames4.add(f36);
        frames4.add(f37);
        frames4.add(f38);

        Game g4 = new Game(frames4, t4);
        System.out.println("Game 4 score: " + g4.getScore());

        ArrayList<Game> games = new ArrayList<>();
        games.add(g);

        Player player = new Player("wow", games);

        System.out.println("\n\nAfter Game 1 Added:\n");
        player.printStats();
        player.addGame(g2);
        System.out.println("\n\nAfter Game 2 Added:\n");
        player.printStats();
        player.addGame(g3);
        System.out.println("\n\nAfter Game 3 Added:\n");
        player.printStats();
        player.addGame(g4);
        System.out.println("\n\nAfter Game 4 Added:\n");
        player.printStats();

    }


}
