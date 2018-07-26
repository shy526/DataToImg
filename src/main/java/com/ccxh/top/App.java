package com.ccxh.top;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        DataSplit dataSplit = new DataSplit();
     //   JSONObject object = dataSplit.dataSplit(new File("F:/fdm5_x64_setup.exe"));
        UpLoadHelp upLoadHelp = new UpLoadHelp();
        try {
            upLoadHelp.mergeData( "F:/2018/26/26",48);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
