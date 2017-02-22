package project.timemanag;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    private int seekBarWork;
    private int seekBarRest;

    TextView textView1;
    SeekBar seekBar1;
    TextView textView2;
    SeekBar seekBar2;
    Switch switch1;
    Switch switch2;
    ImageButton back;
    boolean vibro;
    boolean statusMusic;

    public int getSeekBarWork() {
        return seekBarWork;
    }

    public int getSeekBarRest() {
        return seekBarRest;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        getSupportActionBar().hide();

        back = (ImageButton) findViewById(R.id.backButton);
        assert back != null;
        back.setOnClickListener(this);

        switch1 = (Switch) findViewById(R.id.switch1);
        switch2 = (Switch) findViewById(R.id.switch2);

        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        textView1 = (TextView) findViewById(R.id.txtView1);
        textView1.setText(String.valueOf("25"));

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarWork = progress;
                System.out.println(seekBarWork);
                textView1.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        textView2 = (TextView) findViewById(R.id.txtView2);
        textView2.setText(String.valueOf("5"));
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarRest = progress;
                System.out.println(seekBarRest);
                textView2.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        switch2 = (Switch) findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vibro = isChecked;
                if (vibro) {
                    long mills = 500L;
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(mills);
                }
            }
        });
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                statusMusic = isChecked;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backButton:
                Intent intent = new Intent(this, Menu.class);
                intent.putExtra("workTime", seekBarWork);
                intent.putExtra("restTime", seekBarRest);
                intent.putExtra("vibroTime", vibro);
                intent.putExtra("musicTime", statusMusic);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}

