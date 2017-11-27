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

public class CommunicationHub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_hub);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        Button messagesButton = (Button) findViewById(R.id.messagesBtn);
        Button emailButton = (Button) findViewById(R.id.emailBtn);
        Button announcementButton = (Button) findViewById(R.id.announcementsBtn);

        announcementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommunicationHub.this, announcementActivity.class);
                startActivity(i);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(CommunicationHub.this, CoachEmailActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(CommunicationHub.this, PlayerEmailActivity.class);
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

        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CommunicationHub.this, playerMessaging.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

    }
}