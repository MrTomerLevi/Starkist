package com.bytbale.starkist.filesystem.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;

/**
 * Created by tomerlev on 19/01/2017.
 */
public class ZipUtils {

    public static void unzip(String source, String destination) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            throw new IOException("Unable to unzip file: " + source,e);
        }
    }
}
