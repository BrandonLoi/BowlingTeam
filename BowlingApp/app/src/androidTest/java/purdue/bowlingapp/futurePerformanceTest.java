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
public class futurePerformanceTest {

    /**
     * Check the accuracy of the performance algorithm, and whether the database updates
     * correctly or not.
     */
    @Test
    public void futurePerformance() {

        // Create a new user
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("data");

        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testUser");
                assertTrue("user not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser").child("games").child("1").setValue("0");
        myRef.child("testUser").child("games").child("2").setValue("1");
        myRef.child("testUser").child("games").child("3").setValue("150");
        myRef.child("testUser").child("games").child("4").setValue("299");
        myRef.child("testUser").child("games").child("5").setValue("300");

        // Pull performance stats from the database
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int[] games = new int[5];
                games[0] = Integer.parseInt(dataSnapshot.child("testUser").child("games").child("1").getValue().toString());
                games[1] = Integer.parseInt(dataSnapshot.child("testUser").child("games").child("2").getValue().toString());
                games[2] = Integer.parseInt(dataSnapshot.child("testUser").child("games").child("3").getValue().toString());
                games[3] = Integer.parseInt(dataSnapshot.child("testUser").child("games").child("4").getValue().toString());
                games[4] = Integer.parseInt(dataSnapshot.child("testUser").child("games").child("5").getValue().toString());

                int totalGames = 0;
                int totalScore = 0;
                for (int g : games) {
                    if (g != 0) {
                        totalGames++;
                        totalScore += g;
                    }
                }

                // Test the resulting future score using the performance algorithm
                assertEquals(Integer.toString(totalScore / totalGames), "187");
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
        myRef.child("testUser").setValue(null);
    }

    /**
     * Check the accuracy of the performance algorithm for a group of players, and whether the database updates
     * correctly or not.
     */
    @Test
    public void futurePerformanceGroup() {

        // Create two new users
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference myRef = mDatabase.child("data");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean x = dataSnapshot.hasChild("testUser");
                assertTrue("user not created", x);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getCode());
            }
        };
        myRef.child("testUser1").child("games").child("1").setValue("0");
        myRef.child("testUser1").child("games").child("2").setValue("1");
        myRef.child("testUser1").child("games").child("3").setValue("150");
        myRef.child("testUser1").child("games").child("4").setValue("299");
        myRef.child("testUser1").child("games").child("5").setValue("300");

        myRef.child("testUser2").child("games").child("1").setValue("50");
        myRef.child("testUser2").child("games").child("2").setValue("100");
        myRef.child("testUser2").child("games").child("3").setValue("125");
        myRef.child("testUser2").child("games").child("4").setValue("275");
        myRef.child("testUser2").child("games").child("5").setValue("0");

        // Create a group for the two users
        final DatabaseReference myRef2 = mDatabase.child("groups");

        myRef2.child("testGroup").child("testUser1").setValue("1");
        myRef2.child("testGroup").child("testUser2").setValue("0");

        final DatabaseReference myRef3 = mDatabase;
        ValueEventListener listen = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalGames = 0;
                int totalScore = 0;

                for (DataSnapshot iterator : dataSnapshot.child("groups").child("testGroup").getChildren()) {
                    DataSnapshot x = (dataSnapshot.child("data").child(iterator.getKey()));

                    int[] games = new int[5];
                    games[0] = Integer.parseInt(x.child("games").child("1").getValue().toString());
                    games[1] = Integer.parseInt(x.child("games").child("2").getValue().toString());
                    games[2] = Integer.parseInt(x.child("games").child("3").getValue().toString());
                    games[3] = Integer.parseInt(x.child("games").child("4").getValue().toString());
                    games[4] = Integer.parseInt(x.child("games").child("5").getValue().toString());

                    for (int g : games) {
                        if (g != 0) {
                            totalGames++;
                            totalScore += g;
                        }
                    }
                }

                // Test the resulting future score using the performance algorithm
                assertEquals(Integer.toString(totalScore / totalGames), "162");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Required, but we don't use. Leave blank
            }
        };
        myRef3.addListenerForSingleValueEvent(listen);

        // Remove testUser1, testUser2, and testGroup
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            assertTrue("connection failure in test", false);
        }
        myRef.removeEventListener(listen);
        myRef2.removeEventListener(listener);
        myRef.child("testUser1").setValue(null);
        myRef.child("testUser2").setValue(null);
        myRef2.child("testGroup").setValue(null);
    }
}