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
    TextView sensorsData, sensorsData2, sensorsData3;
    float azimut,pitch,roll;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        setContentView(R.layout.activity_main);
        sensorsData = (TextView)findViewById(R.id.textView);
        sensorsData2 = (TextView)findViewById(R.id.textView7);
        sensorsData3 = (TextView)findViewById(R.id.textView8);
    }

    protected void onResume() {
        super.onResume();

        // it shows the data every 4 secs, the latest is in microsec
      mSensorManager.registerListener((SensorEventListener)this, (Sensor)accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
      mSensorManager.registerListener((SensorEventListener)this,(Sensor) magnetometer, SensorManager.SENSOR_DELAY_NORMAL);


    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

    float[] mGravity;
    float[] mGeomagnetic;

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
                /*Computes the device's orientation based on the rotation matrix.

                When it returns, the array values is filled with the result:

                values[0]: azimuth, rotation around the Z axis.
                values[1]: pitch, rotation around the X axis.
                values[2]: roll, rotation around the Y axis.

                The reference coordinate-system used is different from the world coordinate-system defined for the rotation matrix:

                X is defined as the vector product Y.Z (It is tangential to the ground at the device's current location and roughly points West).
                Y is tangential to the ground at the device's current location and points towards the magnetic North Pole.
                Z points towards the center of the Earth and is perpendicular to the ground.
                */
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                pitch=orientation[1];
                roll=orientation[2];

                Log.i(Float.toString(azimut), Float.toString((float) Math.toDegrees(azimut) ));
                Log.i(Float.toString(pitch), Float.toString((float) Math.toDegrees(pitch)));
                Log.i(Float.toString(roll), Float.toString((float) Math.toDegrees(roll)));
                 sensorsData.setText(Float.toString((float) Math.toDegrees(azimut)));
                sensorsData2.setText(Float.toString((float) Math.toDegrees(pitch)));
                sensorsData3.setText(Float.toString((float) Math.toDegrees(roll)));
            }
        }
    }
}
