package framework.common.util.MD5;


import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class MD5Transform {
	private static final char HEX_DIGITS[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };
	//md5
	public static synchronized String EncoderByMd5(String str,String charSet){
        try{
			MessageDigest md5=MessageDigest.getInstance("MD5");
			md5.update(str.getBytes(charSet));
			byte[] digest = md5.digest();
			return getFormattedText(digest);
        }catch(Exception e){}
        return null;
    }
	
	
	public static String EncoderByMd5(File file,String charset){
		try{
			MessageDigest md5=MessageDigest.getInstance("MD5");
			FileInputStream in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());	
	        md5.update(byteBuffer);
			byte[] digest = md5.digest();
			return getFormattedText(digest);
        }catch(Exception e){}
		return null;
	}
	
	private static String getFormattedText(byte bytes[]) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for(int j = 0; j < bytes.length; j++)
        {
            buf.append(HEX_DIGITS[bytes[j] >> 4 & 15]);
            buf.append(HEX_DIGITS[bytes[j] & 15]);
        }
        return buf.toString();
    }
}

