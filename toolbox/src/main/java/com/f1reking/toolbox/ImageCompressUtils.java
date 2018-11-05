package com.f1reking.toolbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by HuangYH on 2015/10/9.
 * 图片压缩工具类
 */
public class ImageCompressUtils {

    private ImageCompressUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 对图片质量进行压缩
     *
     * @param bitmap 图片源
     * @return 返回压缩后的新bitmap
     */
    public static Bitmap compressQuality(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        //循环判断如果压缩后图片是否大于50kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > 50) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //每次都减少10
            options -= 10;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap newBitmap = BitmapFactory.decodeStream(bais, null, null);
        return newBitmap;
    }

    /**
     * 对图片尺寸进行压缩
     */
    public static Bitmap compressSize(Bitmap bitmap, int pixelW, int pixelH) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 512) {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeStream(bais, null, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize =
            computeSampleSize(options, pixelH > pixelW ? pixelW : pixelH, pixelW * pixelH);
        bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap newBitmap = BitmapFactory.decodeStream(bais, null, options);
        return newBitmap;
    }

    private static int computeSampleSize(BitmapFactory.Options options, int minSideLength,
        int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength,
        int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(
            Math.sqrt(w * h / maxNumOfPixels));
        int upperBound =
            (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
