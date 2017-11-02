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

public class requestResponseActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    String reqs = "";
    int totalReqs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_response);
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
                for (DataSnapshot iterator : dataSnapshot.child("messages").child(username).child("req").child("join").child("GROUP").getChildren()) {
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
                for (DataSnapshot iterator : dataSnapshot.child("messages").child(username).child("req").child("drop").child("GROUP").getChildren()) {
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
                if (dropCheck.isChecked()) {
                    String group = dataSnapshot.child("messages").child(username).child("req").child("drop").child("GROUP").child(user).getValue().toString();
                    if (denyCheck.isChecked()) {
                        dataSnapshot.child("messages").child(username).child("req").child("drop").child("GROUP").child(user).getRef().setValue(null);
                    }
                    else {
                        dataSnapshot.child("groups").child(group).child(user).getRef().setValue(null);
                        dataSnapshot.child("messages").child(username).child("req").child("drop").child("GROUP").child(user).getRef().setValue(null);
                    }
                }
                else {
                    String group = dataSnapshot.child("messages").child(username).child("req").child("join").child("GROUP").child(user).getValue().toString();
                    if (denyCheck.isChecked()) {
                        dataSnapshot.child("messages").child(username).child("req").child("join").child("GROUP").child(user).getRef().setValue(null);
                    }
                    else {
                        if (dataSnapshot.child("groups").child(group).child(username).getValue().equals("1") || dataSnapshot.child("groups").child(group).child(username).getValue().equals("2")) {
                            myRef.child("groups").child(group).child(user).setValue("0");
                            dataSnapshot.child("messages").child(username).child("req").child("join").child("GROUP").child(user).getRef().setValue(null);
                        }
                        else {
                            myRef.child("groups").child(group).child(user).setValue("03");
                            dataSnapshot.child("messages").child(username).child("req").child("join").child("GROUP").child(user).getRef().setValue(null);
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


/*
    public void checkAddEventRequest(View view) {
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        reqs = "";
        totalReqs = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot iterator : dataSnapshot.child("messages").child(username).child("req").child("join").child("EVENT").getChildren()) {
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void checkDropEventRequest(View view) {
        final TextView output = (TextView) findViewById(R.id.output);
        output.setMovementMethod(new ScrollingMovementMethod());

        reqs = "";
        totalReqs = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot iterator : dataSnapshot.child("messages").child(username).child("req").child("drop").child("EVENT").getChildren()) {
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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }
    Made event stuff before realizing that was gonna be somewhere else
*/
    public void createFail(String groupName) {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        if (groupName.matches("1")) {
            textView.setText("Error: Not in group.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Error: Already in group.");
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
