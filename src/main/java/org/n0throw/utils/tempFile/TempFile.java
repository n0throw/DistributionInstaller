package org.n0throw.utils.tempFile;

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
     * Фабрика, для работы с временными файлами
     */
    private static final TempFileFactory tempFileFactory = TempFileFactory.getInstance();

    /**
     * Создаёт пустой временный файл
     *
     * @param fileName    Наименование файла
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл
     * @throws IOException Ошибка при создании файла
     */
    public static File createEmptyFile(
            String fileName,
            String... resolvePath
    ) throws IOException {
        return createEmptyFile(fileName, null, resolvePath);
    }

    /**
     * Создаёт пустой временный файл
     *
     * @param fileName    Наименование файла
     * @param fileExt     Расширение файла (Если null, то будет .tmp)
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл
     * @throws IOException Ошибка при создании файла
     */
    public static File createEmptyFile(
            String fileName,
            String fileExt,
            String... resolvePath
    ) throws IOException {
        Path path = Path.of(
                System.getProperty("java.io.tmpdir"),
                resolvePath
        );

        File tempFile = File.createTempFile(
                fileName,
                fileExt,
                Files.createDirectories(path).toFile()
        );
        tempFile.deleteOnExit();
        tempFileFactory.registerForDelete(tempFile);
        return tempFile;
    }

    /**
     * Создание временного файла с содержимым из потока
     *
     * @param stream      Поток
     * @param fileName    Наименование файла
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл
     * @throws IOException Ошибка при создании файла
     */
    public static File createFromInputStream(
            InputStream stream,
            String fileName,
            String... resolvePath
    ) throws IOException {
        return createFromInputStream(stream, fileName, null, resolvePath);
    }

    /**
     * Создание временного файла с содержимым из потока
     *
     * @param stream      Поток
     * @param fileName    Наименование файла
     * @param fileExt     Расширение файла (Если null, то будет .tmp)
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл
     * @throws IOException Ошибка при создании файла
     */
    public static File createFromInputStream(
            InputStream stream,
            String fileName,
            String fileExt,
            String... resolvePath
    ) throws IOException {
        final File tempFile = createEmptyFile(fileName, fileExt, resolvePath);
        Files.copy(
                stream,
                tempFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );
        return tempFile;
    }

    /**
     * Создание временного файла с содержимым из строки
     *
     * @param fileName    Наименование файла
     * @param fileContent Содержимое файла
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл
     * @throws IOException Ошибка при создании файла
     */
    public static File createFromString(
            String fileName,
            String fileContent,
            String... resolvePath) throws IOException {
        return createFromInputStream(
                new ByteArrayInputStream(fileContent.getBytes()),
                fileName,
                resolvePath
        );
    }

    /**
     * Создание временного файла с содержимым из строки
     *
     * @param fileName    Наименование файла
     * @param fileExt     Расширение файла (Если null, то будет .tmp)
     * @param fileContent Содержимое файла
     * @param resolvePath Относительный путь от темповой папки
     * @return Файл
     * @throws IOException Ошибка при создании файла
     */
    public static File createFromString(
            String fileName,
            String fileExt,
            String fileContent,
            String... resolvePath) throws IOException {
        return createFromInputStream(
                new ByteArrayInputStream(fileContent.getBytes()),
                fileName,
                fileExt,
                resolvePath
        );
    }
}
