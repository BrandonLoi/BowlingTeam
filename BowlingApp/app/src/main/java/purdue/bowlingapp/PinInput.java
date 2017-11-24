package purdue.bowlingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PinInput extends AppCompatActivity {
    private int frameCount = 0;
    private String[] pinsLeft = new String[21];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_input);
        TextView f1b1 = (TextView) findViewById(R.id.f1FirstBall);
        TextView f1b2 = (TextView) findViewById(R.id.f1SecondBall);
        TextView f2b1 = (TextView) findViewById(R.id.f2FirstBall);
        TextView f2b2 = (TextView) findViewById(R.id.f2SecondBall);
        TextView f3b1 = (TextView) findViewById(R.id.f3FirstBall);
        TextView f3b2 = (TextView) findViewById(R.id.f3SecondBall);
        TextView f4b1 = (TextView) findViewById(R.id.f4FirstBall);
        TextView f4b2 = (TextView) findViewById(R.id.f4SecondBall);
        TextView f5b1 = (TextView) findViewById(R.id.f5FirstBall);
        TextView f5b2 = (TextView) findViewById(R.id.f5SecondBall);
        TextView f6b1 = (TextView) findViewById(R.id.f6FirstBall);
        TextView f6b2 = (TextView) findViewById(R.id.f6SecondBall);
        TextView f7b1 = (TextView) findViewById(R.id.f7FirstBall);
        TextView f7b2 = (TextView) findViewById(R.id.f7SecondBall);
        TextView f8b1 = (TextView) findViewById(R.id.f8FirstBall);
        TextView f8b2 = (TextView) findViewById(R.id.f8SecondBall);
        TextView f9b1 = (TextView) findViewById(R.id.f9FirstBall);
        TextView f9b2 = (TextView) findViewById(R.id.f9SecondBall);
        TextView f10b1 = (TextView) findViewById(R.id.f10FirstBall);
        TextView f10b2 = (TextView) findViewById(R.id.f10SecondBall);
        TextView f10b3 = (TextView) findViewById(R.id.f10ThirdBall);
        final TextView score = (TextView) findViewById(R.id.score);

        final TextView[] tvs = {f1b1,f1b2,f2b1,f2b2,f3b1,f3b2,f4b1,f4b2,f5b1,f5b2,f6b1,f6b2,f7b1,f7b2,f8b1,f8b2,f9b1,f9b2,f10b1,f10b2,f10b3};

        CheckBox one = (CheckBox) findViewById(R.id.onePin);
        CheckBox two = (CheckBox) findViewById(R.id.twoPin);
        CheckBox three = (CheckBox) findViewById(R.id.threePin);
        CheckBox four = (CheckBox) findViewById(R.id.fourPin);
        CheckBox five = (CheckBox) findViewById(R.id.fivePin);
        CheckBox six = (CheckBox) findViewById(R.id.sixPin);
        CheckBox seven = (CheckBox) findViewById(R.id.sevenPin);
        CheckBox eight = (CheckBox) findViewById(R.id.eightPin);
        CheckBox nine = (CheckBox) findViewById(R.id.ninePin);
        CheckBox ten = (CheckBox) findViewById(R.id.tenPin);

        final CheckBox[] pins = {one,two,three,four,five,six,seven,eight,nine,ten};

        Button submit = (Button) findViewById(R.id.submit);
        Button backspace = (Button) findViewById(R.id.backspace);
        Button done = (Button) findViewById(R.id.done);

        for (TextView t : tvs) {
            t.setText(" ");
        }



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= 21)
                    return;
                int count = 0;
                String temp = "";
                for(int i = 0 ; i < pins.length; i++) {
                    if(!pins[i].isChecked()) {
                        count++;
                        temp += "" + i + ",";
                    }
                }
                pinsLeft[frameCount] = temp;
                if(frameCount < 18) {
                    if(frameCount%2 == 0) {
                        if(count == 10) {
                            tvs[frameCount++].setText("X");
                            tvs[frameCount++].setText(" ");
                            for(CheckBox c : pins)
                                c.setChecked(false);
                        } else {
                            tvs[frameCount++].setText(""+count);
                        }
                    } else {
                        if(count == 10) {
                            tvs[frameCount++].setText("/");
                            for(CheckBox c : pins)
                                c.setChecked(false);
                        } else {
                            int diff = count - Integer.parseInt(tvs[frameCount-1].getText().toString());
                            if(diff < 0) {
                                return;
                            } else {
                                tvs[frameCount++].setText(""+diff);
                                for(CheckBox c : pins)
                                    c.setChecked(false);
                            }
                        }
                    }
                } else {
                    if(frameCount == 18) {
                        if(count == 10) {
                            tvs[frameCount++].setText("X");
                        } else {
                            tvs[frameCount++].setText(""+count);
                        }
                        return;
                    } else if (frameCount == 19) {
                        if(count==10) {
                            if(tvs[18].getText().toString().equals("X")) {
                                tvs[frameCount++].setText("X");
                            } else {
                                tvs[frameCount++].setText("/");
                            }
                        } else {
                            if (tvs[18].getText().toString().equals("X")) {
                                tvs[frameCount++].setText("" + count);
                                return;
                            }
                            int diff = count - Integer.parseInt(tvs[frameCount-1].getText().toString());
                            if(diff < 0) {
                                return;
                            } else {
                                tvs[frameCount++].setText(""+diff);
                                for(CheckBox c : pins)
                                    c.setChecked(false);
                            }
                        }
                        return;
                    } else if (frameCount == 20) {
                        if(!tvs[18].getText().toString().equals("X") && !tvs[19].getText().toString().equals("/"))
                            return;
                        else if (tvs[19].getText().toString().equals("X") || tvs[19].getText().toString().equals("/")) {
                            if(count == 10) {
                                tvs[frameCount++].setText("X");
                                return;
                            }
                            tvs[frameCount++].setText(""+count);
                            return;
                        } else {
                            if(count == 10) {
                                tvs[frameCount++].setText("/");
                                return;
                            }
                            int diff = count - Integer.parseInt(tvs[frameCount-1].getText().toString());
                            if(diff < 0) {
                                return;
                            } else {
                                tvs[frameCount++].setText("" + diff);
                                for (CheckBox c : pins)
                                    c.setChecked(false);
                            }
                        }
                    }
                }
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount == 0)
                    return;
                if(frameCount%2==0 && !tvs[frameCount-1].getText().toString().equals(" ")) {
                    for(CheckBox c : pins)
                        c.setChecked(true);
                    StringTokenizer st = new StringTokenizer(pinsLeft[frameCount-2],",");
                    while(st.hasMoreElements()) {
                        pins[Integer.parseInt(st.nextToken())].setChecked(false);
                    }
                } else {
                    for(CheckBox c : pins)
                        c.setChecked(false);
                }
                if (tvs[frameCount - 1].getText().equals(" ")) {
                    frameCount--;
                    tvs[--frameCount].setText(" ");
                } else {
                    tvs[--frameCount].setText(" ");
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(frameCount < 20)
                    return;
                boolean setTenth = false;
                Frame f1;
                Frame f2;
                Frame f3;
                Frame f4;
                Frame f5;
                Frame f6;
                Frame f7;
                Frame f8;
                Frame f9;
                TenthFrame f10 = null;
                if (frameCount == tvs.length - 1) {
                    if (!(tvs[18].getText().equals("X") || tvs[19].getText().equals("/"))) {
                        f10 = new TenthFrame(tvs[tvs.length - 3].getText().charAt(0),tvs[tvs.length - 2].getText().charAt(0));
                        setTenth = true;
                    } else {
                        return;
                    }
                }
                f1 = new Frame(tvs[0].getText().charAt(0),tvs[1].getText().charAt(0));
                f2 = new Frame(tvs[2].getText().charAt(0),tvs[3].getText().charAt(0));
                f3 = new Frame(tvs[4].getText().charAt(0),tvs[5].getText().charAt(0));
                f4 = new Frame(tvs[6].getText().charAt(0),tvs[7].getText().charAt(0));
                f5 = new Frame(tvs[8].getText().charAt(0),tvs[9].getText().charAt(0));
                f6 = new Frame(tvs[10].getText().charAt(0),tvs[11].getText().charAt(0));
                f7 = new Frame(tvs[12].getText().charAt(0),tvs[13].getText().charAt(0));
                f8 = new Frame(tvs[14].getText().charAt(0),tvs[15].getText().charAt(0));
                f9 = new Frame(tvs[16].getText().charAt(0),tvs[17].getText().charAt(0));
                if (!setTenth)
                    f10 = new TenthFrame(tvs[18].getText().charAt(0),tvs[19].getText().charAt(0),tvs[20].getText().charAt(0));
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
                if (f10 == null) {
                    score.setText("Something went wrong... very wrong");
                    return;
                }
                Game g = new Game(frames, f10);
                final int scoreTemp = g.setScore();
                String out = scoreTemp + "";
                score.setText(out);
                return;
            }
        });

    }
}

