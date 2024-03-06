package org.n0throw.downloader;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.n0throw.utils.files.TempFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Класс для скачивания дистрибутивов по SMB
 */
public class SmbDistributionDownloader implements IDistributionDownloader {
    /**
     * Данные пользователя для аутентификации.
     */
    private final NtlmPasswordAuthentication auth;

    /**
     * @param login    Логин пользователя.
     * @param password Пароль пользователя.
     */
    public SmbDistributionDownloader(String login, String password) {
        this(null, login, password);
    }

    public SmbDistributionDownloader(String domain, String login, String password) {
        this(new NtlmPasswordAuthentication(domain, login, password));
    }

    /**
     * @param auth Данные для авторизации пользователя.
     */
    public SmbDistributionDownloader(NtlmPasswordAuthentication auth) {
        this.auth = auth;
    }

    /**
     * Авторизация через анонимного пользователя.
     */
    public SmbDistributionDownloader() {
        this.auth = NtlmPasswordAuthentication.ANONYMOUS;
    }

    /**
     * Скачивает дистрибутив.
     *
     * @param url           Путь до дистрибутива.
     * @param sFileName     Наименование файла.
     * @param sFileExt      Расширение файла.
     * @param sResolvePaths Относительный путь от темповой папки.
     * @return Файл дистрибутива.
     * @throws IOException              Если не удалось создать файл или подключиться/получить данные по протоколу SMB.
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
        SmbFile smbFile = new SmbFile(url, auth);
        return TempFile.createFromInputStream(
                smbFile.getInputStream(),
                sFileName,
                sFileExt,
                sResolvePaths
        );
    }
}
