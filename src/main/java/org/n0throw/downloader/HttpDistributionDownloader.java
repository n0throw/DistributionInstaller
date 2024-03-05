package org.n0throw.downloader;

import org.n0throw.utils.tempFile.TempFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Класс для скачивания дистрибутивов по HTTP/HTTPS
 */
public class HttpDistributionDownloader implements IDistributionDownloader {

    @Override
    public File download(String spec, String fileName, String fileExt, String... resolvePath) throws IOException {
        return download(new URL(spec), fileName, fileExt, resolvePath);
    }

    @Override
    public File download(URL url, String fileName, String fileExt, String... resolvePath) throws IOException {
        URLConnection connection = url.openConnection();
        return TempFile.createFromInputStream(
                connection.getInputStream(),
                fileName,
                fileExt,
                resolvePath
        );
    }
}
