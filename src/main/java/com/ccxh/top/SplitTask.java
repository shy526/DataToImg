package com.ccxh.top;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;

public class SplitTask implements Runnable {
    /**
     * 根目录
     */
    private String rootPath;
    /**
     * 任务id
     */
    private int index;
    /**
     * 数据
     */
    private byte datas[] = null;
    private int outIndex = 0;
    private CountDownLatch countDownLatch;
    public SplitTask(byte[] datas, int index, String rootPash) {
        this.datas = datas;
        this.index=index;
        this.rootPath=rootPash;
    }

    public SplitTask(String rootPath, int index, byte[] datas, CountDownLatch countDownLatch) {
        this.rootPath = rootPath;
        this.index = index;
        this.datas = datas;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        int width = (int) Math.round(Math.sqrt(datas.length / 3) + 1);
        BufferedImage br = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                int corol = 0;
                if (i == 0 && j == 0) {
                    corol = datas.length;
                    System.out.println("corol = " + corol);
                } else {
                    corol = this.getColor();
                }
                //设置像素
                br.setRGB(i, j, corol);
            }
        }
        try {

            ImageIO.write(br, "png", getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }

    public File getFile() {
        LocalDate now = LocalDate.now();
        Path path = Paths.get(rootPath, intToString(now.getYear()), intToString(now.getDayOfMonth()), intToString(now.getDayOfMonth()));
        File file = path.toFile();
        if (!file.exists()) {
            if (!file.mkdirs()) {
                new NullPointerException("文件夹创建失败");
            }
        }
        return Paths.get(path.toString(), index+".png").toFile();
    }

    private String intToString(int i) {
        return i + "";
    }

    /**
     * rgb转color
     * @return
     */
    private int getColor() {
        byte r = this.getByte();
        byte g = this.getByte();
        byte b = this.getByte();
        return r << 16 | g << 8 | b;
    }

    private byte getByte() {
        if (this.outIndex >= this.datas.length) {
            return 0;
        }
        return this.datas[outIndex++];
    }

    /**
     * 将color 转换为rgb
     * @param color
     * @return
     */
    public static byte[] getColorRGB(int color) {
        byte r = (byte) ((color >> 16) & 0xff);
        byte g = (byte) ((color >> 8) & 0xff);
        byte b = (byte) (color & 0xff);
        return new byte[]{r, g, b};
    }

}
