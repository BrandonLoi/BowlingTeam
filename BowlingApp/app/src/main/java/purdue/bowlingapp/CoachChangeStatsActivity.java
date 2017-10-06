package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class CoachChangeStatsActivity extends AppCompatActivity {

    final String NOT_AVAILABLE = "N/A";

    TextView usernameText;
    TextView errorText;

    Button saveChangesButton;

    EditText averageScore;
    EditText highScore;
    EditText strikePc;
    EditText numGames;
    EditText singlePinPc;
    EditText sparePc;
    EditText filledPc;
    // etc.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_change_stats);


        saveChangesButton = (Button) findViewById(R.id.saveChangesButton);

        usernameText = (TextView) findViewById(R.id.usernameText);
        averageScore = (EditText) findViewById(R.id.averageScore);
        highScore = (EditText) findViewById(R.id.highScore);
        numGames = (EditText) findViewById(R.id.numGames);
        singlePinPc = (EditText) findViewById(R.id.singlePinPercentage);
        strikePc = (EditText) findViewById(R.id.strikePercentage);
        sparePc = (EditText) findViewById(R.id.sparePercentage);
        filledPc = (EditText) findViewById(R.id.filledPercentage);
        errorText = (TextView) findViewById(R.id.errorMessage);

        // etc.

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        String headerText = "Statistics for " + username + ":";
        usernameText.setText(headerText);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        String dataPath = "data";
        DatabaseReference dataRef = database.getReference(dataPath);
        dataRef = dataRef.child(username);

        /*
         * Retrieve current user's stats
         */
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String avgScore = dataSnapshot.child("avgScore").getValue().toString();
                if (avgScore.equals("-1")) {
                    averageScore.setText(NOT_AVAILABLE);
                } else {
                    averageScore.setText(avgScore);
                }

                String bestScore = dataSnapshot.child("highScore").getValue().toString();
                if (bestScore.equals("-1")) {
                    highScore.setText(NOT_AVAILABLE);
                } else {
                    highScore.setText(bestScore);
                }

                String numberOfGames = dataSnapshot.child("numGames").getValue().toString();
                if (numberOfGames.equals("-1")) {
                    numGames.setText(NOT_AVAILABLE);
                } else {
                    numGames.setText(numberOfGames);
                }

                String strikeScore = dataSnapshot.child("strikePercentage").getValue().toString();
                if (strikeScore.equals("-1")) {
                    strikePc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(strikeScore.trim());
                        //d *= 100;
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage; // + "%";
                    strikePc.setText(percentageString);
                }

                String spareScore = dataSnapshot.child("sparePercentage").getValue().toString();
                if (spareScore.equals("-1")) {
                    sparePc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(spareScore.trim());
                        //d *= 100;
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    //percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage;  //+ "%";
                    sparePc.setText(percentageString);
                }

                String singlePin = dataSnapshot.child("singlePinPercentage").getValue().toString();
                if (singlePin.equals("-1")) {
                    singlePinPc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(singlePin.trim());
                        //d *= 100;
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage;// + "%";
                    singlePinPc.setText(percentageString);
                }

                String filledFrames = dataSnapshot.child("filledPercentage").getValue().toString();
                if (filledFrames.equals("-1")) {
                    filledPc.setText(NOT_AVAILABLE);
                } else {
                    String percentage = null;
                    try
                    {
                        double d = Double.valueOf(filledFrames.trim());
                        //d *= 100;
                        percentage = String.valueOf(d);
                    } catch (NumberFormatException nfe) {
                        System.err.println("NumberFormatException: " + nfe.getMessage());
                    }
                    percentage = percentage.substring(0, Math.min(4, percentage.length()));
                    String percentageString = percentage; //+ "%";
                    filledPc.setText(percentageString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving current user's stats failed: " +
                        databaseError.getCode());
            }
        });
        final DatabaseReference finalDataRef = dataRef;
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorText.setText("");
                //N/A -> -1
                //make sure everything is a number 0> if a number < 1 if a percent
                boolean flag = true;
                try {
                    if (Integer.parseInt(averageScore.getText().toString()) < 0) {
                        flag = false;
                    }
                    } catch (NumberFormatException e) {
                    if (averageScore.getText().toString().equals("N/A")) {
                        averageScore.setText("-1");
                    }
                    else flag = false;
                }
                try {
                    if (Integer.parseInt(highScore.getText().toString()) < 0) {
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    if ((highScore.getText().toString()).equals("N/A")) {
                        highScore.setText("-1");
                    }
                    else {
                        flag = false;
                    }
                }
                try {
                    if (Integer.parseInt(numGames.getText().toString()) < 0) {
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    if (numGames.getText().toString().equals(NOT_AVAILABLE)) {
                        numGames.setText("-1");
                    }
                    else flag = false;
                }
                //make sure everything is a number 0> if a number < 1 if a percent

                try {
                    if (Double.parseDouble(strikePc.getText().toString()) < 0 || Double.parseDouble(strikePc.getText().toString()) > 100 ) {
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    if (strikePc.getText().toString().equals(NOT_AVAILABLE)) {
                        strikePc.setText("-1");
                    }
                    else flag = false;
                }
                try {
                    if (Double.parseDouble(sparePc.getText().toString()) < 0 || Double.parseDouble(sparePc.getText().toString()) > 100 ) {
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    if (sparePc.getText().toString().equals(NOT_AVAILABLE)) {
                        sparePc.setText("-1");
                    }
                    else flag = false;
                }
                try {
                    if (Double.parseDouble(singlePinPc.getText().toString()) < 0 || Double.parseDouble(singlePinPc.getText().toString()) > 100 )
                    {
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    if (singlePinPc.getText().toString().equals(NOT_AVAILABLE)) {
                        singlePinPc.setText("-1");
                    }
                    else flag = false;
                }
                try {
                    if (Double.parseDouble(filledPc.getText().toString()) < 0 || Double.parseDouble(filledPc.getText().toString()) > 100 ) {
                        flag = false;
                    }
                } catch (NumberFormatException e) {
                    if (filledPc.getText().toString().equals(NOT_AVAILABLE)) {
                        filledPc.setText("-1");
                    }
                    else flag = false;
                }
                if (flag) {
                    finalDataRef.child("avgScore").setValue(averageScore.getText().toString());
                    finalDataRef.child("highScore").setValue(highScore.getText().toString());
                    finalDataRef.child("numGames").setValue(numGames.getText().toString());
                    finalDataRef.child("strikePercentage").setValue(strikePc.getText().toString());
                    finalDataRef.child("sparePercentage").setValue(sparePc.getText().toString());
                    finalDataRef.child("singlePinPercentage").setValue(singlePinPc.getText().toString());
                    finalDataRef.child("filledPercentage").setValue(filledPc.getText().toString());
                }
                else {
                    errorText.setText("Error");
                }
            }
        });
    }
}