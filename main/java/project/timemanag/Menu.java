package project.timemanag;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    public enum TransitionType {
        SlideLeft
    }

    public static TransitionType transitionType;

    ImageButton menuTimer;

    ImageButton menuUs;

    ImageButton menuApp;

    ImageButton menuSettings;

    ImageButton menuStatistick;

    private float statisticWork;
    private float statisticRest;
    private int statisticOther;
    private int timeWork = 25 ;
    private int timeRelax = 5 ;
    private boolean statusVibro;
    private boolean statusMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        menuTimer = (ImageButton) findViewById(R.id.menuTime);
        assert menuTimer != null;
        menuTimer.setOnClickListener(this);

        menuStatistick = (ImageButton) findViewById(R.id.menuStat);
        assert menuStatistick != null;
        menuStatistick.setOnClickListener(this);

        menuUs = (ImageButton) findViewById(R.id.menuUs);
        menuUs.setOnClickListener(this);

        menuApp = (ImageButton) findViewById(R.id.menuInfo);
        assert menuApp != null;
        menuApp.setOnClickListener(this);

        menuSettings = (ImageButton) findViewById(R.id.menuSettings);
        assert menuSettings != null;
        menuSettings.setOnClickListener(this);

        menuStatistick = (ImageButton) findViewById(R.id.menuStat);
        assert menuStatistick != null;
        menuStatistick.setOnClickListener(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            timeWork = bundle.getInt("workTime");
            System.out.println(timeWork + "Menu");
            timeRelax = bundle.getInt("restTime");
            System.out.println(timeRelax + "Menu");
            statusVibro = bundle.getBoolean("vibroTime");
            statusMusic = bundle.getBoolean("musicTime");
            statisticWork = bundle.getFloat("statworkTime");//
            statisticRest = bundle.getFloat("statrestTime");//
        }


    }


    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.menuTime:
                intent = new Intent(this, Time.class);
                intent.putExtra("workMenu", timeWork);
                intent.putExtra("restMenu", timeRelax);
                intent.putExtra("vibroMenu", statusVibro);
                intent.putExtra("musicMenu", statusMusic);
                startActivity(intent);

                break;
            case R.id.menuSettings:
                intent = new Intent(this, Setting.class);
                startActivity(intent);
                break;
            case R.id.menuStat:
                this.finish();
                transitionType = TransitionType.SlideLeft;
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                intent = new Intent(this, Statistic.class);
                intent.putExtra("workTime", timeWork);
                intent.putExtra("restTime", timeRelax);
                intent.putExtra("vibroTime", statusVibro);
                intent.putExtra("musicTime", statusMusic);
                intent.putExtra("statworkTime", statisticWork);
                intent.putExtra("statrestTime", statisticRest);
                startActivity(intent);
                break;
            case R.id.menuInfo:
                intent = new Intent(this, Info.class);
                startActivity(intent);
                break;
            case R.id.menuUs:
                intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}