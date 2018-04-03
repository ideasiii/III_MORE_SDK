package org.iii.more.sdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.iii.more.agent.Agent;

public class MainActivity extends AppCompatActivity
{
    
    // Used to load the 'native-lib' library on application startup.
    static
    {
        System.loadLibrary("native-lib");
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.textViewMsg);
        //tv.setText(stringFromJNI());
        
        Button btnAgentSync = (Button) findViewById(R.id.buttonAgentSync);
        btnAgentSync.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Agent.syncData("suck me");
            }
        });
    }
    
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
