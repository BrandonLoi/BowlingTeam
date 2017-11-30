package purdue.bowlingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_display);

        String username = getIntent().getStringExtra("username");
        final String selection = getIntent().getStringExtra("selection");

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = mDatabase.child("data").child(username);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(selection))
                    return;
                GraphView graph = (GraphView) findViewById(R.id.graph);
                int g1 = Integer.parseInt(dataSnapshot.child(selection).child("1").getValue().toString());
                int g2 = Integer.parseInt(dataSnapshot.child(selection).child("2").getValue().toString());
                int g3 = Integer.parseInt(dataSnapshot.child(selection).child("3").getValue().toString());
                int g4 = Integer.parseInt(dataSnapshot.child(selection).child("4").getValue().toString());
                int g5 = Integer.parseInt(dataSnapshot.child(selection).child("5").getValue().toString());
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                        new DataPoint(1, g1),
                        new DataPoint(2, g2),
                        new DataPoint(3, g3),
                        new DataPoint(4, g4),
                        new DataPoint(5, g5)
                });
                graph.addSeries(series);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
