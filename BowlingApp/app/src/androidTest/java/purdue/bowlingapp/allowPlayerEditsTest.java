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
public class allowPlayerEditsTest {

    /**
     * Checks if enabling player edits for a specific player correctly updates the database
     */
    @Test
    public void enablePlayerEditsTest() {

        // Create a new user
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
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456789");
        myRef.child("testUser").child("email").setValue("test@email.com");
        myRef.child("testUser").child("canEdit").setValue("0");

        // Check if testUser can now update his stats
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("testUser").child("canEdit").setValue("1");
                assertEquals(dataSnapshot.child("testUser").child("canEdit").getValue(), "1");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };

        // Remove testUser
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.removeEventListener(listen);
        myRef.removeEventListener(listener);
        myRef.child("testUser").setValue(null);
    }

    /**
     * Checks if disabling player edits for a specific player correctly updates the database
     */
    @Test
    public void disablePlayerEditsTest() {

        // Create a new user
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
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456789");
        myRef.child("testUser").child("email").setValue("test@email.com");
        myRef.child("testUser").child("canEdit").setValue("0");

        // Check if testUser can now update his stats
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("testUser").child("canEdit").setValue("1");
                assertEquals(dataSnapshot.child("testUser").child("canEdit").getValue(), "1");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };

        // Check if testUser can no longer update his stats
        ValueEventListener listening = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("testUser").child("canEdit").setValue("0");
                assertEquals(dataSnapshot.child("testUser").child("canEdit").getValue(), "0");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };

        // Remove testUser
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test",false);
        }
        myRef.removeEventListener(listen);
        myRef.removeEventListener(listener);
        myRef.removeEventListener(listening);
        myRef.child("testUser").setValue(null);
    }
}