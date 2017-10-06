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
        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Showing ranking for " + selection);
        DatabaseReference data = mDatabase.child("data"); // points reference to data in DB
        final LinearLayout linear = (LinearLayout) findViewById(R.id.llayout);
        final TextView tv = new TextView(this); // TextView to add to layout
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
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
                int[] arr = new int[scores.size()];
                //converts string high scores to ints
                for(int i = 0; i < scores.size(); i++) {
                    arr[i] = Integer.parseInt(scores.get(i));
                }
                Arrays.sort(arr); //sort array to get correct ranking order
                String out = "";
                for(int i = arr.length-1; i >= 0; i--) {
                    String temp = "";
                    temp += arr[i];
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
                linear.addView(tv); //adds TextView to the bottom of LinearLayout
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        data.addListenerForSingleValueEvent(listen);
    }
}
