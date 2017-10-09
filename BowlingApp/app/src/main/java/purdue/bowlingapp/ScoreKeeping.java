package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreKeeping extends AppCompatActivity {
    private int frameCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);

        //Access all TextViews in the scorekeeping activity
        // NAMING CONVENTIONS: fXbY = frame X, ball Y
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

        //Access all buttons on the scorekeeping activity
        Button b1 = (Button) findViewById(R.id.b1);
        Button b2 = (Button) findViewById(R.id.b2);
        Button b3 = (Button) findViewById(R.id.b3);
        Button b4 = (Button) findViewById(R.id.b4);
        Button b5 = (Button) findViewById(R.id.b5);
        Button b6 = (Button) findViewById(R.id.b6);
        Button b7 = (Button) findViewById(R.id.b7);
        Button b8 = (Button) findViewById(R.id.b8);
        Button b9 = (Button) findViewById(R.id.b9);
        Button bX = (Button) findViewById(R.id.bX);
        Button b0 = (Button) findViewById(R.id.b0);
        Button bSpare = (Button) findViewById(R.id.bSpare);
        Button bSplit = (Button) findViewById(R.id.bSplit);
        Button bMakeable = (Button) findViewById(R.id.bMakeable);
        Button bLeft = (Button) findViewById(R.id.bLeft);
        Button bPocket = (Button) findViewById(R.id.bPocket);
        Button bRight = (Button) findViewById(R.id.bRight);
        Button done = (Button) findViewById(R.id.done);
        Button backspace = (Button) findViewById(R.id.backspace);
        Button clear = (Button) findViewById(R.id.clear);

        final Toast toast = Toast.makeText(getApplicationContext(),"Please fill all frames",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);

        final TextView[] tvs = {f1b1,f1b2,f2b1,f2b2,f3b1,f3b2,f4b1,f4b2,f5b1,f5b2,f6b1,f6b2,f7b1,f7b2,f8b1,f8b2,f9b1,f9b2,f10b1,f10b2,f10b3};

        //make sure text views are clear when activity is launched
        for (TextView t : tvs) {
            t.setText(" ");
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("1");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 1 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("1");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount >= tvs.length)
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("2");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 2 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("2");
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("3");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 3 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("3");
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("4");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 4 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("4");
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("5");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 5 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("5");
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("6");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 6 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("6");
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("7");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 7 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("7");
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("8");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 8 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("8");
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount%2 == 1 || (frameCount == 20 && tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))) {
                    if (frameCount == 19 && tvs[18].getText().equals("X")) {
                        tvs[frameCount++].setText("9");
                        return;
                    }
                    int prevScore = Integer.parseInt(tvs[frameCount - 1].getText().toString());
                    if (prevScore + 9 > 9) {
                        tvs[frameCount++].setText("/");
                        return;
                    }
                }
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("9");
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount < tvs.length)
                    tvs[frameCount++].setText("0");
            }
        });

        bX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 20 && !(tvs[18].getText().equals("X") || tvs[19].getText().equals("/")))
                    return;
                if (frameCount < tvs.length - 3 && frameCount%2 == 0) {
                    tvs[frameCount++].setText("X");
                    tvs[frameCount++].setText(" ");
                } else if (frameCount < tvs.length && frameCount >= tvs.length -3) {
                    tvs[frameCount++].setText("X");
                }
            }
        });

        bSpare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount >= tvs.length)
                    return;
                if (frameCount == 19 && tvs[18].getText().equals("X"))
                    return;
                if (frameCount < tvs.length && (frameCount%2 == 1 || (tvs[18].getText().equals("X") && !tvs[19].getText().equals("X"))))
                    tvs[frameCount++].setText("/");
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (frameCount == 0)
                    return;
                if (tvs[frameCount - 1].getText().equals(" ")) {
                    frameCount--;
                    tvs[--frameCount].setText(" ");
                } else {
                    tvs[--frameCount].setText(" ");
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameCount = 0;
                for (TextView t : tvs)
                    t.setText(" ");
                score.setText(" ");
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                if (frameCount < tvs.length - 1) {
                    toast.show();
                    return;
                }
                if (frameCount == tvs.length - 1) {
                    if (!(tvs[18].getText().equals("X") || tvs[19].getText().equals("/"))) {
                        f10 = new TenthFrame(tvs[tvs.length - 3].getText().charAt(0),tvs[tvs.length - 2].getText().charAt(0));
                        setTenth = true;
                    } else {
                        toast.show();
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

                int scoreTemp = g.setScore();
                String out = scoreTemp + "";

                score.setText(out);

            }
        });

    }
}
