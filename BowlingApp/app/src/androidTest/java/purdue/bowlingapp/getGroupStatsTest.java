package purdue.bowlingapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class getGroupStatsTest {
    @Test
    public void userCreateTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("groups");

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    lookupGroupStats(x);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }
    public void lookupGroupStats(DataSnapshot group) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase;
        final String groupName = group.getKey();



        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String playerN;
                String avgS;
                String highS;
                String gameN;
                String singlePP;
                String strikePr;
                String sparePr;
                String filledPr;

                double avgSL = 0;
                double highSL = 0;
                double gameNL = 0;
                double singlePPL = 0;
                double strikePrL = 0;
                double sparePrL = 0;
                double filledPrL = 0;
                if (dataSnapshot.child("groups").hasChild(groupName) && !(groupName.matches(""))) {
                    double divisor = dataSnapshot.child("groups").child(groupName).getChildrenCount();
                    playerN = Double.toString(dataSnapshot.child("groups").child(groupName).getChildrenCount());

                    for (DataSnapshot iterator : dataSnapshot.child("groups").child(groupName).getChildren()) {
                        DataSnapshot x = (dataSnapshot.child("data").child(iterator.getKey()));
                        if (x.child("avgScore").getValue().equals("-1")) {

                        } else {
                            avgSL = avgSL + Double.valueOf(x.child("avgScore").getValue().toString());
                        }
                        if (x.child("highScore").getValue().equals("-1")) {

                        } else {
                            highSL = highSL + Double.valueOf(x.child("highScore").getValue().toString());
                        }
                        if (x.child("numGames").getValue().equals("-1")) {

                        } else {
                            gameNL = gameNL + Double.valueOf(x.child("numGames").getValue().toString());
                        }
                        if (x.child("singlePinPercentage").getValue().equals("-1")) {

                        } else {
                            singlePPL = singlePPL + Double.valueOf(x.child("singlePinPercentage").getValue().toString());
                        }
                        if (x.child("strikePercentage").getValue().equals("-1")) {

                        } else {
                            strikePrL = strikePrL + Double.valueOf(x.child("strikePercentage").getValue().toString());
                        }
                        if (x.child("sparePercentage").getValue().equals("-1")) {

                        } else {
                            sparePrL = sparePrL + Double.valueOf(x.child("sparePercentage").getValue().toString());
                        }
                        if (x.child("filledPercentage").getValue().equals("-1")) {

                        } else {
                            filledPrL = filledPrL + Double.valueOf(x.child("filledPercentage").getValue().toString());
                        }
                    }
                    avgSL = avgSL / divisor;
                    highSL = highSL / divisor;
                    gameNL = gameNL / divisor;
                    singlePPL = singlePPL / divisor;
                    strikePrL = strikePrL / divisor;
                    sparePrL = sparePrL / divisor;
                    filledPrL = filledPrL / divisor;

                    avgS = Double.toString(avgSL);
                    highS = Double.toString(highSL);
                    gameN = Double.toString(gameNL);
                    singlePP = Double.toString(singlePPL);
                    strikePr = Double.toString(strikePrL);
                    sparePr = Double.toString(sparePrL);
                    filledPr = Double.toString(filledPrL);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}