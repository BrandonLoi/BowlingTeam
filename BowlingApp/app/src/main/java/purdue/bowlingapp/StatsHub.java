package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsHub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_hub);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        Button editPlayerStats = (Button) findViewById(R.id.editStats);
        Button myStats = (Button) findViewById(R.id.myStats);
        Button compareStats = (Button) findViewById(R.id.compareStats);
        Button editStatsPermission = (Button) findViewById(R.id.editStatsPermission);
        Button rankingsButton = (Button) findViewById(R.id.rankings);
        Button graph = (Button) findViewById(R.id.graph);
        Button futureButton = (Button) findViewById(R.id.futureButton);

        futureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(StatsHub.this, FuturePerformanceLookupActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(StatsHub.this, FuturePerformanceActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving coach status failed: " +
                                databaseError.getCode());
                    }
                });
            }
        });

        myStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatsHub.this, StatisticsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        compareStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatsHub.this, CompareStatisticsLookupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });


        editPlayerStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent i = new Intent(LoginMessageActivity.this, CoachChangeStatsLookupActivity.class);
                startActivity(i);
                */

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // Check if the current user is a coach
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(StatsHub.this, CoachChangeStatsLookupActivity.class);
                            startActivity(i);
                        } else {

                            // Check if the current user is a player with edit privileges
                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            String currDP = "users";
                            DatabaseReference userRef = database.getReference(currDP);
                            userRef = userRef.child(username);

                            userRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot db) {
                                    String canEdit = db.child("canEdit").getValue().toString();
                                    if (canEdit.equals("1")) {

                                        // If player has edit privileges, allow editing of own stats
                                        Intent i = new Intent(StatsHub.this, CoachChangeStatsActivity.class);
                                        i.putExtra("username", username);
                                        startActivity(i);
                                    } else {

                                        // If player does not have edit privileges, tell them
                                        Intent i = new Intent(StatsHub.this, NotCoachFailureActivity.class);
                                        i.putExtra("username", username);
                                        startActivity(i);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError dbError) {
                                    System.out.println("Retrieving user edit stats failed: " + dbError.getCode());
                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving coach status failed: " + databaseError.getCode());
                    }
                });
            }
        });

        editStatsPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(StatsHub.this, AllowPlayerEditsLookupActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(StatsHub.this, NotCoachFailureActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("Retrieving coach status failed: " +
                                databaseError.getCode());
                    }
                });
            }
        });

        rankingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatsHub.this, RankingSelectionActivity.class);
                i.putExtra("selection", "highScore");
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        graph.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatsHub.this, GraphSelection.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

    }
}
