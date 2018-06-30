package framework;

public class test {
	public static void main(String[] args) {
		Integer x = Integer.valueOf("11");
		String hex = "0"+Integer.toHexString(x);
		hex=hex.substring(hex.length()-2,hex.length()).toUpperCase();
		System.out.println(hex);
	}
         
}