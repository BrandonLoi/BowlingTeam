package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.WELCOME_MESSAGE + " " + R.id.usernameField);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }
}
