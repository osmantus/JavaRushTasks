package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.List;

/* 
Что внутри папки?
*/
public class Solution extends SimpleFileVisitor<Path>
{
    private static int dirsCount;
    private static int filesCount;
    private static long allFilesSize;

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selectedPath = br.readLine();
        br.close();

        if (!selectedPath.equals(""))
        {
            Path path = Paths.get(selectedPath);

            if (Files.exists(path))
            {
                if (!Files.isDirectory(path))
                {
                    System.out.println(path + " - не папка");
                    return;
                }
                else
                {
                    EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
                    final Solution fileVisitor = new Solution();
                    Files.walkFileTree(path, options, Integer.MAX_VALUE, fileVisitor);

                    System.out.println("Всего папок - " + (dirsCount-1));
                    System.out.println("Всего файлов - " + filesCount);
                    System.out.println("Общий размер - " + allFilesSize);
                }
            }
            else
                System.out.println(path + " - не папка");
        }
        else
            System.out.println(args[0] + " - не папка");
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
        Solution.dirsCount++;
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        Solution.filesCount++;
        Solution.allFilesSize += Files.size(file);
        return super.visitFile(file, attrs);
    }
}
