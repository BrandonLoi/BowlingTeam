package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFailureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_failure);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.FAILURE_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
    }

    public void returnToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
