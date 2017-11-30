package purdue.bowlingapp;

import android.widget.CheckBox;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aaron on 11/30/2017.
 */

public class splitTest {
    final Graph graph = new Graph(10);
    Boolean one = false;
    Boolean two = false;
    Boolean three = false;
    Boolean four = false;
    Boolean five = false;
    Boolean six = false;
    Boolean seven = false;
    Boolean eight = false;
    Boolean nine = false;
    Boolean ten = false;

    /*
         O   X   X   O
           X   X   X
             X   X
               X

         Seven-ten split, result should be true

     */

    @Test
    public void test_split() {
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,4);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
        graph.addEdge(1,7);
        graph.addEdge(2,4);
        graph.addEdge(2,5);
        graph.addEdge(2,8);
        graph.addEdge(3,6);
        graph.addEdge(3,7);
        graph.addEdge(4,7);
        graph.addEdge(4,8);
        graph.addEdge(5,8);
        graph.addEdge(5,9);

        Boolean[] bools = {one,two,three,four,five,six,true,eight,nine,true};

        boolean result = false;

        if(!bools[0]) {
            boolean split = false;
            DepthFirstSearch dfs = new DepthFirstSearch(graph,bools);
            for(int i = 0 ; i < bools.length; i++) {
                if(bools[i] && !dfs.hasPathTo(i)) {
                    split = true;
                }
            }
            if(split) {
                result = true;
            }
        }

        assertEquals(true,result);

    }

        /*
         X   X   X   O
           X   X   O
             X   X
               X

         Six-ten spare, not a split, result should be false

     */

    @Test
    public void test_no_split() {
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,4);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
        graph.addEdge(1,7);
        graph.addEdge(2,4);
        graph.addEdge(2,5);
        graph.addEdge(2,8);
        graph.addEdge(3,6);
        graph.addEdge(3,7);
        graph.addEdge(4,7);
        graph.addEdge(4,8);
        graph.addEdge(5,8);
        graph.addEdge(5,9);

        Boolean[] bools = {one,two,three,four,five,true,seven,eight,nine,true};

        boolean result = false;

        if(!bools[0]) {
            boolean split = false;
            DepthFirstSearch dfs = new DepthFirstSearch(graph,bools);
            for(int i = 0 ; i < bools.length; i++) {
                if(bools[i] && !dfs.hasPathTo(i)) {
                    split = true;
                }
            }
            if(split) {
                result = true;
            }
        }

        assertEquals(false,result);

    }

        /*
         X   X   X   O
           X   X   X
             X   X
               O

         One-ten spare, not a split, result should be false

     */

    @Test
    public void test_washout() {
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(0,4);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
        graph.addEdge(1,7);
        graph.addEdge(2,4);
        graph.addEdge(2,5);
        graph.addEdge(2,8);
        graph.addEdge(3,6);
        graph.addEdge(3,7);
        graph.addEdge(4,7);
        graph.addEdge(4,8);
        graph.addEdge(5,8);
        graph.addEdge(5,9);
        seven = true;
        ten = true;

        Boolean[] bools = {true,two,three,four,five,six,seven,eight,nine,true};

        boolean result = false;

        if(!bools[0]) {
            boolean split = false;
            DepthFirstSearch dfs = new DepthFirstSearch(graph,bools);
            for(int i = 0 ; i < bools.length; i++) {
                if(bools[i] && !dfs.hasPathTo(i)) {
                    split = true;
                }
            }
            if(split) {
                result = true;
            }
        }

        assertEquals(false,result);

    }
}
