package com.ccxh.top;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

/**
 * https://sm.ms/
 *
 * @author admin
 */
public class SmmsUpload {
    private static  String URL_STR = "https://sm.ms/api/upload";

    public static  void upload(String localFile) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(URL_STR);
            // 把文件转换成流对象FileBody
            FileBody bin = new FileBody(new File(localFile));
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    // 相当于<input type="file" name="file"/>
                    .addPart("smfile", bin)

                    // 相当于<input type="text" name="userName" value=userName>
                    .addPart("format", getStringBodyUtf8("json"))
                    .build();
            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }
            // 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void bathUpload(List<String> s) {
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);


    }


    private static StringBody getStringBodyUtf8(String s) {
        return new StringBody(s, ContentType.create(
                "text/plain", Consts.UTF_8));
    }

    public static  void uploadPic( BufferedImage image) throws IOException {
        ByteArrayOutputStream baoImag=new ByteArrayOutputStream();
        boolean png = ImageIO.write(image, "png", baoImag);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(URL_STR);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 添加二进制文件流，第一个参数是接口参数名，第二个参数是文件的二进制流，第三个参数是文件名
        builder.addBinaryBody("smfile", new ByteArrayInputStream(baoImag.toByteArray()), ContentType.create("multipart/form-data"), "11.png");
        // 添加参数和参数值
        builder.addPart("format", getStringBodyUtf8("json"));
        // 创建 httpentity 实体
        HttpEntity httpEntity = builder.build();
        // 设置 httpentity 实体
        httpPost.setEntity(httpEntity);
        // 发送请求获取相应

        response = httpClient.execute(httpPost);
        // 获取响应对象
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
            // 打印响应长度
            System.out.println("Response content length: " + resEntity.getContentLength());
            // 打印响应内容
            System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
        }
        // 销毁
        EntityUtils.consume(resEntity);

    }


    public static void main(String[] args) throws IOException {
   /*     SmmsUpload smmsUpload = new SmmsUpload();
        smmsUpload.upload("D:/2018/7/27/0.png");*/
        SmmsUpload smmsUpload = new SmmsUpload();
        BufferedImage read = ImageIO.read(new File("D:/2018/7/27/1.png"));
        SmmsUpload.uploadPic(read);
    }
}

