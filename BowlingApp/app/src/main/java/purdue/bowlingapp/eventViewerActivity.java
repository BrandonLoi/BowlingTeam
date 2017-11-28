package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class eventViewerActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    String reqs = "";
    int totalReqs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_viewer);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void checkEvents(View view) {
        clearError();
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        reqs = "";
        totalReqs = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot iterator : dataSnapshot.child("events").getChildren()) {
                    totalReqs++;
                    reqs = reqs + (Integer.toString(totalReqs) + " " + iterator.getKey() + " " + iterator.child("date").getValue() + " " + iterator.child("type").getValue() + "\n");

                    for (DataSnapshot iterator2 : iterator.child("players").getChildren()) {
                        String name = iterator2.getKey();
                        String date = iterator2.getValue().toString();
                        if (reqs.equals("")) {
                            reqs = "          " + name + " " + "\n";
                        } else {
                            reqs = reqs + ("          " + name + "\n");
                        }
                    }
                    output.setText(reqs);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void checkMyEvents(View view) {
        clearError();
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        reqs = "";
        totalReqs = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot iterator : dataSnapshot.child("events").getChildren()) {
                    if (iterator.child("players").hasChild(username)) {
                        totalReqs++;
                        reqs = reqs + (Integer.toString(totalReqs) + " " + iterator.getKey() + " " + iterator.child("date").getValue() + " " + iterator.child("type").getValue() + "\n");

                        for (DataSnapshot iterator2 : iterator.child("players").getChildren()) {
                            String name = iterator2.getKey();
                            String date = iterator2.getValue().toString();
                            if (reqs.equals("")) {
                                reqs = "          " + name + " " + "\n";
                            } else {
                                reqs = reqs + ("          " + name + "\n");
                            }
                            output.setText(reqs);
                        }
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
        TextView textView = (TextView) findViewById(R.id.output);
        if (groupName.matches("1")) {
            textView.setText("You will no longer receive messages from other users.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Inbox cleared.");
        }
        else if (groupName.matches("3")) {
            textView.setText("Other users will be able to send you messages.");
        }
        else {
            textView.setText("Error: Group does not exist.");
        }
    }

    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.output);
        textView.setText("");
    }
}
