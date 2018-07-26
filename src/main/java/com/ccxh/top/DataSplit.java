package com.ccxh.top;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class DataSplit {
    /**
     * 最大1M
     */
    private final static  Integer MAX_BYTE_SIZE=1024*1024;
    private String rootPath="F:/";
    public JSONObject dataSplit(final File file){
        if (!file.exists()){
            System.out.println("is error");
        }
        long fileSize = file.length();
         //预先切分 数量
        int index = Math.round(fileSize / MAX_BYTE_SIZE)+1;
        final CountDownLatch latch = new CountDownLatch(index);
        ThreadPoolExecutor dataSplit = ThreadPoolUtil.getThreadPool("DataSplit");
        byte baseData[]=new byte[MAX_BYTE_SIZE];
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int size=-1;
            int flag=0;
            AtomicInteger x=new AtomicInteger(0);
            while ((size=bufferedInputStream.read(baseData,flag,baseData.length-flag))!=-1){
                // 说明读满
                flag+=size;
                if (flag>=baseData.length){
                    flag=0;
                    dataSplit.execute(new SplitTask( this.rootPath,x.getAndIncrement(),baseData,latch));
                }
            };
            byte[] bytes = new byte[flag];
            //参数：数组源，拷贝的起始下标，目标数组，填写的起始下标，拷贝的长度）
            System.arraycopy(baseData, 0, bytes, 0, flag);
            SplitTask splitTask = new SplitTask( this.rootPath,x.getAndIncrement(),bytes,latch);
            dataSplit.execute(splitTask);
            latch.await();
            JSONObject jsonObject=new JSONObject();
            System.out.println("index = " + index);
            System.out.println("x.intValue() = " + x.intValue());
            jsonObject.put("rootPath",splitTask.getFile().getParent());
            jsonObject.put("maxIndex",x.intValue()-1);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
