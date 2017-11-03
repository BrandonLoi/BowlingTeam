package purdue.bowlingapp;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Jimmy on 11/2/2017.
 */

public class ScoringTest {

    @Test
    public void scoring_all_opens() {
        Frame f1 = new Frame('0','7');
        Frame f2 = new Frame('0','9');
        Frame f3 = new Frame('7','2');
        Frame f4 = new Frame('9','0');
        Frame f5 = new Frame('0','8');
        Frame f6 = new Frame('6','3');
        Frame f7 = new Frame('8','1');
        Frame f8 = new Frame('7','2');
        Frame f9 = new Frame('8','1');
        TenthFrame t1 = new TenthFrame('4','3',' ');

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

        assertEquals(g.getScore(), 85);
    }

    @Test
    public void scoring_all_spares() {
        Frame f1 = new Frame('7','/');
        Frame f2 = new Frame('7','/');
        Frame f3 = new Frame('7','/');
        Frame f4 = new Frame('7','/');
        Frame f5 = new Frame('7','/');
        Frame f6 = new Frame('7','/');
        Frame f7 = new Frame('7','/');
        Frame f8 = new Frame('7','/');
        Frame f9 = new Frame('7','/');
        TenthFrame t1 = new TenthFrame('7','/','7');

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

        assertEquals(g.getScore(), 170);
    }

    @Test
    public void scoring_perfect() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
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

        assertEquals(g.getScore(), 300);
    }

    @Test
    public void scoring_invalid_inputs() {
        Frame f1 = new Frame('X','/');
        Frame f2 = new Frame('X','6');
        Frame f3 = new Frame('7','5');
        Frame f4 = new Frame(' ',' ');
        Frame f5 = new Frame('A','B');
        Frame f6 = new Frame('x',' ');
        Frame f7 = new Frame('/','X');
        Frame f8 = new Frame('5','X');
        Frame f9 = new Frame('/','5');
        TenthFrame t1 = new TenthFrame('X','/',' ');

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

        assertEquals(g.getScore(), 300);
    }

    @Test
    public void scoring_tenth_frame_1() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
        TenthFrame t1 = new TenthFrame('X','X','6');

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

        assertEquals(g.getScore(), 296);
    }

    @Test
    public void scoring_tenth_frame_2() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
        TenthFrame t1 = new TenthFrame('X','6','/');

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

        assertEquals(g.getScore(), 286);
    }

    @Test
    public void scoring_tenth_frame_3() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
        TenthFrame t1 = new TenthFrame('X','6','3');

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

        assertEquals(g.getScore(), 285);
    }

    @Test
    public void scoring_tenth_frame_4() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
        TenthFrame t1 = new TenthFrame('6','/','X');

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

        assertEquals(g.getScore(), 276);
    }

    @Test
    public void scoring_tenth_frame_5() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
        TenthFrame t1 = new TenthFrame('6','/','6');

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

        assertEquals(g.getScore(), 272);
    }

    @Test
    public void scoring_tenth_frame_6() {
        Frame f1 = new Frame('X',' ');
        Frame f2 = new Frame('X',' ');
        Frame f3 = new Frame('X',' ');
        Frame f4 = new Frame('X',' ');
        Frame f5 = new Frame('X',' ');
        Frame f6 = new Frame('X',' ');
        Frame f7 = new Frame('X',' ');
        Frame f8 = new Frame('X',' ');
        Frame f9 = new Frame('X',' ');
        TenthFrame t1 = new TenthFrame('6','3',' ');

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

        assertEquals(g.getScore(), 264);
    }
}
