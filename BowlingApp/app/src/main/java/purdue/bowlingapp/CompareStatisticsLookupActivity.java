package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompareStatisticsLookupActivity extends AppCompatActivity {

    Button usernameButton;
    Button emailButton;
    EditText usernameText;
    EditText emailText;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_statistics_lookup);

        usernameButton = (Button) findViewById(R.id.usernameButton);
        emailButton = (Button) findViewById(R.id.emailButton);
        usernameText = (EditText) findViewById(R.id.inputUsername);
        emailText = (EditText) findViewById(R.id.inputEmail);
        errorText = (TextView) findViewById(R.id.errorText);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        /*
         * Search by username
         */
        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameLookUp = usernameText.getText().toString();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("users");

                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(usernameLookUp)) {
                            Intent i = new Intent(CompareStatisticsLookupActivity.this,
                                    CompareStatisticsActivity.class);
                            i.putExtra("currUsername", username);
                            i.putExtra("compUsername", usernameLookUp);
                            startActivity(i);
                        } else {
                            String error = "Error! Could not find username: " + usernameLookUp;
                            errorText.setText(error);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The lookup failed: " + databaseError.getCode());
                    }
                });
            }
        });

        /*
         * Search by email TODO: searching for '.' char breaks app
         */
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailText.getText().toString();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("users");

                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent i = null;

                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            if (user.hasChild("email") ) {
                                if (user.child("email").getValue().toString().equals(email)) {
                                    i = new Intent(CompareStatisticsLookupActivity.this,
                                            CompareStatisticsActivity.class);
                                    i.putExtra("currUsername", username);
                                    i.putExtra("compUsername", user.child("username").getValue().toString());
                                    break;
                                }
                            }
                        }
                        if (i != null) {
                            startActivity(i);
                        } else {
                            String error = "Error! Could not find email: " + email;
                            errorText.setText(error);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The lookup failed: " + databaseError.getCode());
                    }
                });
            }
        });
    }
}
