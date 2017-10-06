package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class createGroupSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_success);
        Intent intent = getIntent();
        String message = intent.getStringExtra(createGroupActivity.createSuccess);
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(message + " was created!");
    }
}
