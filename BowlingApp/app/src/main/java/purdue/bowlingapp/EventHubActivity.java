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

public class EventHubActivity extends AppCompatActivity {

    String username;

    Button createEventButton;
    Button editEventButton;
    Button requestButton;
    Button checkRequestButton;
    Button viewEvents;
    Button addCalendar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_hub);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        createEventButton = (Button) findViewById(R.id.createEventButton);
        editEventButton = (Button) findViewById(R.id.editEventButton);
        requestButton = (Button) findViewById(R.id.requestButton);
        checkRequestButton = (Button) findViewById(R.id.checkRequestsButton);
        viewEvents = (Button) findViewById(R.id.viewEvents);
        addCalendar = (Button) findViewById(R.id.calendarAdd);


        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHubActivity.this, eventCreateActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        editEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHubActivity.this, editEventsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHubActivity.this, requestsEventsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        checkRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHubActivity.this, requestEventResponseActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        viewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHubActivity.this, eventViewerActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        addCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventHubActivity.this, calendarAddActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
    }
}
