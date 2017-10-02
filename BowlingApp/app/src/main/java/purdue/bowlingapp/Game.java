package purdue.bowlingapp;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Jimmy on 9/26/2017.
 */

public class Game {

    private int score;
    private ArrayList<Frame> frames;
    private TenthFrame tenth;


    public Game(ArrayList<Frame> frames, TenthFrame tenth) {
        this.frames = frames;
        this.tenth = tenth;
        this.score = setScore();
    }

    private int setScore() {
        int score = 0;
        for(int i = 0; i < frames.size(); i++) {
            int frameScore = frames.get(i).getBothThrows();
            if(frameScore == 10 && i < 8) { //handle spare
                frameScore += frames.get(i+1).getFirstThrow();
            }
            if (frameScore == 10 && i == 8) {
                frameScore += tenth.getFirstThrow();
            }
            else if (frameScore == 11) { //handles strike
                if (i == 8) {
                    //if there is a spare in the first two throws of the tenth, add 10 to framescore
                    if (tenth.getSecondThrow() == 11) frameScore += 10 - 1;
                        //else add the sum of the first two throws to framescore
                    else frameScore += tenth.getFirstThrow() + tenth.getSecondThrow() - 1;
                } else {
                    int nextFrame = frames.get(i + 1).getBothThrows(); //gets throw immediately after strike
                    //if throw after strike is a strike and doesnt have to interact with tenth frame
                    if (nextFrame == 11 && i < 7)
                        frameScore += nextFrame + frames.get(i + 2).getFirstThrow() - 2;
                        //if throw after strike is also a strike, and must get first throw from tenth frame
                    else if (nextFrame == 11 && i == 7)
                        frameScore += nextFrame + tenth.getFirstThrow() - 2;
                        //if throw after strike is also a strike, and must get first two throws from tenth frame
                    else frameScore += nextFrame - 1;
                }
            }
            else if (frameScore == -1) return -1;
            score += frameScore;
        }
        score += tenth.getFirstThrow();
        if (tenth.getSecondThrow() != 11) score += tenth.getSecondThrow();
        else {
            score += 10 - tenth.getFirstThrow();
        }
        if (tenth.getFirstThrow() == 10 || tenth.getSecondThrow() == 11) {
            if (tenth.getThirdThrow() != 11) score += tenth.getThirdThrow();
            else {
                score += 10 - tenth.getSecondThrow();
            }
        }
        return score;
    }

    public int getScore() {
        return this.score;
    }

    public static void main(String[] args) {
        Frame f1 = new Frame('X','6');
        Frame f2 = new Frame('X','6');
        Frame f3 = new Frame('X','6');
        Frame f4 = new Frame('X','6');
        Frame f5 = new Frame('X','6');
        Frame f6 = new Frame('X','6');
        Frame f7 = new Frame('X','6');
        Frame f8 = new Frame('X','6');
        Frame f9 = new Frame('X','6');
        TenthFrame t1 = new TenthFrame('X','X','X');

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

        System.out.println(g.getScore());

    }


}
