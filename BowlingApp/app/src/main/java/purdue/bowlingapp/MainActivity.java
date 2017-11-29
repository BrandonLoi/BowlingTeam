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

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        final EditText editText = (EditText) findViewById(R.id.usernameField);
        final EditText editText2 = (EditText) findViewById(R.id.passwordField);
        final String username = editText.getText().toString();
        final String password = editText2.getText().toString();
        //for debugging without internet. remove later
        if(username.equals("asdf") && password.equals("asdf")) {
            login("asdf");
        }

        clearError();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        DatabaseReference myRef = mDatabase.child("users");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    dataSnapshot = dataSnapshot.child(username);
                    User x = dataSnapshot.getValue(User.class);
                    if (password.equals(x.getPassword())) {
                        System.out.println(welcome_message); //SUCCESSFUL LOGIN
                        login(username);
                    }
                    else {
                        System.out.println(failure_message); //FAILED LOGIN
                        loginFail();
                    }
                }
                else {
                    System.out.println(failure_message); //FAILED LOGIN
                    loginFail();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);
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
                    myRef.child(username).child("canEdit").setValue("0");
                    myRef.child(username).child("emailNotification").setValue("1");
                    final DatabaseReference myRef2 = mDatabase.child("data").child(username);
                    myRef2.child("avgScore").setValue("-1");
                    myRef2.child("highScore").setValue("-1");
                    myRef2.child("strikePercentage").setValue("-1");
                    myRef2.child("filledPercentage").setValue("-1");
                    myRef2.child("singlePinPercentage").setValue("-1");
                    myRef2.child("strikePercentage").setValue("-1");
                    myRef2.child("numGames").setValue("0");
                    myRef2.child("singleMade").setValue("0");
                    myRef2.child("singleLeft").setValue("0");
                    myRef2.child("splitMade").setValue("0");
                    myRef2.child("splitLeft").setValue("0");
                    myRef2.child("multiMade").setValue("0");
                    myRef2.child("multiLeft").setValue("0");
                    myRef2.child("numStrikes").setValue("0");
                    myRef2.child("ballsThrown").setValue("0");
                    myRef2.child("cumulativeScore").setValue("0");
                    myRef2.child("filledFrames").setValue("0");
                    final DatabaseReference myRef3 = mDatabase.child("messages").child(username);
                //Is this gonna break something?    myRef3.child("inbox").child("message").setValue("");
                    myRef3.child("notifications").child("note1").setValue("");
                    myRef3.child("notifications").child("note2").setValue("");
                    myRef3.child("notifications").child("note3").setValue("");
                    myRef3.child("notifications").child("note4").setValue("");
                    myRef3.child("notifications").child("note5").setValue("");
                    final DatabaseReference myRef4 = mDatabase.child("data").child(username);

                    // For sending the verification email
                    Random rand = new Random();
                    int verificationNum = rand.nextInt(9999) + 1; // Random number from 1 to 9999
                    myRef.child(username).child("verification").setValue(Integer.toString(verificationNum));
                    sendEmail(email, verificationNum);

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

    // Sends verification email
    private void sendEmail(String email, int verificationNum) {
        //Getting content for email
        String subject = "Verification Number";
        String message = "Here is your verification number: " + verificationNum +
                         "\nTo verify your email, please use the \"Email\" tab found in the \"Communication\" tab." +
                         "\n\nThanks\n- Bowling Statistics Tracker\n";

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
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
    public void resetPassword(View view) {
        clearError();

        clearError();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final EditText editText = (EditText) findViewById(R.id.usernameField);
        final EditText editText2 = (EditText) findViewById(R.id.passwordField);
        final EditText editText3 = (EditText) findViewById(R.id.emailField);


        final String username = editText.getText().toString();
        final String password = editText2.getText().toString();
        final String email = editText3.getText().toString();

        //malformed input
        if (username.equals("") || password.equals("") || email.equals("")) {
            return;
        }


        DatabaseReference myRef = mDatabase.child("users");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    //success!
                    String storedEmail = dataSnapshot.child(username).child("email").getValue().toString();
                    if (storedEmail.equals(email)) {
                        dataSnapshot.child(username).child("password").getRef().setValue(password);
                        System.out.println("successful change");
                    }
                    else {
                        System.out.println("incorrect email");
                    }
                }
                else {
                    System.out.println("user does not exist");
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
