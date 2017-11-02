package purdue.bowlingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class groupRankedList extends AppCompatActivity {
    Map<String, Double> groupStat;
    String output;
    TextView outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_ranked_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String headerString = "";
        outputText = (TextView) findViewById(R.id.outputText);
        groupStat  = new HashMap<String, Double>();
        output = "";

        final String selection = intent.getStringExtra("selection");
        switch(selection) {
            case "highScore":
                headerString = "High Score";
                break;
            case "avgScore":
                headerString = "Average Score";
                break;
            case "filledPct":
                headerString = "Filled Frame Percentage";
                break;
            case "strikePct":
                headerString = "Strike Percentage";
                break;
            case "sparePct":
                headerString = "Spare Percentage";
                break;
            case "singlePct":
                headerString = "Single Pin Spare Percentage";
                break;
            default:
                headerString = " ";
        }

        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Showing ranking for " + headerString);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot data = dataSnapshot.child("data");
                for (DataSnapshot group : dataSnapshot.child("groups").getChildren()) {
                    String groupName = group.getKey();
                    double avgSL = 0;
                    double highSL = 0;
                    double gameNL = 0;
                    double singlePPL = 0;
                    double strikePrL = 0;
                    double sparePrL = 0;
                    double filledPrL = 0;                        
                    double divisor = group.getChildrenCount();
                    for (DataSnapshot playerName : group.getChildren()) {
                        String name = playerName.getKey();
                        playerName = dataSnapshot.child("data").child(name);


                        if (playerName.child("avgScore").getValue().equals("-1")) {

                        } else {
                            avgSL = avgSL + Double.valueOf(playerName.child("avgScore").getValue().toString());
                        }
                        if (playerName.child("highScore").getValue().equals("-1")) {

                        } else {
                            highSL = highSL + Double.valueOf(playerName.child("highScore").getValue().toString());
                        }
                        if (playerName.child("numGames").getValue().equals("-1")) {

                        } else {
                            gameNL = gameNL + Double.valueOf(playerName.child("numGames").getValue().toString());
                        }
                        if (playerName.child("singlePinPercentage").getValue().equals("-1")) {

                        } else {
                            singlePPL = singlePPL + Double.valueOf(playerName.child("singlePinPercentage").getValue().toString());
                        }
                        if (playerName.child("strikePercentage").getValue().equals("-1")) {

                        } else {
                            strikePrL = strikePrL + Double.valueOf(playerName.child("strikePercentage").getValue().toString());
                        }
                        if (playerName.child("sparePercentage").getValue().equals("-1")) {

                        } else {
                            sparePrL = sparePrL + Double.valueOf(playerName.child("sparePercentage").getValue().toString());
                        }
                        if (playerName.child("filledPercentage").getValue().equals("-1")) {

                        } else {
                            filledPrL = filledPrL + Double.valueOf(playerName.child("filledPercentage").getValue().toString());
                        }
                    }
                    avgSL = avgSL / divisor;
                    highSL = highSL / divisor;
                    gameNL = gameNL / divisor;
                    singlePPL = singlePPL / divisor;
                    strikePrL = strikePrL / divisor;
                    sparePrL = sparePrL / divisor;
                    filledPrL = filledPrL / divisor;
                    switch(selection) {
                        case "highScore":
                        groupStat.put(groupName, highSL);
                        break;
                        case "avgScore":
                        groupStat.put(groupName, avgSL);
                        break;
                        case "filledPct":
                        groupStat.put(groupName, filledPrL);
                        break;
                        case "strikePct":
                        groupStat.put(groupName, strikePrL);
                        break;
                        case "sparePct":
                        groupStat.put(groupName, sparePrL);
                        break;
                        case "singlePct":
                        groupStat.put(groupName, singlePPL);
                        break;
                    }
                }
                //All group stats found, print in order
                Map<String, Double> sortedMap = new HashMap<String, Double>();
                int rank = 1;
                while (groupStat.isEmpty() == false) {
                    double highest = 0;
                    String key = "";
                    for (Map.Entry<String, Double> entry : groupStat.entrySet()) {
                        if (highest < entry.getValue()) {
                            highest = entry.getValue();
                            key = entry.getKey();
                        }
                    }
                    output += rank++ + " " + key + " " + groupStat.get(key) + "\n";
                    groupStat.remove(key);
                }
                outputText.setText(output);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(listen);
    }
}
