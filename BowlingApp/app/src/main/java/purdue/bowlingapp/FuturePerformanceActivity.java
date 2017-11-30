package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FuturePerformanceActivity extends AppCompatActivity {


    TextView usernameText;
    TextView futureText;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_performance);

        usernameText = (TextView) findViewById(R.id.usernameText);
        futureText = (TextView) findViewById(R.id.futureText);
        scoreText = (TextView) findViewById(R.id.scoreText);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef = database.getReference("data");
        dataRef = dataRef.child(username);
        dataRef = dataRef.child("games");

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int[] games = new int[5];
                games[0] = Integer.parseInt(dataSnapshot.child("1").getValue().toString());
                games[1] = Integer.parseInt(dataSnapshot.child("2").getValue().toString());
                games[2] = Integer.parseInt(dataSnapshot.child("3").getValue().toString());
                games[3] = Integer.parseInt(dataSnapshot.child("4").getValue().toString());
                games[4] = Integer.parseInt(dataSnapshot.child("5").getValue().toString());

                int totalGames = 0;
                int totalScore = 0;
                for (int g : games) {
                    if (g != 0) {
                        totalGames++;
                        totalScore += g;
                    }
                }
                int futureScore = totalScore / totalGames;

                String futureScoreString = Integer.toString(futureScore);
                String futureTextString = "Based on past data, the estimated performance for " + username + "'s next game is...";
                usernameText.setText(username);
                futureText.setText(futureTextString);
                scoreText.setText(futureScoreString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The data lookup failed: " + databaseError.getCode());
            }
        });
    }
}