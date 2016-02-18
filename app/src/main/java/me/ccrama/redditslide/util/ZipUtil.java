package me.ccrama.redditslide.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class ZipUtil {
    private static final int BUFFER = 2048;

    /**
     * Source : http://www.jondev.net/articles/Zipping_Files_with_Android_%28Programmatically%29
     * @param files Files to zip
     * @param zipPath Name of zip file (including path)
     * @return Success
     */
    public static boolean zipFiles(List<String> files, File zipPath, String zipName) {
        //fixme needs to check if destination exists
        //fixme use zipName somewhere
        try {
            LogUtil.v("Attempting to create" + zipPath + zipName);
            BufferedInputStream origin;


            FileOutputStream dest = new FileOutputStream(zipPath);

            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for (String file : files) {
                LogUtil.v("Adding: " + file);
                FileInputStream fi = new FileInputStream(file);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * Source: http://stackoverflow.com/a/10997886
     * @param outputPath Where to put the files
     * @param zipName Name of zip file (including path)
     * @return Success
     */
    public static boolean unzipFiles(String outputPath, String zipName) {
        InputStream is;
        ZipInputStream zis;
        try {
            String filename;
            is = new FileInputStream(outputPath + zipName);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[BUFFER];
            int count;

            while ((ze = zis.getNextEntry()) != null) {
                filename = ze.getName();

                // Need to create directories if not exists
                if (ze.isDirectory()) {
                    File fmd = new File(outputPath + filename);
                    //noinspection ResultOfMethodCallIgnored
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(outputPath + filename);

                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}