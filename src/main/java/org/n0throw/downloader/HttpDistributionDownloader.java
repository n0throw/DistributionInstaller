package org.n0throw.downloader;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.n0throw.utils.files.TempFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Класс для скачивания дистрибутивов по HTTP/HTTPS
 */
public class HttpDistributionDownloader implements IDistributionDownloader {
    /**
     * Скачивает дистрибутив.
     *
     * @param url           Путь до дистрибутива.
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл дистрибутива
     * @throws IOException              Если не удалось создать файл или подключиться/получить данные по протоколу HTTP/HTTPs.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @Override
    public File download(
            URL url,
            @NonNls @NotNull String sFileName,
            @NonNls String sFileExt,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        URLConnection connection = url.openConnection();
        return TempFile.createFromInputStream(
                connection.getInputStream(),
                sFileName,
                sFileExt,
                sResolvePaths
        );
    }
}
