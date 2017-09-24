package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String WELCOME_MESSAGE = "Welcome";
    public static final String FAILURE_MESSAGE = "Login Information is not valid.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText editText = (EditText) findViewById(R.id.usernameField);
        String password = "" + R.id.passwordField;  //TO DO: Need to do something to protect passwords at some point
        /* Temporary Line **/ boolean sqlResult = password.length() % 2 == 0; //TO DO: Get login info from MySQL database
        if(sqlResult) {
            Intent success = new Intent(this, LoginMessageActivity.class);
            String message = WELCOME_MESSAGE + " " + editText.getText().toString();
            startActivity(success);
        }
        else {
            Intent failure = new Intent(this, LoginFailureActivity.class);
            String message = FAILURE_MESSAGE + "";
            startActivity(failure);
        }
    }
}
