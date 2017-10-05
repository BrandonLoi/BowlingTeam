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

    TextView usernameText;

    TextView averageScore;
    TextView bestScore;
    // etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);


        usernameText = (TextView) findViewById(R.id.usernameText);
        averageScore = (TextView) findViewById(R.id.averageScore);
        bestScore = (TextView) findViewById(R.id.bestScore);
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
                averageScore.setText(avgScore);
                String best = dataSnapshot.child("bestScore").getValue().toString();
                bestScore.setText(best);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving current user's stats failed: " +
                        databaseError.getCode());
            }
        });
    }
}