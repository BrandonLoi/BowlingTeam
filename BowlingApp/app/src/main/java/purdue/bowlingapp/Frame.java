package purdue.bowlingapp;

/**
 * Created by Jimmy on 9/27/2017.
 */

public class Frame {

    private int firstThrow;
    private int secondThrow;


    public Frame(int firstThrow, int secondThrow) {
        if(firstThrow >= 0 && firstThrow <= 10) {
            this.firstThrow = firstThrow;
            if(firstThrow + secondThrow <= 10 && secondThrow >= 0) {
                this.secondThrow = secondThrow;
            }
        }
    }

    public int getFirstThrow() {
        return firstThrow;
    }

    public int getBothThrows() {
        return firstThrow + secondThrow;
    }
}
