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

public class LoginMessageActivity extends AppCompatActivity {

    boolean isCoach = false;
    String username;
    Button button;
    Button button2;

    Button compareStatsButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.welcome_message);
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(message);
        username = textView.getText().toString();
        username = " " + username.substring(0,username.length()-1);

        button = (Button) findViewById(R.id.continueButton);
        button2 = (Button) findViewById(R.id.coachButton);

        compareStatsButton = (Button) findViewById(R.id.compareStatsButton);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("coaches");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: find if user is coach, set visible to button

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(listen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent statAct = new Intent(LoginMessageActivity.this, StatisticsActivity.class);
                statAct.putExtra(MainActivity.welcome_message, username);
                startActivity(statAct);
            }
        });

        compareStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginMessageActivity.this,
                        CompareStatisticsLookupActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

    }
}
