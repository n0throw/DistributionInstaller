package org.n0throw.utils.os;

import java.util.Locale;

/**
 * Менеджер операционных систем.
 */
public final class OsManager {
    /**
     * Операционная система.
     */
    private static final String OS = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    /**
     * Версия операционной системы.
     */
    private static final String VERSION = System.getProperty("os.version");

    /**
     * Архитектура операционной системы.
     */
    private static final String ARCH = System.getProperty("os.arch");

    /**
     * Возвращает флаг, является текущая система Windows.
     */
    public static boolean isWindows() {
        return OS.contains("win");
    }

    /**
     * Возвращает флаг, является текущая система Mac.
     */
    public static boolean isMac() {
        return OS.contains("mac");
    }

    /**
     * Возвращает флаг, является текущая система Unix.
     */
    public static boolean isUnix() {
        return OS.contains("nix") || OS.contains("nux") || OS.contains("aix");
    }

    /**
     * Возвращает наименование ОС.
     */
    public static OsName getOS() {
        if (isWindows()) return OsName.Windows;
        else if (isMac()) return OsName.Mac;
        else if (isUnix()) return OsName.Linux;
        else return OsName.None;
    }

    /**
     * Возвращает версию ОС.
     */
    public static String getOsVersion() {
        return VERSION;
    }

    /**
     * Возвращает архитектуру ОС.
     */
    public static String getOsArch() {
        return ARCH;
    }
}
