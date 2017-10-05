package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompareStatisticsLookupActivity extends AppCompatActivity {

    Button usernameButton;
    Button emailButton;

    String username;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_statistics_lookup);

        usernameButton = (Button) findViewById(R.id.usernameButton);
        emailButton = (Button) findViewById(R.id.emailButton);
        username = null;
        email = null;

        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent compareStatisticsActivity = new Intent(CompareStatisticsLookupActivity.this,
                        CompareStatisticsActivity.class);
                compareStatisticsActivity.putExtra(MainActivity.welcome_message, username);
                startActivity(compareStatisticsActivity);

            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent compareStatisticsActivity = new Intent(CompareStatisticsLookupActivity.this,
                        CompareStatisticsActivity.class);
                compareStatisticsActivity.putExtra(MainActivity.welcome_message, username);
                startActivity(compareStatisticsActivity);
            }
        });
    }
}
