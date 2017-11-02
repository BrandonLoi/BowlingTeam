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

@RunWith(AndroidJUnit4.class)
public class createRequestTest {
    @Test
    public void userCreateTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");
        final DatabaseReference myRef5 = mDatabase;



        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.child("messages").child("testUser").child("req").child("join").child("GROUP").hasChild("testUser2");
                assertTrue("request not sent correctly", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }

        };

        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456789");
        myRef.child("testUser").child("email").setValue("test@email.com");
        final DatabaseReference myRef2 = mDatabase.child("data").child("testUser");
        myRef2.child("avgScore").setValue("-1");
        myRef2.child("highScore").setValue("-1");
        myRef2.child("sparePercentage").setValue("-1");
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

        myRef.child("testUser2").child("username").setValue("testUser2");
        myRef.child("testUser2").child("password").setValue("123456789");
        myRef.child("testUser2").child("email").setValue("test2@email.com");
        final DatabaseReference myRef4 = mDatabase.child("data").child("testUser2");
        myRef4.child("avgScore").setValue("-1");
        myRef4.child("highScore").setValue("-1");
        myRef4.child("sparePercentage").setValue("-1");
        myRef4.child("strikePercentage").setValue("-1");
        myRef4.child("filledPercentage").setValue("-1");
        myRef4.child("singlePinPercentage").setValue("-1");
        myRef4.child("strikePercentage").setValue("-1");
        myRef4.child("numGames").setValue("0");
        myRef4.child("singleMade").setValue("0");
        myRef4.child("singleLeft").setValue("0");
        myRef4.child("splitMade").setValue("0");
        myRef4.child("splitLeft").setValue("0");
        myRef4.child("multiMade").setValue("0");
        myRef4.child("multiLeft").setValue("0");
        myRef4.child("numStrikes").setValue("0");
        myRef4.child("ballsThrown").setValue("0");
        myRef4.child("cumulativeScore").setValue("0");
        myRef4.child("filledFrames").setValue("0");

        DatabaseReference myRef3 = mDatabase.child("groups");
        myRef3.child("testGroup").child("testUser").setValue("1");

        DatabaseReference myRef6 = mDatabase.child("messages").child("testUser").child("req").child("join").child("GROUP").child("testUser2");
        myRef6.setValue("testGroup");


        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef5.addListenerForSingleValueEvent(listen);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef5.removeEventListener(listen);
        mDatabase.child("users").child("testUser").setValue(null);
        mDatabase.child("users").child("testUser2").setValue(null);
        mDatabase.child("groups").child("testGroup").setValue(null);
        mDatabase.child("data").child("testUser").setValue(null);
        mDatabase.child("data").child("testUser2").setValue(null);
        mDatabase.child("messages").child("testUser").setValue(null);

    }
}
