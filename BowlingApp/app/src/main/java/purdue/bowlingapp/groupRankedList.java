package purdue.bowlingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;


public class groupRankedList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_ranked_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String headerString = "";

        final String selection = intent.getStringExtra("selection");
        switch(selection) {
            case "highScore":
                headerString = "High Score";
                break;
            case "avgScore":
                headerString = "Average Score";
                break;
            case "filledPercentage":
                headerString = "Filled Frame Percentage";
                break;
            case "strikePercentage":
                headerString = "Strike Percentage";
                break;
            case "sparePercentage":
                headerString = "Spare Percentage";
                break;
            case "singlePinPercentage":
                headerString = "Single Pin Spare Percentage";
                break;
            default:
                headerString = " ";
        }

        TextView header = (TextView) findViewById(R.id.header);
        header.setText("Showing ranking for " + headerString);




    }

}
