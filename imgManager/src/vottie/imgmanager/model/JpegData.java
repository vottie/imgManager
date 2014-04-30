package vottie.imgmanager.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import vottie.imgmanager.service.FileCopier;

public class JpegData {
	private String orgPath;
	private String year;
	private String month;
	private String date;
	private String filename;
	
	public JpegData(String orgPath, String name) {
		super();
		this.orgPath = orgPath;
		this.filename = name;
	}

	public String getYear() { return year; }
	public void setYear(String year) { this.year = year; }

	public String getMonth() { return month; }
	public void setMonth(String month) { this.month = month; }

	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }

	public String getOrgPath() { return orgPath; }
	public String getFilename() { return filename; }
	

	public void execute(String outDir) {
		File in = new File(getOrgPath());
		// 196バイト目から19バイト読めば
		// yyyy/mm/dd hh:mm:ssで文字列のデータが取れます
		try {
			FileReader fir = new FileReader(in);
			char[] buff = new char[1024];
			fir.read(buff);
			fir.close();
			
			// ファイルをparseして、日付やディレクトリ情報をjpegDataへ格納
			parse(buff);
			
			// コピーを指定されたディレクトリを作成し、ファイルオブジェクトを取得
			File out = getCopyFile(outDir);
			
			// ファイルコピー
			if (out.exists()) {
				// もしファイルが既に存在した場合は、次のファイル処理を継続
				System.out.printf("File is existed [%s]\n", out.getAbsolutePath());
				//continue;
			}
			FileCopier.copy(in, out);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	/**
	 * ファイルを読んで解析
	 * @param buff		読んだファイルの先頭xxxバイト
	 * @param jpegData	解析した内容を格納するオブジェクト
	 */
	private void parse(char[] buff) {
		for(int i=0; i < buff.length; i++) {
			// 2xxx:xx:xxの2をsearch
			if (buff[i] != '2')
				continue;
			// 20xx:という形式であれば日付とみなす
			if ((buff[i+1] == '0') && (buff[i+4] == ':')) {
				String year = new String(buff, i, 4);
				String month = new String(buff, i+5, 2);
				String date = new String(buff, i, 10);
				
				//System.out.printf("file=%s : [year=%s month=%s date=%s]\n",
				//		jpegData.getOrgPath(), year, month, date);

				setYear(year);
				setMonth(month);
				setDate(date);
				break;
			}
		}
	
	}

	/**
	 * コピー先のファイルオブジェクトを取得する。途中のディレクトリがなければ作る。
	 * @param jpegData
	 * @param outDir
	 * @return
	 */
	private File getCopyFile(String outDir) {
		//System.out.printf("copy in=%s out=%s\n", jpegData.getOrgPath(), outDir);
		// 出力先のディレクトリは存在する？
		File file = new File(outDir);
		if (!(file.exists())) {
			boolean result = file.mkdirs();
			if(result) {
				System.out.printf("mkdir %s\n", file);
			}
		}
		
		// 出力先のディレクトリの下に年のディレクトリは存在する？なければ作る
		StringBuffer buffer = new StringBuffer(outDir);
		buffer.append("\\");
		buffer.append(getYear());
		File file1 = new File(buffer.toString());
		if (!(file1.exists())) {
			boolean result = file1.mkdirs();
			if(result) {
				System.out.printf("mkdir %s\n", file1);
			}
		}
		// 出力先のディレクトリの下に年月のディレクトリは存在する？なければ作る
		buffer.append("\\");
		buffer.append(getYear());
		buffer.append(getMonth());
		File file2 = new File(buffer.toString());
		if (!(file2.exists())) {
			boolean result = file2.mkdirs();
			if(result) {
				System.out.printf("mkdir %s\n", file2);
			}
		}
		
		// for debug
		//System.out.println("File path = " + file2.toString());
		buffer.append("\\");
		buffer.append(getFilename());
		System.out.printf("File=%s\n", buffer.toString());
	
		return new File(buffer.toString());
	}
}
