package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayerEmailActivity extends AppCompatActivity {

    Button enableButton;
    Button disableButton;
    TextView emailNotificationText;
    TextView errorText;

    String emailNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_email);

        enableButton = (Button) findViewById(R.id.enableButton);
        disableButton = (Button) findViewById(R.id.disableButton);
        emailNotificationText = (TextView) findViewById(R.id.emailNotificationText);
        errorText = (TextView) findViewById(R.id.errorText);

        String blankText = "";
        errorText.setText(blankText);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currDataPath = "users";
        DatabaseReference ref = database.getReference(currDataPath);
        final DatabaseReference userRef = ref.child(username);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emailNotification = dataSnapshot.child("emailNotification").getValue().toString();
                if (emailNotification.equals("0")) {
                    String editText = "No";
                    emailNotificationText.setText(editText);
                } else {
                    String editText = "Yes";
                    emailNotificationText.setText(editText);
                }

                enableButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (emailNotification.equals("0")) {
                            String blankText = "";
                            userRef.child("emailNotification").setValue("1");

                            errorText.setText(blankText);
                        } else {
                            String error = "Error: You are already receiving email notification!";
                            errorText.setText(error);
                        }
                    }
                });

                disableButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (emailNotification.equals("1")) {
                            String blankText = "";
                            userRef.child("emailNotification").setValue("0");
                            errorText.setText(blankText);
                        } else {
                            String error = "Error: You have already disabled email notifications!";
                            errorText.setText(error);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving emailNotification variable failed: " + databaseError.getCode());
            }
        });
    }
}