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

public class createGroupActivity extends AppCompatActivity {
   
    public DatabaseReference mDatabase;
    String username;
    public static String createSuccess = "Group created";
    public static String createFail_message = "Group already exists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }
    
    public void createUserGroup(View view) {
        //clearError();
        final EditText editText = (EditText) findViewById(R.id.groupNameField);
        final String groupName = editText.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("groups").hasChild(groupName)) {
                    System.out.println(createFail_message); //CREATE FAILURE
                    createFail(groupName);
                } else {
                    //CREATE SUCCESS
                    if (dataSnapshot.child("coaches").hasChild(username)) {
                        myRef.child("groups").child(groupName).child(username).setValue("2");
                        create(groupName);
                    }
                    else {
                        myRef.child("groups").child(groupName).child(username).setValue("1");
                        create(groupName);
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

    public void create(String groupName) {
        Intent success = new Intent(this, createGroupSuccess.class);
        String message = groupName;
        success.putExtra(createSuccess, message);
        startActivity(success);

    }

    public void createFail(String groupName) {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("Error: Group already exists.");
    }
    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
