package cc.utils;

import java.io.*;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

public class ZipRar {

    /**
     * 压缩文件
     *
     * @param srcfile
     *            File[] 需要压缩的文件列表
     * @param zipfile
     *            File 压缩后的文件
     */
    public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                String str = srcfile[i].getName();
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println(zipfile.getName()+"压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}