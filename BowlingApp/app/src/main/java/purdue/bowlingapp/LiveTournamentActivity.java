package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LiveTournamentActivity extends AppCompatActivity {

    Button beginLiveTournament;
    Button endLiveTournament;
    TextView tournamentInProgressText;
    TextView errorText;

    String live;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tournament);

        beginLiveTournament = (Button) findViewById(R.id.beginLiveTournamentButton);
        endLiveTournament = (Button) findViewById(R.id.endLiveTournamentButton);
        tournamentInProgressText = (TextView) findViewById(R.id.tournamentInProgressText);
        errorText = (TextView) findViewById(R.id.errorText);

        String blankText = "";
        errorText.setText(blankText);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currDataPath = "liveTournament";
        final DatabaseReference liveTournament = database.getReference(currDataPath);

        liveTournament.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                live = dataSnapshot.child("currentlyLive").getValue().toString();
                if (live.equals("0")) {
                    String tournamentText = "No";
                    tournamentInProgressText.setText(tournamentText);
                } else {
                    String tournamentText = "Yes";
                    tournamentInProgressText.setText(tournamentText);
                }

                beginLiveTournament.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (live.equals("0")) {
                            String blankText = "";
                            liveTournament.child("currentlyLive").setValue("1");
                            errorText.setText(blankText);
                        } else {
                            String error = "Error: Tournament already live!";
                            errorText.setText(error);
                        }
                    }
                });

                endLiveTournament.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (live.equals("1")) {
                            String blankText = "";
                            liveTournament.child("currentlyLive").setValue("0");
                            errorText.setText(blankText);
                        } else {
                            String error = "Error: No live tournament!";
                            errorText.setText(error);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
            }
        });
    }
}
