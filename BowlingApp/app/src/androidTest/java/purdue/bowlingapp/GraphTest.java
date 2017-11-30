package purdue.bowlingapp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Aaron on 11/30/2017.
 */

public class GraphTest {
    private int[] arr = {1,2,3,4,5};

    //essentially a copy of the algorithm that updates games in the database
    private void addGame(int in) {
        arr[0] = arr[1];
        arr[1] = arr[2];
        arr[2] = arr[3];
        arr[3] = arr[4];
        arr[4] = in;
    }

    @Test
    public void test_order() {
        addGame(6);
        assertEquals(arr[0],2);
        assertEquals(arr[1],3);
        assertEquals(arr[2],4);
        assertEquals(arr[3],5);
        assertEquals(arr[4],6);
    }

}
