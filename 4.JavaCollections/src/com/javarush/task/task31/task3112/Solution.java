package com.javarush.task.task31.task3112;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/* 
Загрузчик файлов
*/
public class Solution {

    public static void main(String[] args) throws IOException {
        Path passwords = downloadFile("https://www.amigo.com/ship/secretPassword.txt", Paths.get("D:/MyDownloads"));

        for (String line : Files.readAllLines(passwords, Charset.defaultCharset())) {
            System.out.println(line);
        }
    }

    public static Path downloadFile(String urlString, Path downloadDirectory) throws IOException {
        // implement this method

        /*Path pathToDownloadingFile = null;
        String downloadingFileName = null;

        if (urlString == null || downloadDirectory == null)
            return null;

        Map<String, String> envArray = System.getenv();
        Path tmpDir = null;
        if (envArray.containsKey("TEMP"))
            tmpDir = Paths.get(envArray.get("TEMP"));
        else
            tmpDir = Paths.get("D:\\Temp");

        if (tmpDir == null)
            return null;

        if (!Files.exists(tmpDir))
            Files.createDirectory(tmpDir);
        if (!Files.exists(downloadDirectory))
            Files.createDirectory(downloadDirectory);

        if (Files.exists(tmpDir) && Files.exists(downloadDirectory))
        {
            URL webpage = new URL(urlString);
            ReadableByteChannel rbc = Channels.newChannel(webpage.openStream());
            ByteBuffer buffer = ByteBuffer.allocate(2048);

            downloadingFileName = webpage.getFile();
            if (downloadingFileName.contains("/"))
            {
                int pos = downloadingFileName.lastIndexOf("/");
                downloadingFileName = downloadingFileName.substring(pos+1);
            }
            pathToDownloadingFile = Paths.get(tmpDir + "\\" + downloadingFileName);

            if (!Files.exists(pathToDownloadingFile))
                Files.createFile(pathToDownloadingFile);

            OutputStream writeStream = Files.newOutputStream(pathToDownloadingFile);

            WritableByteChannel writeChannel = Channels.newChannel(writeStream);
            while (rbc.read(buffer) != -1 && writeChannel.isOpen())
            {
                buffer.flip();
                writeChannel.write(buffer);
                buffer.compact();
            }
            rbc.close();
            writeChannel.close();
        }

        pathToDownloadingFile = Files.move(pathToDownloadingFile, Paths.get(downloadDirectory.toString() + "\\" + downloadingFileName));

        return pathToDownloadingFile;
        */

        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Path tmp = Files.createTempFile("temp-",".tmp");
        Files.copy(inputStream, tmp);
        String fieName = urlString.substring(urlString.lastIndexOf("/"));
        String dir = downloadDirectory.toString();
        Path moveTo = Paths.get(dir + fieName);
        Files.move(tmp, moveTo);

        return Paths.get(downloadDirectory.toString() + urlString.substring(urlString.lastIndexOf("/")));
    }
}
