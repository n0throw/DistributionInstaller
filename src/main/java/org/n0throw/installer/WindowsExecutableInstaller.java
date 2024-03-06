package org.n0throw.installer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.n0throw.utils.files.FileExtension;
import org.n0throw.utils.files.FileExtensionHelper;

import java.io.*;
import java.nio.file.FileSystemException;
import java.nio.file.Path;

/**
 * Класс для установки дистрибутивов c помощью установщика windows.
 */
public class WindowsExecutableInstaller implements IDistributionInstaller {

    /**
     * Устанавливает дистрибутив из файла.
     *
     * @param file                 Файл дистрибутива.
     * @param path                 Для исполняемых файлов данный параметр является излишним.
     * @param isWaitInstallProcess Нужно ли ждать окончания процесса установки.
     * @return Флаг, успешно ли завершился процесс установки.
     * @throws FileSystemException Если файл имеет расширение отличное от .msi или .exe.
     */
    @Override
    public boolean install(
            @NotNull File file,
            @Nullable Path path,
            Boolean isWaitInstallProcess
    ) throws FileSystemException {
        FileExtension fileExt = FileExtensionHelper.getFromFile(file);
        if (fileExt != FileExtension.WindowsExecutable) {
            throw new FileSystemException("Недопустимое расширение файла.");
        }

        final Process process;
        try {
            process = Runtime.getRuntime().exec(file.getName(), null, file.getParentFile());
        } catch (IOException e) {
            // todo slf4j
            return false;
        }

        if (isWaitInstallProcess) {
            try {
                process.waitFor();
                return true;
            } catch (InterruptedException e) {
                // todo slf4j
                return false;
            }
        }

        return true;
    }
}
