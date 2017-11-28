package purdue.bowlingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CoachEmailActivity extends AppCompatActivity {
    EditText emailAddressText;
    EditText emailSubjectText;
    EditText emailMessageText;

    Button sendEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_email);

        emailAddressText = (EditText) findViewById(R.id.emailAddressText);
        emailSubjectText = (EditText) findViewById(R.id.emailSubjectText);
        emailMessageText = (EditText) findViewById(R.id.emailMessageText);

        sendEmailButton = (Button) findViewById(R.id.sendEmailButton);

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailAddress = emailAddressText.getText().toString();
                final String emailSubject = emailSubjectText.getText().toString();
                final String emailMessage = emailMessageText.getText().toString();

                if (emailAddress.equals("")) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference("users");

                    // Get list of all emails
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String listOfEmailAddresses = "";
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                if (user.hasChild("email") && user.hasChild("emailNotification")) {
                                    String emailNotification = user.child("emailNotification").getValue().toString();
                                    if (emailNotification.equals("1")) {
                                        String email = user.child("email").getValue().toString();
                                        listOfEmailAddresses += email;
                                        listOfEmailAddresses += "; ";
                                    }
                                }
                            }

                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[]{listOfEmailAddresses});
                            email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                            email.putExtra(Intent.EXTRA_TEXT, emailMessage);

                            email.setType("message/rfc822"); // Need this to prompt email client only

                            startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("The lookup failed: " + databaseError.getCode());
                        }
                    });

                } else {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                    email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                    email.putExtra(Intent.EXTRA_TEXT, emailMessage);

                    email.setType("message/rfc822"); // Need this to prompt email client only

                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                }
            }
        });
    }
}
