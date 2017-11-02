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
public class liveTournamentTest {

    String defaultLive;

    /**
     * Checks if initiate tournament correctly updates the currentlyLive variable in the database
     */
    @Test
    public void initiateTournamentTest() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currDataPath = "liveTournament";
        final DatabaseReference liveTournament = database.getReference(currDataPath);

        liveTournament.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                defaultLive = dataSnapshot.child("currentlyLive").getValue().toString();
                liveTournament.child("currentlyLive").setValue("0");
                assertTrue(dataSnapshot.hasChild("currentlyLive"));
                String checkValue = dataSnapshot.child("currentlyLive").getValue().toString();
                assertEquals(checkValue, "0");
                liveTournament.child("currentlyLive").setValue(defaultLive);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
            }
        });
    }

    /**
     * Checks if end tournament correctly updates the currentlyLive variable in the database
     */
    @Test
    public void endTournamentTest() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String currDataPath = "liveTournament";
        final DatabaseReference liveTournament = database.getReference(currDataPath);

        liveTournament.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                defaultLive = dataSnapshot.child("currentlyLive").getValue().toString();
                liveTournament.child("currentlyLive").setValue("1");
                assertTrue(dataSnapshot.hasChild("currentlyLive"));
                String checkValue = dataSnapshot.child("currentlyLive").getValue().toString();
                assertEquals(checkValue, "1");
                liveTournament.child("currentlyLive").setValue(defaultLive);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
            }
        });
    }
}