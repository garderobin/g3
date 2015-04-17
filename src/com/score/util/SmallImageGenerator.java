package com.score.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/*import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;*/

public class SmallImageGenerator {
	/*public static byte[] generate(byte[] srcFile, int dstMaxWidth, int dstMaxHeight) throws ImageFormatException, IOException
	{
	    JPEGImageEncoder encoder = null;
	    BufferedImage tagImage = null;
	    Image srcImage = null;
	    ByteArrayInputStream inputStream = new ByteArrayInputStream(srcFile);
	    try{
	        srcImage = ImageIO.read(inputStream);
	        int srcWidth = srcImage.getWidth(null);//原图片宽度
	        int srcHeight = srcImage.getHeight(null);//原图片高度
	        int dstWidth = srcWidth;//缩略图宽度
	        int dstHeight = srcHeight;//缩略图高度
	        float scale = 0;
	        //计算缩略图的宽和高
	        if(srcWidth>dstMaxWidth){
	            dstWidth = dstMaxWidth;
	            scale = (float)srcWidth/(float)dstMaxWidth;
	            dstHeight = Math.round((float)srcHeight/scale);
	        }
	        srcHeight = dstHeight;
	        if(srcHeight>dstMaxHeight){
	            dstHeight = dstMaxHeight;
	            scale = (float)srcHeight/(float)dstMaxHeight;
	            dstWidth = Math.round((float)dstWidth/scale);
	        }
	        //生成缩略图
	        tagImage = new BufferedImage(dstWidth,dstHeight,BufferedImage.TYPE_INT_RGB);
	        tagImage.getGraphics().drawImage(srcImage,0,0,dstWidth,dstHeight,null);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        encoder = JPEGCodec.createJPEGEncoder(outputStream);
			encoder.encode(tagImage);
	        return outputStream.toByteArray();
	    }finally{
	        encoder = null;
	        tagImage = null;
	        srcImage = null;
	        System.gc();
	    }
	}*/
}
