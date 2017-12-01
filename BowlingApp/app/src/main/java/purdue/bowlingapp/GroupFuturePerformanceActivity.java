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

public class GroupFuturePerformanceActivity extends AppCompatActivity {

    String username;
    String groupName;

    EditText groupNameText;
    Button searchButton;
    TextView groupText;
    TextView futureText;
    TextView scoreText;
    TextView errorText;
    TextView scoreTextStatic;

    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_future_performance);

        groupNameText = (EditText) findViewById(R.id.groupNameText);
        searchButton = (Button) findViewById(R.id.searchButton);
        groupText = (TextView) findViewById(R.id.usernameText);
        futureText = (TextView) findViewById(R.id.futureText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        errorText = (TextView) findViewById(R.id.errorText);
        scoreTextStatic = (TextView) findViewById(R.id.scoreTextStatic);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        String blankText = "";
        groupText.setText(blankText);
        futureText.setText(blankText);
        errorText.setText(blankText);
        scoreTextStatic.setText(blankText);
        scoreText.setText(blankText);
    }


    public void lookupGroupStats(View view) {
        clearScreen();

        groupNameText = (EditText) findViewById(R.id.groupNameText);
        searchButton = (Button) findViewById(R.id.searchButton);
        groupText = (TextView) findViewById(R.id.usernameText);
        futureText = (TextView) findViewById(R.id.futureText);
        scoreText = (TextView) findViewById(R.id.scoreText);
        errorText = (TextView) findViewById(R.id.errorText);
        scoreTextStatic = (TextView) findViewById(R.id.scoreTextStatic);

        groupName = groupNameText.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalGames = 0;
                int totalScore = 0;

                if (dataSnapshot.child("groups").hasChild(groupName) && !(groupName.matches(""))) {

                    for (DataSnapshot iterator : dataSnapshot.child("groups").child(groupName).getChildren()) {
                        DataSnapshot x = (dataSnapshot.child("data").child(iterator.getKey()));

                        int[] games = new int[5];
                        games[0] = Integer.parseInt(x.child("games").child("1").getValue().toString());
                        games[1] = Integer.parseInt(x.child("games").child("2").getValue().toString());
                        games[2] = Integer.parseInt(x.child("games").child("3").getValue().toString());
                        games[3] = Integer.parseInt(x.child("games").child("4").getValue().toString());
                        games[4] = Integer.parseInt(x.child("games").child("5").getValue().toString());

                        for (int g : games) {
                            if (g != 0) {
                                totalGames++;
                                totalScore += g;
                            }
                        }
                    }

                    int futureScore = totalScore / totalGames;

                    String futureScoreString = Integer.toString(futureScore);
                    String futureTextString = "Based on past data, the estimated performance for the next game of a member of " + groupName + " is...";
                    String scoreTextStaticString = "Score:";
                    String groupNameTextString = "Group: " + groupName;
                    groupText.setText(groupNameTextString);
                    futureText.setText(futureTextString);
                    scoreText.setText(futureScoreString);
                    scoreTextStatic.setText(scoreTextStaticString);
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
            errorText.setText("Error: No username input.");
        }
        else if (groupName.matches("2")) {
            errorText.setText("Error: You are not the group's owner.");
        }
        else if (groupName.matches("3")) {
            errorText.setText("Error: Username does not exist.");
        }
        else if (groupName.matches("4")) {
            errorText.setText("Error: User already in group.");
        }
        else if (groupName.matches("5")) {
            errorText.setText("Error: Cannot remove yourself from group.");
        }
        else {
            errorText.setText("Error: Group does not exist.");
        }
    }
    public void clearScreen() {
        String blankText = "";
        groupText.setText(blankText);
        futureText.setText(blankText);
        errorText.setText(blankText);
        scoreTextStatic.setText(blankText);
        scoreText.setText(blankText);
    }
}
