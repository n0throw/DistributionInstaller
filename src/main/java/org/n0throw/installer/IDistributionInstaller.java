package org.n0throw.installer;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.FileSystemException;
import java.nio.file.Path;

/**
 * Общий интерфейс для установки дистрибутива.
 */
public interface IDistributionInstaller {

    /**
     * Устанавливает дистрибутив из файла.
     *
     * @param file                 Файл дистрибутива.
     * @param path                 Путь в виде строки, куда устанавливать дистрибутив.
     * @param isWaitInstallProcess Нужно ли ждать окончания процесса установки.
     * @return Флаг, успешно ли завершился процесс установки.
     * @throws FileSystemException Если файл имеет не валидное расширение.
     */
    default boolean install(
            @NotNull File file,
            @NonNls @NotNull String path,
            Boolean isWaitInstallProcess
    ) throws FileSystemException {
        return install(file, Path.of(path), isWaitInstallProcess);
    }

    /**
     * Устанавливает дистрибутив из файла.
     *
     * @param file                 Файл дистрибутива.
     * @param isWaitInstallProcess Нужно ли ждать окончания процесса установки.
     * @return Флаг, успешно ли завершился процесс установки.
     * @throws FileSystemException Если файл имеет не валидное расширение.
     */
    default boolean install(
            @NotNull File file,
            Boolean isWaitInstallProcess
    ) throws FileSystemException {
        return install(file, (Path) null, isWaitInstallProcess);
    }

    /**
     * Устанавливает дистрибутив из файла.
     *
     * @param file                 Файл дистрибутива.
     * @param path                 Путь куда установить дистрибутив (null, если указывать путь не требуется).
     * @param isWaitInstallProcess Нужно ли ждать окончания процесса установки.
     * @return Флаг, успешно ли завершился процесс установки.
     * @throws FileSystemException Если файл имеет не валидное расширение.
     */
    boolean install(
            @NotNull File file,
            @Nullable Path path,
            Boolean isWaitInstallProcess
    ) throws FileSystemException;
}
