package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginMessageActivity extends AppCompatActivity {

    String username;
    Button button;



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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent statAct = new Intent(LoginMessageActivity.this, StatisticsActivity.class);
                statAct.putExtra(MainActivity.welcome_message, username);
                startActivity(statAct);
            }
        });

    }
}
