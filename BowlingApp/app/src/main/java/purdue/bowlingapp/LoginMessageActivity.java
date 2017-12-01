package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginMessageActivity extends AppCompatActivity {
    String username;
    Button stats;

    Button CommunicationButton;
    Button scoreKeepingButton;
    Button liveTournamentButton;
    Button groupsButton;
    Button eventHubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.welcome_message);
        TextView textView = (TextView) findViewById(R.id.textView3);
        final TextView announcementText = (TextView) findViewById(R.id.announce);
        textView.setText(message);
        username = textView.getText().toString();
        username = "" + username.substring(0, username.length() - 1);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot iterator : dataSnapshot.child("events").getChildren()) {
                    for (DataSnapshot iterator2 : iterator.child("players").getChildren()) {
                        String name = iterator2.getKey();
                        String date = iterator.getValue().toString();
                        date = date.substring(6,16);
                        if(name.equals(username)) {
                            int num = eventCreateActivity.daysUntil(date);
                            if(num < 8 && num >= 0) { // Aaron, in this if statement add the email reminder for events
                                // Send email reminder
                                String email = dataSnapshot.child("users").child(username).child("email").getValue().toString();
                                sendEmail(email, date);

                                final Toast toast = Toast.makeText(getApplicationContext(), "You have an upcoming event on " + date + ". Check your calendar for more information.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                                toast.show();
                            }
                        }
                    }
                }
                mDatabase.child("messages").child(username).child("notifications").child("eventFlag").setValue("1");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                announcementText.setText(dataSnapshot.child("announcement").getValue().toString());
                String note1 = dataSnapshot.child("messages").child(username).child("notifications").child("note1").getValue().toString();
                String note2 = dataSnapshot.child("messages").child(username).child("notifications").child("note2").getValue().toString();
                String note3 = dataSnapshot.child("messages").child(username).child("notifications").child("note3").getValue().toString();
                String note4 = dataSnapshot.child("messages").child(username).child("notifications").child("note4").getValue().toString();
                String note5 = dataSnapshot.child("messages").child(username).child("notifications").child("note5").getValue().toString();
                int flag = Integer.parseInt(dataSnapshot.child("messages").child(username).child("notifications").child("flag").getValue().toString());
                if(flag == 0) {
                    if (!note1.equals("")) {
                        final Toast toast = Toast.makeText(getApplicationContext(), note1, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    if (!note2.equals("")) {
                        final Toast toast = Toast.makeText(getApplicationContext(), note2, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    if (!note3.equals("")) {
                        final Toast toast = Toast.makeText(getApplicationContext(), note3, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    if (!note4.equals("")) {
                        final Toast toast = Toast.makeText(getApplicationContext(), note4, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    if (!note5.equals("")) {
                        final Toast toast = Toast.makeText(getApplicationContext(), note5, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                    if (!note1.equals("") || !note2.equals("") || !note3.equals("") || !note4.equals("") || !note5.equals("")) {
                        mDatabase.child("messages").child(username).child("notifications").child("flag").setValue("1");
                        final Toast toast = Toast.makeText(getApplicationContext(), "Open Communication -> Notifications to view and clear your notifications", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.show();
                    }
                }
                mDatabase.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        stats = (Button) findViewById(R.id.stats);
        scoreKeepingButton = (Button) findViewById(R.id.scoreKeepingButton);
        liveTournamentButton = (Button) findViewById(R.id.liveTournamentButton);
        groupsButton = (Button) findViewById(R.id.groupsButton);
        eventHubButton = (Button) findViewById(R.id.eventHubButton);


        CommunicationButton = (Button) findViewById(R.id.communcationButton);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this,
                        StatsHub.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        CommunicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, CommunicationHub.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        scoreKeepingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, GameTypeSelection.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, groupHubActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        eventHubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this, EventHubActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
        /*
        Checks if the current user has coach privileges
         */
        liveTournamentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                String currDataPath = "coaches";
                final DatabaseReference coach = database.getReference(currDataPath);

                coach.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username)) {
                            Intent i = new Intent(LoginMessageActivity.this, LiveTournamentActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(LoginMessageActivity.this, NotCoachFailureActivity.class);
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
    }

    // Sends email reminder
    private void sendEmail(String email, String date) {
        //Getting content for email
        String subject = "Event Reminder";
        String message = "You have an upcoming event on " + date + ". Check your calendar for more information.";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
}