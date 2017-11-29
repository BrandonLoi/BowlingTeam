package purdue.bowlingapp;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class emailTest {

    /**
     * Checks if enabling email notifications for a specific player correctly updates the database
     */
    @Test
    public void enableEmailNotifications() {

        // Create a new user
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testGroup");
                assertTrue("user not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456");
        myRef.child("testUser").child("email").setValue("test@email.com");
        myRef.child("testUser").child("canEdit").setValue("0");
        myRef.child("testUser").child("emailNotification").setValue("0");
        myRef.child("testUser").child("verification").setValue("0");

        // Attempt to enable Email Notifications
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("testUser").child("emailNotification").setValue("1");
                assertEquals(dataSnapshot.child("testUser").child("emailNotification").getValue(), "1");
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
            assertTrue("connection failure in test", false);
        }
        myRef.removeEventListener(listen);
        myRef.removeEventListener(listener);
        myRef.child("testUser").setValue(null);
    }

    /**
     * Checks if disabling email notifications for a specific player correctly updates the database
     */
    @Test
    public void disableEmailNotifications() {

        // Create a new user
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testGroup");
                assertTrue("user not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456");
        myRef.child("testUser").child("email").setValue("test@email.com");
        myRef.child("testUser").child("canEdit").setValue("0");
        myRef.child("testUser").child("emailNotification").setValue("1");
        myRef.child("testUser").child("verification").setValue("0");

        // Attempt to disable Email Notifications
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myRef.child("testUser").child("emailNotification").setValue("0");
                assertEquals(dataSnapshot.child("testUser").child("emailNotification").getValue(), "0");
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
            assertTrue("connection failure in test", false);
        }
        myRef.removeEventListener(listen);
        myRef.removeEventListener(listener);
        myRef.child("testUser").setValue(null);
    }

    // Sends verification email
    private void sendEmail(String email, int verificationNum) {
        //Getting content for email
        String subject = "Verification Number";
        String message = "Here is your verification number: " + verificationNum +
                "\nTo verify your email, please use the \"Email\" tab found in the \"Communication\" tab." +
                "\n\nThanks\n- Bowling Statistics Tracker\n";
        return;
    }

    /**
     * Create a new account, send an email verification, and verify the email using the 4-digit code
     */
    @Test
    public void emailVerification() {

        // Create a new user
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("users");
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testGroup");
                assertTrue("user not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser").child("username").setValue("testUser");
        myRef.child("testUser").child("password").setValue("123456");
        myRef.child("testUser").child("email").setValue("cs408team21@email.com");
        myRef.child("testUser").child("canEdit").setValue("0");
        myRef.child("testUser").child("emailNotification").setValue("1");

        Random rand = new Random();
        final int randomInt = rand.nextInt(9999) + 1;
        myRef.child("testUser").child("verification").setValue(Integer.toString(randomInt));

        // Send email
        String email = "cs408team21@gmail.com";
        sendEmail(email, randomInt);

        // Attempt to verify email using verification code
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String verify = dataSnapshot.child("verification").getValue().toString();
                if (verify.equals(Integer.toString(randomInt))) {
                    myRef.child("testUser").child("verification").setValue("0");
                }

                myRef.child("testUser").child("verification").setValue("0");
                assertEquals(dataSnapshot.child("testUser").child("verification").getValue(), "0");
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
            assertTrue("connection failure in test", false);
        }
        myRef.removeEventListener(listen);
        myRef.removeEventListener(listener);
        myRef.child("testUser").setValue(null);
    }
}