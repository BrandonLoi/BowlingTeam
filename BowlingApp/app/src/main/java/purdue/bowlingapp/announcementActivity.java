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

public class announcementActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    Button clear;
    Button announce;
    EditText newAnnouncement;
    TextView currentAnnouncement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);


        newAnnouncement = (EditText) findViewById(R.id.newAnnouncement);
        currentAnnouncement = (TextView) findViewById(R.id.currentAnnouncement);
        clear = (Button) findViewById(R.id.cButton);
        announce = (Button) findViewById(R.id.aButton);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("annoucement");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentAnnouncement.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabase.addListenerForSingleValueEvent(listen);

        announce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newAnnouncement.getText().equals("")) {
                    return;
                }

                mDatabase.setValue((String) newAnnouncement.getText().toString());
                currentAnnouncement.setText(newAnnouncement.getText().toString());
                newAnnouncement.setText("");
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.setValue("");
                currentAnnouncement.setText("");
            }
        });
    }
}
