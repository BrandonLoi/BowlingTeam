package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class groupRankActivity extends AppCompatActivity {
    String selection;
    Button highScore;
    Button avgScore;
    Button strikePct;
    Button sparePct;
    Button singlePct;
    Button filledPct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_rank);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selection = "";

        highScore = (Button) findViewById(R.id.highScoreGroup);
        avgScore = (Button) findViewById(R.id.averageScoreGroup);
        strikePct = (Button) findViewById(R.id.strikePercentGroup);
        sparePct = (Button) findViewById(R.id.sparePercentGroup);
        singlePct = (Button) findViewById(R.id.singlePercentGroup);
        filledPct = (Button) findViewById(R.id.filledFramePercentGroup);

        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "highScore";
                Intent i = new Intent(groupRankActivity.this, groupRankedList.class);
                i.putExtra("selection", selection);
                startActivity(i);
            }

        });
        avgScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "avgScore";
                Intent i = new Intent(groupRankActivity.this, groupRankedList.class);
                i.putExtra("selection", selection);
                startActivity(i);
            }
        });
        strikePct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "strikePct";
                Intent i = new Intent(groupRankActivity.this, groupRankedList.class);
                i.putExtra("selection", selection);
                startActivity(i);
            }
        });
        sparePct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "sparePct";
                Intent i = new Intent(groupRankActivity.this, groupRankedList.class);
                i.putExtra("selection", selection);
                startActivity(i);
            }
        });
        singlePct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "singlePct";
                Intent i = new Intent(groupRankActivity.this, groupRankedList.class);
                i.putExtra("selection", selection);
                startActivity(i);
            }
        });
        filledPct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "filledPct";
                Intent i = new Intent(groupRankActivity.this, groupRankedList.class);
                i.putExtra("selection", selection);
                startActivity(i);
            }
        });
    }
}
