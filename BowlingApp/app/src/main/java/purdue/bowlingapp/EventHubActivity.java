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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_hub);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        createEventButton = (Button) findViewById(R.id.createEventButton);
        editEventButton = (Button) findViewById(R.id.editEventButton);

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

    }
}
