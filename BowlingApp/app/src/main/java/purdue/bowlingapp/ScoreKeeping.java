package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

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
    Player player;
    ArrayList<Player> players;

    public DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    // Variables used in updating live tournament stats
    String live = "0";
    Integer tournamentPrevSingleMade;
    Integer tournamentPrevSingleLeft;
    Integer tournamentPrevSplitMade;
    Integer tournamentPrevSplitLeft;
    Integer tournamentPrevMultiMade;
    Integer tournamentPrevMultiLeft;
    Integer tournamentPrevStrikes;
    Integer tournamentPrevTotal;
    Integer tournamentNumGames;
    Integer tournamentBallsThrown;
    Integer tournamentHighScore;
    Integer tournamentPrevFilled;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String currDataPath = "liveTournament";
    final DatabaseReference liveTournament = database.getReference(currDataPath);
    final DatabaseReference tournamentStats = liveTournament.child("totalStatistics");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_keeping);


        /*
           Load live tournament statistics
         */
        tournamentStats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Determine if a live tournament is in progress, and if so, update live tournament stats
                liveTournament.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot liveTourney) {
                        // Set variable "live" to either 1 or 0
                        live = liveTourney.child("currentlyLive").getValue().toString();

                        if (live.equals("1")) {
                            // Initialize statistics variables
                            tournamentPrevSingleMade = Integer.parseInt(dataSnapshot.child("singleMade").getValue().toString());
                            tournamentPrevSingleLeft = Integer.parseInt(dataSnapshot.child("singleLeft").getValue().toString());
                            tournamentPrevSplitMade = Integer.parseInt(dataSnapshot.child("splitMade").getValue().toString());
                            tournamentPrevSplitLeft = Integer.parseInt(dataSnapshot.child("splitLeft").getValue().toString());
                            tournamentPrevMultiMade = Integer.parseInt(dataSnapshot.child("multiMade").getValue().toString());
                            tournamentPrevMultiLeft = Integer.parseInt(dataSnapshot.child("multiLeft").getValue().toString());
                            tournamentPrevStrikes = Integer.parseInt(dataSnapshot.child("numStrikes").getValue().toString());
                            tournamentPrevTotal = Integer.parseInt(dataSnapshot.child("cumulativeScore").getValue().toString());
                            tournamentNumGames = Integer.parseInt(dataSnapshot.child("numGames").getValue().toString());
                            tournamentBallsThrown = Integer.parseInt(dataSnapshot.child("ballsThrown").getValue().toString());
                            tournamentHighScore = Integer.parseInt(dataSnapshot.child("highScore").getValue().toString());
                            tournamentPrevFilled = Integer.parseInt(dataSnapshot.child("filledFrames").getValue().toString());
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving totalStats variable(s) failed: " + databaseError.getCode());
            }
        });


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

        final CheckBox practice = (CheckBox) findViewById(R.id.practice);

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
                if (practice.isChecked()) {
                    final int scoreTemp = g.setScore();
                    String out = scoreTemp + "";
                    score.setText(out);
                    return;
                }


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

                ArrayList<Player> players = g.getPlayers();

        //        for(int i = 0; i < singleLeft.length; i++) {
          //          if(singleLeft[i] == 1) players.get(i).singlePinLeft++;
            //    }
        //        for(int i = 0; i < singleMade.length; i++) {
         //           if(singleMade[i] == 1) players.get(i).singlePinMade++;
         //       }
         //       for(int i = 0; i < splitLeft.length; i++) {
         //           if(splitLeft[i] == 1) players.get(i).splitsLeft++;
         //       }
         //       for(int i = 0; i < splitMade.length; i++) {
         //           if(splitMade[i] == 1) players.get(i).splitsMade++;
         //       }
         //       for(int i = 0; i < splitMade.length; i++) {
         //           if(splitMade[i] == 1) players.get(i).multiPinsMade++;
         //       }
         //       for(int i = 0; i < multiLeft.length; i++) {
         //           if(multiLeft[i] == 1) players.get(i).multiPinsLeft++;
         //       }
         //       for(int i = 0; i < strike.length; i++) {
         //           if(strike[i] == 1) players.get(i).strikes++;
         //       }
          //      for(int i = 0; i < filledFrames.length; i++) {
         //           if(filledFrames[i] == 1) players.get(i).filledFramesCount++;
         //       }
          //      for(int i = 0; i < players.size(); i++) {
         //           players.get(i).newBallsThrown++;
         //       }
         //       players.get(0).scoreTemp = scoreTemp;

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
                if(prevSingleLeft == 0)
                    singleTemp = "-1";
                //update strike percentage
                Double strikePct = (double)prevStrikes/(double)ballsThrown;
                strikePct *= 100;
                String strikeTemp = strikePct.toString();
                strikeTemp = strikeTemp.substring(0,Math.min(5,strikeTemp.length()));

                DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra("username"));
          //      ArrayList<DatabaseReference> refs = new ArrayList<DatabaseReference>();
         //       for(int i = 0; i < players.size(); i++) {
         //           refs.add(mDatabase.child("data").child(players.get(i).getUsername()));
          //      }
         //       for(int i = 0; i < refs.size(); i++) {
          //          DatabaseReference ref = refs.get(i);
          //          ref.child("singleLeft").setValue(prevSingleLeft.toString());
          //          ref.child("singleMade").setValue(prevSingleMade.toString());
          //          ref.child("splitLeft").setValue(prevSplitLeft.toString());
          //          ref.child("splitMade").setValue(prevSplitMade.toString());
          //          ref.child("multiLeft").setValue(prevMultiLeft.toString());
           //         ref.child("multiMade").setValue(prevMultiMade.toString());
          //          ref.child("numStrikes").setValue(prevStrikes.toString());
          //          ref.child("cumulativeScore").setValue(prevTotal.toString());
          //          ref.child("numGames").setValue(numGames.toString());
          //          ref.child("ballsThrown").setValue(ballsThrown.toString());
         //           ref.child("filledFrames").setValue(prevFilled.toString());
         //           ref.child("highScore").setValue(highScore.toString());
          //          ref.child("avgScore").setValue(avgTemp);
         //           ref.child("filledPercentage").setValue(filledpctTemp);
         //           ref.child("singlePinPercentage").setValue(singleTemp);
         //           ref.child("strikePercentage").setValue(strikeTemp);
         //       }

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


                /*
                   Update live tournament stats
                */
                final DatabaseReference tournamentRef = mDatabase.child("liveTournament").child("totalStatistics");

                liveTournament.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot liveTourney) {
                        // Set variable "live" to either 1 or 0
                        live = liveTourney.child("currentlyLive").getValue().toString();

                        if (live.equals("1")) {
                            tournamentPrevSingleLeft += singleLeft;
                            tournamentPrevSingleMade += singleMade;
                            tournamentPrevSplitLeft += splitLeft;
                            tournamentPrevSplitMade += splitMade;
                            tournamentPrevMultiLeft += multiLeft;
                            tournamentPrevMultiMade += multiMade;
                            tournamentPrevStrikes += strike;
                            tournamentPrevTotal += scoreTemp;
                            tournamentPrevFilled += filledFrames;
                            tournamentNumGames++;
                            tournamentBallsThrown += newBallsThrown;
                            //update high score
                            if (scoreTemp > tournamentHighScore)
                                tournamentHighScore = scoreTemp;
                            //update average score
                            Integer tournamentAvg = (int)((double)tournamentPrevTotal/(double)tournamentNumGames);
                            String tournamentAvgTemp = tournamentAvg.toString();
                            tournamentAvgTemp = tournamentAvgTemp.substring(0,Math.min(5,tournamentAvgTemp.length()));
                            //update filled frame percentage
                            Double tournamentFilledpct = (double)tournamentPrevFilled/(double)(tournamentNumGames*10);
                            tournamentFilledpct *= 100;
                            String tournamentFilledpctTemp = tournamentFilledpct.toString();
                            tournamentFilledpctTemp = tournamentFilledpctTemp.substring(0,Math.min(5,tournamentFilledpctTemp.length()));
                            //update single pin percentage
                            Double tournamentSinglePct = (double)tournamentPrevSingleMade/(double)tournamentPrevSingleLeft;
                            tournamentSinglePct *= 100;
                            String tournamentSingleTemp = tournamentSinglePct.toString();
                            tournamentSingleTemp = tournamentSingleTemp.substring(0,Math.min(5,tournamentSingleTemp.length()));
                            if(tournamentPrevSingleLeft == 0)
                                tournamentSingleTemp = "-1";
                            //update strike percentage
                            Double tournamentStrikePct = (double)tournamentPrevStrikes/(double)tournamentBallsThrown;
                            tournamentStrikePct *= 100;
                            String tournamentStrikeTemp = tournamentStrikePct.toString();
                            tournamentStrikeTemp = tournamentStrikeTemp.substring(0,Math.min(5,tournamentStrikeTemp.length()));

                            tournamentRef.child("singleLeft").setValue(tournamentPrevSingleLeft.toString());
                            tournamentRef.child("singleMade").setValue(tournamentPrevSingleMade.toString());
                            tournamentRef.child("splitLeft").setValue(tournamentPrevSplitLeft.toString());
                            tournamentRef.child("splitMade").setValue(tournamentPrevSplitMade.toString());
                            tournamentRef.child("multiLeft").setValue(tournamentPrevMultiLeft.toString());
                            tournamentRef.child("multiMade").setValue(tournamentPrevMultiMade.toString());
                            tournamentRef.child("numStrikes").setValue(tournamentPrevStrikes.toString());
                            tournamentRef.child("cumulativeScore").setValue(tournamentPrevTotal.toString());
                            tournamentRef.child("numGames").setValue(tournamentNumGames.toString());
                            tournamentRef.child("ballsThrown").setValue(tournamentBallsThrown.toString());
                            tournamentRef.child("filledFrames").setValue(tournamentPrevFilled.toString());
                            tournamentRef.child("highScore").setValue(tournamentHighScore.toString());
                            tournamentRef.child("avgScore").setValue(tournamentAvgTemp);
                            tournamentRef.child("filledPercentage").setValue(tournamentFilledpctTemp);
                            tournamentRef.child("singlePinPercentage").setValue(tournamentSingleTemp);
                            tournamentRef.child("strikePercentage").setValue(tournamentStrikeTemp);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
                    }
                });


                String out = scoreTemp + "";
                score.setText(out);
                final Toast toast2 = Toast.makeText(getApplicationContext(),"Statistics added",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                toast2.show();
            }
        });


        Button sub = (Button) findViewById(R.id.sub);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Toast toast = Toast.makeText(getApplicationContext(),"Please input a valid user",Toast.LENGTH_SHORT);;
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                final EditText editText = (EditText) findViewById(R.id.editText2);
                final String newusername = editText.getText().toString();

                if(newusername.equals("")) {
                    toast.show();
                }
                else {
                    DatabaseReference myRef = mDatabase.child("users");
                    ValueEventListener listen = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChild(newusername)) {
                                toast.show();
                            }
                            else {
                                TextView header = (TextView) findViewById(R.id.header);
                                String headerText = "New game for: " + newusername;
                                header.setText(headerText);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Required, but we don't use. Leave blank
                        }
                    };
                    myRef.addListenerForSingleValueEvent(listen);
                }

            }
        });

    }

}
