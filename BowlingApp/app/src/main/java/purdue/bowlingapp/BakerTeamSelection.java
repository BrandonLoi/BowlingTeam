package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Jimmy on 11/3/2017.
 */

public class BakerTeamSelection extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baker_team);


        Button cont = (Button) findViewById(R.id.button5);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Toast toast = Toast.makeText(getApplicationContext(),"Please input valid users",Toast.LENGTH_SHORT);;
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                final EditText editText3 = (EditText) findViewById(R.id.editText3);
                final EditText editText4 = (EditText) findViewById(R.id.editText4);
                final EditText editText5 = (EditText) findViewById(R.id.editText5);
                final EditText editText6 = (EditText) findViewById(R.id.editText6);
                final String username2 = editText3.getText().toString();
                final String username3 = editText4.getText().toString();
                final String username4 = editText5.getText().toString();
                final String username5 = editText6.getText().toString();

                if(username2.equals("") || username3.equals("") || username4.equals("") || username5.equals("")) {
                    toast.show();
                }
                else {
                    DatabaseReference myRef = mDatabase.child("users");
                    ValueEventListener listen = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!(dataSnapshot.hasChild(username2) && dataSnapshot.hasChild(username3) &&
                                    dataSnapshot.hasChild(username4) && dataSnapshot.hasChild(username5))) {
                                toast.show();
                            }
                            else {

                                Intent i = new Intent(BakerTeamSelection.this, ScoreKeeping.class);
                                i.putExtra("username", getIntent().getStringExtra("username"));
                                i.putExtra("username2", username2);
                                i.putExtra("username3", username3);
                                i.putExtra("username4", username4);
                                i.putExtra("username5", username5);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Required, but we don't use. Leave blank
                        }
                    };
                    myRef.addListenerForSingleValueEvent(listen);
                }

            }
        });
    }
}
