package purdue.bowlingapp;

import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class calendarAddActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_add);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        errorMessage = (TextView) findViewById(R.id.errorMessage);
    }

    public void add(View view) {
        final EditText editText = (EditText) findViewById(R.id.eventName);
        final String groupName = editText.getText().toString();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearScreen();
                if (dataSnapshot.child("events").hasChild(groupName)) {
                    Calendar beginTime = Calendar.getInstance();
                    String [] date = new String[3];

                    date = dataSnapshot.child("events").child(groupName).child("date").getValue().toString().split("/");

                    beginTime.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0]), 0, 0);
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0]), 23, 59);
                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, groupName)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);
                }
                else {
                    createFail("1");
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
            errorMessage.setText("Error: Event does not exist.");
        }
        else {
            errorMessage.setText("Event successfully added to calendar.");
        }
    }

    public void clearScreen() {
        errorMessage.setText("");
    }

}
