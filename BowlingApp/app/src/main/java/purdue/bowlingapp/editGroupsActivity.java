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

import java.util.ArrayList;

public class editGroupsActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    public static String createSuccess = "Group created";
    public static String createFail_message = "Group already exists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_groups);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void addUser(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.groupName);
        final String groupName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.userName);
        final String userAddName = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("groups").hasChild(groupName) && !(groupName.matches(""))) {
                    if (userAddName.matches("")) {
                        createFail("1");
                    }
                    else if (dataSnapshot.child("users").hasChild(userAddName)) {
                        dataSnapshot = dataSnapshot.child("groups").child(groupName);
                        if (dataSnapshot.hasChild(username)) {
                            if (dataSnapshot.child(username).getValue().equals("1")) {
                                if (!(dataSnapshot.hasChild(userAddName))) {
                                    myRef.child("groups").child(groupName).child(userAddName).setValue("0");
                                }
                                else {
                                    createFail("4");
                                }
                            }
                        }
                        else {
                            createFail("2");
                        }
                    }
                    else {
                        createFail("3");
                    }
                }
                else {
                    System.out.println(createFail_message); //CREATE FAILURE
                    createFail(groupName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void rmUser(View view) {
        clearError();
        final EditText editText = (EditText) findViewById(R.id.groupName);
        final String groupName = editText.getText().toString();
        final EditText editText2 = (EditText) findViewById(R.id.userName);
        final String userAddName = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase.child("groups");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(groupName) && !(groupName.matches(""))) {
                    if (userAddName.matches("")) {
                        createFail("1");
                    }
                    else if (!(userAddName.matches(username))) {
                        dataSnapshot = dataSnapshot.child(groupName);
                        if (dataSnapshot.hasChild(username)) {
                            if (dataSnapshot.child(username).getValue().equals("1")) {
                                dataSnapshot = dataSnapshot.child(userAddName);
                                dataSnapshot.getRef().setValue(null);
                            }
                        }
                        else {
                            createFail("2");
                        }
                    }
                    else {
                        createFail("5");
                    }
                }
                else {
                    System.out.println(createFail_message); //CREATE FAILURE
                    createFail(groupName);
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
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        if (groupName.matches("1")) {
            textView.setText("Error: No username input.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Error: You are not the group's owner.");
        }
        else if (groupName.matches("3")) {
            textView.setText("Error: Username does not exist.");
        }
        else if (groupName.matches("4")) {
            textView.setText("Error: User already in group.");
        }
        else if (groupName.matches("5")) {
            textView.setText("Error: Cannot remove yourself from group.");
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
