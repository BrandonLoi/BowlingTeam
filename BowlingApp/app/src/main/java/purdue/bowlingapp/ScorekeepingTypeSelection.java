package purdue.bowlingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScorekeepingTypeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorekeeping_type_selection);

        Button standard = (Button) findViewById(R.id.standard);
        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScorekeepingTypeSelection.this, ScoreKeeping.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                i.putExtra("type", "0");
                startActivity(i);
            }
        });

        Button pins = (Button) findViewById(R.id.pins);
        pins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScorekeepingTypeSelection.this, PinInput.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
    }
}
