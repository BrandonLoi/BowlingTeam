package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static purdue.bowlingapp.MainActivity.welcome_message;

public class NotCoachFailureActivity extends AppCompatActivity {

    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_coach_failure);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        returnButton = (Button) findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotCoachFailureActivity.this, LoginMessageActivity.class);
                String message = username + "!";
                i.putExtra(welcome_message, message);
                startActivity(i);
            }
        });
    }
}