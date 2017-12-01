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

public class groupHubActivity extends AppCompatActivity {

    String username;

    Button createGroupButton;
    Button editGroupButton;
    Button groupStatsButton;
    Button mergeGroupsButton;
    Button requestButton;
    Button checkReqButton;
    Button groupRankingButton;
    Button futureButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_hub);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        createGroupButton = (Button) findViewById(R.id.createGroupButton);
        editGroupButton = (Button) findViewById(R.id.editGroupButton);
        groupStatsButton = (Button) findViewById(R.id.groupStatsButton);
        mergeGroupsButton = (Button) findViewById(R.id.mergeGroupsButton);
        requestButton = (Button) findViewById(R.id.requestButton);
        checkReqButton = (Button) findViewById(R.id.checkReqsButton);
        groupRankingButton = (Button) findViewById(R.id.groupRankButton);
        futureButton = (Button) findViewById(R.id.futureButton);

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
                            Intent i = new Intent(groupHubActivity.this, GroupFuturePerformanceActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(groupHubActivity.this, NotCoachFailureActivity.class);
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

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, createGroupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        editGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, editGroupsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        groupStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, groupStatisticsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        mergeGroupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, mergeGroupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, requestActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        checkReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, requestResponseActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        groupRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(groupHubActivity.this, groupRankActivity.class);
                startActivity(i);
            }
        });
    }
}
