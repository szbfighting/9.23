package com.example.download;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView text;
    private InputStream in;
    private FileOutputStream fo;
    private int flag=1;
    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.download_text);
        text = (TextView) findViewById(R.id.text);
        Button download = (Button) findViewById(R.id.download);
        Button giveup = (Button) findViewById(R.id.giveup);
        download.setOnClickListener(this);
        giveup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download:
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    flag=1;
                    download();
                }
                break;
            case R.id.giveup:
                flag=0;
                if (f.exists()) {
                    f.delete();
                }
                text.setText("未开始下载");
                textView.setText("取消下载，已删除文件");
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    download();
                }else{
                    Toast.makeText(this, "You dinied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void download(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://qd.myapp.com/myapp/qqteam/pcqq/PCQQ2019.exe")
                            .build();

                    ResponseBody responseBody = client.newCall(request).execute().body();
                    final long length = responseBody.contentLength();
                    in = responseBody.byteStream();
                    f = new File(Environment.getExternalStorageDirectory(), "date");
                    if(!f.exists())
                        f.createNewFile();
                    fo = new FileOutputStream(f);

                    byte[] b = new byte[2048];
                    int len;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("正在下载");
                        }
                    });
                    long l = 0;
                    while ((len = in.read(b)) != -1) {
                        if (flag==0) {
                            break;
                        }
                        fo.write(b, 0, len);
                        l += len;
                        final long currentLength = l;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText("当前进度：" + (int)(((double)currentLength / length)*100) + "%");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }
}
