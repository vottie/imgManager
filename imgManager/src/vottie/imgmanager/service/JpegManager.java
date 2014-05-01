package vottie.imgmanager.service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import vottie.imgmanager.model.JpegData;

public class JpegManager {
	// コピーしたい写真があるディレクトリ
	private String inDir;
	// コピー先のディレクトリ
	private String outDir;
	// 写真オブジェクトのリスト
	private ArrayList<JpegData> list = new ArrayList<JpegData>();
	// Logger
	private static Logger logger = Logger.getGlobal();
	/**
	 * Constructor
	 */
	public JpegManager(String inDir_, String outDir_) {
		inDir = inDir_;
		outDir = outDir_;
	}
	
	/**
	 * ディレクトリを検索して、JpegDataをJpegManagerへaddする。
	 * @param path
	 * @return
	 */
	private boolean searchDir(String path) {
		File file = new File(path);
		
		File[] files = file.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				if (name.startsWith(".")) {
					return false;
				}
				if ((name.endsWith(".jpg")) || (name.endsWith(".JPG"))) {
					String target = dir.getAbsolutePath() + File.separator + name;
					
					// 絶対パスとファイル名をコンストラクタに指定
					JpegData jpeg = new JpegData(target, name);
					list.add(jpeg);
					return true;
				}
				String absPath = dir.getAbsolutePath() + File.separator + name;
				if (new File(absPath).isFile()) {
					return true;
				} else {
					return searchDir(absPath);
				}
			}
		});

		for (File f : files) {
			if (f.isFile()) {
				logger.log(Level.FINE, "FILE={0}", f.getAbsolutePath());
			}
		}
		return true;
	}

	/**
	 * ファイルを読んで、新しいところへ出力。出力先ディレクトリがなければ作る。
	 */
	public void execute() {
		// inDirを再帰検索してリストを作成
		searchDir(inDir);
		
		// listをループしてファイル単位に処理を実施
		for(JpegData jpegData : list) {
			jpegData.execute(outDir);
				
		}
	}
}

