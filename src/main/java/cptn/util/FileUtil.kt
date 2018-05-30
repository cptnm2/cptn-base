package cptn.util

import org.slf4j.LoggerFactory
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.*
import java.util.*

/**
 * 文件操作工具类
 */
object FileUtil {
    val FILE_BUFF_SIZE = 65536
    val PROGRESS_ACTION_ID = 900

    private val logger = LoggerFactory.getLogger(FileUtil::class.java)

    /**
     * 获取程序运行的当前目录
     * @return
     */
    val curDir: String
        get() = System.getProperty("user.dir")

    /**
     * 读取指定资源文件（需指定确定的文件名，包含扩展名，无法自动国际化）
     * @param filePath
     * @return
     */
    fun getResourceBundle(filePath: String): ResourceBundle? {
        try {
            val fis = FileInputStream(filePath)
            return PropertyResourceBundle(fis)
        } catch (e: Exception) {
            logger.error("读取配置文件失败：$filePath", e)
            return null
        }

    }

    /**
     * 创建目录树
     * @param path
     * @return
     */
    fun createFolderTree(path: String): Boolean {
        var result = true

        try {
            val f = File(path)

            if (f.exists()) {
                return true
            }

            f.mkdirs()
        } catch (e: Exception) {
            logger.warn("", e)
            result = false
        }

        return result
    }

    /**
     * 复制文件
     * @param `ins`
     * @param destFile
     * @param fileSize
     * @param listener
     * @return
     */
    fun copyFile(ins: InputStream, destFile: String, fileSize: Long, listener: ActionListener?): Int {
        var result = 0

        try {
            val out = FileOutputStream(destFile)
            val buf = ByteArray(FILE_BUFF_SIZE)
            var progress = 0
            var writedLen: Long = 0
            var len: Int = ins.read(buf)

            while (len > 0) {
                out.write(buf, 0, len)
                writedLen += len.toLong()

                if (listener != null && fileSize > 0) {
                    progress = (writedLen * 100 / fileSize).toInt()

                    val ae = ActionEvent(FileUtil::class.java, PROGRESS_ACTION_ID, progress.toString())
                    listener.actionPerformed(ae)
                }

                len = ins.read(buf)
            }

            out.close()
            ins.close()
        } catch (e: Exception) {
            logger.warn("", e)
            result = -1
        }

        return result
    }

    /**
     * 从路径中分离出文件名
     * @param filePathName
     * @return
     */
    fun getFileName(filePathName: String): String {
        var filePathName = filePathName
        if (StrUtil.isEmpty2(filePathName)) {
            return ""
        }

        filePathName = filePathName.trim { it <= ' ' }

        val pos1 = filePathName.lastIndexOf("/")
        val pos2 = filePathName.lastIndexOf("\\")
        val pos = if (pos1 > pos2) pos1 else pos2

        return if (pos >= 0) {
            filePathName.substring(pos + 1)
        } else {
            filePathName
        }
    }

    /**
     * 从路径中分离出路径和文件名
     * @param filePathName
     * @return
     */
    fun getFilePathName(filePathName: String): Array<String> {
        var filePathName = filePathName
        val data = arrayOf("", "")

        if (StrUtil.isEmpty2(filePathName)) {
            return data
        }

        filePathName = filePathName.trim { it <= ' ' }

        // 寻找最后一个分隔符
        val pos1 = filePathName.lastIndexOf("/")
        val pos2 = filePathName.lastIndexOf("\\")
        val pos = if (pos1 > pos2) pos1 else pos2

        //
        if (pos == filePathName.length - 1) {
            // 以分隔符结尾，一定是路径，不带文件名
            data[0] = filePathName
            data[1] = ""
        } else if (pos >= 0) {
            data[0] = filePathName.substring(0, pos + 1)
            data[1] = filePathName.substring(pos + 1)
        } else {
            data[0] = ""
            data[1] = filePathName
        }

        return data
    }

    /**
     * 取得文件扩展名
     * @param pathFileName
     * @return
     */
    fun getFileExt(pathFileName: String): String {
        val fileName = getFileName(pathFileName)

        if (StrUtil.isEmpty2(fileName)) {
            return ""
        }

        val pos = pathFileName.lastIndexOf(".")

        return if (pos >= 0) {
            pathFileName.substring(pos + 1)
        } else {
            ""
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
    fun appendFileName(path: String, fileName: String): String {
        var path = path
        var fileName = fileName
        path = StrUtil.safeTrim2(path)
        fileName = StrUtil.safeTrim2(fileName)

        var b1 = false
        var b2 = false

        if (StrUtil.isEmpty(path)) {
            return fileName
        }

        if (StrUtil.isEmpty(fileName)) {
            return path
        }

        val suffix = path.substring(path.length - 1)
        if (suffix == "/" || suffix == "\\") {
            b1 = true
        }

        val prefix = fileName.substring(0, 1)
        if (prefix == "/" || prefix == "\\") {
            b2 = true
        }

        return if (b1 && b2) {
            path + fileName.substring(1)
        } else if (b1 || b2) {
            path + fileName
        } else {
            "$path/$fileName"
        }
    }

    /**
     * 为文件名添加扩展名
     * @param fileName
     * @param ext
     * @return
     */
    fun appendFileExt(fileName: String, ext: String): String {
        var fileName = fileName
        var ext = ext
        fileName = StrUtil.safeTrim2(fileName)
        ext = StrUtil.safeTrim2(ext)

        return if (StrUtil.isEmpty2(ext)) {
            fileName
        } else "$fileName.$ext"

    }

    /**
     * 删除单个文件
     * @param filePathName
     * @return
     */
    fun deleteFile(filePathName: String): Boolean {
        val file = File(filePathName)

        if (file.exists()) {
            if (!file.isDirectory) {
                file.delete()
                return true
            }
        }

        return false
    }

    /**
     * 读取文本文件
     * @param filePathName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Throws(FileNotFoundException::class, IOException::class)
    fun readTextFile(filePathName: String): List<String> {
        val lines = ArrayList<String>()
        var reader: InputStreamReader? = null
        var br: BufferedReader? = null
        var input: InputStream? = null

        try {
            input = FileInputStream(filePathName)
            reader = InputStreamReader(input)
            br = BufferedReader(reader)
            var line: String? = br.readLine()

            while (line != null) {
                lines.add(line)

                line = br.readLine()
            }
        } finally {
            try {
                if (br != null) {
                    br.close()
                }
            } catch (e: IOException) {
            }

            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (e: IOException) {
            }

            try {
                if (input != null) {
                    input.close()
                }
            } catch (e: IOException) {
            }

        }

        return lines
    }
}
