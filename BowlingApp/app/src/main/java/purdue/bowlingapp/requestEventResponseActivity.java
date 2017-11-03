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

public class requestEventResponseActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    String reqs = "";
    int totalReqs = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_event_response);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void checkAddGroupRequest(View view) {
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        reqs = "";
        totalReqs = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("coaches").hasChild(username)) {
                    for (DataSnapshot iterator : dataSnapshot.child("messages").child("Coach").child("req").child("join").child("EVENT").getChildren()) {
                        String name = iterator.getKey();
                        String val = iterator.getValue().toString();
                        totalReqs++;
                        if (reqs.equals("")) {
                            reqs = Integer.toString(totalReqs) + " " + name + " " + val + "\n";
                        } else {
                            reqs = reqs + (Integer.toString(totalReqs) + " " + name + " " + val + "\n");
                        }
                    }
                    output.setText(reqs);
                }
                else {
                    createFail("3");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void checkDropGroupRequest(View view) {
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        reqs = "";
        totalReqs = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("coaches").hasChild(username)) {
                    for (DataSnapshot iterator : dataSnapshot.child("messages").child("Coach").child("req").child("drop").child("EVENT").getChildren()) {
                        String name = iterator.getKey();
                        String val = iterator.getValue().toString();
                        totalReqs++;
                        if (reqs.equals("")) {
                            reqs = Integer.toString(totalReqs) + " " + name + " " + val + "\n";
                        }
                        else {
                            reqs = reqs + (Integer.toString(totalReqs) + " " + name + " " + val + "\n");
                        }
                    }
                    output.setText(reqs);
                }
                else {
                    createFail("3");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void modifyGroups(View view) {
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());
        final EditText editText = (EditText) findViewById(R.id.user);
        final String user = editText.getText().toString();
        final CheckBox denyCheck = (CheckBox) findViewById(R.id.deny);
        final CheckBox dropCheck = (CheckBox) findViewById(R.id.drop);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("coaches").hasChild(username)) {
                    if (dropCheck.isChecked()) {
                        if (dataSnapshot.child("messages").child("Coach").child("req").child("drop").child("EVENT").hasChild(user)) {
                            String event = dataSnapshot.child("messages").child("Coach").child("req").child("drop").child("EVENT").child(user).getValue().toString();
                            if (denyCheck.isChecked()) {
                                dataSnapshot.child("messages").child("Coach").child("req").child("drop").child("EVENT").child(user).getRef().setValue(null);
                            } else {
                                dataSnapshot.child("events").child(event).child("players").child(user).getRef().setValue(null);
                                dataSnapshot.child("messages").child("Coach").child("req").child("drop").child("EVENT").child(user).getRef().setValue(null);
                            }
                        } else {
                            createFail("1");
                        }
                    } else {
                        if (dataSnapshot.child("messages").child("Coach").child("req").child("join").child("EVENT").hasChild(user)) {
                            String event = dataSnapshot.child("messages").child("Coach").child("req").child("join").child("EVENT").child(user).getValue().toString();
                            if (denyCheck.isChecked()) {
                                dataSnapshot.child("messages").child("Coach").child("req").child("join").child("EVENT").child(user).getRef().setValue(null);
                            } else {
                                    myRef.child("events").child(event).child("players").child(user).setValue("0");
                                    dataSnapshot.child("messages").child("Coach").child("req").child("join").child("EVENT").child(user).getRef().setValue(null);
                            }
                        } else {
                            createFail("1");
                        }
                    }
                }
                else {
                    createFail("3");
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
            textView.setText("Error: User does not have a pending request.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Error: Already in group.");
        }
        else if (groupName.matches("3")) {
            textView.setText("Error: You do not have permission to view or modify event requests.");
        }
        else {
            textView.setText("Error: Group does not exist.");
        }
    }
    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
