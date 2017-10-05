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
    TextView sparePc;
    // etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        usernameText = (TextView) findViewById(R.id.usernameText);
        averageScore = (TextView) findViewById(R.id.averageScore);
        highScore = (TextView) findViewById(R.id.highScore);
        strikePc = (TextView) findViewById(R.id.strikePercentage);
        sparePc = (TextView) findViewById(R.id.sparePercentage);
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
                String strikeScore = dataSnapshot.child("strikePercentage").getValue().toString();
                if (strikeScore.equals("-1")) {
                    strikePc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(strikeScore.trim());
                        d *= 100;
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
                        d *= 100;
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage + "%";
                    sparePc.setText(percentageString);
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