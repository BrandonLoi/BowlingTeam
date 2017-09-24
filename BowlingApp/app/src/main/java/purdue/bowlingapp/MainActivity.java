package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Welcome";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent success = new Intent(this, LoginMessageActivity.class);
        Intent failure = new Intent(this, LoginFailureActivity.class);
        
        EditText editText = (EditText) findViewById(R.id.usernameField);
        String message = editText.getText().toString() + " " + R.id.usernameField;
        success.putExtra(EXTRA_MESSAGE, message);
        startActivity(success);
    }
}
