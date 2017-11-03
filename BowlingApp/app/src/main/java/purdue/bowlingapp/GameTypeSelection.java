package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Jimmy on 11/3/2017.
 */

public class GameTypeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);


        Button standard = (Button) findViewById(R.id.standard);
        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameTypeSelection.this, ScoreKeeping.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        Button baker = (Button) findViewById(R.id.baker);
        baker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameTypeSelection.this, BakerTeamSelection.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
    }


}
