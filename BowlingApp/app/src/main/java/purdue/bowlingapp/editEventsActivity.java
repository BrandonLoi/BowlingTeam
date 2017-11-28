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

public class editEventsActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    TextView errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_events);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        errorMessage = (TextView) findViewById(R.id.errorMessage);
    }

    public void addUser(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.playerName);
        final String playerName = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("events").hasChild(eventName) && !(eventName.matches(""))) {
                    if (playerName.matches("")) {
                        createFail("1");
                    }
                    else if (dataSnapshot.child("users").hasChild(playerName)) {
                        if (dataSnapshot.child("coaches").hasChild(username)) {
                            if (!(dataSnapshot.child("events").child(eventName).child("players").hasChild(playerName))) {
                                myRef.child("events").child(eventName).child("players").child(playerName).setValue("0");
                            }
                            else {
                                createFail("4");
                            }
                        }
                        else {
                            createFail("2");
                        }
                    }
                    else {
                        createFail("3");
                    }
                }
                else {
                    createFail("blah");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void rmUser(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.playerName);
        final String playerName = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("events").hasChild(eventName) && !(eventName.matches(""))) {
                    if (playerName.matches("")) {
                        createFail("1");
                    }
                    if (dataSnapshot.child("coaches").hasChild(username)) {
                        dataSnapshot = dataSnapshot.child("events").child(eventName).child("players");
                            dataSnapshot = dataSnapshot.child(playerName);
                            dataSnapshot.getRef().setValue(null);
                    }
                    else {
                        createFail("2");
                    }


                }
                else {
                    createFail("blah");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void deleteEvent(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("events").hasChild(eventName) && !(eventName.matches(""))) {
                    if (dataSnapshot.child("coaches").hasChild(username)) {
                        dataSnapshot.child("events").child(eventName).getRef().setValue(null);
                    }
                    else {
                        createFail("2");
                    }


                }
                else {
                    createFail("blah");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void editDate(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.playerName);
        final String date = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("events").hasChild(eventName) && !(eventName.matches(""))) {
                    if (!date.matches("")) {
                        if (dataSnapshot.child("coaches").hasChild(username)) {
                            myRef.child("events").child(eventName).child("date").setValue(date);
                        }
                        else {
                            createFail("2");
                        }
                    }
                    else {
                        createFail("6");

                    }
                }
                else {
                    createFail("blah");
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
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        if (groupName.matches("1")) {
            textView.setText("Error: No username input.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Error: You do not have permission to edit events.");
        }
        else if (groupName.matches("3")) {
            textView.setText("Error: Username does not exist.");
        }
        else if (groupName.matches("4")) {
            textView.setText("Error: User already in event.");
        }
        else if (groupName.matches("5")) {
            textView.setText("Error: Cannot remove yourself from group.");
        }
        else if (groupName.matches("6")) {
            textView.setText("Error: No date input.");
        }
        else {
            textView.setText("Error: Event does not exist.");
        }
    }
    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
