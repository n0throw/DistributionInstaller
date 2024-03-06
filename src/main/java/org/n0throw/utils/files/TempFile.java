package org.n0throw.utils.files;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Временный файл.
 * Стандартный механизм (File.createTempFile) не может обеспечить 100% удаление темпового файла.
 * Данный класс исправляет данную проблему.
 */
public class TempFile {
    /**
     * Фабрика, для работы с временными файлами.
     */
    private static final TempFileFactory tempFileFactory = TempFileFactory.getInstance();

    /**
     * Создаёт пустой временный файл с расширением .tmp.
     *
     * @param sFileName     Наименование файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @NotNull
    public static File createEmptyFile(
            @NonNls @NotNull String sFileName,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        return createEmptyFile(sFileName, null, sResolvePaths);
    }

    /**
     * Создаёт пустой временный файл.
     *
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла (Если null, то будет .tmp).
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @NotNull
    public static File createEmptyFile(
            @NonNls @NotNull String sFileName,
            @NonNls String sFileExt,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        Path path = Path.of(
                System.getProperty("java.io.tmpdir"),
                sResolvePaths
        );

        File tempFile = File.createTempFile(
                sFileName,
                sFileExt,
                Files.createDirectories(path).toFile()
        );
        tempFile.deleteOnExit();
        tempFileFactory.registerForDelete(tempFile);
        return tempFile;
    }

    /**
     * Создание временного файла с расширением .tmp из потока.
     *
     * @param stream        Поток.
     * @param sFileName     Наименование файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @NotNull
    public static File createFromInputStream(
            @NotNull InputStream stream,
            @NonNls @NotNull String sFileName,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        return createFromInputStream(stream, sFileName, null, sResolvePaths);
    }

    /**
     * Создание временного файла с расширением .tmp из строки.
     *
     * @param sFileName     Наименование файла.
     * @param sFileContent  Содержимое файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @NotNull
    public static File createFromString(
            @NonNls @NotNull String sFileName,
            @Nullable String sFileContent,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        return createFromString(
                sFileName,
                null,
                sFileContent,
                sResolvePaths
        );
    }

    /**
     * Создание временного файла из строки.
     *
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла (Если null, то будет .tmp).
     * @param sFileContent  Содержимое файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @NotNull
    public static File createFromString(
            @NonNls @NotNull String sFileName,
            @NonNls String sFileExt,
            @Nullable String sFileContent,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        if (sFileContent == null) return createEmptyFile(sFileName, sFileExt, sResolvePaths);
        return createFromInputStream(
                new ByteArrayInputStream(sFileContent.getBytes()),
                sFileName,
                sFileExt,
                sResolvePaths
        );
    }

    /**
     * Создание временного файла из потока.
     *
     * @param stream        Поток.
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла (Если null, то будет .tmp).
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл.
     * @throws IOException              Если не удалось создать файл.
     * @throws IllegalArgumentException Если наименование файла содержит неразрешённые символы.
     * @throws SecurityException        Если нет прав на создание файла.
     */
    @NotNull
    public static File createFromInputStream(
            InputStream stream,
            @NonNls @NotNull String sFileName,
            @NonNls String sFileExt,
            @NonNls @NotNull String... sResolvePaths
    ) throws IOException {
        final File tempFile = createEmptyFile(sFileName, sFileExt, sResolvePaths);
        Files.copy(
                stream,
                tempFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
        return tempFile;
    }
}
