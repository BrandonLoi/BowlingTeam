package purdue.bowlingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ScoreKeeping extends AppCompatActivity {
    private int frameCount = 0;
    boolean[] split = {false,false,false,false,false,false,false,false,false,false};
    String player;
    ArrayList<Player> players;
    String[] usernameStrings;

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

        TextView header = (TextView) findViewById(R.id.header);
        Intent i = getIntent();
        String headerText = "New game for: " + i.getStringExtra("username");
        header.setText(headerText);

        final String type = i.getStringExtra("type");
        if(type.equals("0")) {
            usernameStrings = new String[1];
            usernameStrings[0] = "username";
            player = i.getStringExtra(usernameStrings[0]);
            players = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                players.add(new Player(player, null));
            }
        }
        else {
            usernameStrings = new String[5];
            usernameStrings[0] = "username";
            usernameStrings[1] = "username2";
            usernameStrings[2] = "username3";
            usernameStrings[3] = "username4";
            usernameStrings[4] = "username5";
            player = i.getStringExtra(usernameStrings[0]);
            String player2 = i.getStringExtra(usernameStrings[1]);
            String player3 = i.getStringExtra(usernameStrings[2]);
            String player4 = i.getStringExtra(usernameStrings[3]);
            String player5 = i.getStringExtra(usernameStrings[4]);
            players = new ArrayList<>();
            Player p1 = new Player(player, null);
            Player p2 = new Player(player2, null);
            Player p3 = new Player(player3, null);
            Player p4 = new Player(player4, null);
            Player p5 = new Player(player5, null);
            players.add(p1);
            players.add(p2);
            players.add(p3);
            players.add(p4);
            players.add(p5);
            players.add(p1);
            players.add(p2);
            players.add(p3);
            players.add(p4);
            players.add(p5);
        }




        if(type.equals("1")) {
            for (int j = 0; j < 5; j++) {
                DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra(usernameStrings[j]));
                final int j2 = j;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        players.get(j2).prevSingleMade = Integer.parseInt(dataSnapshot.child("singleMade").getValue().toString());
                        players.get(j2).prevSingleLeft = Integer.parseInt(dataSnapshot.child("singleLeft").getValue().toString());
                        players.get(j2).prevSplitMade = Integer.parseInt(dataSnapshot.child("splitMade").getValue().toString());
                        players.get(j2).prevSplitLeft = Integer.parseInt(dataSnapshot.child("splitLeft").getValue().toString());
                        players.get(j2).prevMultiMade = Integer.parseInt(dataSnapshot.child("multiMade").getValue().toString());
                        players.get(j2).prevMultiLeft = Integer.parseInt(dataSnapshot.child("multiLeft").getValue().toString());
                        players.get(j2).prevStrikes = Integer.parseInt(dataSnapshot.child("numStrikes").getValue().toString());
                        players.get(j2).prevTotal = Integer.parseInt(dataSnapshot.child("cumulativeScore").getValue().toString());
                        players.get(j2).numberGames = Integer.parseInt(dataSnapshot.child("numGames").getValue().toString());
                        players.get(j2).ballsThrown = Integer.parseInt(dataSnapshot.child("ballsThrown").getValue().toString());
                        players.get(j2).highestScore = Integer.parseInt(dataSnapshot.child("highScore").getValue().toString());
                        players.get(j2).prevFilled = Integer.parseInt(dataSnapshot.child("filledFrames").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        else {
                DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra(usernameStrings[0]));
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        players.get(0).prevSingleMade = Integer.parseInt(dataSnapshot.child("singleMade").getValue().toString());
                        players.get(0).prevSingleLeft = Integer.parseInt(dataSnapshot.child("singleLeft").getValue().toString());
                        players.get(0).prevSplitMade = Integer.parseInt(dataSnapshot.child("splitMade").getValue().toString());
                        players.get(0).prevSplitLeft = Integer.parseInt(dataSnapshot.child("splitLeft").getValue().toString());
                        players.get(0).prevMultiMade = Integer.parseInt(dataSnapshot.child("multiMade").getValue().toString());
                        players.get(0).prevMultiLeft = Integer.parseInt(dataSnapshot.child("multiLeft").getValue().toString());
                        players.get(0).prevStrikes = Integer.parseInt(dataSnapshot.child("numStrikes").getValue().toString());
                        players.get(0).prevTotal = Integer.parseInt(dataSnapshot.child("cumulativeScore").getValue().toString());
                        players.get(0).numberGames = Integer.parseInt(dataSnapshot.child("numGames").getValue().toString());
                        players.get(0).ballsThrown = Integer.parseInt(dataSnapshot.child("ballsThrown").getValue().toString());
                        players.get(0).highestScore = Integer.parseInt(dataSnapshot.child("highScore").getValue().toString());
                        players.get(0).prevFilled = Integer.parseInt(dataSnapshot.child("filledFrames").getValue().toString());
                        if(!dataSnapshot.hasChild("games")) {
                            dataSnapshot.child("games").child("1").getRef().setValue("0");
                            dataSnapshot.child("games").child("2").getRef().setValue("0");
                            dataSnapshot.child("games").child("3").getRef().setValue("0");
                            dataSnapshot.child("games").child("4").getRef().setValue("0");
                            dataSnapshot.child("games").child("5").getRef().setValue("0");
                            dataSnapshot.child("games").child("set").getRef().setValue("0");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }


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
                int i = 0;
                for(; i < frames.size(); i++) {
                    Frame f = frames.get(i);
                    if(f.getFirstThrow() == '9') players.get(i).prevSingleLeft++;
                    if(f.getFirstThrow() == '9' && f.getSecondThrow() == '/') players.get(i).prevSingleMade++;
                    if(f.isSplit()) players.get(i).prevSplitLeft++;
                    if(f.isSplit() && f.getSecondThrow() == '/') players.get(i).prevSplitMade++;
                    if(f.isMakeable() && f.getFirstThrow() != 'X' && f.getFirstThrow() != '9') players.get(i).prevMultiLeft++;
                    if(f.isMakeable() && f.getFirstThrow() != 'X' && f.getFirstThrow() != '9' && f.getSecondThrow() == '/') players.get(i).prevMultiMade++;
                    if(f.getFirstThrow() == 'X' || f.getSecondThrow() == '/') players.get(i).prevFilled++;
                    if(f.getFirstThrow() == 'X') players.get(i).prevStrikes++;
                    players.get(i).ballsThrown++;
                    if(f.getFirstThrow() != 'X') players.get(i).ballsThrown++;
                }
                TenthFrame tenth = g.getTenth();
                if(tenth.getFirstThrow() == '9' || tenth.getSecondThrow() == '9') players.get(i).prevSingleLeft++;
                if(tenth.getFirstThrow() == '9' && tenth.getSecondThrow() == '/' || tenth.getSecondThrow() == '9' && tenth.getThirdThrow() == '/') players.get(i).prevSingleMade++;
                if(tenth.isSplit()) players.get(i).prevSplitLeft++;
                if(tenth.isSplit() && tenth.getSecondThrow() == '/' || tenth.getThirdThrow() == '/') players.get(i).prevSplitMade++;
                if(tenth.isMakeable()) players.get(i).prevMultiLeft++;
                if(tenth.isMakeable() && (tenth.getSecondThrow() != '/' || tenth.getThirdThrow() != '/') && (tenth.getFirstThrow() != '9' || tenth.getSecondThrow() != '9')) players.get(i).prevMultiMade++;
                if(tenth.getFirstThrow() == 'X' || tenth.getSecondThrow() == '/') players.get(i).prevFilled++;
                if(tenth.getFirstThrow() == 'X') players.get(i).prevStrikes++;
                if(tenth.getSecondThrow() == 'X') players.get(i).prevStrikes++;
                if(tenth.getThirdThrow() == 'X') players.get(i).prevStrikes++;
                players.get(i).ballsThrown += 2;
                if(tenth.getFirstThrow() == 'X' || tenth.getSecondThrow() == '/') players.get(i).ballsThrown++;

                Integer scoreTemp = g.setScore();

                if(type.equals("0")) {
                    //DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra(usernameStrings[0])).child("games");
                    //ref.child("1").setValue(ref.child("2").toString());
                    //ref.child("2").setValue(ref.child("3").toString());
                    //ref.child("3").setValue(ref.child("4").toString());
                    //ref.child("4").setValue(ref.child("5").toString());
                    //ref.child("5").setValue(scoreTemp.toString());
                    players.get(0).prevTotal += scoreTemp;

                }

                final int singleLeft = g.getSinglePinLeft();
                final int singleMade = g.getSinglePinMade();
                final int splitLeft = g.getSplitLeft();
                final int splitMade = g.getSplitMade();
                final int multiLeft = g.getMultiLeft();
                final int multiMade = g.getMultiMade();
                final int newBallsThrown = g.getBallsThrown();
                final int strike = g.getStrike();
                final int scoreTemp2 = g.setScore();
                final int filledFrames = g.getFilledFrame();


                if (scoreTemp > players.get(0).highestScore && type.equals("0"))
                    players.get(0).highestScore = scoreTemp;
                //update average score
                String avgTemp = null;
                if(type.equals("0")) {
                    Integer avg = (int) ((double) players.get(0).prevTotal / (double) players.get(0).numberGames);
                    avgTemp = avg.toString();
                    avgTemp = avgTemp.substring(0, Math.min(5, avgTemp.length()));
                }

                int j = 0;
                for(Player player : players) {
                    if(j < 5) {
                        //update filled frame percentage
                        int totalFrames = (player.prevStrikes + (player.prevSplitLeft + player.prevSingleLeft + player.prevMultiLeft));
                        Double filledpct = (double) player.prevFilled / (double) (totalFrames);
                        filledpct *= 100;
                        String filledpctTemp = filledpct.toString();
                        filledpctTemp = filledpctTemp.substring(0, Math.min(5, filledpctTemp.length()));
                        //update single pin percentage
                        Double singlePct = (double) player.prevSingleMade / (double) player.prevSingleLeft;
                        singlePct *= 100;
                        String singleTemp = singlePct.toString();
                        singleTemp = singleTemp.substring(0, Math.min(5, singleTemp.length()));
                        if (player.prevSingleLeft == 0)
                            singleTemp = "-1";
                        //update strike percentage
                        Double strikePct = (double) player.prevStrikes / (double) (totalFrames);
                        strikePct *= 100;
                        String strikeTemp = strikePct.toString();
                        strikeTemp = strikeTemp.substring(0, Math.min(5, strikeTemp.length()));
                        DatabaseReference ref = mDatabase.child("data").child(getIntent().getStringExtra(usernameStrings[j]));
                        ref.child("singleLeft").setValue(player.prevSingleLeft.toString());
                        ref.child("singleMade").setValue(player.prevSingleMade.toString());
                        ref.child("splitLeft").setValue(player.prevSplitLeft.toString());
                        ref.child("splitMade").setValue(player.prevSplitMade.toString());
                        ref.child("multiLeft").setValue(player.prevMultiLeft.toString());
                        ref.child("multiMade").setValue(player.prevMultiMade.toString());
                        ref.child("numStrikes").setValue(player.prevStrikes.toString());
                        ref.child("cumulativeScore").setValue(player.prevTotal.toString());
                        ref.child("numGames").setValue(player.numberGames.toString());
                        ref.child("ballsThrown").setValue(player.ballsThrown.toString());
                        ref.child("filledFrames").setValue(player.prevFilled.toString());
                        ref.child("highScore").setValue(player.highestScore.toString());
                        if (avgTemp != null) ref.child("avgScore").setValue(avgTemp);
                        ref.child("filledPercentage").setValue(filledpctTemp);
                        ref.child("singlePinPercentage").setValue(singleTemp);
                        ref.child("strikePercentage").setValue(strikeTemp);
                        j++;
                    }
                }

                /*
                   Update live tournament stats
                */
                final DatabaseReference tournamentRef = mDatabase.child("liveTournament").child("totalStatistics");

                liveTournament.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            tournamentPrevTotal += scoreTemp2;
                            tournamentPrevFilled += filledFrames;
                            tournamentNumGames++;
                            tournamentBallsThrown += newBallsThrown;
                            //update high score
                            if (scoreTemp2 > tournamentHighScore)
                                tournamentHighScore = scoreTemp2;
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
                                System.out.println(players);
                                player = newusername;
                                for(int i = 0; i < 10 - (frameCount / 2); i++) {
                              //      players[i] = player;
                                }
                                System.out.println(players);
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
