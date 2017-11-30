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

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class coachMessageTest {
    @Test
    public void createMessageTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");//.child(editText.toString());

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testCoach");
                assertTrue("message not sent", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };

        myRef.child("testCoach").child("username").setValue("testCoach");
        myRef.child("testCoach").child("password").setValue("123456789");
        myRef.child("testCoach").child("email").setValue("test@email.com");
        myRef.child("testUser2").child("username").setValue("testUser2");
        myRef.child("testUser2").child("password").setValue("123456789");
        myRef.child("testUser2").child("email").setValue("test2@email.com");

        DatabaseReference myRef2 = mDatabase.child("messages").child("testUser2").child("inbox").child("1");
        myRef2.addListenerForSingleValueEvent(listen);
        myRef2.child("testCoach").setValue("test");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef2.removeEventListener(listen);
        myRef.child("testCoach").setValue(null);
        myRef.child("testUser2").setValue(null);
        mDatabase.child("messages").child("testUser2").setValue(null);

    }
}
