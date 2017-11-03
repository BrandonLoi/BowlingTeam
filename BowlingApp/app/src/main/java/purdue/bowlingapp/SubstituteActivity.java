package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Jimmy on 11/2/2017.
 */

public class SubstituteActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute);

        Button sub = (Button) findViewById(R.id.button4);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Toast toast = Toast.makeText(getApplicationContext(),"Please input a valid user",Toast.LENGTH_SHORT);;
                toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM,0,0);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                final EditText editText = (EditText) findViewById(R.id.editText);
                final String newusername = editText.getText().toString();

                if(newusername.equals(" ")) {
                    toast.show();
                }
                else {
                    DatabaseReference myRef = mDatabase.child("users");
                    ValueEventListener listen = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChild(newusername)) {
                                toast.show();
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
