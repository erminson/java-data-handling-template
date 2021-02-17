package com.epam.izh.rd.online.repository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class SimpleFileRepository implements FileRepository {

    /**
     * Метод рекурсивно подсчитывает количество файлов в директории
     *
     * @param path путь до директори
     * @return файлов, в том числе скрытых
     */
    @Override
    public long countFilesInDirectory(String path) {
        File file = new File(path);

        if (!file.exists()) {
            file = new File("src/main/resources/" + path);
            if (!file.exists()) return 0L;
        }

        if (file.isFile()) return 1L;

        long count = 0;
        File[] files = file.listFiles();
        assert files != null;
        for (File currentFile : files) {
            count += countFilesInDirectory(currentFile.getPath());
        }

        return count;
    }

    /**
     * Метод рекурсивно подсчитывает количество папок в директории, считая корень
     *
     * @param path путь до директории
     * @return число папок
     */
    @Override
    public long countDirsInDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file = new File("src/main/resources/" + path);
            if (!file.exists()) return 0L;
        }

        long count = 1;
        File[] files = file.listFiles();
        assert files != null;
        for (File currentFile : files) {
            if (currentFile.isDirectory()) {
                count += countDirsInDirectory(currentFile.getPath());
            }
        }

        return count;
    }

    /**
     * Метод копирует все файлы с расширением .txt
     *
     * @param from путь откуда
     * @param to   путь куда
     */
    @Override
    public void copyTXTFiles(String from, String to) {
        File inputFile = new File(from);
        File outputFile = new File(to);

        if (!inputFile.exists()) {
            return;
        }

        if (inputFile.isFile()) {
            try {
                String content = readFile(from);
                writeContentToFile(to, content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        if (inputFile.isDirectory()) {
//            File[] files = Arrays.stream(inputFile.listFiles())
//                    .filter(file -> getExtension(file).equals("txt"))
//                    .toArray(File[]::new);
//
//            for (File file : files) {
//                try {
//                    String content = readFile(file.getPath());
//                    String path = Paths.get(outputFile.getPath(), file.getName()).toString();
//                    writeContentToFile(path, content);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    String getExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        if (index == -1) return "";
        return fileName.substring(index + 1);
    }

    String readFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(String.format("File: %s not found", path));
        }

        StringBuilder sb = new StringBuilder();
        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream)
        ) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line);
                if (scanner.hasNextLine()) {
                    sb.append('\n');
                }
            }
        }

        return sb.toString();
    }

    void writeContentToFile(String path, String content) throws IOException {
        File file = new File(path);
        File parent = new File(file.getParent());
        if (!parent.exists()) parent.mkdirs();

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
             BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {
            writer.write(content);
        }
    }

    /**
     * Метод создает файл на диске с расширением txt
     *
     * @param path путь до нового файла
     * @param name имя файла
     * @return был ли создан файл
     */
    @Override
    public boolean createFile(String path, String name) {
        Path dirPath = Paths.get("target", "classes", path);
        File dir = new File(dirPath.toString());
        if (!dir.exists()) dir.mkdirs();

        Path filePath = Paths.get(dir.getPath(), name);
        File file = new File(filePath.toString());
        if (file.exists())  return false;

        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Метод считывает тело файла .txt из папки src/main/resources
     *
     * @param fileName имя файла
     * @return контент
     */
    @Override
    public String readFileFromResources(String fileName) {
        Path filePath = Paths.get("src", "main", "resources", fileName);
        File file = new File(filePath.toString());

        String content = null;
        try {
            content = readFile(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
