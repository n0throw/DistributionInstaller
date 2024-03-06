package org.n0throw.downloader;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Общий интерфейс для скачивания дистрибутива.
 */
public interface IDistributionDownloader {
    /**
     * Скачивает дистрибутив. Итоговый файл имеет расширение .tmp.
     *
     * @param sSpec         Путь до дистрибутива.
     * @param sFileName     Наименование файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл дистрибутива.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    default File download(
            String sSpec,
            @NonNls @NotNull String sFileName,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        return download(sSpec, sFileName, null, sResolvePaths);
    }

    /**
     * Скачивает дистрибутив.
     *
     * @param sSpec         Путь до дистрибутива.
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл дистрибутива.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    default File download(
            String sSpec,
            @NonNls @NotNull String sFileName,
            @NonNls String sFileExt,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        return download(new URL(null, sSpec, new jcifs.smb.Handler()), sFileName, sFileExt, sResolvePaths);
    }

    /**
     * Скачивает дистрибутив. Итоговый файл имеет расширение .tmp.
     *
     * @param url           Путь до дистрибутива.
     * @param sFileName     Наименование файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл дистрибутива.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    default File download(
            URL url,
            @NonNls @NotNull String sFileName,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        return download(url, sFileName, null, sResolvePaths);
    }

    /**
     * Скачивает дистрибутив.
     *
     * @param url           Путь до дистрибутива.
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл дистрибутива.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    File download(
            URL url,
            @NonNls @NotNull String sFileName,
            @NonNls String sFileExt,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException;
}
