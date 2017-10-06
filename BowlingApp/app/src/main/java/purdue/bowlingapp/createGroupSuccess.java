package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class createGroupSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_account);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.createSuccess);
        TextView textView = (TextView) findViewById(R.id.createMessage);
        textView.setText(message + " was created!");
    }
}
