package com.javarush.task.task31.task3111;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class SearchFileVisitor extends SimpleFileVisitor<Path> {

    private String partOfName;
    private String partOfContent;
    private int minSize;
    private int maxSize;

    private List<Path> foundFiles = new LinkedList<>();;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        boolean isNeedToContinue = true;
        long limitedMaxSize = 0;

        if (partOfName != null && !String.valueOf(file.getFileName()).contains(partOfName))
            isNeedToContinue = false;

        if (isNeedToContinue)
        {
            if (minSize > 0)
            {
                if (Files.size(file) <= minSize)
                    isNeedToContinue = false;
            }
        }

        if (isNeedToContinue)
        {
            if (maxSize > 0)
            {
                /*if (Files.size(file) > Integer.MAX_VALUE)
                    limitedMaxSize = Integer.MAX_VALUE;
                else
                    limitedMaxSize = maxSize;*/

                limitedMaxSize = maxSize;
                if (Files.size(file) >= limitedMaxSize)
                    isNeedToContinue = false;
            }
        }

        if (isNeedToContinue)
        {
            if (partOfContent != null && !isPartOfContentValid(file, partOfContent))
                isNeedToContinue = false;
        }

        if (isNeedToContinue)
            foundFiles.add(file);

        return super.visitFile(file, attrs);
    }

    public void setPartOfName(String partOfName)
    {
        this.partOfName = partOfName;
    }

    public void setPartOfContent(String partOfContent)
    {
        this.partOfContent = partOfContent;
    }

    public void setMinSize(int minSize)
    {
        this.minSize = minSize;
    }

    public void setMaxSize(int maxSize)
    {
        this.maxSize = maxSize;
    }

    public List<Path> getFoundFiles()
    {
        return foundFiles;
    }

    private boolean isPartOfContentValid(Path file, String partOfContent) throws IOException
    {
        boolean isFound = false;
        try (BufferedReader bf = Files.newBufferedReader(file, Charset.forName("UTF-8")))
        {
            String line = null;
            while ((line = bf.readLine()) != null)
            {
                if (line.contains(partOfContent))
                {
                    isFound = true;
                    break;
                }
            }
        }
        catch (IOException e)
        {}

        return isFound;
    }
}
