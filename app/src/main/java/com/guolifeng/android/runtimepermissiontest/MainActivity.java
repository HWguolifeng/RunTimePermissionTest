package com.guolifeng.android.runtimepermissiontest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMethod();
    }

    /**
     * 错误的方法：该方法会报W/System.err: java.lang.SecurityException: Permission Denial: starting Intent 的异常
     */
    private void errorMethod()
    {
        Button button = (Button) this.findViewById(R.id.make_call);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:10086"));
                    startActivity(intent);
                } catch (SecurityException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


}
