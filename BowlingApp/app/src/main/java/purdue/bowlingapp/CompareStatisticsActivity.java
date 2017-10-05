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

    TextView currUsernameText;
    TextView compUsernameText;

    TextView currAverageScore;
    TextView currBestScore;
    // etc.

    TextView compAverageScore;
    TextView compBestScore;
    // etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_statistics);


        currUsernameText = (TextView) findViewById(R.id.currUsernameText);
        currAverageScore = (TextView) findViewById(R.id.currAverageScore);
        currBestScore = (TextView) findViewById(R.id.currBestScore);
        // etc.

        compUsernameText = (TextView) findViewById(R.id.compUsernameText);
        compAverageScore = (TextView) findViewById(R.id.compAverageScore);
        compBestScore = (TextView) findViewById(R.id.compBestScore);
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
                currAverageScore.setText(avgScore);
                String bestScore = dataSnapshot.child("bestScore").getValue().toString();
                currBestScore.setText(bestScore);
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
                compAverageScore.setText(avgScore);
                String bestScore = dataSnapshot.child("bestScore").getValue().toString();
                compBestScore.setText(bestScore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving comparing user's stats failed: " +
                        databaseError.getCode());
            }
        });
    }
}