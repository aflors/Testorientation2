package softwares21.com.testorientation2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


/* Created by armando on 11/01/15.

*/
public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    TextView sensorsData;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        setContentView(R.layout.activity_main);
        sensorsData = (TextView)findViewById(R.id.textView);

    }

    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener((SensorEventListener)this, (Sensor)accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener((SensorEventListener)this,(Sensor) magnetometer, SensorManager.SENSOR_DELAY_UI);

    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    float[] mGravity;
    float[] mGeomagnetic;
    float azimut,pitch,roll;
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                pitch=orientation[1];
                roll=orientation[2];

                Log.i(Float.toString(azimut), Float.toString((float) Math.toDegrees(azimut)));
                Log.i(Float.toString(pitch), Float.toString((float) Math.toDegrees(pitch)));
                Log.i(Float.toString(roll), Float.toString((float) Math.toDegrees(roll)));
                 //sensorsData.setText((int) azimut);
            }
        }
    }
}
