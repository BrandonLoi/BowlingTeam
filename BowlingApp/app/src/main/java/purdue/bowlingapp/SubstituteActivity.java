package purdue.bowlingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Jimmy on 11/2/2017.
 */

public class SubstituteActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute);
    }

    public void toScoreKeeping(View view) {
        final EditText editText = (EditText) findViewById(R.id.usernameField);
        Intent intent = new Intent(this, SubstituteActivity.class);
        startActivity(intent);
    }



}
