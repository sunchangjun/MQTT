package framework.core.utils;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.render.WriteRender;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 图片工具类
 * Created by zeyuphoenix on 2017/2/27.
 */
public class ImageUtils {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 缩放用户上传图片，减小宽带占用
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @throws SimpleImageException
     */
    public static void scale(InputStream inputStream, FileOutputStream outputStream, int maxWidth, int maxHeight)
            throws SimpleImageException {
        ScaleParameter scaleParam = new ScaleParameter(maxWidth, maxHeight);
        WriteParameter writeParam = new WriteParameter();

        ImageRender rr = new ReadRender(inputStream);
        ImageRender sr = new ScaleRender(rr, scaleParam);
        ImageRender wr = null;
        try {
            wr = new WriteRender(sr, outputStream, ImageFormat.JPEG, writeParam);
            wr.render();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            try {
                if (wr != null) {
                    wr.dispose();
                }
            } catch (Exception ignored) {
            }
        }
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================

}
