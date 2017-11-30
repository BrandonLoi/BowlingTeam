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

public class coachMessageActivity extends AppCompatActivity {

    public DatabaseReference mDatabase;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_message);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    public void send(View view) {
        clearError();
        final EditText editText2 = (EditText) findViewById(R.id.message);
        final String message = editText2.getText().toString();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference myRef = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("coaches").hasChild(username)) {
                    for (DataSnapshot iterator : dataSnapshot.child("users").getChildren()) {
                        String name = iterator.getKey();
                        if (dataSnapshot.child("coaches").hasChild(name)) {
                            //Leave blank to skip over coaches
                        }
                        else {
                            long sum = dataSnapshot.child("messages").child(name).child("inbox").getChildrenCount() + 1;
                            if (sum > 100) {
                                dataSnapshot.child("messages").child(username).child("inbox").getRef().setValue(null);
                                sum = 1;
                            }
                            myRef.child("messages").child(name).child("inbox").child(Long.toString(sum)).child(username).setValue(message);
                            createFail("2");
                        }

                    }

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
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        if (groupName.matches("1")) {
            textView.setText("Error: You do not have permission to do this.");
        }
        else if (groupName.matches("2")) {
            textView.setText("Message sent.");
        }
        else {
            textView.setText("Error: User does not exist.");
        }
    }
    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
