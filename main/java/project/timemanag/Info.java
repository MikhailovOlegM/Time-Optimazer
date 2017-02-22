package project.timemanag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Info extends AppCompatActivity implements View.OnClickListener {

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ImageButton back = (ImageButton) findViewById(R.id.backButton);
        back.setOnClickListener(this);

        getSupportActionBar().hide();

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}