package vottie.imgmanager.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopier {
	/**
	 * ファイルをコピーする
	 * @param in  入力ファイル
	 * @param out　出力ファイル
	 * @throws IOException
	 */
	public static void copy(File in, File out) throws IOException  {
        FileChannel src  = new FileInputStream(in).getChannel();
        FileChannel dest = new FileOutputStream(out).getChannel();
        
        src.transferTo(0, src.size(), dest);
        //System.out.printf("copy file [in=%s out=%s]\n",
        //		in.getAbsolutePath(),out.getAbsolutePath());
        
        src.close();
        dest.close();
	}

}
