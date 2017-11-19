package purdue.bowlingapp;

import android.content.Intent;
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

public class mergeGroupActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    TextView errorMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_group);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        errorMessage = (TextView) findViewById(R.id.errorMessage);

    }

    public void mergeGroups(View view) {
        final EditText editText = (EditText) findViewById(R.id.groupA);
        final String groupName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.groupB);
        final String groupName2 = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clearScreen();
                if (!(groupName.equals(groupName2))) {
                    if (dataSnapshot.child("groups").hasChild(groupName) && dataSnapshot.child("groups").hasChild(groupName2) && !(groupName.matches("")) && !(groupName2.matches(""))) {
                        if (dataSnapshot.child("groups").child(groupName).hasChild(username) && dataSnapshot.child("groups").child(groupName2).hasChild(username)) {
                            if ((!dataSnapshot.child("groups").child(groupName).child(username).getValue().equals("0") || !dataSnapshot.child("groups").child(groupName).child(username).getValue().equals("03")) && (!dataSnapshot.child("groups").child(groupName2).child(username).getValue().equals("0") || !dataSnapshot.child("groups").child(groupName2).child(username).getValue().equals("03"))) {
                                for (DataSnapshot iterator : dataSnapshot.child("groups").child(groupName2).getChildren()) {
                                    String name = iterator.getKey();
                                    String val = iterator.getValue().toString();
                                    if (name.equals(username)) {
                                        //Makes sure the owner's value isn't written over
                                    } else {
                                        if (dataSnapshot.child("groups").child(groupName).child(username).getValue().equals("13") || dataSnapshot.child("groups").child(groupName).child(username).getValue().equals("23")) {
                                            myRef.child("groups").child(groupName).child(name).setValue("03");
                                        } else {
                                            myRef.child("groups").child(groupName).child(name).setValue("0");
                                        }
                                    }
                                    dataSnapshot.child("groups").child(groupName2).child(name).getRef().setValue(null);
                                    createFail("blah");
                                }
                            } else {
                                createFail("1");
                            }
                        } else {
                            createFail("1");
                        }
                    } else {
                        createFail("2");
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
        if (groupName.matches("1")) {
            errorMessage.setText("Error: You do not have permission to edit these groups.");
        }
        else if (groupName.matches("2")) {
            errorMessage.setText("Error: Group names incorrectly input.");
        }
        else if (groupName.matches("3")) {
            errorMessage.setText("Error: Group names cannot be the same.");
        }
        else {
            errorMessage.setText("Groups successfully merged.");
        }
    }

    public void clearScreen() {
        errorMessage.setText("");
    }
}
