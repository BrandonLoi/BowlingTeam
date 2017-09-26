package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String welcome_message = "";
    public static String failure_message = "Login Information is not valid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        if(isValidPassword()) {
            Intent success = new Intent(this, LoginMessageActivity.class);
            EditText editText = (EditText) findViewById(R.id.usernameField);
            String message = editText.getText().toString() + "!";
            success.putExtra(welcome_message, message);
            startActivity(success);
        }
        else {
            Intent failure = new Intent(this, LoginFailureActivity.class);
            EditText editText = (EditText) findViewById(R.id.usernameField);
            String message = failure_message + "!";
            failure.putExtra(failure_message, message);
            startActivity(failure);
        }
    }

    public boolean isValidPassword() {
        //TO DO: Determine if password is valid
        return false;
    }
}
