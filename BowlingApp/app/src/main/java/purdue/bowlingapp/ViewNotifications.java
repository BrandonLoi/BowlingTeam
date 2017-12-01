package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewNotifications extends AppCompatActivity {
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notifications);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        final TextView tv1 = (TextView) findViewById(R.id.notification1);
        final TextView tv2 = (TextView) findViewById(R.id.notification2);
        final TextView tv3 = (TextView) findViewById(R.id.notification3);
        final TextView tv4 = (TextView) findViewById(R.id.notification4);
        final TextView tv5 = (TextView) findViewById(R.id.notification5);
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("messages");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv1.setText(dataSnapshot.child(username).child("notifications").child("note1").getValue().toString());
                tv2.setText(dataSnapshot.child(username).child("notifications").child("note2").getValue().toString());
                tv3.setText(dataSnapshot.child(username).child("notifications").child("note3").getValue().toString());
                tv4.setText(dataSnapshot.child(username).child("notifications").child("note4").getValue().toString());
                tv5.setText(dataSnapshot.child(username).child("notifications").child("note5").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Button clear = (Button) findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                tv4.setText("");
                tv5.setText("");
                mDatabase.child("messages").child(username).child("notifications").child("note1").setValue("");
                mDatabase.child("messages").child(username).child("notifications").child("note2").setValue("");
                mDatabase.child("messages").child(username).child("notifications").child("note3").setValue("");
                mDatabase.child("messages").child(username).child("notifications").child("note4").setValue("");
                mDatabase.child("messages").child(username).child("notifications").child("note5").setValue("");
                mDatabase.child("messages").child(username).child("notifications").child("flag").setValue("0");
                final Toast toast = Toast.makeText(getApplicationContext(),"Notifications deleted", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                toast.show();
            }
        });



    }

}
