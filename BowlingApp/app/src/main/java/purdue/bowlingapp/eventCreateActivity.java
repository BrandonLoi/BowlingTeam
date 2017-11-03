package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class eventCreateActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    TextView errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void createEvent(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.date);
        final String date = editText2.getText().toString();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (eventName.equals("") || date.equals("")) {
                    createFail("4");
                }
                else if (dataSnapshot.child("events").hasChild(eventName)) {
                    createFail("1");
                }
                else {
                    //CREATE SUCCESS
                    if (dataSnapshot.child("coaches").hasChild(username)) {
                        myRef.child("events").child(eventName).child("date").setValue(date);
                        createFail("3");
                    }
                    else {
                            createFail("2");
                    }
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
            errorMessage.setText("Error: Event already exists");
        }
        else if (groupName.matches("2")) {
            errorMessage.setText("Error: You do not have permission to create events.");
        }
        else if (groupName.matches("3")) {
            errorMessage.setText("Event created.");
        }
        else if (groupName.matches("4")) {
            errorMessage.setText("Error: Please input an event name and date.");
        }
        else {
            errorMessage.setText("Blah");
        }
    }

    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
