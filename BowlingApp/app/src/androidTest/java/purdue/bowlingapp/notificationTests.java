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
public class notificationTests {

    @Test
    public void notification_test() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("messages").child("test").child("notifications").child("note1").setValue("test notification1");
        mDatabase.child("messages").child("test").child("notifications").child("note2").setValue("test notification2");
        mDatabase.child("messages").child("test").child("notifications").child("note3").setValue("test notification3");
        mDatabase.child("messages").child("test").child("notifications").child("note4").setValue("test notification4");
        mDatabase.child("messages").child("test").child("notifications").child("note5").setValue("test notification5");
        mDatabase.child("messages").child("test").child("notifications").child("flag").setValue("1");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        mDatabase.child("messages").child("test").child("notifications").child("note1").setValue("");
        mDatabase.child("messages").child("test").child("notifications").child("note2").setValue("");
        mDatabase.child("messages").child("test").child("notifications").child("note3").setValue("");
        mDatabase.child("messages").child("test").child("notifications").child("note4").setValue("");
        mDatabase.child("messages").child("test").child("notifications").child("note5").setValue("");
        mDatabase.child("messages").child("test").child("notifications").child("flag").setValue("0");
    }

    @Test
    public void notification_events_date() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("messages").child("test").child("notifications").child("eventFlag").setValue("1");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        mDatabase.child("messages").child("test").child("notifications").child("eventFlag").setValue("0");
    }


}
