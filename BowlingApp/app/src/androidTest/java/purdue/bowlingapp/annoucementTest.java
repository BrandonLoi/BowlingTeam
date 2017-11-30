package purdue.bowlingapp;

import android.content.Context;
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


/**
 * Created by bloi on 11/27/17.
 */
@RunWith(AndroidJUnit4.class)

public class annoucementTest {
    @Test
    public void annoucementTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue("Announcement not in database", dataSnapshot.hasChild("announcement"));
                if (!dataSnapshot.child("announcement").getValue().toString().equals("")) {
                    String temp = dataSnapshot.child("announcement").getValue().toString();
                    mDatabase.child("announcement").setValue("TEST ANNOUNCEMENT SYSTEM");
                    mDatabase.child("announcement").setValue(temp);
                }
                else {
                    mDatabase.child("announcement").setValue("TEST ANNOUNCEMENT SYSTEM");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        assertTrue("connection failure in test",false);
                    }
                    mDatabase.child("announcement").setValue("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }

        };
        mDatabase.addListenerForSingleValueEvent(listen);

    }
}
