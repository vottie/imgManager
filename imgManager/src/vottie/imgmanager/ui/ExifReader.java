package vottie.imgmanager.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import vottie.imgmanager.service.JpegManager;

public class ExifReader {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 入力パラメタチェック
		if (args.length != 2) {
			System.out.println("Error!! invalid argments");
			System.out.println("Usage:");
			System.out.println("ExifReader C:\\inputdir C:\\outputdir");
			System.exit(-1);
		}
	    
		Logger.getGlobal().setLevel(Level.INFO);
		Logger.getGlobal().info("ExifReader Start.");
		// jpegファイルと出力先の指定を元にコピー開始
		JpegManager manager = new JpegManager(args[0], args[1]);
		manager.execute();

		//- 実行内容の表示
		System.out.println("Exif Reader execute");
		System.out.println("-------------");
		System.out.printf	("src  dir = %s\n" ,args[0]);
		System.out.printf("dest dir = %s\n" ,args[1]);
		System.out.println("---- end ----");
	}
}
