package com.example.tys.weather;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import org.xml.sax.SAXException;


import java.io.IOException;
import java.util.ArrayList;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main2Activity extends AppCompatActivity {
    MyHandler myHandler=new MyHandler();
    int firstindex=0;
    String returnstring="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            SAXParser saxParser=SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(getResources().getAssets().open("citys.xml"),myHandler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main2);
        final ListView lv1=(ListView)findViewById(R.id.listView);
        final ListView lv2=(ListView)findViewById(R.id.listView2);
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,myHandler.provice);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID=(String)myHandler.provice.get(position);
                firstindex=position;
                ArrayList shiqu=myHandler.items.get(ID);
                ArrayAdapter adapter2=new ArrayAdapter(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,shiqu);
                lv2.setAdapter(adapter2);
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID=(String)myHandler.provice.get(firstindex);
                ArrayList shiqu=myHandler.items.get(ID);
                returnstring=ID+","+shiqu.get(position);
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                intent.putExtra("returnstring",returnstring);
                setResult(1,intent);
                finish();
            }
        });
    }
}
