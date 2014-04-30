package vottie.imgmanager.ui;

import vottie.imgmanager.service.JpegManager;

public class ExifReader {
	/* コンストラクタ */
	ExifReader() {}
	

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 入力パラメタチェック
		if (args.length != 2) {
			System.out.println("Error!! invalid argments");
			System.exit(-1);
		}

		//ExifReader reader = new ExifReader();

		// jpegファイルと出力先の指定を元にコピー開始
		JpegManager manager = new JpegManager(args[0], args[1]);
		manager.execute();

		//- 実行内容の表示
		System.out.println("Exif Reader execute");
		System.out.println("-------------");
		System.out.printf("src  dir = %s\n" ,args[0]);
		System.out.printf("dest dir = %s\n" ,args[1]);
		System.out.println("---- end ----");
	}
}
