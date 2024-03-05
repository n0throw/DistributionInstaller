package org.n0throw.downloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Общий интерфейс для скачивания дистрибутива
 */
public interface IDistributionDownloader {

    /**
     * Скачивает дистрибутив.
     *
     * @param spec         Путь до дистрибутива
     * @param fileName    Наименование файла
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл дистрибутива
     */
    default File download(String spec, String fileName, String... resolvePath) throws IOException {
        return download(spec, fileName, null, resolvePath);
    }

    /**
     * Скачивает дистрибутив.
     *
     * @param spec         Путь до дистрибутива
     * @param fileName    Наименование файла
     * @param fileExt     Расширение файла (Если null, то будет .tmp)
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл дистрибутива
     */
    File download(String spec, String fileName, String fileExt, String... resolvePath) throws IOException;

    /**
     * Скачивает дистрибутив.
     *
     * @param url         Путь до дистрибутива
     * @param fileName    Наименование файла
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл дистрибутива
     */
    default File download(URL url, String fileName, String... resolvePath) throws IOException {
        return download(url, fileName, null, resolvePath);
    }

    /**
     * Скачивает дистрибутив.
     *
     * @param url         Путь до дистрибутива
     * @param fileName    Наименование файла
     * @param fileExt     Расширение файла (Если null, то будет .tmp)
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл дистрибутива
     */
    File download(URL url, String fileName, String fileExt, String... resolvePath) throws IOException;
}
