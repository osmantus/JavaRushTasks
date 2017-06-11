package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {

        LinkedList<String> filesAbsPaths = null;
        LinkedList<String> filesAuxAbsPaths = null;
        LinkedList<File> foldersNames = null;
        LinkedList<String> foldersRemovedFromStack = null;

        String filePath = null;

        if (!root.isEmpty())
        {
            File file = new File(root);
            if (file.isDirectory())
            {
                foldersNames = new LinkedList<>();
                foldersNames.push(file);
                filesAbsPaths = new LinkedList<>();
                foldersRemovedFromStack = new LinkedList<>();
                String fileName = null;

                int dirsCount = 0;
                //int filesCount = 0;
                while (!foldersNames.isEmpty())
                {
                    filesAuxAbsPaths = new LinkedList<>();
                    file = foldersNames.getFirst();
                    if (!foldersRemovedFromStack.contains(file.getAbsolutePath()))
                    {
                        File[] fileList = file.listFiles();
                        if (fileList.length > 0)
                        {
                            dirsCount = 0;
                            //filesCount = 0;
                            for (int i = fileList.length - 1; i >= 0; i--)
                            {
                                filePath = fileList[i].getAbsolutePath();
                                if (fileList[i].isDirectory())
                                {
                                    if (!foldersRemovedFromStack.contains(filePath))
                                    {
                                        foldersNames.push(fileList[i]);
                                        dirsCount++;
                                    }
                                } else
                                {
                                    if (!filesAbsPaths.contains(filePath))
                                    {
                                        filesAuxAbsPaths.addFirst(filePath);
                                        //filesAbsPaths.add(fileList[i].getAbsolutePath());
                                        //filesCount++;
                                    }
                                }
                            }
                            if (!filesAuxAbsPaths.isEmpty())
                            {
                                filesAbsPaths.addAll(filesAuxAbsPaths);
                                System.out.println(filesAbsPaths.get(filesAbsPaths.size()-1));
                            }

                            if (dirsCount == 0)
                            {
                                foldersRemovedFromStack.add(foldersNames.getFirst().getAbsolutePath());
                                foldersNames.removeFirst();
                            }
                        }
                        else
                        {
                            foldersRemovedFromStack.add(file.getAbsolutePath());
                            foldersNames.removeFirst();
                        }

                    }
                }
            }
        }

        return filesAbsPaths;
    }

    public static void main(String[] args)
    {
        try
        {
            String root = "D:\\Work";
            List<String> filesPaths = Solution.getFileTree(root);
            System.out.println("Number of all files under " + root + " counts: " + filesPaths.size());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
