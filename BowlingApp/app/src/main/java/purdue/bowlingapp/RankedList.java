package purdue.bowlingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class RankedList extends AppCompatActivity {
    public DatabaseReference mDatabase;
    LinearLayout linear = (LinearLayout) findViewById(R.id.layout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_list);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference data = mDatabase.child("data"); // points reference to data in DB
        final TextView tv = new TextView(this); // TextView to add to layout
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> d = dataSnapshot.getChildren(); // all children of data
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
                            linear.addView(tv); //adds TextView to the top of LinearLayout
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
