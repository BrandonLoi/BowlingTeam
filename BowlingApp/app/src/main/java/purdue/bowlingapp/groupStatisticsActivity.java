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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_statistics);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        playerNum = (TextView) findViewById(R.id.playerNum);
        avgScore = (TextView) findViewById(R.id.avgScore);
        highScore = (TextView) findViewById(R.id.groupHighScore);
        gameNum = (TextView) findViewById(R.id.gameNum);
        singlePinPer = (TextView) findViewById(R.id.singlePinPer);
        strikePer = (TextView) findViewById(R.id.strikePer);
        sparePer = (TextView) findViewById(R.id.sparePer);
        filledPer = (TextView) findViewById(R.id.filledPer);
    }

    public void lookupGroupStats(View view) {
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

                long avgSL = 0;
                long highSL = 0;
                long gameNL = 0;
                long singlePPL = 0;
                long strikePrL = 0;
                long sparePrL = 0;
                long filledPrL = 0;
                if (dataSnapshot.child("groups").hasChild(groupName) && !(groupName.matches(""))) {
                    long divisor = dataSnapshot.child("groups").child(groupName).getChildrenCount();
                    playerN = Long.toString(dataSnapshot.child("groups").child(groupName).getChildrenCount());
                    playerNum.setText(playerN);

                    for (DataSnapshot iterator : dataSnapshot.child("groups").child(groupName).getChildren()) {
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("avgScore").getValue().equals("-1")) {

                        }
                        else {
                            avgSL = avgSL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("avgScore").getValue().toString());
                        }
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("highScore").getValue().equals("-1")) {

                        }
                        else {
                            highSL = highSL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("highScore").getValue().toString());
                        }
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("numGames").getValue().equals("-1")) {

                        }
                        else {
                            gameNL = gameNL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("numGames").getValue().toString());
                        }
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("singlePinPercentage").getValue().equals("-1")) {

                        }
                        else { //TODO Find out why this part doesn't work
                            singlePPL = singlePPL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("singlePinPercentage").getValue().toString());
                        }
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("strikePercentage").getValue().equals("-1")) {

                        }
                        else {
                            strikePrL = strikePrL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("strikePercentage").getValue().toString());
                        }
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("sparePercentage").getValue().equals("-1")) {

                        }
                        else {
                            sparePrL = sparePrL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("sparePercentage").getValue().toString());
                        }
                        if (dataSnapshot.child("data").child(iterator.getKey()).child("filledPercentage").getValue().equals("-1")) {

                        }
                        else {
                            filledPrL = filledPrL + Long.parseLong(dataSnapshot.child("data").child(iterator.getKey()).child("filledPercentage").getValue().toString());
                        }
                    }
                    avgSL = avgSL / divisor;
                    highSL = highSL / divisor;
                    gameNL = gameNL / divisor;
                    singlePPL = singlePPL / divisor;
                    strikePrL = strikePrL / divisor;
                    sparePrL = sparePrL / divisor;
                    filledPrL = filledPrL / divisor;

                    avgS = Long.toString(avgSL);
                    highS = Long.toString(highSL);
                    gameN = Long.toString(gameNL);
                    singlePP = Long.toString(singlePPL);
                    strikePr = Long.toString(strikePrL);
                    sparePr = Long.toString(sparePrL);
                    filledPr = Long.toString(filledPrL);

                    avgScore.setText(avgS);
                    highScore.setText(highS);
                    gameNum.setText(gameN);
                    singlePinPer.setText(singlePP);
                    strikePer.setText(strikePr);
                    sparePer.setText(sparePr);
                    filledPer.setText(filledPr);
                }
                else {
                    createFail("groupname");
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
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        if (groupName.matches("1")) {
            textView.setText("Error: No username input.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Error: You are not the group's owner.");
        }
        else if (groupName.matches("3")) {
            textView.setText("Error: Username does not exist.");
        }
        else if (groupName.matches("4")) {
            textView.setText("Error: User already in group.");
        }
        else if (groupName.matches("5")) {
            textView.setText("Error: Cannot remove yourself from group.");
        }
        else {
            textView.setText("Error: Group does not exist.");
        }
    }
}
