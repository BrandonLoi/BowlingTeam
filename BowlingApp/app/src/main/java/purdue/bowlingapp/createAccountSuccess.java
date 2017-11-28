package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class createAccountSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_account);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.createSuccess);
        TextView textView = (TextView) findViewById(R.id.createMessage);
        textView.setText(message + " was created! Please check your email for verification steps.");
    }

    public void returnToLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
