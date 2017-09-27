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
        Frame lastFrame = frames.get(0);
        int score = lastFrame.getBothThrows();
        for(int i = 1; i < frames.size(); i++) {
            score += frames.get(i).getBothThrows();
            if (lastFrame.getBothThrows() == 10) {
                if (lastFrame.getFirstThrow() == 10) {
                    score += frames.get(i).getBothThrows();
                }
                else {
                    score += frames.get(i).getFirstThrow();
                }
            }
        }
        return score;
    }

    public int getScore() {
        return this.score;
    }

    public static void main(String[] args) {
        ArrayList<Frame> frames = new ArrayList<Frame>();
        Frame frame = new Frame(10 , 0);
        Frame frame2 = new Frame(9 , 1);
        frames.add(frame);
        frames.add(frame2);
        frames.add(frame);
        frames.add(frame2);
        frames.add(frame);
        frames.add(new Frame(8 , 0));
        frames.add(frame);
        frames.add(frame2);
        frames.add(frame);
        frames.add(frame2);
        frames.add(frame);
        frames.add(frame2);

        Game game = new Game(frames);

        System.out.println(game.getScore());
    }


}
