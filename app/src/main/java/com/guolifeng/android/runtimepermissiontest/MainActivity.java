package com.guolifeng.android.runtimepermissiontest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        errorMethod();
        trueMethod();
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

/***************************************************************************************************/

    /**
     * 正确的方法
     */
    private void trueMethod()
    {
        Button makeCall = (Button) findViewById(R.id.make_call);
        makeCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //这一步很重要 检查运行时权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else
                {
                    //如果已经有了权限，就直接打电话就行
                    call();
                }
            }
        });
    }

    /**
     * 打电话的方法
     */
    private void call()
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

    /**
     * 该方法很重要，判断运行时权限必须要使用的步骤
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 返回的权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    call();
                } else
                {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}
