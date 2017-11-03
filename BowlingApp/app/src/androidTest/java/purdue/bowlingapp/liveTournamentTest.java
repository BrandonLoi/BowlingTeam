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
                final DatabaseReference tournamentRef = liveTournament.child("totalStatistics");

                defaultLive = dataSnapshot.child("currentlyLive").getValue().toString();
                String defaultSingleMade = dataSnapshot.child("singleMade").getValue().toString();
                String defaultSingleLeft = dataSnapshot.child("singleLeft").getValue().toString();
                String defaultSplitMade = dataSnapshot.child("splitMade").getValue().toString();
                String defaultSplitLeft = dataSnapshot.child("splitLeft").getValue().toString();
                String defaultMultiMade = dataSnapshot.child("multiMade").getValue().toString();
                String defaultMultiLeft = dataSnapshot.child("multiLeft").getValue().toString();
                String defaultStrikes = dataSnapshot.child("numStrikes").getValue().toString();
                String defaultTotal = dataSnapshot.child("cumulativeScore").getValue().toString();
                String defaultNumGames = dataSnapshot.child("numGames").getValue().toString();
                String defaultBallsThrown = dataSnapshot.child("ballsThrown").getValue().toString();
                String defaultHighScore = dataSnapshot.child("highScore").getValue().toString();
                String defaultPrevFilled = dataSnapshot.child("filledFrames").getValue().toString();
                String defaultAvgScore = dataSnapshot.child("avgScore").getValue().toString();
                String defaultFilledPercentage = dataSnapshot.child("filledPercentage").getValue().toString();
                String defaultPinPercentage = dataSnapshot.child("pinPercentage").getValue().toString();
                String defaultStrikePercentage = dataSnapshot.child("strikePercentage").getValue().toString();
                String defaultSparePercentage = dataSnapshot.child("sparePercentage").getValue().toString();


                liveTournament.child("currentlyLive").setValue("0");
                assertTrue(dataSnapshot.hasChild("currentlyLive"));

                tournamentRef.child("singleLeft").setValue("0");
                tournamentRef.child("singleMade").setValue("0");
                tournamentRef.child("splitLeft").setValue("0");
                tournamentRef.child("splitMade").setValue("0");
                tournamentRef.child("multiLeft").setValue("0");
                tournamentRef.child("multiMade").setValue("0");
                tournamentRef.child("numStrikes").setValue("0");
                tournamentRef.child("cumulativeScore").setValue("0");
                tournamentRef.child("numGames").setValue("0");
                tournamentRef.child("ballsThrown").setValue("0");
                tournamentRef.child("filledFrames").setValue("0");
                tournamentRef.child("highScore").setValue("-1");
                tournamentRef.child("avgScore").setValue("-1");
                tournamentRef.child("filledPercentage").setValue("-1");
                tournamentRef.child("singlePinPercentage").setValue("-1");
                tournamentRef.child("sparePercentage").setValue("-1");
                tournamentRef.child("strikePercentage").setValue("-1");

                String checkValue = dataSnapshot.child("currentlyLive").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("singleMade").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("splitLeft").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("splitMade").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("multiLeft").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("multiMade").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("numStrikes").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("cumulativeScore").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("numGames").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("ballsThrown").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("filledFrames").getValue().toString();
                assertEquals(checkValue, "0");
                checkValue = dataSnapshot.child("highScore").getValue().toString();
                assertEquals(checkValue, "-1");
                checkValue = dataSnapshot.child("avgScore").getValue().toString();
                assertEquals(checkValue, "-1");
                checkValue = dataSnapshot.child("filledPercentage").getValue().toString();
                assertEquals(checkValue, "-1");
                checkValue = dataSnapshot.child("singlePinPercentage").getValue().toString();
                assertEquals(checkValue, "-1");
                checkValue = dataSnapshot.child("sparePercentage").getValue().toString();
                assertEquals(checkValue, "-1");
                checkValue = dataSnapshot.child("strikePercentage").getValue().toString();
                assertEquals(checkValue, "-1");


                liveTournament.child("currentlyLive").setValue(defaultLive);
                tournamentRef.child("singleLeft").setValue(defaultSingleLeft);
                tournamentRef.child("singleMade").setValue(defaultSingleMade);
                tournamentRef.child("splitLeft").setValue(defaultSplitLeft);
                tournamentRef.child("splitMade").setValue(defaultSplitMade);
                tournamentRef.child("multiLeft").setValue(defaultMultiLeft);
                tournamentRef.child("multiMade").setValue(defaultMultiMade);
                tournamentRef.child("numStrikes").setValue(defaultStrikes);
                tournamentRef.child("cumulativeScore").setValue(defaultTotal);
                tournamentRef.child("numGames").setValue(defaultNumGames);
                tournamentRef.child("ballsThrown").setValue(defaultBallsThrown);
                tournamentRef.child("filledFrames").setValue(defaultPrevFilled);
                tournamentRef.child("highScore").setValue(defaultHighScore);
                tournamentRef.child("avgScore").setValue(defaultAvgScore);
                tournamentRef.child("filledPercentage").setValue(defaultFilledPercentage);
                tournamentRef.child("singlePinPercentage").setValue(defaultPinPercentage);
                tournamentRef.child("sparePercentage").setValue(defaultSparePercentage);
                tournamentRef.child("strikePercentage").setValue(defaultStrikePercentage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Retrieving currentlyLive variable failed: " + databaseError.getCode());
            }
        });
    }
}