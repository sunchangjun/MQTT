package framework.core.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.imageio.ImageIO;

/**
 * 使用zxing产生二维码的工具类,对条形码和二维码编码解码
 * Created by zeyuphoenix on 16/7/29.
 */
public class QRCodeUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(QRCodeUtils.class);

    /**
     * 图片保存格式
     */
    private static final String IMAGE_TYPE = "png";

    private static final String CHARSET = "UTF-8";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

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
     * 条形码编码
     *
     * @param contents 条形码内容
     * @param width    宽度
     * @param height   高度
     * @param path     生成地址
     */
    public static void encode(String contents, int width, int height, String path) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter
                    .writeToPath(bitMatrix, IMAGE_TYPE, Paths.get(path));

        } catch (Exception e) {
            logger.error("create image error: {}", e);
        }
    }

    /**
     * 条形码解码
     *
     * @param path 二维码路径
     * @return String 二维码内容
     */
    public static String decode(String path) {
        BufferedImage image;
        Result result;
        try {
            image = ImageIO.read(new File(path));
            if (image == null) {
                logger.error("the decode image may be not exit.");
                return null;
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);

            return result.getText();
        } catch (Exception e) {
            logger.error("read image error: {}", e);
        }
        return null;
    }

    /**
     * 二维码编码
     *
     * @param contents 二维码内容
     * @param logoPath logo路径
     * @param width    宽度
     * @param height   高度
     * @param imgPath  保存图片路径
     */
    public static void encode2(String contents, String logoPath, int width, int height, String imgPath) {

        try {
            BufferedImage image = createImage(contents, logoPath, width, height);
            ImageIO.write(image, IMAGE_TYPE, new File(imgPath));
        } catch (Exception e) {
            logger.error("create image error: {}", e);
        }
    }


    /**
     * 生成二维码(内嵌LOGO,默认宽高)
     */
    public static void encode2(String contents, String logoPath, String imgPath) {
        encode2(contents, logoPath, QRCODE_SIZE, QRCODE_SIZE, imgPath);
    }

    /**
     * 生成二维码(无LOGO, 默认宽高)
     */
    public static void encode2(String contents, String imgPath) {
        encode2(contents, null, QRCODE_SIZE, QRCODE_SIZE, imgPath);
    }

    /**
     * 生成二维码(无LOGO, 指定宽高)
     */
    public static void encode2(String contents, int width, int height, String imgPath) {
        encode2(contents, null, width, height, imgPath);
    }

    /**
     * 二维码解码
     *
     * @param file 二维码文件
     * @return String 二维码解码结果
     */
    public static String decode2(File file) {
        BufferedImage image;
        Result result;
        try {
            image = ImageIO.read(file);
            if (image == null) {
                logger.error("the decode image may be not exit.");
                return null;
            }

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
            hints.put(DecodeHintType.CHARACTER_SET, CHARSET);

            result = new MultiFormatReader().decode(bitmap, hints);

            logger.debug("result = " + result.toString());
            logger.debug("resultFormat = " + result.getBarcodeFormat());
            logger.debug("resultText = " + result.getText());

            return result.getText();
        } catch (Exception e) {
            logger.error("read image error: {}", e);
        }
        return null;
    }


    /**
     * 解析二维码
     *
     * @param path 二维码图片地址
     * @return 二维码解码结果
     */
    public static String decode2(String path) throws Exception {
        return decode2(new File(path));
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    /**
     * 设置二维码的格式参数
     */
    private static Hashtable<EncodeHintType, Object> getDecodeHintType() {

        // 用于设置QR二维码参数
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);

        return hints;
    }

    /**
     * 创建二维码图片
     *
     * @param content  图片内容
     * @param logoPath logo地址
     * @param width    宽度
     * @param height   高度
     * @return 二维码图片
     */
    private static BufferedImage createImage(String content, String logoPath,
                                             int width, int height) {

        MultiFormatWriter multiFormatWriter;
        BitMatrix bm;
        BufferedImage image = null;
        try {
            multiFormatWriter = new MultiFormatWriter();

            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            bm = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, getDecodeHintType());

            int w = bm.getWidth();
            int h = bm.getHeight();
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
                }
            }
            // 插入图片
            if (logoPath == null || "".equals(logoPath)) {
                return image;
            }
            // 插入图片
            insertImage(image, logoPath, new LogoConfig());

        } catch (Exception e) {
            logger.error("create image error: {}", e);
        }
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source  二维码图片
     * @param imgPath LOGO图片地址
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    LogoConfig logoConfig) throws Exception {
        File file = new File(imgPath);
        if (!file.exists() || !file.isFile()) {
            logger.info("" + imgPath + "   该文件不存在！");
            return;
        }

        // 读取Logo图片
        BufferedImage logo = ImageIO.read(new File(imgPath));

        int widthLogo = source.getWidth() / logoConfig.getLogoRate(),
                heightLogo = source.getHeight() / logoConfig.getLogoRate();

        widthLogo = Math.min(logo.getWidth(), widthLogo);
        heightLogo = Math.min(logo.getHeight(), heightLogo);

        // 计算图片放置位置
        int x = (source.getWidth() - widthLogo) / 2;
        int y = (source.getHeight() - heightLogo) / 2;

        // 插入LOGO
        Graphics2D graph = source.createGraphics();

        //开始绘制图片
        graph.drawImage(logo, x, y, widthLogo, heightLogo, null);
        graph.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        graph.setStroke(new BasicStroke(logoConfig.getBorder()));
        graph.setColor(logoConfig.getBorderColor());
        graph.drawRect(x, y, widthLogo, heightLogo);

        graph.dispose();
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    /**
     * 插入图片时,设置图片默认属性
     */
    static class LogoConfig {

        // ================================================================
        // Constants
        // ================================================================

        // logo默认边框颜色
        private static final Color DEFAULT_BORDER_COLOR = Color.WHITE;
        // logo默认边框宽度
        private static final int DEFAULT_BORDER = 2;
        // logo大小默认为照片的1/5
        private static final int DEFAULT_RATE = 5;

        // ================================================================
        // Fields
        // ================================================================

        private final int border = DEFAULT_BORDER;
        private final Color borderColor;
        private final int logoRate;

        // ================================================================
        // Constructors
        // ================================================================

        /**
         * Creates a default config with on color BLACK and off color
         * WHITE, generating normal black-on-white barcodes.
         */
        public LogoConfig() {
            this(DEFAULT_BORDER_COLOR, DEFAULT_RATE);
        }

        public LogoConfig(Color borderColor, int logoPart) {
            this.borderColor = borderColor;
            this.logoRate = logoPart;
        }

        // ================================================================
        // Getter & Setter
        // ================================================================

        public Color getBorderColor() {
            return this.borderColor;
        }

        public int getBorder() {
            return this.border;
        }

        public int getLogoRate() {
            return this.logoRate;
        }
    }

    // ================================================================
    // Test Methods
    // ================================================================

}
