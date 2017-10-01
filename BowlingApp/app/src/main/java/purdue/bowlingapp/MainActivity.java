package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public DatabaseReference mDatabase;
    public static String welcome_message = "";
    public static String failure_message = "Login Information is not valid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final EditText editText = (EditText) findViewById(R.id.usernameField);
        final EditText editText2 = (EditText) findViewById(R.id.passwordField);

        final String username = editText.getText().toString();
        final String password = editText2.getText().toString();

        DatabaseReference myRef = mDatabase.child("users");//.child(editText.toString());
        ValueEventListener listen = new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    dataSnapshot = dataSnapshot.child(username);
                    Player x = dataSnapshot.getValue(Player.class);
                    if (password.equals(x.getPassword())) {
                        System.out.println(welcome_message); //SUCCESSFUL LOGIN
                    }
                    else {
                        System.out.println(failure_message); //FAILED LOGIN
                    }

                }
                else {
                    System.out.println(failure_message); //FAILED LOGIN
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);


        /*
        DatabaseReference myRef = database.getReference("user/" + editText.toString());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User current = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do we require something here?
            }
        };
        myRef.addListenerForSingleValueEvent(userListener);
         */


    /*
        if(isValidLogin(true)) {
            Intent success = new Intent(this, LoginMessageActivity.class);
            EditText editText = (EditText) findViewById(R.id.usernameField);
            String message = editText.getText().toString() + "!";
            success.putExtra(welcome_message, message);
            startActivity(success);
        }
        else {
            Intent failure = new Intent(this, LoginFailureActivity.class);
            EditText editText = (EditText) findViewById(R.id.usernameField);
            String message = failure_message + "!";
            failure.putExtra(failure_message, message);
            startActivity(failure);
        }
        */
    }

    public static boolean isValidLogin(boolean bool) {
        //TO DO: Determine if login credentials are valid
        return bool;
    }
}
