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

public class requestActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;
    String reciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void request(View view) {
//        clearError();
        final EditText editText = (EditText) findViewById(R.id.groupName);
        final String groupName = editText.getText().toString();
        final CheckBox checked = (CheckBox) findViewById(R.id.dropCheck);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("groups").hasChild(groupName) && !(groupName.matches(""))) {
                    for (DataSnapshot iterator : dataSnapshot.child("groups").child(groupName).getChildren()) {
                        String name = iterator.getKey();
                        String val = iterator.getValue().toString();
                        if (val.equals("1") || val.equals("2") || val.equals("13") || val.equals("23")) {
                            reciever = name;
                        }
                    }
                    if (checked.isChecked()) {
                        if (dataSnapshot.child("groups").child(groupName).hasChild(username)) {
                            myRef.child("messages").child(reciever).child("req").child("drop").child("GROUP").child(username).setValue(groupName);
                        }
                        else {
                            createFail("1");
                        }
                    }
                    else {
                        if (!dataSnapshot.child("groups").child(groupName).hasChild(username)) {
                            myRef.child("messages").child(reciever).child("req").child("join").child("GROUP").child(username).setValue(groupName);
                        }
                        else {
                            createFail("2");
                        }
                    }
                }
                else {
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
