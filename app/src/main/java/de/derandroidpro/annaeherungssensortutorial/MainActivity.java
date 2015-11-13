package de.derandroidpro.annaeherungssensortutorial;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    RelativeLayout contentlayout;
    TextView tv;

    SensorManager sensorManager;
    Sensor proximitySensor;

    boolean hasProximity;

    float maxDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentlayout = (RelativeLayout) findViewById(R.id.contentlayout);
        tv = (TextView) findViewById(R.id.textView);

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY)) {
            hasProximity = true;
            sensorManager = (SensorManager) getSystemService(MainActivity.this.SENSOR_SERVICE);
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

           maxDistance= proximitySensor.getMaximumRange();
        } else {
            Toast.makeText(getApplicationContext(), "Gerät hat keinen Annäherungssensor!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hasProximity){
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(hasProximity){
            sensorManager.unregisterListener(MainActivity.this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];
        if(distance < maxDistance){ // Nah
            tv.setText("Nah");
            contentlayout.setBackgroundColor(Color.RED);
        } else { // Fern
            tv.setText("Fern");
            contentlayout.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
