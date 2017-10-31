package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScoreKeeping extends AppCompatActivity {
    private int frameCount = 0;
    Integer prevSingleMade = 0;
    Integer prevSingleLeft = 0;
    Integer prevSplitMade = 0;
    Integer prevSplitLeft = 0;
    Integer prevMultiMade = 0;
    Integer prevMultiLeft = 0;
    Integer prevStrikes = 0;
    Integer prevTotal = 0;
    Integer numGames = 0;
    Integer ballsThrown = 0;
    Integer highScore = 0;
    Integer prevFilled = 0;
    boolean[] split = {false,false,false,false,false,false,false,false,false,false};

    public DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);
        DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra("username"));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                prevSingleMade = Integer.parseInt(dataSnapshot.child("singleMade").getValue().toString());
                prevSingleLeft = Integer.parseInt(dataSnapshot.child("singleLeft").getValue().toString());
                prevSplitMade = Integer.parseInt(dataSnapshot.child("splitMade").getValue().toString());
                prevSplitLeft = Integer.parseInt(dataSnapshot.child("splitLeft").getValue().toString());
                prevMultiMade = Integer.parseInt(dataSnapshot.child("multiMade").getValue().toString());
                prevMultiLeft = Integer.parseInt(dataSnapshot.child("multiLeft").getValue().toString());
                prevStrikes = Integer.parseInt(dataSnapshot.child("numStrikes").getValue().toString());
                prevTotal = Integer.parseInt(dataSnapshot.child("cumulativeScore").getValue().toString());
                numGames = Integer.parseInt(dataSnapshot.child("numGames").getValue().toString());
                ballsThrown = Integer.parseInt(dataSnapshot.child("ballsThrown").getValue().toString());
                highScore = Integer.parseInt(dataSnapshot.child("highScore").getValue().toString());
                prevFilled = Integer.parseInt(dataSnapshot.child("filledFrames").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView header = (TextView) findViewById(R.id.header);
        Intent i = getIntent();
        String headerText = "New game for: " + i.getStringExtra("username");
        header.setText(headerText);

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
                if(frameCount == 19 && !(tvs[18].getText().equals("X")))
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

        bSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                split[frameCount/2] = !split[frameCount/2];
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
                final int singleLeft = g.getSinglePinLeft();
                final int singleMade = g.getSinglePinMade();
                final int splitLeft = g.getSplitLeft();
                final int splitMade = g.getSplitMade();
                final int multiLeft = g.getMultiLeft();
                final int multiMade = g.getMultiMade();
                final int newBallsThrown = g.getBallsThrown();
                final int strike = g.getStrike();
                final int scoreTemp = g.setScore();
                final int filledFrames = g.getFilledFrame();

                prevSingleLeft += singleLeft;
                prevSingleMade += singleMade;
                prevSplitLeft += splitLeft;
                prevSplitMade += splitMade;
                prevMultiLeft += multiLeft;
                prevMultiMade += multiMade;
                prevStrikes += strike;
                prevTotal += scoreTemp;
                prevFilled += filledFrames;
                numGames++;
                ballsThrown += newBallsThrown;
                //update high score
                if (scoreTemp > highScore)
                    highScore = scoreTemp;
                //update average score
                Integer avg = (int)((double)prevTotal/(double)numGames);
                String avgTemp = avg.toString();
                avgTemp = avgTemp.substring(0,Math.min(5,avgTemp.length()));
                //update filled frame percentage
                Double filledpct = (double)prevFilled/(double)(numGames*10);
                filledpct *= 100;
                String filledpctTemp = filledpct.toString();
                filledpctTemp = filledpctTemp.substring(0,Math.min(5,filledpctTemp.length()));
                //update single pin percentage
                Double singlePct = (double)prevSingleMade/(double)prevSingleLeft;
                singlePct *= 100;
                String singleTemp = singlePct.toString();
                singleTemp = singleTemp.substring(0,Math.min(5,singleTemp.length()));
                //update strike percentage
                Double strikePct = (double)prevStrikes/(double)ballsThrown;
                strikePct *= 100;
                String strikeTemp = strikePct.toString();
                strikeTemp = strikeTemp.substring(0,Math.min(5,strikeTemp.length()));

                DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra("username"));

                ref.child("singleLeft").setValue(prevSingleLeft.toString());
                ref.child("singleMade").setValue(prevSingleMade.toString());
                ref.child("splitLeft").setValue(prevSplitLeft.toString());
                ref.child("splitMade").setValue(prevSplitMade.toString());
                ref.child("multiLeft").setValue(prevMultiLeft.toString());
                ref.child("multiMade").setValue(prevMultiMade.toString());
                ref.child("numStrikes").setValue(prevStrikes.toString());
                ref.child("cumulativeScore").setValue(prevTotal.toString());
                ref.child("numGames").setValue(numGames.toString());
                ref.child("ballsThrown").setValue(ballsThrown.toString());
                ref.child("filledFrames").setValue(prevFilled.toString());
                ref.child("highScore").setValue(highScore.toString());
                ref.child("avgScore").setValue(avgTemp);
                ref.child("filledPercentage").setValue(filledpctTemp);
                ref.child("singlePinPercentage").setValue(singleTemp);
                ref.child("strikePercentage").setValue(strikeTemp);



                String out = scoreTemp + "";
                score.setText(out);
                final Toast toast2 = Toast.makeText(getApplicationContext(),"Statistics added",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                toast2.show();
            }
        });

    }
}
