package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendNotifications extends AppCompatActivity {
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifications);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        Button sendButton = (Button) findViewById(R.id.sendButton);
        final EditText usernameField = (EditText) findViewById(R.id.playerUsername);
        final EditText groupName = (EditText) findViewById(R.id.groupUsername);
        final EditText messageText = (EditText) findViewById(R.id.messageToSend);

        sendButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Toast toast = Toast.makeText(getApplicationContext(),"Please enter a username or group name and a message",Toast.LENGTH_SHORT);
               toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
               final String groupString = groupName.getText().toString();
               final String usernameString = usernameField.getText().toString();
               final String message = messageText.getText().toString();

               if((groupString.equals("") && usernameString.equals("")) || message.equals("")) {
                   toast.show();
               }
               else {
                   final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("messages");
                   DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("groups");
                   userRef.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           if (!dataSnapshot.hasChild(usernameString)) {
                               toast.show();
                           }
                           else {
                                String note1 = dataSnapshot.child(usernameString).child("notifications").child("note1").getValue().toString();
                                String note2 = dataSnapshot.child(usernameString).child("notifications").child("note2").getValue().toString();
                                String note3 = dataSnapshot.child(usernameString).child("notifications").child("note3").getValue().toString();
                                String note4 = dataSnapshot.child(usernameString).child("notifications").child("note4").getValue().toString();
                                String note5 = dataSnapshot.child(usernameString).child("notifications").child("note5").getValue().toString();
                                if(note1.equals("")) {
                                    mDatabase.child("messages").child(usernameString).child("notifications").child("note1").setValue(message);
                                }
                                else if(note2.equals("")) {
                                    mDatabase.child("messages").child(usernameString).child("notifications").child("note2").setValue(message);
                                }
                                else if(note3.equals("")) {
                                    mDatabase.child("messages").child(usernameString).child("notifications").child("note3").setValue(message);
                                }
                                else if(note4.equals("")) {
                                    mDatabase.child("messages").child(usernameString).child("notifications").child("note4").setValue(message);
                                }
                                else if(note5.equals("")) {
                                    mDatabase.child("messages").child(usernameString).child("notifications").child("note5").setValue(message);
                                }
                                else {
                                    final Toast toast2 = Toast.makeText(getApplicationContext(),"That user has too many pending notifications",Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                                    toast2.show();
                                    userRef.removeEventListener(this);
                                    return;
                                }
                                mDatabase.child("messages").child(usernameString).child("notifications").child("flag").setValue("0");
                                final Toast toast2 = Toast.makeText(getApplicationContext(),"Success. User will see the notification when they log in.",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                                toast2.show();
                                userRef.removeEventListener(this);
                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });

                   groupRef.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           if (!dataSnapshot.hasChild(groupString)) {
                              // toast.show();
                           }
                           else {

                           }
                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });

               }

           }
        });


    }

}