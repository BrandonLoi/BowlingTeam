package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatisticsActivity extends AppCompatActivity {

    final String NOT_AVAILABLE = "N/A";

    TextView usernameText;

    TextView averageScore;
    TextView highScore;
    TextView strikePc;
    TextView numGames;
    TextView singlePinPc;
    TextView sparePc;
    TextView filledPc;
    // etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        usernameText = (TextView) findViewById(R.id.usernameText);
        averageScore = (TextView) findViewById(R.id.averageScore);
        highScore = (TextView) findViewById(R.id.highScore);
        numGames = (TextView) findViewById(R.id.numGames);
        singlePinPc = (TextView) findViewById(R.id.singlePinPercentage);
        strikePc = (TextView) findViewById(R.id.strikePercentage);
        sparePc = (TextView) findViewById(R.id.sparePercentage);
        filledPc = (TextView) findViewById(R.id.filledPercentage);
        // etc.

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        String headerText = "Statistics for " + username + ":";
        usernameText.setText(headerText);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String dataPath = "data";
        DatabaseReference dataRef = database.getReference(dataPath);
        dataRef = dataRef.child(username);

        /*
         * Retrieve current user's stats
         */
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String avgScore = dataSnapshot.child("avgScore").getValue().toString();
                if (avgScore.equals("-1")) {
                    averageScore.setText(NOT_AVAILABLE);
                } else {
                    averageScore.setText(avgScore);
                }

                String bestScore = dataSnapshot.child("highScore").getValue().toString();
                if (bestScore.equals("-1")) {
                    highScore.setText(NOT_AVAILABLE);
                } else {
                    highScore.setText(bestScore);
                }

                String numberOfGames = dataSnapshot.child("numGames").getValue().toString();
                if (numberOfGames.equals("-1")) {
                    numGames.setText(NOT_AVAILABLE);
                } else {
                    numGames.setText(numberOfGames);
                }

                String strikeScore = dataSnapshot.child("strikePercentage").getValue().toString();
                if (strikeScore.equals("-1")) {
                    strikePc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(strikeScore.trim());
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage + "%";
                    strikePc.setText(percentageString);
                }

                String spareScore = dataSnapshot.child("sparePercentage").getValue().toString();
                if (spareScore.equals("-1")) {
                    sparePc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(spareScore.trim());
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage + "%";
                    sparePc.setText(percentageString);
                }

                String singlePin = dataSnapshot.child("singlePinPercentage").getValue().toString();
                if (singlePin.equals("-1")) {
                    singlePinPc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(singlePin.trim());
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage + "%";
                    singlePinPc.setText(percentageString);
                }

                String filledFrames = dataSnapshot.child("filledPercentage").getValue().toString();
                if (filledFrames.equals("-1")) {
                    filledPc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(filledFrames.trim());
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage + "%";
                    filledPc.setText(percentageString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving current user's stats failed: " +
                        databaseError.getCode());
            }
        });
    }
}