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

public class AllowPlayerEditsActivity extends AppCompatActivity {

    Button yesButton;
    Button noButton;
    TextView usernameText;
    TextView canEditText;
    TextView errorText;

    String canEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_player_edits);

        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        usernameText = (TextView) findViewById(R.id.usernameText);
        canEditText = (TextView) findViewById(R.id.canEditText);
        errorText = (TextView) findViewById(R.id.errorText);

        String blankText = "";
        errorText.setText(blankText);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        usernameText.setText(username);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currDataPath = "users";
        DatabaseReference ref = database.getReference(currDataPath);
        final DatabaseReference userRef = ref.child(username);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                canEdit = dataSnapshot.child("canEdit").getValue().toString();
                if (canEdit.equals("0")) {
                    String editText = "No";
                    canEditText.setText(editText);
                } else {
                    String editText = "Yes";
                    canEditText.setText(editText);
                }

                yesButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (canEdit.equals("0")) {
                            String blankText = "";
                            userRef.child("canEdit").setValue("1");

                            errorText.setText(blankText);
                        } else {
                            String error = "Error: " + username + " can already edit statistics!";
                            errorText.setText(error);
                        }
                    }
                });

                noButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        if (canEdit.equals("1")) {
                            String blankText = "";
                            userRef.child("canEdit").setValue("0");
                            errorText.setText(blankText);
                        } else {
                            String error = "Error: " + username + " is already prohibited from editing statistics!";
                            errorText.setText(error);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
            }
        });
    }
}
