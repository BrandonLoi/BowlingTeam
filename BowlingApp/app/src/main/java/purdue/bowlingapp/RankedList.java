package purdue.bowlingapp;

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
        DatabaseReference data = mDatabase.child("data"); // points reference to data in DB
        final LinearLayout linear = (LinearLayout) findViewById(R.id.llayout);
        final TextView tv = new TextView(this); // TextView to add to layout
        tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<DataSnapshot> d = (ArrayList<DataSnapshot>) dataSnapshot.getChildren(); // all children of data
                //tv.setText("" + d.size());
                //linear.addView(tv);

                /*
                ArrayList<String> s = new ArrayList<>();
                //converts every person in data's high scores to Strings
                for (DataSnapshot x : d) {
                    s.add(x.child("highScore").toString());
                }
                int[] arr = new int[s.size()];
                //converts string high scores to ints
                for(int i = 0; i < s.size(); i++) {
                    arr[i] = Integer.parseInt(s.get(i));
                }
                Arrays.sort(arr); //sort array to get correct ranking order
                for(int i = 0; i < arr.length; i++) {
                    String temp = "";
                    temp += arr[i];
                    //loops through all data to find the user who corresponds with the score
                    for(DataSnapshot x : d) {
                        if (x.child("highScore").toString().equals(temp)) {
                            // Puts users name, 3 tabs and then their high score
                            String out = i + ": " + x.toString() + "           " + temp;
                            tv.setText(out); //sets text of TextView to add to the output
                            linear.addView(tv); //adds TextView to the bottom of LinearLayout
                            break;
                        }
                    }
                } */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        data.addListenerForSingleValueEvent(listen);
    }
}
