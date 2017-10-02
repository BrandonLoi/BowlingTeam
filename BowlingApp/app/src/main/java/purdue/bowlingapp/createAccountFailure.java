package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class createAccountFailure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_account);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.createFail_message);
        TextView textView = (TextView) findViewById(R.id.createFailMessage);
        textView.setText(message);
    }

}
