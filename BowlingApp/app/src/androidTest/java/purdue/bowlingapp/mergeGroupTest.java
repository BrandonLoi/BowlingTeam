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
public class mergeGroupTest {
    @Test
    public void mergeGroupTest() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("groups");


        //Create new group testGroup1
        myRef.child("testGroup1").child("testuser1").setValue("1");
        myRef.child("testGroup1").child("testuser2").setValue("0");
        //Create new group testGroup2
        myRef.child("testGroup2").child("testuser3").setValue("1");
        myRef.child("testGroup2").child("testuser4").setValue("0");
        myRef.child("testGroup2").child("testuser1").setValue("0");

        mergeGroups(myRef.child("testGroup1"), myRef.child("testGroup2"), "testGroup3");
    }

    public void mergeGroups(DatabaseReference a, DatabaseReference b, final String newGroupName) {
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("groups");
        final ArrayList<String> names = new ArrayList<String>();

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot name : dataSnapshot.getChildren()) {
                    names.add(name.getKey().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        a.addListenerForSingleValueEvent(listen);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }

        a.removeEventListener(listen);
        b.addListenerForSingleValueEvent(listen);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }

        b.removeEventListener(listen);
        for (String name: names) {
            myRef.child(newGroupName).child(name).setValue("1");
        }
        a.setValue(null);
        b.setValue(null);

        ValueEventListener l = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild(newGroupName);
                assertTrue(dataSnapshot.hasChild(newGroupName));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };



        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.addListenerForSingleValueEvent(l);
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.removeEventListener(l);
        myRef.child(newGroupName).setValue(null);
    }
}