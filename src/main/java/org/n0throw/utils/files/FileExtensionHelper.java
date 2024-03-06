package org.n0throw.utils.files;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

/**
 * Класс для определения типов файлов по их расширению.
 */
public final class FileExtensionHelper {

    /**
     * По файлу определяет его тип.
     *
     * @param file Файл.
     * @return Тип файла.
     */
    @NotNull
    public static FileExtension getFromFile(@Nullable File file) {
        if (file == null) return FileExtension.None;
        return getFromFileName(file.getName());
    }

    /**
     * По пути определяет тип файла.
     *
     * @param path Путь к файлу.
     * @return Тип файла.
     */
    @NotNull
    public static FileExtension getFromPath(@Nullable Path path) {
        if (path == null) return FileExtension.None;
        return getFromFileName(path.getFileName());
    }

    /**
     * По пути к файлу в виде строки определяет его тип.
     *
     * @param sFilePath Путь к файлу в виде строки.
     * @return Тип файла.
     */
    @NotNull
    public static FileExtension getFromPath(@Nullable String sFilePath) {
        if (sFilePath == null) return FileExtension.None;
        return getFromPath(Path.of(sFilePath));
    }

    /**
     * По наименованию файла в виде объекта Path определяет его тип.
     *
     * @param fileName Наименование файла.
     * @return Тип файла.
     */
    @NotNull
    public static FileExtension getFromFileName(@Nullable Path fileName) {
        if (fileName == null) return FileExtension.None;
        return getFromFileName(fileName.toString());
    }

    /**
     * По наименованию файла в виде строки определяет его тип.
     *
     * @param sFileName Наименование файла.
     * @return Тип файла.
     */
    @NotNull
    public static FileExtension getFromFileName(@Nullable String sFileName) {
        if (sFileName == null) return FileExtension.None;
        int separatorIndex = sFileName.indexOf(".");
        final String sFileExtension;
        if (separatorIndex == -1) {
            sFileExtension = "";
        } else {
            sFileExtension = sFileName.substring(separatorIndex);
        }
        return getFromExtName(sFileExtension);
    }

    /**
     * По расширению файла определяет его тип.
     *
     * @param sExtensionName Расширение файла (без точки).
     * @return Тип файла.
     */
    @NotNull
    public static FileExtension getFromExtName(String sExtensionName) {
        return switch (sExtensionName) {
            case "exe", "msi" -> FileExtension.WindowsExecutable;
            case "pkg" -> FileExtension.OsXExecutable;
            case "tar.gz", "tgz", "zip" -> FileExtension.Archive;
            default -> FileExtension.None;
        };
    }
}
