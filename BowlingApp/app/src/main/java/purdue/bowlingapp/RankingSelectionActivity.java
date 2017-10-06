package purdue.bowlingapp;

import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RankingSelectionActivity extends AppCompatActivity {
    String selection = "";
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_selection);
        Button highScore = (Button) findViewById(R.id.highScore);
        Button avgScore = (Button) findViewById(R.id.averageScore);
        Button strikePct = (Button) findViewById(R.id.strikePercent);
        Button sparePct = (Button) findViewById(R.id.sparePercent);
        Button singlePct = (Button) findViewById(R.id.singlePercent);
        Button filledPct = (Button) findViewById(R.id.filledFramePercent);
        Button goBtn = (Button) findViewById(R.id.goBtn);
        final TextView display = (TextView) findViewById(R.id.currentSelection);

        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "highScore";
                display.setText("High Score");
                valid = true;
            }
        });

        avgScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "avgScore";
                display.setText("Average Score");
                valid = true;
            }
        });

        strikePct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "strikePercentage";
                display.setText("Strike Percentage");
            }
        });

        sparePct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "sparePercentage";
                display.setText("Spare Percentage");
            }
        });

        singlePct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "singlePinPercentage";
                display.setText("Single Pin Spare Percentage");
            }
        });

        filledPct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "filledPercentage";
                display.setText("Filled Frame Percentage");
            }
        });


        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!valid) {
                    //display.setText("Option currently under development!");
                    return;
                }
                if (!selection.equals("")) {
                    Intent i = new Intent(RankingSelectionActivity.this,
                            RankedList.class);
                    i.putExtra("selection", selection);
                    startActivity(i);
                }
            }
        });

    }
}
