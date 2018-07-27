package com.ccxh.top;

import javax.imageio.ImageIO;
import java.awt.font.ImageGraphicAttribute;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;

/**
 * 合并图片数据
 * @author admin
 */
public class DateMerge {

    public void run(String rootPath,Integer maxIndex,File file) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        for(int x=0;x<maxIndex;x++){
            BufferedImage image = ImageIO.read(Paths.get(rootPath, x+ ".png").toFile());
            imageToData(bufferedOutputStream,image);
        }
        bufferedOutputStream.close();
        System.out.println("gg" );
    }

    private void imageToData(BufferedOutputStream bufferedOutputStream, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int dataSize=0;
        int outSize=0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = image.getRGB(i, j);
                if (j==0&&i==0){
                    dataSize = getDataSize(color);
                    continue;
                }

                if ((outSize=outPutData(color,dataSize,outSize,bufferedOutputStream))==-1){
                    return;
                }
            }
        }
    }

    /**
     *
     * @param color
     * @param dataSize
     * @param outSize
     * @param bufferedOutputStream
     * @return
     */
    private int outPutData(int color, int dataSize, int outSize, BufferedOutputStream bufferedOutputStream) {
        byte[] colorRGB = this.getColorRGB(color);
        for (byte data:colorRGB){
            try {
                if (outSize>=dataSize){
                    //文件读取结束
                    return -1;
                }
                bufferedOutputStream.write(data);
                outSize++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outSize;
    }

    /**
     * 获取数据大小
     * @param color
     * @return
     */
    private  int getDataSize(Integer color) {
        byte[] colorRGB = this.getColorRGB(color);
        return ((colorRGB[0]<<16))|((colorRGB[1])<<8&0x00ff00)|(colorRGB[2]&0xff);
    }

    /**
     * 将颜色转换为 byte 数据
     * @param color
     * @return
     */
    private   byte[] getColorRGB(Integer color) {
        byte r = (byte) ((color >> 16) & 0xff);
        byte g = (byte) ((color >> 8) & 0xff);
        byte b = (byte) (color & 0xff);
        return new byte[]{r, g, b};
    }

}
