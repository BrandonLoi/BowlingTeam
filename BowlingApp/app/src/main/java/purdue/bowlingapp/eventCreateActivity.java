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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void createEvent(View view) {
        //clearError();
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String eventName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.date);
        final String date = editText2.getText().toString();
/*

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("groups").hasChild(eventName)) {
                    System.out.println(createFail_message); //CREATE FAILURE
                    createFail(groupName);
                } else {
                    //CREATE SUCCESS
                    if (dataSnapshot.child("coaches").hasChild(username)) {
                        myRef.child("groups").child(groupName).child(username).setValue("23");
                        create(groupName);
                    }
                    else {
                            myRef.child("groups").child(groupName).child(username).setValue("13");
                            create(groupName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
        */
    }
}
