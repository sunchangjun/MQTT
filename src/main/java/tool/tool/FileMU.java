package tool.tool;

import java.io.File;

public class FileMU {
	static String Xpath = "C:/Users/koo/koo8/tiandianBoxMqtt/tiandianBoxMqtt/src/main/assets";

	public static void main(String[] args) {
		// 这是需要获取的文件夹路径
		getFile(Xpath, 0);
	}

	/*
	 * 函数名：getFile 作用：使用递归，输出指定文件夹内的所有文件 参数：path：文件夹路径 deep：表示文件的层次深度，控制前置空格的个数
	 * 前置空格缩进，显示文件层次结构
	 */
	private static void getFile(String path, int deep) {
		// 获得指定文件对象
		File file = new File(path);
		// 获得该文件夹内的所有文件
		File[] array = file.listFiles();
		for (int i = 0; i < array.length; i++) {
			if (array[i].isFile())// 如果是文件
			{
				String name=array[i].getAbsolutePath().substring(Xpath.length() + 1);
				if(name.indexOf(".DS_Store")>-1||name.indexOf(".apk")>-1) {
					continue;
				}
				System.out.println(name.replaceAll("\\\\", "/"));
			} else if (array[i].isDirectory())// 如果是文件夹
			{
				getFile(array[i].getPath(), deep + 1);
			}
		}
	}
}