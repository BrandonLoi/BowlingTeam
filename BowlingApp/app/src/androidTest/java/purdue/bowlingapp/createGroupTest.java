package purdue.bowlingapp;

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
public class createGroupTest {
    @Test
    public void createGroupTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");//.child(editText.toString());

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testGroup");
                assertTrue("group not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };

        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456789");
        myRef.child("testUser").child("email").setValue("test@email.com");
        DatabaseReference myRef2 = mDatabase.child("data").child("testUser");
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
        DatabaseReference myRef3 = mDatabase.child("groups");



        myRef3.addListenerForSingleValueEvent(listen);


        myRef3.child("testGroup").child("testUser").setValue("1");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef3.removeEventListener(listen);
        myRef3.child("testGroup").setValue(null);
        myRef.child("testUser").setValue(null);
        mDatabase.child("data").child("testUser").setValue(null);

    }
}
