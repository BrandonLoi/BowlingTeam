package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class groupStatisticsActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    TextView playerNum;
    TextView avgScore;
    TextView highScore;
    TextView strikePer;
    TextView sparePer;
    TextView gameNum;
    TextView singlePinPer;
    TextView filledPer;
    TextView errorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_statistics);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        playerNum = (TextView) findViewById(R.id.nop);
        avgScore = (TextView) findViewById(R.id.avg);
        highScore = (TextView) findViewById(R.id.hs);
        gameNum = (TextView) findViewById(R.id.nog);
        singlePinPer = (TextView) findViewById(R.id.sinp);
        strikePer = (TextView) findViewById(R.id.stp);
        sparePer = (TextView) findViewById(R.id.spp);
        filledPer = (TextView) findViewById(R.id.fp);
        errorMessage = (TextView) findViewById(R.id.errorMsg);
    }

    public void lookupGroupStats(View view) {
        clearScreen();
        final EditText editText = (EditText) findViewById(R.id.groupInput);
        final String groupName = editText.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String playerN;
                String avgS;
                String highS;
                String gameN;
                String singlePP;
                String strikePr;
                String sparePr;
                String filledPr;

                double avgSL = 0;
                double highSL = 0;
                double gameNL = 0;
                double singlePPL = 0;
                double strikePrL = 0;
                double sparePrL = 0;
                double filledPrL = 0;
                if (dataSnapshot.child("groups").hasChild(groupName) && !(groupName.matches(""))) {
                    double divisor = dataSnapshot.child("groups").child(groupName).getChildrenCount();
                    playerN = Double.toString(dataSnapshot.child("groups").child(groupName).getChildrenCount());
                    playerNum.setText(playerN);

                    for (DataSnapshot iterator : dataSnapshot.child("groups").child(groupName).getChildren()) {
                        DataSnapshot x = (dataSnapshot.child("data").child(iterator.getKey()));
                        if (x.child("avgScore").getValue().equals("-1")) {

                        }
                        else {
                            avgSL = avgSL + Double.valueOf(x.child("avgScore").getValue().toString())  ;
                        }
                        if (x.child("highScore").getValue().equals("-1")) {

                        }
                        else {
                            highSL = highSL + Double.valueOf(x.child("highScore").getValue().toString());
                        }
                        if (x.child("numGames").getValue().equals("-1")) {

                        }
                        else {
                            gameNL = gameNL + Double.valueOf(x.child("numGames").getValue().toString());
                        }
                        if (x.child("singlePinPercentage").getValue().equals("-1")) {

                        }
                        else {
                            singlePPL = singlePPL + Double.valueOf(x.child("singlePinPercentage").getValue().toString());
                        }
                        if (x.child("strikePercentage").getValue().equals("-1")) {

                        }
                        else {
                            strikePrL = strikePrL + Double.valueOf(x.child("strikePercentage").getValue().toString());
                        }
                        if (x.child("sparePercentage").getValue().equals("-1")) {

                        }
                        else {
                            sparePrL = sparePrL + Double.valueOf(x.child("sparePercentage").getValue().toString());
                        }
                        if (x.child("filledPercentage").getValue().equals("-1")) {

                        }
                        else {
                            filledPrL = filledPrL + Double.valueOf(x.child("filledPercentage").getValue().toString());
                        }
                    }
                    avgSL = avgSL / divisor;
                    highSL = highSL / divisor;
                    gameNL = gameNL / divisor;
                    singlePPL = singlePPL / divisor;
                    strikePrL = strikePrL / divisor;
                    sparePrL = sparePrL / divisor;
                    filledPrL = filledPrL / divisor;

                    avgS = Double.toString(avgSL);
                    highS = Double.toString(highSL);
                    gameN = Double.toString(gameNL);
                    singlePP = Double.toString(singlePPL);
                    strikePr = Double.toString(strikePrL);
                    sparePr = Double.toString(sparePrL);
                    filledPr = Double.toString(filledPrL);

                    avgScore.setText(avgS);
                    highScore.setText(highS);
                    gameNum.setText(gameN);
                    singlePinPer.setText(singlePP);
                    strikePer.setText(strikePr);
                    sparePer.setText(sparePr);
                    filledPer.setText(filledPr);
                }
                else {
                    createFail(groupName);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void createFail(String groupName) {
        if (groupName.matches("1")) {
            errorMessage.setText("Error: No username input.");
        }
        else if (groupName.matches("2")) {
            errorMessage.setText("Error: You are not the group's owner.");
        }
        else if (groupName.matches("3")) {
            errorMessage.setText("Error: Username does not exist.");
        }
        else if (groupName.matches("4")) {
            errorMessage.setText("Error: User already in group.");
        }
        else if (groupName.matches("5")) {
            errorMessage.setText("Error: Cannot remove yourself from group.");
        }
        else {
            errorMessage.setText("Error: Group does not exist.");
        }
    }
    public void clearScreen() {
        playerNum.setText("");
        avgScore.setText("");
        highScore.setText("");
        gameNum.setText("");
        singlePinPer.setText("");
        strikePer.setText("");
        sparePer.setText("");
        filledPer.setText("");
        errorMessage.setText("");
    }
}
