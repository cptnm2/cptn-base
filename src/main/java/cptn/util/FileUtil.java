package cptn.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作工具类
 */
public class FileUtil {
	public static final int FILE_BUFF_SIZE = 65536;
	public static final int PROGRESS_ACTION_ID = 900;
	
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 获取程序运行的当前目录
	 * @return
	 */
	public static String getCurDir() {
		String dir = System.getProperty("user.dir");
		return dir;
	}

	/**
	 * 读取指定资源文件（需指定确定的文件名，包含扩展名，无法自动国际化）
	 * @param filePath
	 * @return
	 */
	public static ResourceBundle getResourceBundle(String filePath) {
		try {
			FileInputStream fis = new FileInputStream(filePath);
			return new PropertyResourceBundle(fis);
		}
		catch (Exception e) {
			logger.error("读取配置文件失败：" + filePath, e);
			return null;
		}
	}

	/**
	 * 创建目录树
	 * @param path
	 * @return
	 */
	public static boolean createFolderTree(String path) {
		boolean result = true;

		try {
			File f = new File(path);

			if (f.exists()) {
				return true;
			}

			f.mkdirs();
		}
		catch (Exception e) {
			logger.warn("", e);
			result = false;
		}

		return result;
	}

	/**
	 * 复制文件
	 * @param is
	 * @param destFile
	 * @param fileSize
	 * @param listener
	 * @return
	 */
	public static int copyFile(InputStream is, String destFile, long fileSize, ActionListener listener) {
		int result = 0;

		try {
			OutputStream out = new FileOutputStream(destFile);
			byte buf[] = new byte[FILE_BUFF_SIZE];
			int len;
			int progress = 0;
			long writedLen = 0;

			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
				writedLen += len;
				
				if (listener != null && fileSize > 0) {
					progress = (int) (writedLen * 100 / fileSize);
					
					ActionEvent ae = new ActionEvent(FileUtil.class, PROGRESS_ACTION_ID, String.valueOf(progress));
					listener.actionPerformed(ae);
				}
			}

			out.close();
			is.close();
		}
		catch (Exception e) {
			logger.warn("", e);
			result = -1;
		}

		return result;
	}

	/**
	 * 从路径中分离出文件名
	 * @param filePathName
	 * @return
	 */
	public static String getFileName(String filePathName) {
		if (StrUtil.isEmpty2(filePathName)) {
			return "";
		}

		filePathName = filePathName.trim();

		int pos1 = filePathName.lastIndexOf("/");
		int pos2 = filePathName.lastIndexOf("\\");
		int pos = pos1 > pos2 ? pos1 : pos2;

		if (pos >= 0) {
			return filePathName.substring(pos + 1);
		}
		else {
			return filePathName;
		}
	}
	
	/**
	 * 从路径中分离出路径和文件名
	 * @param filePathName
	 * @return
	 */
	public static String[] getFilePathName(String filePathName) {
		String[] data = { "", "" };
		
		if (StrUtil.isEmpty2(filePathName)) {
			return data;
		}

		filePathName = filePathName.trim();

		// 寻找最后一个分隔符
		int pos1 = filePathName.lastIndexOf("/");
		int pos2 = filePathName.lastIndexOf("\\");
		int pos = pos1 > pos2 ? pos1 : pos2;

		// 
		if (pos == (filePathName.length() - 1)) {
			// 以分隔符结尾，一定是路径，不带文件名
			data[0] = filePathName;
			data[1] = "";
		}
		else if (pos >= 0) {
			data[0] = filePathName.substring(0, pos + 1);
			data[1] = filePathName.substring(pos + 1);
		}
		else {
			data[0] = "";
			data[1] = filePathName;
		}
		
		return data;
	}

	/**
	 * 取得文件扩展名
	 * @param pathFileName
	 * @return
	 */
	public static String getFileExt(String pathFileName) {
		String fileName = getFileName(pathFileName);

		if (StrUtil.isEmpty2(fileName)) {
			return "";
		}

		int pos = pathFileName.lastIndexOf(".");

		if (pos >= 0) {
			return pathFileName.substring(pos + 1);
		}
		else {
			return "";
		}
	}

	/**
	 * 删除多个文件
	 * @param files
	 */
//	public static void deleteFiles(String files) {
//
//	}
	
	/**
	 * 在路径后拼接文件名
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static String appendFileName(String path, String fileName) {
		path = StrUtil.safeTrim2(path);
		fileName = StrUtil.safeTrim2(fileName);
		
		boolean b1 = false;
		boolean b2 = false;
		
		if (StrUtil.isEmpty(path)) {
			return fileName;
		}
		
		if (StrUtil.isEmpty(fileName)) {
			return path;
		}
		
		String suffix = path.substring(path.length() - 1);
		if (suffix.equals("/") || suffix.equals("\\")) {
			b1 = true;
		}
		
		String prefix = fileName.substring(0, 1);
		if (prefix.equals("/") || prefix.equals("\\")) {
			b2 = true;
		}
		
		if (b1 && b2) {
			return path + fileName.substring(1);
		}
		else if (b1 || b2) {
			return path + fileName;
		}
		else {
			return path + "/" + fileName;
		}
	}
	
	/**
	 * 为文件名添加扩展名
	 * @param fileName
	 * @param ext
	 * @return
	 */
	public static String appendFileExt(String fileName, String ext) {
		fileName = StrUtil.safeTrim2(fileName);
		ext = StrUtil.safeTrim2(ext);
		
		if (StrUtil.isEmpty2(ext)) {
			return fileName;
		}
		
		return (fileName + "." + ext);
	}
	
	/**
	 * 删除单个文件
	 * @param filePathName
	 * @return
	 */
	public static boolean deleteFile(String filePathName) {
		File file = new File(filePathName);
		
		if (file.exists()) {
			if (!file.isDirectory()) {
				file.delete();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 读取文本文件
	 * @param filePathName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String> readTextFile(String filePathName) throws FileNotFoundException, IOException {
		List<String> lines = new ArrayList<String>();
		InputStreamReader reader = null;
		BufferedReader br = null;
		InputStream input = null;
		
		try {
			input = new FileInputStream(filePathName);
			reader = new InputStreamReader(input);
	        br = new BufferedReader(reader);  
	        String line;  
	        
	        while ((line = br.readLine()) != null) {  
	            lines.add(line);  
	        }
		}
		finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
			}
			
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}

			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
			}
		}
		
		return lines;
	}
}
