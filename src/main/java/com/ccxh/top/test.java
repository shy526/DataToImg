package com.ccxh.top;

import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class test {
    @Test
    public void fdsa(){
        BufferedImage br = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        br.setRGB(0,0,5069392);
        try {
            boolean png = ImageIO.write(br, "png", new FileOutputStream("D:/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void fdsafd(){
        try {
            //77 90 80
            BufferedImage read = ImageIO.read(new FileInputStream("D:/test.png"));
            int rgb = read.getRGB(0, 0);
            byte[] colorRGB = SplitTask.getColorRGB(rgb);
            System.out.println("rgb = " + rgb);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
