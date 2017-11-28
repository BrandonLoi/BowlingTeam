package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VerifyEmail extends AppCompatActivity {

    Button verifyButton;
    EditText verificationNumText;
    TextView verifiedText;
    TextView errorText;

    String verified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        verifyButton = (Button) findViewById(R.id.verifyButton);
        verificationNumText = (EditText) findViewById(R.id.verificationNumText);
        verifiedText = (TextView) findViewById(R.id.verifiedText);
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
                verified = dataSnapshot.child("verification").getValue().toString();
                if (verified.equals("0")) {
                    String editText = "Yes";
                    verifiedText.setText(editText);
                } else {
                    String editText = "No";
                    verifiedText.setText(editText);
                }

                verifyButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (verified.equals("0")) {
                            String error = "Error: Email already verified!";
                            errorText.setText(error);
                        } else {
                            final String number = verificationNumText.getText().toString();
                            if (number.equals(verified)) {
                                String blankText = "Success! Email has been verified!";
                                userRef.child("verification").setValue("0");

                                errorText.setText(blankText);
                            } else {
                                String blankText = "Error: Incorrect verification number!";

                                errorText.setText(blankText);
                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving verification variable failed: " + databaseError.getCode());
            }
        });

    }
}