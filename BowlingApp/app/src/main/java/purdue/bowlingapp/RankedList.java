package purdue.bowlingapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class RankedList extends AppCompatActivity {
    public DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        final String selection = intent.getStringExtra("selection");
        String headerString = "";
        switch(selection) {
            case "highScore":
                headerString = "High Score";
                break;
            case "avgScore":
                headerString = "Average Score";
                break;
            case "filledPercentage":
                headerString = "Filled Frame Percentage";
                break;
            case "strikePercentage":
                headerString = "Strike Percentage";
                break;
            case "sparePercentage":
                headerString = "Spare Percentage";
                break;
            case "singlePinPercentage":
                headerString = "Single Pin Spare Percentage";
                break;
            default:
                headerString = " ";
        }
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Showing ranking for " + headerString);
        DatabaseReference data = mDatabase.child("data"); // points reference to data in DB
        final LinearLayout linear = (LinearLayout) findViewById(R.id.llayout);
        final TextView tv = (TextView) findViewById(R.id.output); // TextView to add to layout

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> d = dataSnapshot.getChildren(); // all children of data
                ArrayList<String> scores = new ArrayList<>();
                ArrayList<String> names = new ArrayList<>();
                //converts every person in data's high scores to Strings
                for (DataSnapshot x : d) {
                    scores.add(x.child(selection).getValue().toString());
                    names.add(x.getKey());
                }
                double[] arr = new double[scores.size()];
                //converts string high scores to ints
                for(int i = 0; i < scores.size(); i++) {
                    if (selection.equals("highScore") || selection.equals("avgScore"))
                        arr[i] = (double) Integer.parseInt(scores.get(i));
                    else
                        arr[i] = Double.parseDouble(scores.get(i));
                }
                Arrays.sort(arr); //sort array to get correct ranking order
                String out = "";
                for(int i = arr.length-1; i >= 0; i--) {
                    String temp = "";
                    temp += arr[i];
                    if(selection.equals("highScore") || selection.equals("avgScore"))
                        temp = temp.substring(0,temp.length()-2);
                    //loops through all data to find the user who corresponds with the score
                    for(int j = 0; j < scores.size(); j++) {
                        if (scores.get(j).equals(temp)) {
                            if (arr[i] < 0)
                                temp = "No Data";
                            // Puts users name, 3 tabs and then their high score
                            out += arr.length - i + ": " + names.get(j) + "\t\t\t\t\t\t\t" + temp + "\n";
                            names.remove(j);
                            scores.remove(j);
                            break;
                        }
                    }
                }
                tv.setText(out); //sets text of TextView to add to the output
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        data.addListenerForSingleValueEvent(listen);
    }
}
