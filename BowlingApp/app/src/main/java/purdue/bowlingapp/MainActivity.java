package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public DatabaseReference mDatabase;
    public static String welcome_message = "Welcome!";
    public static String failure_message = "Login Information is not valid";
    public static String createSuccess = "User created";
    public static String createFail_message = "Username already in use";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void login(View view) {
        final boolean[] loginSuccess = {false};
        clearError();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final EditText editText = (EditText) findViewById(R.id.usernameField);
        final EditText editText2 = (EditText) findViewById(R.id.passwordField);

        final String username = editText.getText().toString();
        final String password = editText2.getText().toString();

        DatabaseReference myRef = mDatabase.child("users");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    dataSnapshot = dataSnapshot.child(username);
                    User x = dataSnapshot.getValue(User.class);
                    if (password.equals(x.getPassword())) {
                        System.out.println(welcome_message); //SUCCESSFUL LOGIN
                        loginSuccess[0] = true;

                    }
                    else {
                        System.out.println(failure_message); //FAILED LOGIN
                        loginSuccess[0] = false;
                    }
                }
                else {
                    System.out.println(failure_message); //FAILED LOGIN
                    loginSuccess[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
        if (loginSuccess[0] = true) {
            login(username);
        }
        else {
            loginFail();
        }
    }

    public void userCreate(View view) {
        clearError();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final EditText editText = (EditText) findViewById(R.id.usernameField);
        final EditText editText2 = (EditText) findViewById(R.id.passwordField);
        final EditText editText3 = (EditText) findViewById(R.id.emailField);

        final String username = editText.getText().toString();
        final String password = editText2.getText().toString();
        final String email = editText3.getText().toString();

        final DatabaseReference myRef = mDatabase.child("users");//.child(editText.toString());
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    System.out.println(createFail_message); //CREATE FAILURE
                    createFail(username);
                }
                else {
                    //CREATE SUCCESS
                    myRef.child(username).child("username").setValue(username);
                    myRef.child(username).child("password").setValue(password);
                    myRef.child(username).child("email").setValue(email);
                    create(username);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
    }

    public void login(String username) {
        Intent success = new Intent(this, LoginMessageActivity.class);
        String message = username + "!";
        success.putExtra(welcome_message, message);
        startActivity(success);
    }

    public void loginFail() {
        Intent failure = new Intent(this, LoginFailureActivity.class);
        String message = failure_message + "!";
        failure.putExtra(failure_message, message);
        startActivity(failure);
    }

    public void create(String username) {
        Intent success = new Intent(this, createAccountSuccess.class);
        String message = username;
        success.putExtra(createSuccess, message);
        startActivity(success);

    }

    public void createFail(String username) {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("Error: User already exists.");
    }
    public void clearError() {
        TextView textView = (TextView) findViewById(R.id.errorMessage);
        textView.setText("");
    }
}
