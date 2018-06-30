package framework.common.util.QRcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import framework.common.util.MD5.MD5Transform;

/**
 * 二维码条形码功能类
 * 
 * @author yanglei
 * @since 2012-07-25
 */
public class BarCode {
	/**
	 * 把url转换成二维码并保存
	 * 
	 * @param url
	 *            待转换的url
	 * @param savePath
	 *            存储路径
	 * @param fname
	 *            需要保存的文件名，为null则将字符串做MD5编码
	 * @return 保存后的文件名
	 */
	public static String toQrCode(String url, String savePath, String fname, int width, int height) {
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
			// 指定纠错等级
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			// 指定编码格式
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 1); // 设置白边
			BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height, hints);
			if (StringUtils.isEmpty(fname)) {
				fname = MD5Transform.EncoderByMd5(url, "utf-8") + ".jpg";
			}
			// 路径不存在，则直接生成路径
			File path = new File(savePath);
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File(savePath + "/" + fname);
			MatrixToImageWriter.writeToFile(matrix, "jpg", file);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}

		return fname;
	}

	/**
	 * 把url转换成二维码并保存
	 * 
	 * @param url
	 *            待转换的url
	 * @param savePath
	 *            存储路径
	 * @param fname
	 *            需要保存的文件名，为null则将字符串做MD5编码
	 * @return 保存后的文件名
	 * @param width
	 *            生成二维码的宽
	 * @param height
	 *            生成二维码的高
	 * @return
	 */
	public static String toQrOwnwhCode(String url, String savePath, String fname, Integer width, Integer height) {
		try {
			if (width == 0 || null == width) {
				width = 200;
			}
			if (height == 0 || null == height) {
				height = 200;
			}
			BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
			if (StringUtils.isEmpty(fname))
				fname = MD5Transform.EncoderByMd5(url, "utf-8") + ".jpg";

			// 路径，判断路径是否存在，不存在则新建
			File pathFile = new File(savePath);
			if (!pathFile.exists())
				pathFile.mkdirs();
			// 判断文件是否已存在，存在则修改文件名
			File file;
			while (true) {
				file = new File(savePath + "/" + fname);
				if (!file.exists())
					break;
				fname += "0";
			}
			MatrixToImageWriter.writeToFile(matrix, "jpg", file);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}

		return fname;
	}

	/**
	 * 把url转换成条码并保存
	 * 
	 * @param str
	 *            待转换的str
	 * @param savePath
	 *            存储路径
	 * @param fname
	 *            需要保存的文件名，为null则将字符串做MD5编码
	 * @return 保存后的文件名
	 */
	public static String toBarCode(String str, String savePath, String fname) {
		try {
			BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, 300, 50);
			if (null == fname || !StringUtils.isEmpty(fname))
				fname = MD5Transform.EncoderByMd5(str, "utf-8") + ".jpg";
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(savePath + "/" + fname);
			MatrixToImageWriter.writeToFile(matrix, "jpg", file);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		return fname;
	}

	/**
	 * 解析指定路径下的二维码图片
	 *
	 * @param filePath
	 *            二维码图片路径
	 * @return
	 */
	public static String parseQRCode(BufferedImage image) {
		String content = "";
		try {
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			MultiFormatReader formatReader = new MultiFormatReader();
			Result result = formatReader.decode(binaryBitmap, hints);

			System.out.println("result 为：" + result.toString());
			System.out.println("resultFormat 为：" + result.getBarcodeFormat());
			System.out.println("resultText 为：" + result.getText());
			// 设置返回值
			content = result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

}

