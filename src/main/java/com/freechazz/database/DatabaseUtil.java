package com.freechazz.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DatabaseUtil {

    public static String compress(String data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
        try (GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
            gzipOS.write(data.getBytes("UTF-8"));
        }
        byte[] compressedData = bos.toByteArray();
        return Base64.getEncoder().encodeToString(compressedData);
    }

    public static String decompress(String compressedString) throws IOException {
        byte[] compressedData = Base64.getDecoder().decode(compressedString);
        ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
        try (GZIPInputStream gzipIS = new GZIPInputStream(bis)) {
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipIS.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, bytesRead, "UTF-8"));
            }
            return builder.toString();
        }
    }
}
