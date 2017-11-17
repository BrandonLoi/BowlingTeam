package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class playerMessaging extends AppCompatActivity {

    String username;

    Button inbox;
    Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_messaging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        inbox = (Button) findViewById(R.id.inbox);
        sendMessage = (Button) findViewById(R.id.sendMessage);

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(playerMessaging.this, inboxActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(playerMessaging.this, sendMessagesActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
    }



}
