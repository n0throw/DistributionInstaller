package org.n0throw.downloader;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.n0throw.utils.tempFile.TempFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Класс для скачивания дистрибутивов по SMB
 */
public class SmbDistributionDownloader implements IDistributionDownloader {

    private final NtlmPasswordAuthentication auth;

    public  SmbDistributionDownloader(String login, String password) {
        this(null, login, password);
    }

    public  SmbDistributionDownloader(String domain, String login, String password) {
        auth = new NtlmPasswordAuthentication(domain, login, password);
    }

    @Override
    public File download(String spec, String fileName, String fileExt, String... resolvePath) throws IOException {
        return download(
                new URL(null, spec, new jcifs.smb.Handler()),
                fileName,
                fileExt,
                resolvePath
        );
    }

    @Override
    public File download(URL url, String fileName, String fileExt, String... resolvePath) throws IOException {
        SmbFile smbFile = new SmbFile(url, auth);
        return TempFile.createFromInputStream(
                smbFile.getInputStream(),
                fileName,
                fileExt,
                resolvePath
        );
    }
}
