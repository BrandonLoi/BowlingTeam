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

public class requestsEventsActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_events);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void request(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();
        final CheckBox checked = (CheckBox) findViewById(R.id.dropCheck);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("events").hasChild(eventName) && !(eventName.matches(""))) {
                    if (checked.isChecked()) {
                        if (dataSnapshot.child("events").child(eventName).child("players").hasChild(username)) {
                            myRef.child("messages").child("Coach").child("req").child("drop").child("EVENT").child(username).setValue(eventName);
                        }
                        else {
                            createFail("1");
                        }
                    }
                    else {
                        if (!dataSnapshot.child("events").child(eventName).child("players").hasChild(username)) {
                            myRef.child("messages").child("Coach").child("req").child("join").child("EVENT").child(username).setValue(eventName);
                        }
                        else {
                            createFail("2");
                        }
                    }
                }
                else {
                    createFail(eventName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void createFail(String eventName) {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        if (eventName.matches("1")) {
            textView.setText("Error: Not in event.");
        }
        else if (eventName.matches("2")) {
            textView.setText("Error: Already in event.");
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
