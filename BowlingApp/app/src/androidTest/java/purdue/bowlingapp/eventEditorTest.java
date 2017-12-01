package purdue.bowlingapp;

/**
 * Created by Hunter on 11/30/2017.
 */

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

public class eventEditorTest {
    @Test
    public void createEventTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("events");//.child(editText.toString());

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("");
                assertTrue("event not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef.addListenerForSingleValueEvent(listen);

        myRef.child("testEvent").child("date").setValue("11/11/1111");
        myRef.child("testEvent").child("type").setValue("TOURNAMENT");
        myRef.child("testEvent").child("type").setValue("PRACTICE");
        myRef.child("testEvent").child("players").child("testPlayer").setValue("0");
        myRef.child("testEvent").child("players").child("testPlayer2").setValue("0");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.removeEventListener(listen);
        myRef.child("testEvent").setValue(null);
    }
}
