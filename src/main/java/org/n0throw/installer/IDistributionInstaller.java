package org.n0throw.installer;

import java.io.File;
import java.nio.file.Path;

/**
 * Общий интерфейс для установки дистрибутива
 */
public interface IDistributionInstaller {

    /**
     * Устанавливает дистрибутив из файла
     * @param file Файл дистрибутива
     * @param path Путь куда установить дистрибутив
     * @return Флаг, успешно ли завершился процесс установки
     */
    boolean install(File file, String path);

    /**
     * Устанавливает дистрибутив из файла
     * @param file Файл дистрибутива
     * @param path Путь куда установить дистрибутив
     * @return Флаг, успешно ли завершился процесс установки
     */
    boolean install(File file, Path path);

    /**
     * Устанавливает дистрибутив из файла
     * @param file Файл дистрибутива
     * @return Флаг, успешно ли завершился процесс установки
     */
    boolean install(File file);
}
