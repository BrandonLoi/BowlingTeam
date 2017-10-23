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
public class userCreateTest {
    @Test
    public void userCreateTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");//.child(editText.toString());

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testUser");
                assertTrue("user not added to users directory correctly", dataSnapshot.hasChild("testUser"));
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
        myRef2.child("numGames").setValue("-1");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.addListenerForSingleValueEvent(listen);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.removeEventListener(listen);
        myRef.child("testUser").setValue(null);
        mDatabase.child("data").child("testUser").setValue(null);
    }
}
/*
NOTE: This test will fail when debugging due to the listener mistakenly checking data AGAIN after deleting test data

 */