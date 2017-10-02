package purdue.bowlingapp;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Jimmy on 9/26/2017.
 */

public class Game {

    private int score;
    private ArrayList<Frame> frames;


    public Game(ArrayList<Frame> frames) {
        this.frames = frames;
        this.score = setScore();
    }

    private int setScore() {
        int score = 0;
        for(int i = 0; i < frames.size() - 1; i++) {
            int frameScore = frames.get(i).getBothThrows();
            if(frameScore == 10) {
                frameScore += frames.get(i+1).getFirstThrow();
            }
            else if (frameScore == 11) {
                int nextFrame = frames.get(i+1).getBothThrows();
                if(nextFrame == 11 && i < 8) frameScore += nextFrame + frames.get(i+2).getFirstThrow() - 2;
                else frameScore += nextFrame - 1;
            }
            else if (frameScore == -1) return -1;
        }
        //TO DO: Add tenth frame to this
        return score;
    }

    public int getScore() {
        return this.score;
    }

    public static void main(String[] args) {
        
    }


}
