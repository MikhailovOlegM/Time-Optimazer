package project.timemanag;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Time extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer mPlayer;

    Button btnStartWork, startRelax;
    ImageButton back;
    TextView textViewTime;
    TextView otherTime;

    protected int countWorkTime = 0;
    protected int countRestTime = 0;

    private boolean statusVibro;
    private boolean statusMusic;
    private boolean statusFinishMusic = false;

    private int timeWork = 25;
    private int timeRelax = 5;
    private int clickWork = 0;
    private int clickRest = 0;

    private boolean statusWork = false;
    private boolean statusRest = false;
    CounterOtherTime counterOtherTime = new CounterOtherTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mPlayer=MediaPlayer.create(this, R.raw.gun);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });

        getSupportActionBar().hide();

        back = (ImageButton) findViewById(R.id.back);
        assert back != null;
        back.setOnClickListener(this);
        btnStartWork = (Button) findViewById(R.id.btnStart);
        startRelax = (Button) findViewById(R.id.btnRelax);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        otherTime = (TextView) findViewById(R.id.otherTime);


        takeSettings();

        final CounterClass timer = new CounterClass(timeWork*60000, 1000);
        btnStartWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickWork++;
                countWorkTime = (clickWork * timeWork)/1000;
                System.out.println(countWorkTime);
                statusWork = false;
                statusRest = false;
                timer.start();
            }
        });

        final CounterRestTime timerRelax = new CounterRestTime(timeRelax*60000, 1000);
        startRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRest++;
                countRestTime = (clickRest * timeRelax)/1000;
                System.out.println(countRestTime);
                statusRest = false;
                statusWork = false;
                timerRelax.start();
            }
        });


        counterOtherTime.runTimer();


    }

    private void vibro(boolean statusVibro) {
        if (statusVibro) {
            long mills = 3000L;
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(mills);
        }
    }

    private void takeSettings() {
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1 != null) {
            timeWork = bundle1.getInt("workMenu");
            timeRelax = bundle1.getInt("restMenu");
            statusVibro=bundle1.getBoolean("vibroMenu");
            statusMusic = bundle1.getBoolean("musicMenu");
        }
    }

    private void stop(){
        mPlayer.stop();
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void startAudio(boolean statusMusic, boolean statusFinishMusic){
        if(statusMusic && statusFinishMusic) {
            mPlayer.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, Menu.class);
                intent.putExtra("statworkTime",(float) countWorkTime);
                intent.putExtra("statrestTime", (float)countRestTime);

                intent.putExtra("workTime", timeWork);
                intent.putExtra("restTime", timeRelax);
                intent.putExtra("vibroTime", statusVibro);
                intent.putExtra("musicTime", statusMusic);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)

        @Override
        public void onTick(long millisUntilFinished) {

            btnStartWork.setClickable(false);
            startRelax.setClickable(false);

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);

        }

        @Override
        public void onFinish() {
            textViewTime.setText("Go to rest");
            statusWork = true;
            statusRest = false;
            vibro(statusVibro);
            statusFinishMusic = true;
            startAudio(statusMusic, statusFinishMusic);

            btnStartWork.setClickable(false);
            startRelax.setClickable(true);
        }
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    public class CounterRestTime extends CountDownTimer {
        public CounterRestTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            btnStartWork.setClickable(false);
            startRelax.setClickable(false);

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);
        }

        @Override
        public void onFinish() {
            textViewTime.setText("Go to Work");
            statusRest = true;
            statusWork = false;
            vibro(statusVibro);
            statusFinishMusic = true;
            startAudio(statusMusic, statusFinishMusic);



            btnStartWork.setClickable(true);
            startRelax.setClickable(false);
        }
    }

    public class CounterOtherTime {
        int countOtherTime1 = 0;
        public void runTimer() {
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int minutes = (countOtherTime1 % 3600) / 60;
                    int seconds = countOtherTime1 % 60;

                    String timeOther = String.format("%02d:%02d", minutes, seconds);
                    otherTime.setText(timeOther);
                    if (statusRest || statusWork) {
                        countOtherTime1++;
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }


}
