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

public class CompareStatisticsActivity extends AppCompatActivity {

    final String NOT_AVAILABLE = "N/A";

    TextView currUsernameText;
    TextView compUsernameText;

    TextView currAverageScore;
    TextView currHighScore;
    TextView currStrikePc;
    TextView currNumGames;
    TextView currSinglePinPc;
    TextView currSparePc;
    TextView currFilledPc;
    // etc.

    TextView compAverageScore;
    TextView compHighScore;
    TextView compStrikePc;
    TextView compNumGames;
    TextView compSinglePinPc;
    TextView compSparePc;
    TextView compFilledPc;
    // etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_statistics);


        currUsernameText = (TextView) findViewById(R.id.currUsernameText);
        currAverageScore = (TextView) findViewById(R.id.currAverageScore);
        currHighScore = (TextView) findViewById(R.id.currHighScore);
        currStrikePc = (TextView) findViewById(R.id.currStrikePercentage);
        currSparePc = (TextView) findViewById(R.id.currSparePercentage);
        currNumGames = (TextView) findViewById(R.id.currNumGames);
        currSinglePinPc = (TextView) findViewById(R.id.currSinglePinPercentage);
        currFilledPc = (TextView) findViewById(R.id.currFilledPercentage);
        // etc.

        compUsernameText = (TextView) findViewById(R.id.compUsernameText);
        compAverageScore = (TextView) findViewById(R.id.compAverageScore);
        compHighScore = (TextView) findViewById(R.id.compHighScore);
        compStrikePc = (TextView) findViewById(R.id.compStrikePercentage);
        compSparePc = (TextView) findViewById(R.id.compSparePercentage);
        compNumGames = (TextView) findViewById(R.id.compNumGames);
        compSinglePinPc = (TextView) findViewById(R.id.compSinglePinPercentage);
        compFilledPc = (TextView) findViewById(R.id.compFilledPercentage);
        // etc.


        Intent intent = getIntent();
        final String currUsername = intent.getStringExtra("currUsername");
        final String compUsername = intent.getStringExtra("compUsername");

        currUsernameText.setText(currUsername);
        compUsernameText.setText(compUsername);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String currDataPath = "data";
        DatabaseReference currDataRef = database.getReference(currDataPath);
        currDataRef = currDataRef.child(currUsername);

        String compDataPath = "data";
        DatabaseReference compDataRef = database.getReference(compDataPath);
        compDataRef = compDataRef.child(compUsername);

        /*
         * Retrieve current user's stats
         */
        currDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String avgScore = dataSnapshot.child("avgScore").getValue().toString();
                if (avgScore.equals("-1")) {
                    currAverageScore.setText(NOT_AVAILABLE);
                } else {
                    currAverageScore.setText(avgScore);
                }

                String highScore = dataSnapshot.child("highScore").getValue().toString();
                if (highScore.equals("-1")) {
                    currHighScore.setText(NOT_AVAILABLE);
                } else {
                    currHighScore.setText(highScore);
                }

                String numGames = dataSnapshot.child("numGames").getValue().toString();
                if (numGames.equals("-1")) {
                    currNumGames.setText(NOT_AVAILABLE);
                } else {
                    currNumGames.setText(numGames);
                }

                String strikeScore = dataSnapshot.child("strikePercentage").getValue().toString();
                if (strikeScore.equals("-1")) {
                    currStrikePc.setText(NOT_AVAILABLE);
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
                    currStrikePc.setText(percentageString);
                }

                String spareScore = dataSnapshot.child("sparePercentage").getValue().toString();
                if (spareScore.equals("-1")) {
                    currSparePc.setText(NOT_AVAILABLE);
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
                    currSparePc.setText(percentageString);
                }

                String singlePin = dataSnapshot.child("singlePinPercentage").getValue().toString();
                if (singlePin.equals("-1")) {
                    currSinglePinPc.setText(NOT_AVAILABLE);
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
                    currSinglePinPc.setText(percentageString);
                }

                String filledFrames = dataSnapshot.child("filledPercentage").getValue().toString();
                if (filledFrames.equals("-1")) {
                    currFilledPc.setText(NOT_AVAILABLE);
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
                    currFilledPc.setText(percentageString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving current user's stats failed: " +
                        databaseError.getCode());
            }
        });

        /*
         * Retrieve comparing user's stats
         */
        compDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String avgScore = dataSnapshot.child("avgScore").getValue().toString();
                if (avgScore.equals("-1")) {
                    compAverageScore.setText(NOT_AVAILABLE);
                } else {
                    compAverageScore.setText(avgScore);
                }

                String highScore = dataSnapshot.child("highScore").getValue().toString();
                if (highScore.equals("-1")) {
                    compHighScore.setText(NOT_AVAILABLE);
                } else {
                    compHighScore.setText(highScore);
                }

                String numGames = dataSnapshot.child("numGames").getValue().toString();
                if (numGames.equals("-1")) {
                    compNumGames.setText(NOT_AVAILABLE);
                } else {
                    compNumGames.setText(numGames);
                }

                String strikeScore = dataSnapshot.child("strikePercentage").getValue().toString();
                if (strikeScore.equals("-1")) {
                    compStrikePc.setText(NOT_AVAILABLE);
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
                    compStrikePc.setText(percentageString);
                }

                String spareScore = dataSnapshot.child("sparePercentage").getValue().toString();
                if (spareScore.equals("-1")) {
                    compSparePc.setText(NOT_AVAILABLE);
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
                    compSparePc.setText(percentageString);
                }

                String singlePin = dataSnapshot.child("singlePinPercentage").getValue().toString();
                if (singlePin.equals("-1")) {
                    compSinglePinPc.setText(NOT_AVAILABLE);
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
                    compSinglePinPc.setText(percentageString);
                }

                String filledFrames = dataSnapshot.child("filledPercentage").getValue().toString();
                if (filledFrames.equals("-1")) {
                    compFilledPc.setText(NOT_AVAILABLE);
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
                    compFilledPc.setText(percentageString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving comparing user's stats failed: " +
                        databaseError.getCode());
            }
        });
    }
}