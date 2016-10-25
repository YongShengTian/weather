package com.example.tys.weather;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tys on 2016/10/24.
 */
public class MyHandler extends DefaultHandler{
    public ArrayList provice=new ArrayList();
    public ArrayList item=new ArrayList();
    public HashMap<String,ArrayList> items=new HashMap();
    String beginstring="";
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if ("province".equals(localName)){
            provice.add(attributes.getValue(0));
            beginstring=attributes.getValue(0);
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String temp=new String(ch,start,length);
        if (!temp.trim().equals("")&&!temp.trim().equals("\n")){
            item.add(temp);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if ("province".equals(localName)) {
            items.put(beginstring, item);
            item = new ArrayList();
        }
    }
}
