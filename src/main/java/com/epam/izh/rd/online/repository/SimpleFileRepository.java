package com.epam.izh.rd.online.repository;

import java.io.File;

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
        return;
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
        return null;
    }
}
