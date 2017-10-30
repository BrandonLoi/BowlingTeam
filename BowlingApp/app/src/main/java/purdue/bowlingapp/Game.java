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

    public Game() {
        //default construtor
    }

    public ArrayList<Frame> getFrames() {
        return this.frames;
    }

    public Frame getFrame(int index) {
        if (index >= 0 && index < 9) {
            return frames.get(index);
        }
        return null;
    }

    public TenthFrame getTenth() {
        return this.tenth;
    }

    public int getSinglePinLeft() {
        int count = 0;
        for(Frame f : frames) {
            if(f.getFirstThrow() == '9')
                count++;
        }
        if(tenth.getFirstThrowC() == '9' || tenth.getSecondThrowC() == '9')
            count++;
        return count;
    }

    public int getSinglePinMade() {
        int count = 0;
        for(Frame f : frames) {
            if(f.getFirstThrow() == '9' && f.getSecondThrow() == '/')
                count++;
        }
        if(tenth.getFirstThrowC() == '9' && tenth.getSecondThrowC() == '/'
                || tenth.getSecondThrowC() == '9' && tenth.getThirdThrowC() == '/')
            count++;
        return count;
    }

    public int getSplitLeft() {
        int count = 0;
        for(Frame f : frames)
            if(f.isSplit())
                count++;
        if(tenth.isSplit())
            count++;
        return count;
    }

    public int getMultiLeft() {
        int count = 0;
        for(Frame f : frames)
            if(f.isMakeable() && f.getFirstThrow() != 'X' && f.getFirstThrow() != '9')
                count++;
        if(tenth.isMakeable())
            count++;
        return count;
    }

    public int getMultiMade() {
        int count = 0;
        for(Frame f : frames)
            if(f.isMakeable() && f.getSecondThrow() == '/' && f.getFirstThrow() != '9')
                count++;
        if(tenth.isMakeable() && (tenth.getSecondThrowC() == '/' || tenth.getThirdThrowC() == '/'))
            count++;
        return count;
    }

    public int getFilledFrame() {
        int count = 0;
        for(Frame f : frames)
            if(f.getFirstThrow() == 'X' || f.getSecondThrow() == '/')
                count++;
        if(tenth.getFirstThrowC() == 'X' || tenth.getSecondThrowC() == '/')
            count++;
        return count;
    }

    public int getStrike() {
        int count = 0;
        for (Frame f : frames) {
            if (f.getFirstThrow() == 'X')
                count++;
        }
        if (tenth.getFirstThrowC() == 'X')
            count++;
        if (tenth.getSecondThrowC() == 'X')
            count++;
        if(tenth.getThirdThrowC() == 'X')
            count++;
        return count;
    }

    public int getSplitMade() {
        int count = 0;
        for(Frame f : frames)
            if(f.isSplit() && f.getSecondThrow() == '/')
                count++;
        if (tenth.isSplit() && (tenth.getFirstThrowC() == '/' || tenth.getSecondThrowC() =='/'))
            count++;
        return count;
    }

    public int getBallsThrown() {
        int count = 9;
        if (tenth.getSecondThrowC() == 'X')
            count += 3;
        else if (!(tenth.getFirstThrowC() == 'X' || tenth.getSecondThrowC() == '/'))
            count += 1;
        else
            count += 2;
        return count;
    }

    public int setScore() {
        int score = 0;
        for(int i = 0; i < frames.size(); i++) {
            int frameScore = frames.get(i).getBothThrows();
            if(frameScore == 10 && i < 8) { //handle spare
                frameScore += frames.get(i+1).getValFirstThrow();
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
                        frameScore += nextFrame + frames.get(i + 2).getValFirstThrow() - 2;
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

        System.out.println(g.getScore());

    }


}
