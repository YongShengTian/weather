package com.example.tys.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    TextView tv2;
    String returnstring="";
    final String filename="456.txt";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String getstring=msg.getData().getString("tianqi");
            tv2.setText(getstring);
        }
    };
    Runnable myrunnable=new Runnable() {
        @Override
        public void run() {
            String tempstring=GetCityList.post(returnstring);
            Message message=new Message();
            Bundle bundle=new Bundle();
            bundle.putString("tianqi",tempstring);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    public String readFileData(String fileName){
        FileInputStream inputStream= null;
        String line="";
        try {
            inputStream = openFileInput(fileName);
        } catch (FileNotFoundException e) {
            Log.e("123", "readFileData: "+e.toString() );
            e.printStackTrace();
        }
        BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
        try {
            String str="";
            while ((str=reader.readLine())!=null){
                line=str;
            }
            inputStream.close();
        } catch (IOException e) {
            Log.e("123", "readFileData: "+e.toString() );
            e.printStackTrace();
        }
        return line;
    }

    public void saveFile(String filename,String inputstring)
    {
        FileOutputStream outputStream= null;
        try {
            outputStream = openFileOutput(filename, MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            Log.e("123", "saveFile: "+e.toString() );
            e.printStackTrace();
        }
        BufferedWriter writer=new  BufferedWriter(new OutputStreamWriter(outputStream));
        try {
            writer.write(inputstring);
        } catch (IOException e) {
            Log.e("123", "saveFile: "+e.toString() );
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            Log.e("123", "saveFile: "+e.toString() );
            e.printStackTrace();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ////从下一个activity传回数据
        returnstring=data.getExtras().getString("returnstring");
        tv.setText(returnstring);

        saveFile(filename,returnstring);///文件保存
        Thread mythread= new Thread(myrunnable);
        mythread.setDaemon(true);
        mythread.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.button);
        tv=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
      //  File file=new File(filename);
        try{
            returnstring=readFileData(filename);
            tv.setText(returnstring);
            Thread mythread= new Thread(myrunnable);
            mythread.setDaemon(true);
            mythread.start();
        }
        catch (Exception e){

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                startActivityForResult(intent,1);
            }
        });

    }
}
