package purdue.bowlingapp;

/**
 * Created by Jimmy on 9/27/2017.
 */

public class Frame {

    private char firstThrow;
    private char secondThrow;
    private boolean makeable = true;
    private boolean pocket = true;
    private boolean split = false;


    public Frame(char firstThrow, char secondThrow) {
        char first = validateInput(firstThrow);
        char second = validateInput(secondThrow);
        this.firstThrow = (first != ' ' && first != '/') ? firstThrow : '0';
        this.secondThrow = (second != ' ' && second != 'X') ? secondThrow : '0';
        if(this.firstThrow == 'X') this.secondThrow = ' ';
    }

    public Frame() {
        //default constructor for database
    }

    public void setFirstThrow(char c) {
        this.firstThrow = c;
    }

    public void setSecondThrow(char c) {
        this.secondThrow = c;
    }

    //TO DO: Add support for fouls

    private char validateInput(char input) {
        switch (input) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
            case 'X':
            case '/':
                return input;
            case 'x':
                return 'X';
            case '-':
                return '0';
            default:        //Error Handling, MAY NEED TO CHANGE
                return '0';

        }
    }

    //Called when last Frame was a spare
    public int getValFirstThrow() {
        if(this.firstThrow > 47 && this.firstThrow < 58) return this.firstThrow - 48;
        else if(this.firstThrow == 'X') return 10;
        return -1; //Error Handling, MAY NEED TO CHANGE
    }

    //Called when last frame was a strike
    public int getBothThrows() {
        if(this.firstThrow == 'X') return 11; //For double strike case, handled in Game class
        else if(this.secondThrow == '/') return 10;
        else if(this.firstThrow == ' ' || this.secondThrow == ' ') return -1; //Error Handling, MAY NEED TO CHANGE
        else return this.firstThrow + this.secondThrow - 96;
    }

    public void toggleSplit() {
        this.split = !this.split;
    }

    public void toggleMakeable() { this.makeable = !this.makeable; }

    public char getFirstThrow() {
        return firstThrow;
    }

    public char getSecondThrow() {
        return secondThrow;
    }

    public boolean isMakeable() {
        return makeable;
    }

    public boolean isPocket() {
        return pocket;
    }

    public boolean isSplit() {
        return split;
    }
}
