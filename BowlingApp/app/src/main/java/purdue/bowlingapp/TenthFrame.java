package purdue.bowlingapp;

/**
 * Created by Aaron on 10/2/2017.
 */

public class TenthFrame {

    private char firstThrow;
    private char secondThrow;
    private char thirdThrow;

    public TenthFrame(char firstThrow, char secondThrow) {
        char first = validateInput(firstThrow);
        char second = validateInput(secondThrow);
        this.firstThrow = (first != ' ' && first != '/') ? firstThrow : ' ';
        this.secondThrow = second;
    }

    public TenthFrame(char firstThrow, char secondThrow, char thirdThrow) {
        char first = validateInput(firstThrow);
        char second = validateInput(secondThrow);
        char third = validateInput(thirdThrow);
        this.firstThrow = (first != ' ' && first != '/') ? firstThrow : ' ';
        this.secondThrow = second;
        this.thirdThrow = third;
    }

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
                return ' ';

        }
    }

    //Called when last Frame was a spare
    public int getFirstThrow() {
        if(this.firstThrow > 47 && this.firstThrow < 58) return this.firstThrow - 48;
        else if(this.firstThrow == 'X') return 10;
        return -1; //Error Handling, MAY NEED TO CHANGE
    }

    public int getSecondThrow() {
        if(this.secondThrow > 47 && this.secondThrow < 58) return this.secondThrow - 48;
        else if(this.secondThrow == 'X') return 10;
        else if (this.secondThrow == '/') return 11;
        return -1; //Error Handling, MAY NEED TO CHANGE
    }
    public int getThirdThrow() {
        if(this.thirdThrow > 47 && this.thirdThrow < 58) return this.thirdThrow - 48;
        else if(this.thirdThrow == 'X') return 10;
        else if (this.thirdThrow == '/') return 11;
        return -1; //Error Handling, MAY NEED TO CHANGE
    }


}
