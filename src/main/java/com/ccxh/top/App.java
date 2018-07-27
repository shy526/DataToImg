package com.ccxh.top;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    @Test
    public void test1(){
        DataSplit dataSplit = new DataSplit();
        JSONObject jsonObject = dataSplit.dataSplit(new File("D:\\bower.json"));
    }
    @Test
    public void test12(){
        try {
             new DateMerge().run("D:/2018/7/27",1,new File("D:/bower拷贝.josn"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        DataSplit dataSplit = new DataSplit();
        JSONObject jsonObject = dataSplit.dataSplit(new File("D:\\迅雷下载/VMware-workstation-full-14.1.2-8497320.exe"));
        try {
            new DateMerge().run(jsonObject.getString("rootPath"),jsonObject.getInteger("maxIndex"),new File("D:/VMware-workstation-full-14.1.2-8497320.exe"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
