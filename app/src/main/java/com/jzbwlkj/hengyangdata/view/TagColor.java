package com.jzbwlkj.hengyangdata.view;

import android.graphics.Color;

import com.jzbwlkj.hengyangdata.AppConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyuan on 2018/1/12.
 */

public class TagColor {

    public int borderColor = Color.parseColor("#eeeeee");
    public int backgroundColor = Color.parseColor("#49C120");
    public int textColor = Color.WHITE;

    public static List<TagColor> getRandomColors(int size){

        List<TagColor> list = new ArrayList<>();
        for(int i=0; i< size; i++){
            TagColor color = new TagColor();
            color.borderColor = color.backgroundColor = AppConfig.tagColors[i % AppConfig.tagColors.length];
            list.add(color);
        }
        return list;
    }

    public static List<TagColor> getGrayColors(int size){

        List<TagColor> list = new ArrayList<>();
        for(int i=0; i< size; i++){
            TagColor color = new TagColor();
            color.borderColor = color.backgroundColor = Color.parseColor("#eeeeee");
            list.add(color);
        }
        return list;
    }
}