package project.timemanag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistic extends AppCompatActivity implements View.OnClickListener {
    ImageButton back;

    private float work;
    private float rest;
    private int timeWork;
    private int timeRelax;
    private boolean statusVibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistick);

        getSupportActionBar().hide();

        takeStatisticFromMenu();

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);

        PieChart pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setHoleRadius(20f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("");
        pieChart.setCenterTextSize(10);
        pieChart.setHoleColor(222222);

        //// TODO: 22.02.2017 добавить потом сюда work и rest, принимаем данные из Time
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(15, 0));
        entries.add(new Entry(6, 1));
        entries.add(new Entry(2f, 2));


        PieDataSet dataset = new PieDataSet(entries, "minutes");
        dataset.setValueTextSize(12);


        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Work");
        labels.add("Rest");
        labels.add("Others");


        PieData data = new PieData(labels, dataset);
        dataset.setColors(ColorTemplate.LIBERTY_COLORS);
        pieChart.setData(data);
        data.setDrawValues(true);
        pieChart.animateY(2000);

        pieChart.saveToGallery("/sd/mychart.jpg", 85);


    }

    private void takeStatisticFromMenu() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            work = bundle.getFloat("statworkTime");
            rest = bundle.getFloat("statrestTime");
            timeWork = bundle.getInt("workTime");
            System.out.println(timeWork);
            timeRelax = bundle.getInt("restTime");
            System.out.println(timeRelax);
            statusVibro=bundle.getBoolean("vibroTime");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, Menu.class);
                intent.putExtra("workTime", timeWork);
                intent.putExtra("restTime", timeRelax);
                intent.putExtra("vibroTime", statusVibro);
                intent.putExtra("statworkTime",  work);
                intent.putExtra("statrestTime",  rest);
                startActivity(intent);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

        this.finish();

        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("statworkTime", work);
        intent.putExtra("statrestTime", rest);
        startActivity(intent);

        if (Menu.transitionType == Menu.transitionType.SlideLeft) {
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }
    }
}