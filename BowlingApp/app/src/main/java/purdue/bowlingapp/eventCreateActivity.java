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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
        final CheckBox checked = (CheckBox) findViewById(R.id.practice);

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
                        if (checked.isChecked()) {
                            myRef.child("events").child(eventName).child("date").setValue(date);
                            myRef.child("events").child(eventName).child("type").setValue("PRACTICE");
                            createFail("3");
                        }
                        else {
                            myRef.child("events").child(eventName).child("date").setValue(date);
                            myRef.child("events").child(eventName).child("type").setValue("TOURNAMENT");
                            createFail("3");
                        }

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

    public static int daysUntil(String date) {
        if(date == null) return 0;

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        int day = Integer.parseInt(date.substring(0,2));
        int month = Integer.parseInt(date.substring(3,5));
        int year = Integer.parseInt(date.substring(6));
        cal1.set(year, month, day);

        DateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.US);
        Date date1 = new Date();
        String currentDate = df.format(date1);
        cal2.set(Integer.parseInt(currentDate.substring(4)), Integer.parseInt(currentDate.substring(2,4)), Integer.parseInt(currentDate.substring(0,2)));

        return (int)( (cal2.getTime().getTime() - cal1.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
