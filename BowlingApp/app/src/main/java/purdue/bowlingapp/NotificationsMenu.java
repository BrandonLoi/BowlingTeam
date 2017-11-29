package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_menu);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        Button viewNotificationsButton = (Button) findViewById(R.id.viewNotificationsBtn);
        Button sendNotificationsButton = (Button) findViewById(R.id.sendNotificationsBtn);

        viewNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotificationsMenu.this, ViewNotifications.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });

        sendNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotificationsMenu.this, SendNotifications.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
    }
}
