package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CompareStatisticsActivity extends AppCompatActivity {

    TextView currUsernameText;
    TextView compUsernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_statistics);

        currUsernameText = (TextView) findViewById(R.id.currUsernameText);
        compUsernameText = (TextView) findViewById(R.id.compUsernameText);

        Intent intent = getIntent();
        final String currUsername = intent.getStringExtra("currUsername");
        final String compUsername = intent.getStringExtra("compUsername");

        currUsernameText.setText(currUsername);
        compUsernameText.setText(compUsername);
    }
}
