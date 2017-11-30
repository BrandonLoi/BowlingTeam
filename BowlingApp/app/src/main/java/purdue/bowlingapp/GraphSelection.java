package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GraphSelection extends AppCompatActivity {
    String selection = "games";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_selection);

        Button games = (Button) findViewById(R.id.games);
        Button filled = (Button) findViewById(R.id.filled);
        Button strike = (Button) findViewById(R.id.strike);
        Button single = (Button) findViewById(R.id.single);
        Button go = (Button) findViewById(R.id.go);

        final TextView display = (TextView) findViewById(R.id.display);

        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "games";
                display.setText("Current Selection: games");
            }
        });

        filled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "filledCount";
                display.setText("Current Selection: number of frames filled");
            }
        });

        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "singleCount";
                display.setText("Current Selection: single pins made");
            }
        });

        strike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = "strikeCount";
                display.setText("Current Selection: number of strikes");
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GraphSelection.this, GraphDisplay.class);
                i.putExtra("selection", selection);
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

    }
}
