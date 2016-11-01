package com.mo9.thirdapi.appender.umeng;


import org.apache.commons.io.IOUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ImagePreProcess {  
  
    public static int isWhite(int colorInt) {   //100为阈值，即背景色跟字体颜色的分隔值，
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {  
            return 1;  //为背景颜色时返回1，非背景颜色时返回0
        }  
        return 0;  
    }  
  
    public static int isBlack(int colorInt) { 
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {  
            return 1;  
        }  
        return 0;  
    }  
  
    public static BufferedImage removeBackgroud(String picFile)  
            throws Exception {  
        BufferedImage img = ImageIO.read(new File(picFile));
        int width = img.getWidth();  
        int height = img.getHeight();  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isWhite(img.getRGB(x, y)) == 1) {  
                    img.setRGB(x, y, Color.WHITE.getRGB());  
                } else {  
                    img.setRGB(x, y, Color.BLACK.getRGB());  
                }  
            }  
        }  
        return img;  
    }  
  
    public static List splitImage(BufferedImage img)  
            throws Exception {  //分隔图片
        List subImgs = new ArrayList();  
        subImgs.add(img.getSubimage(10, 6, 8, 10));  
        subImgs.add(img.getSubimage(19, 6, 8, 10));  
        subImgs.add(img.getSubimage(28, 6, 8, 10));  
        subImgs.add(img.getSubimage(37, 6, 8, 10));  
        return subImgs;  
    }  
  
    public static Map loadTrainData() throws Exception {  //用来验证的图片存到Map中,供后面比对调用
        Map map = new HashMap();  
        File dir = new File("train");  
        File[] files = dir.listFiles();  
        for (File file : files) {  
            map.put(ImageIO.read(file), file.getName().charAt(0) + "");  
        }  
        return map;  
    }  
  
    public static String getSingleCharOcr(BufferedImage img,  
            Map map) {  
        String result = "";  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int min = width * height;  //图片的面积
        BufferedImage[] keySet = (BufferedImage[]) map.keySet().toArray(new BufferedImage[0]);
        for (BufferedImage bi :keySet) {  
            int count = 0;  
            Label1: for (int x = 0; x < width; ++x) {  
                for (int y = 0; y < height; ++y) {  
                    if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {  
                        count++;  //当不是背景色的时间count值加1
                        if (count >= min) //一般count不会大于图片的面积，特殊情况下就中断处理
                            break Label1;  
                    }  
                }  
            }  
            if (count < min) {  //正常情况下,当得到的图片面积比图片面积小，或者比上一次得到的图片面积小时，用得到的面积替代图片的面积，这样当所有的用来验证的图片都遍历一遍后就能得到最接近现在图片的验证图片，
                min = count;  
                result = (String)map.get(bi);  //得到最接近现在图片的验证图片的数字
            }  
        }  
        return result;  
    }  
  
    public static String getAllOcr(String file) throws Exception {  
        BufferedImage img = removeBackgroud(file);  //去除验证码背景色
        List listImg = splitImage(img);   //验证码分隔，如四个数字分隔开
        Map map = loadTrainData();  //用来匹配的所有数字图片
        String result = "";  
        for (BufferedImage bi :(List<BufferedImage>) listImg) {  
            result += getSingleCharOcr(bi, map);  //得到匹配结果
        }  
        ImageIO.write(img, "JPG", new File("result//"+result+".jpg"));  //输出匹配结果
        return result;  
    }  
  
    public static void downloadImage() {  //下载验证码
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(  "http://www.puke888.com/authimg.php");  
        for (int i = 0; i < 30; i++) {  
            try {  
                // 执行getMethod  
                int statusCode = httpClient.executeMethod(getMethod);  
                if (statusCode != HttpStatus.SC_OK) {
                    System.err.println("Method failed: "  
                            + getMethod.getStatusLine());  
                }  
                // 读取内容  
                String picName = "img//" + i + ".jpg";  
                InputStream inputStream = getMethod.getResponseBodyAsStream();  
                OutputStream outStream = new FileOutputStream(picName);  
                IOUtils.copy(inputStream, outStream);
                outStream.close();  
                System.out.println("OK!");  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                // 释放连接  
                getMethod.releaseConnection();  
            }  
        }  
    }  
  
      
    public static void main(String[] args) throws Exception {  
        for (int i = 0; i < 30; ++i) {  //验证给出的30个验证码一 一验证
            String text = getAllOcr("img//" + i + ".jpg");  
            System.out.println(i + ".jpg = " + text);  
        }  
    }  
} 