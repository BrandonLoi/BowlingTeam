package purdue.bowlingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class PinInput extends AppCompatActivity {
    private int frameCount = 0;
    private String[] pinsLeft = new String[21];
    boolean[] splits = {false,false,false,false,false,false,false,false,false,false};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_input);

        final String username = getIntent().getStringExtra("username");

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference userData = mDatabase.child("data").child(username);
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild("games")) {
                    dataSnapshot.child("games").child("1").getRef().setValue("0");
                    dataSnapshot.child("games").child("2").getRef().setValue("0");
                    dataSnapshot.child("games").child("3").getRef().setValue("0");
                    dataSnapshot.child("games").child("4").getRef().setValue("0");
                    dataSnapshot.child("games").child("5").getRef().setValue("0");
                    dataSnapshot.child("games").child("set").getRef().setValue("0");
                    dataSnapshot.child("strikeCount").child("1").getRef().setValue("0");
                    dataSnapshot.child("strikeCount").child("2").getRef().setValue("0");
                    dataSnapshot.child("strikeCount").child("3").getRef().setValue("0");
                    dataSnapshot.child("strikeCount").child("4").getRef().setValue("0");
                    dataSnapshot.child("strikeCount").child("5").getRef().setValue("0");
                    dataSnapshot.child("strikeCount").child("set").getRef().setValue("0");
                    dataSnapshot.child("filledCount").child("1").getRef().setValue("0");
                    dataSnapshot.child("filledCount").child("2").getRef().setValue("0");
                    dataSnapshot.child("filledCount").child("3").getRef().setValue("0");
                    dataSnapshot.child("filledCount").child("4").getRef().setValue("0");
                    dataSnapshot.child("filledCount").child("5").getRef().setValue("0");
                    dataSnapshot.child("filledCount").child("set").getRef().setValue("0");
                    dataSnapshot.child("singleCount").child("1").getRef().setValue("0");
                    dataSnapshot.child("singleCount").child("2").getRef().setValue("0");
                    dataSnapshot.child("singleCount").child("3").getRef().setValue("0");
                    dataSnapshot.child("signleCount").child("4").getRef().setValue("0");
                    dataSnapshot.child("singleCount").child("5").getRef().setValue("0");
                    dataSnapshot.child("singleCount").child("set").getRef().setValue("0");
                } else {
                    dataSnapshot.child("games").child("set").getRef().setValue("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference userGames = userData.child("games");


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

        final CheckBox one = (CheckBox) findViewById(R.id.onePin);
        final CheckBox two = (CheckBox) findViewById(R.id.twoPin);
        final CheckBox three = (CheckBox) findViewById(R.id.threePin);
        final CheckBox four = (CheckBox) findViewById(R.id.fourPin);
        final CheckBox five = (CheckBox) findViewById(R.id.fivePin);
        final CheckBox six = (CheckBox) findViewById(R.id.sixPin);
        final CheckBox seven = (CheckBox) findViewById(R.id.sevenPin);
        final CheckBox eight = (CheckBox) findViewById(R.id.eightPin);
        final CheckBox nine = (CheckBox) findViewById(R.id.ninePin);
        final CheckBox ten = (CheckBox) findViewById(R.id.tenPin);

        final CheckBox[] pins = {one,two,three,four,five,six,seven,eight,nine,ten};

        Button submit = (Button) findViewById(R.id.submit);
        Button backspace = (Button) findViewById(R.id.backspace);
        Button done = (Button) findViewById(R.id.done);

        for (TextView t : tvs) {
            t.setText(" ");
        }

        final Graph graph = new Graph(10);
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

        final HashMap<CheckBox,Integer> map = new HashMap<>(10);
        map.put(one,0);
        map.put(two,1);
        map.put(three,2);
        map.put(four,3);
        map.put(five,4);
        map.put(six,5);
        map.put(seven,6);
        map.put(eight,7);
        map.put(nine,8);
        map.put(ten,9);

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
                            if(!one.isChecked() && count != 9) {
                                boolean split = false;
                                DepthFirstSearch dfs = new DepthFirstSearch(graph,pins);
                                for(CheckBox c : pins) {
                                    if(c.isChecked() && !dfs.hasPathTo(map.get(c))) {
                                        split = true;
                                    }
                                }
                                if(split) {
                                    tvs[frameCount - 1].setBackgroundColor(0xF2FF0000);
                                    splits[(frameCount-1)/2] = true;
                                }
                            }
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
                tvs[frameCount].setBackgroundColor(0x00FFFFFF);
                score.setText("");
                userGames.child("set").setValue("0");
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


                final Integer scoreTemp = g.setScore();

                userGames.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("set").getValue().toString().equals("0")) {
                            userGames.child("set").setValue("1");
                            userGames.child("1").setValue(dataSnapshot.child("2").getValue());
                            userGames.child("2").setValue(dataSnapshot.child("3").getValue());
                            userGames.child("3").setValue(dataSnapshot.child("4").getValue());
                            userGames.child("4").setValue(dataSnapshot.child("5").getValue());
                            userGames.child("5").setValue(scoreTemp.toString());
                            userGames.removeEventListener(this);
                        } else {
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                String out = scoreTemp + "";
                score.setText(out);
                return;
            }
        });

    }
}

