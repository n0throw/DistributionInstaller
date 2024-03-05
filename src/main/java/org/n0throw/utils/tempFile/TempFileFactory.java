package org.n0throw.utils.tempFile;

import java.io.File;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.nio.file.FileSystemException;
import java.util.HashSet;

/**
 * Фабрика временных файлов
 */
public class TempFileFactory {
    /**
     * Поток для удаления временных файлов
     */
    private volatile TempFileThread thread;

    /**
     * Очеред временных файлов, которая позволяет понять, освободила ли JVM файл или нет
     */
    private final ReferenceQueue<File> referenceQueue;

    /**
     * Уникальынй набор ссылок на временные файлы
     */
    private final HashSet<TempFileReference> references;

    private TempFileFactory() {
        thread = new TempFileThread();
        referenceQueue = new ReferenceQueue<>();
        references = new HashSet<>();
    }

    /**
     * Возвращает инстанцию фабрики для работы с временными файлами.
     */
    public static TempFileFactory getInstance() {
        return new TempFileFactory();
    }


    /**
     * Метод регистрации файла на удаление.
     * Синхронизирует потоки, что бы случайно не удалить один файл два раза и не словить ошибку.
     * @param file Файл, который нужно будет удалить.
     */
    public synchronized void  registerForDelete(File file) {
        references.add(new TempFileReference(file, referenceQueue));
        if (thread == null) {
            thread = new TempFileThread();
            thread.start();
        }
    }

    /**
     * Поток удаления временных файлов. Ждёт пока JVM, освободит файл, что бы его удалить
     */
    class TempFileThread extends Thread {

        public TempFileThread() {
            super("TempFile");
            setDaemon(true);
        }

        @Override
        public void run() {
            while (!references.isEmpty()) {
                try {
                    // Получаем уже не использованный файл
                    TempFileReference unusedReference = (TempFileReference) referenceQueue.remove();
                    // пытаемся удалить этот файл
                    references.remove(unusedReference);
                    boolean isDeleteFile = unusedReference.delete();
                    if (!isDeleteFile) {
                        throw new FileSystemException(unusedReference.getPath());
                    }
                    unusedReference.clear();
                } catch (InterruptedException | FileSystemException e) {
                    // todo slf4j
                    throw new RuntimeException("Не удалось удалить файл", e);
                }
            }
            thread = null;
        }
    }

    /**
     * Ссылка на временный файл.
     */
    static class TempFileReference extends PhantomReference<File> {
        /**
         * Путь к временному файлу
         */
        private final String path;

        /**
         * Создаёт ссылку на временный файл
         * @param file Файл
         * @param queue Очеред ссылок на временный файл.
         */
        TempFileReference(File file, ReferenceQueue<File> queue) {
            super(file, queue);
            this.path = file.getPath();
        }

        /**
         * Возвращает путь к временному файлу
         */
        public String getPath() {
            return path;
        }

        /**
         * Удаляет временный файл
         */
        public boolean delete() {
            File file = new File(path);
            if (!file.exists()) {
                return false;
            }
            return file.delete();
        }
    }
}