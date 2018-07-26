package com.ccxh.top;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class UpLoadHelp {
    /**
     * 最大1M
     */
    private final static  Integer MAX_BYTE_SIZE=1024*1024;

    public void DataSplit(final File file){
        if (!file.exists()){
            System.out.println("is error");
        }
        long fileSize = file.length();
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
                    dataSplit.execute(new SplitTask(baseData,x.getAndIncrement(),"D:/"));
                }
            };
            byte[] bytes = new byte[flag];
            //参数：数组源，拷贝的起始下标，目标数组，填写的起始下标，拷贝的长度）
            System.arraycopy(baseData, 0, bytes, 0, flag);
            System.out.println("分配最后一个任务" );
            dataSplit.execute(new SplitTask(bytes,x.getAndIncrement(),"D:/"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private static void dataSplit(String path,	int[][] array){
        File file=new File(path);
        BufferedImage br=new BufferedImage(array[0].length, array.length, BufferedImage.TYPE_INT_RGB);
        try {
            for (int i=0;i<array.length;i++){
                for (int j=0;j<array[i].length;j++){
                    br.setRGB(j, i, array[i][j]);//设置像素
                }
            }
            ImageIO.write(br,"gif",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }



    public void mergeData(String path,int index) throws IOException {
        List<Byte> data=new ArrayList<>();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File("F:/dfasd.exe")));
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File("F:/fdm5_x64_setup.exe")));
       for(int x=0;x<=index;x++){
            File file1 = Paths.get(path, x+ ".png").toFile();
            BufferedImage img = ImageIO.read(file1);
            xxxdata(data, img,bufferedOutputStream,in);

        }
        bufferedOutputStream.close();
        System.out.println("gg" );
    }

    private void xxxdata(List<Byte> data, BufferedImage img,OutputStream bufferedOutputStream,InputStream in) {
        int width = img.getWidth();
        int height = img.getHeight();
        boolean flag=false;
        List<Byte> sha1=new ArrayList<>();
        int size=-1;
        int x=0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = img.getRGB(i, j);
                if (j==0&&i==0){
                    byte[] colorRGB = SplitTask.getColorRGB(color);
                    size = ((colorRGB[0]<<16))|((colorRGB[1])<<8&0x00ff00)|(colorRGB[2]&0xff);
                    flag=true;
                    continue;
                }
               if ((x=xxxxxx(data,color,size,x,bufferedOutputStream,in))==-1){
                   return;
               }
            }
        }
    }

    private int xxxxxx(List<Byte> sha1, int color,int max,int x,OutputStream bufferedOutputStream,InputStream in) {
        byte[] colorRGB = SplitTask.getColorRGB(color);
        byte [] xxxx=new byte[3];

        try {
            int read = in.read(xxxx);
            bufferedOutputStream.write(colorRGB[0]);
            bufferedOutputStream.write(colorRGB[1]);
            bufferedOutputStream.write(colorRGB[2]);
            BufferedImage br = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
            br.setRGB(0,0,5069392);
            int rgb = br.getRGB(0, 0);
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        x=+3;
        if (x>=-1){
            return -1;
        }
        return x;
    }
}
