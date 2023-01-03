package io.github.dong4j.coco.kernel.common.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import cn.hutool.core.util.StrUtil;

/**
 * <p>Description: </p>
 *
 * @author Spark.Team
 * @version 1.0.0
 * @email "mailto:Spark.Team@gmail.com"
 * @date 2023.01.03 13:54
 * @since 2023.1.1
 */
public class SystemUtils {
    /** OS_NAME_WINDOWS_PREFIX */
    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
    /** USER_HOME_KEY */
    private static final String USER_HOME_KEY = "user.home";
    /** USER_NAME_KEY */
    private static final String USER_NAME_KEY = "user.name";
    /** USER_DIR_KEY */
    private static final String USER_DIR_KEY = "user.dir";
    /** JAVA_IO_TMPDIR_KEY */
    private static final String JAVA_IO_TMPDIR_KEY = "java.io.tmpdir";
    /** JAVA_HOME_KEY */
    private static final String JAVA_HOME_KEY = "java.home";
    /** AWT_TOOLKIT */
    public static final String AWT_TOOLKIT = getSystemProperty("awt.toolkit");
    /** FILE_ENCODING */
    public static final String FILE_ENCODING = getSystemProperty("file.encoding");
    /** JAVA_AWT_FONTS */
    public static final String JAVA_AWT_FONTS = getSystemProperty("java.awt.fonts");
    /** JAVA_AWT_GRAPHICSENV */
    public static final String JAVA_AWT_GRAPHICSENV = getSystemProperty("java.awt.graphicsenv");
    /** JAVA_AWT_HEADLESS */
    public static final String JAVA_AWT_HEADLESS = getSystemProperty("java.awt.headless");
    /** JAVA_AWT_PRINTERJOB */
    public static final String JAVA_AWT_PRINTERJOB = getSystemProperty("java.awt.printerjob");
    /** JAVA_CLASS_PATH */
    public static final String JAVA_CLASS_PATH = getSystemProperty("java.class.path");
    /** JAVA_CLASS_VERSION */
    public static final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version");
    /** JAVA_COMPILER */
    public static final String JAVA_COMPILER = getSystemProperty("java.compiler");
    /** JAVA_ENDORSED_DIRS */
    public static final String JAVA_ENDORSED_DIRS = getSystemProperty("java.endorsed.dirs");
    /** JAVA_EXT_DIRS */
    public static final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");
    /** JAVA_HOME */
    public static final String JAVA_HOME = getSystemProperty(JAVA_HOME_KEY);
    /** JAVA_IO_TMPDIR */
    public static final String JAVA_IO_TMPDIR = getSystemProperty(JAVA_IO_TMPDIR_KEY);
    /** JAVA_LIBRARY_PATH */
    public static final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");
    /** JAVA_RUNTIME_NAME */
    public static final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");
    /** JAVA_RUNTIME_VERSION */
    public static final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");
    /** JAVA_SPECIFICATION_NAME */
    public static final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");
    /** JAVA_SPECIFICATION_VENDOR */
    public static final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");
    /** JAVA_SPECIFICATION_VERSION */
    public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");
    /** JAVA_UTIL_PREFS_PREFERENCES_FACTORY */
    public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY;
    /** JAVA_VENDOR */
    public static final String JAVA_VENDOR;
    /** JAVA_VENDOR_URL */
    public static final String JAVA_VENDOR_URL;
    /** JAVA_VERSION */
    public static final String JAVA_VERSION;
    /** JAVA_VM_INFO */
    public static final String JAVA_VM_INFO;
    /** JAVA_VM_NAME */
    public static final String JAVA_VM_NAME;
    /** JAVA_VM_SPECIFICATION_NAME */
    public static final String JAVA_VM_SPECIFICATION_NAME;
    /** JAVA_VM_SPECIFICATION_VENDOR */
    public static final String JAVA_VM_SPECIFICATION_VENDOR;
    /** JAVA_VM_SPECIFICATION_VERSION */
    public static final String JAVA_VM_SPECIFICATION_VERSION;
    /** JAVA_VM_VENDOR */
    public static final String JAVA_VM_VENDOR;
    /** JAVA_VM_VERSION */
    public static final String JAVA_VM_VERSION;
    /** OS_ARCH */
    public static final String OS_ARCH;
    /** OS_NAME */
    public static final String OS_NAME;
    /** OS_VERSION */
    public static final String OS_VERSION;
    /** USER_COUNTRY */
    public static final String USER_COUNTRY;
    /** USER_DIR */
    public static final String USER_DIR;
    /** USER_HOME */
    public static final String USER_HOME;
    /** USER_LANGUAGE */
    public static final String USER_LANGUAGE;
    /** USER_NAME */
    public static final String USER_NAME;
    /** USER_TIMEZONE */
    public static final String USER_TIMEZONE;
    /** IS_JAVA_1_1 */
    public static final boolean IS_JAVA_1_1;
    /** IS_JAVA_1_2 */
    public static final boolean IS_JAVA_1_2;
    /** IS_JAVA_1_3 */
    public static final boolean IS_JAVA_1_3;
    /** IS_JAVA_1_4 */
    public static final boolean IS_JAVA_1_4;
    /** IS_JAVA_1_5 */
    public static final boolean IS_JAVA_1_5;
    /** IS_JAVA_1_6 */
    public static final boolean IS_JAVA_1_6;
    /** IS_JAVA_1_7 */
    public static final boolean IS_JAVA_1_7;
    /** IS_JAVA_1_8 */
    public static final boolean IS_JAVA_1_8;
    /** IS_JAVA_9 */
    public static final boolean IS_JAVA_9;
    /** IS_JAVA_10 */
    public static final boolean IS_JAVA_10;
    /** IS_JAVA_11 */
    public static final boolean IS_JAVA_11;
    /** IS_JAVA_12 */
    public static final boolean IS_JAVA_12;
    /** IS_JAVA_13 */
    public static final boolean IS_JAVA_13;
    /** IS_JAVA_14 */
    public static final boolean IS_JAVA_14;
    /** IS_JAVA_15 */
    public static final boolean IS_JAVA_15;
    /** IS_JAVA_16 */
    public static final boolean IS_JAVA_16;
    /** IS_JAVA_17 */
    public static final boolean IS_JAVA_17;
    /** IS_JAVA_18 */
    public static final boolean IS_JAVA_18;
    /** IS_JAVA_19 */
    public static final boolean IS_JAVA_19;
    /** IS_OS_AIX */
    public static final boolean IS_OS_AIX;
    /** IS_OS_HP_UX */
    public static final boolean IS_OS_HP_UX;
    /** IS_OS_400 */
    public static final boolean IS_OS_400;
    /** IS_OS_IRIX */
    public static final boolean IS_OS_IRIX;
    /** IS_OS_LINUX */
    public static final boolean IS_OS_LINUX;
    /** IS_OS_MAC */
    public static final boolean IS_OS_MAC;
    /** IS_OS_MAC_OSX */
    public static final boolean IS_OS_MAC_OSX;
    /** IS_OS_MAC_OSX_CHEETAH */
    public static final boolean IS_OS_MAC_OSX_CHEETAH;
    /** IS_OS_MAC_OSX_PUMA */
    public static final boolean IS_OS_MAC_OSX_PUMA;
    /** IS_OS_MAC_OSX_JAGUAR */
    public static final boolean IS_OS_MAC_OSX_JAGUAR;
    /** IS_OS_MAC_OSX_PANTHER */
    public static final boolean IS_OS_MAC_OSX_PANTHER;
    /** IS_OS_MAC_OSX_TIGER */
    public static final boolean IS_OS_MAC_OSX_TIGER;
    /** IS_OS_MAC_OSX_LEOPARD */
    public static final boolean IS_OS_MAC_OSX_LEOPARD;
    /** IS_OS_MAC_OSX_SNOW_LEOPARD */
    public static final boolean IS_OS_MAC_OSX_SNOW_LEOPARD;
    /** IS_OS_MAC_OSX_LION */
    public static final boolean IS_OS_MAC_OSX_LION;
    /** IS_OS_MAC_OSX_MOUNTAIN_LION */
    public static final boolean IS_OS_MAC_OSX_MOUNTAIN_LION;
    /** IS_OS_MAC_OSX_MAVERICKS */
    public static final boolean IS_OS_MAC_OSX_MAVERICKS;
    /** IS_OS_MAC_OSX_YOSEMITE */
    public static final boolean IS_OS_MAC_OSX_YOSEMITE;
    /** IS_OS_MAC_OSX_EL_CAPITAN */
    public static final boolean IS_OS_MAC_OSX_EL_CAPITAN;
    /** IS_OS_MAC_OSX_EL_MONTEREY */
    public static final boolean IS_OS_MAC_OSX_EL_MONTEREY;
    /** IS_OS_MAC_OSX_VENTURA */
    public static final boolean IS_OS_MAC_OSX_VENTURA;
    /** IS_OS_FREE_BSD */
    public static final boolean IS_OS_FREE_BSD;
    /** IS_OS_OPEN_BSD */
    public static final boolean IS_OS_OPEN_BSD;
    /** IS_OS_NET_BSD */
    public static final boolean IS_OS_NET_BSD;
    /** IS_OS_OS2 */
    public static final boolean IS_OS_OS2;
    /** IS_OS_SOLARIS */
    public static final boolean IS_OS_SOLARIS;
    /** IS_OS_SUN_OS */
    public static final boolean IS_OS_SUN_OS;
    /** IS_OS_UNIX */
    public static final boolean IS_OS_UNIX;
    /** IS_OS_WINDOWS */
    public static final boolean IS_OS_WINDOWS;
    /** IS_OS_WINDOWS_2000 */
    public static final boolean IS_OS_WINDOWS_2000;
    /** IS_OS_WINDOWS_2003 */
    public static final boolean IS_OS_WINDOWS_2003;
    /** IS_OS_WINDOWS_2008 */
    public static final boolean IS_OS_WINDOWS_2008;
    /** IS_OS_WINDOWS_2012 */
    public static final boolean IS_OS_WINDOWS_2012;
    /** IS_OS_WINDOWS_95 */
    public static final boolean IS_OS_WINDOWS_95;
    /** IS_OS_WINDOWS_98 */
    public static final boolean IS_OS_WINDOWS_98;
    /** IS_OS_WINDOWS_ME */
    public static final boolean IS_OS_WINDOWS_ME;
    /** IS_OS_WINDOWS_NT */
    public static final boolean IS_OS_WINDOWS_NT;
    /** IS_OS_WINDOWS_XP */
    public static final boolean IS_OS_WINDOWS_XP;
    /** IS_OS_WINDOWS_VISTA */
    public static final boolean IS_OS_WINDOWS_VISTA;
    /** IS_OS_WINDOWS_7 */
    public static final boolean IS_OS_WINDOWS_7;
    /** IS_OS_WINDOWS_8 */
    public static final boolean IS_OS_WINDOWS_8;
    /** IS_OS_WINDOWS_10 */
    public static final boolean IS_OS_WINDOWS_10;
    /** IS_OS_ZOS */
    public static final boolean IS_OS_ZOS;

    /** NAMESPACE */
    public static final String USER_NAMESPACE = "user.namespace";
    /** 在启动参数中直接修改 namespace, 优先级最高 */
    @Nullable
    public static final String USER_NAMESPACE_VALUE = getProperty(USER_NAMESPACE);

    /**
     * 优先获取 JVM 参数, 然后才是系统环境变量
     *
     * @param property property
     * @return the system property
     * @since 1.0.0
     */
    @Nullable
    public static String getProperty(String property) {
        String value = System.getProperty(property);
        return StrUtil.isBlank(value) ? System.getenv(property) : value;
    }

    /**
     * Is linux boolean
     *
     * @return boolean boolean
     * @since 1.0.0
     */
    public static boolean isLinux() {
        return IS_OS_LINUX;
    }

    /**
     * Is mac boolean
     *
     * @return the boolean
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static boolean isMac() {
        return IS_OS_MAC;
    }

    /**
     * Is windows boolean
     *
     * @return the boolean
     * @since 1.0.0
     */
    @Contract(pure = true)
    public static boolean isWindows() {
        return IS_OS_WINDOWS;
    }

    /**
     * <p>Description: </p>
     *
     * @author dong4j
     * @version 1.2.5
     * @email "mailto:dong4j@gmail.com"
     * @date 2020.02.28 19:28
     * @since 1.0.0
     */
    public enum OsType {
        /** Linux os type */
        LINUX,
        /** Mac os type */
        MAC,
        /** Windows os type */
        WINDOWS
    }

    /**
     * Gets java home *
     *
     * @return the java home
     * @since 2023.1.1
     */
    public static File getJavaHome() {
        return new File(System.getProperty(JAVA_HOME_KEY));
    }

    /**
     * Gets host name *
     *
     * @return the host name
     * @since 2023.1.1
     */
    public static String getHostName() {
        return IS_OS_WINDOWS ? System.getenv("COMPUTERNAME") : System.getenv("HOSTNAME");
    }

    /**
     * Gets java io tmp dir *
     *
     * @return the java io tmp dir
     * @since 2023.1.1
     */
    public static File getJavaIoTmpDir() {
        return new File(System.getProperty(JAVA_IO_TMPDIR_KEY));
    }

    /**
     * Gets java version matches *
     *
     * @param versionPrefix version prefix
     * @return the java version matches
     * @since 2023.1.1
     */
    private static boolean getJavaVersionMatches(String versionPrefix) {
        return isJavaVersionMatch(JAVA_SPECIFICATION_VERSION, versionPrefix);
    }

    /**
     * Gets os matches *
     *
     * @param osNamePrefix    os name prefix
     * @param osVersionPrefix os version prefix
     * @return the os matches
     * @since 2023.1.1
     */
    private static boolean getOsMatches(String osNamePrefix, String osVersionPrefix) {
        return isOSMatch(OS_NAME, OS_VERSION, osNamePrefix, osVersionPrefix);
    }

    /**
     * Gets os matches name *
     *
     * @param osNamePrefix os name prefix
     * @return the os matches name
     * @since 2023.1.1
     */
    private static boolean getOsMatchesName(String osNamePrefix) {
        return isOSNameMatch(OS_NAME, osNamePrefix);
    }

    /**
     * Gets system property *
     *
     * @param property property
     * @return the system property
     * @since 2023.1.1
     */
    private static String getSystemProperty(String property) {
        try {
            return System.getProperty(property);
        } catch (SecurityException var2) {
            return null;
        }
    }

    /**
     * Gets environment variable *
     *
     * @param name         name
     * @param defaultValue default value
     * @return the environment variable
     * @since 2023.1.1
     */
    public static String getEnvironmentVariable(String name, String defaultValue) {
        try {
            String value = System.getenv(name);
            return value == null ? defaultValue : value;
        } catch (SecurityException var3) {
            return defaultValue;
        }
    }

    /**
     * Gets user dir *
     *
     * @return the user dir
     * @since 2023.1.1
     */
    public static File getUserDir() {
        return new File(System.getProperty(USER_DIR_KEY));
    }

    /**
     * Gets user home *
     *
     * @return the user home
     * @since 2023.1.1
     */
    public static File getUserHome() {
        return new File(System.getProperty(USER_HOME_KEY));
    }

    /**
     * Gets user name *
     *
     * @return the user name
     * @since 2023.1.1
     */
    public static String getUserName() {
        return System.getProperty(USER_NAME_KEY);
    }

    /**
     * Gets user name *
     *
     * @param defaultValue default value
     * @return the user name
     * @since 2023.1.1
     */
    public static String getUserName(String defaultValue) {
        return System.getProperty(USER_NAME_KEY, defaultValue);
    }

    /**
     * Is java awt headless
     *
     * @return the boolean
     * @since 2023.1.1
     */
    public static boolean isJavaAwtHeadless() {
        return Boolean.TRUE.toString().equals(JAVA_AWT_HEADLESS);
    }

    /**
     * Is java version match
     *
     * @param version       version
     * @param versionPrefix version prefix
     * @return the boolean
     * @since 2023.1.1
     */
    static boolean isJavaVersionMatch(String version, String versionPrefix) {
        return version != null && version.startsWith(versionPrefix);
    }

    /**
     * Is os match
     *
     * @param osName          os name
     * @param osVersion       os version
     * @param osNamePrefix    os name prefix
     * @param osVersionPrefix os version prefix
     * @return the boolean
     * @since 2023.1.1
     */
    static boolean isOSMatch(String osName, String osVersion, String osNamePrefix, String osVersionPrefix) {
        if (osName != null && osVersion != null) {
            return isOSNameMatch(osName, osNamePrefix) && isOSVersionMatch(osVersion, osVersionPrefix);
        } else {
            return false;
        }
    }

    /**
     * Is os name match
     *
     * @param osName       os name
     * @param osNamePrefix os name prefix
     * @return the boolean
     * @since 2023.1.1
     */
    static boolean isOSNameMatch(String osName, String osNamePrefix) {
        return osName != null && osName.startsWith(osNamePrefix);
    }

    /**
     * Is os version match
     *
     * @param osVersion       os version
     * @param osVersionPrefix os version prefix
     * @return the boolean
     * @since 2023.1.1
     */
    static boolean isOSVersionMatch(String osVersion, String osVersionPrefix) {
        if (StrUtil.isEmpty(osVersion)) {
            return false;
        } else {
            String[] versionPrefixParts = osVersionPrefix.split("\\.");
            String[] versionParts = osVersion.split("\\.");

            for (int i = 0; i < Math.min(versionPrefixParts.length, versionParts.length); ++i) {
                if (!versionPrefixParts[i].equals(versionParts[i])) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * System utils
     *
     * @since 2023.1.1
     */
    public SystemUtils() {
    }

    static {
        JAVA_UTIL_PREFS_PREFERENCES_FACTORY = getSystemProperty("java.util.prefs.PreferencesFactory");
        JAVA_VENDOR = getSystemProperty("java.vendor");
        JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");
        JAVA_VERSION = getSystemProperty("java.version");
        JAVA_VM_INFO = getSystemProperty("java.vm.info");
        JAVA_VM_NAME = getSystemProperty("java.vm.name");
        JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");
        JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");
        JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");
        JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");
        JAVA_VM_VERSION = getSystemProperty("java.vm.version");
        OS_ARCH = getSystemProperty("os.arch");
        OS_NAME = getSystemProperty("os.name");
        OS_VERSION = getSystemProperty("os.version");
        USER_COUNTRY = getSystemProperty("user.country") == null ? getSystemProperty("user.region") : getSystemProperty("user.country");
        USER_DIR = getSystemProperty(USER_DIR_KEY);
        USER_HOME = getSystemProperty(USER_HOME_KEY);
        USER_LANGUAGE = getSystemProperty("user.language");
        USER_NAME = getSystemProperty("user.name");
        USER_TIMEZONE = getSystemProperty("user.timezone");
        IS_JAVA_1_1 = getJavaVersionMatches("1.1");
        IS_JAVA_1_2 = getJavaVersionMatches("1.2");
        IS_JAVA_1_3 = getJavaVersionMatches("1.3");
        IS_JAVA_1_4 = getJavaVersionMatches("1.4");
        IS_JAVA_1_5 = getJavaVersionMatches("1.5");
        IS_JAVA_1_6 = getJavaVersionMatches("1.6");
        IS_JAVA_1_7 = getJavaVersionMatches("1.7");
        IS_JAVA_1_8 = getJavaVersionMatches("1.8");
        IS_JAVA_9 = getJavaVersionMatches("9");
        IS_JAVA_10 = getJavaVersionMatches("10");
        IS_JAVA_11 = getJavaVersionMatches("11");
        IS_JAVA_12 = getJavaVersionMatches("12");
        IS_JAVA_13 = getJavaVersionMatches("13");
        IS_JAVA_14 = getJavaVersionMatches("14");
        IS_JAVA_15 = getJavaVersionMatches("15");
        IS_JAVA_16 = getJavaVersionMatches("16");
        IS_JAVA_17 = getJavaVersionMatches("17");
        IS_JAVA_18 = getJavaVersionMatches("18");
        IS_JAVA_19 = getJavaVersionMatches("19");
        IS_OS_AIX = getOsMatchesName("AIX");
        IS_OS_HP_UX = getOsMatchesName("HP-UX");
        IS_OS_400 = getOsMatchesName("OS/400");
        IS_OS_IRIX = getOsMatchesName("Irix");
        IS_OS_LINUX = getOsMatchesName("Linux") || getOsMatchesName("LINUX");
        IS_OS_MAC = getOsMatchesName("Mac");
        IS_OS_MAC_OSX = getOsMatchesName("Mac OS X");
        IS_OS_MAC_OSX_CHEETAH = getOsMatches("Mac OS X", "10.0");
        IS_OS_MAC_OSX_PUMA = getOsMatches("Mac OS X", "10.1");
        IS_OS_MAC_OSX_JAGUAR = getOsMatches("Mac OS X", "10.2");
        IS_OS_MAC_OSX_PANTHER = getOsMatches("Mac OS X", "10.3");
        IS_OS_MAC_OSX_TIGER = getOsMatches("Mac OS X", "10.4");
        IS_OS_MAC_OSX_LEOPARD = getOsMatches("Mac OS X", "10.5");
        IS_OS_MAC_OSX_SNOW_LEOPARD = getOsMatches("Mac OS X", "10.6");
        IS_OS_MAC_OSX_LION = getOsMatches("Mac OS X", "10.7");
        IS_OS_MAC_OSX_MOUNTAIN_LION = getOsMatches("Mac OS X", "10.8");
        IS_OS_MAC_OSX_MAVERICKS = getOsMatches("Mac OS X", "10.9");
        IS_OS_MAC_OSX_YOSEMITE = getOsMatches("Mac OS X", "10.10");
        IS_OS_MAC_OSX_EL_CAPITAN = getOsMatches("Mac OS X", "10.11");
        IS_OS_MAC_OSX_EL_MONTEREY = getOsMatches("Mac OS X", "10.12");
        IS_OS_MAC_OSX_VENTURA = getOsMatches("Mac OS X", "10.13");
        IS_OS_FREE_BSD = getOsMatchesName("FreeBSD");
        IS_OS_OPEN_BSD = getOsMatchesName("OpenBSD");
        IS_OS_NET_BSD = getOsMatchesName("NetBSD");
        IS_OS_OS2 = getOsMatchesName("OS/2");
        IS_OS_SOLARIS = getOsMatchesName("Solaris");
        IS_OS_SUN_OS = getOsMatchesName("SunOS");
        IS_OS_UNIX = IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX
                     || IS_OS_SOLARIS || IS_OS_SUN_OS || IS_OS_FREE_BSD || IS_OS_OPEN_BSD || IS_OS_NET_BSD;
        IS_OS_WINDOWS = getOsMatchesName(OS_NAME_WINDOWS_PREFIX);
        IS_OS_WINDOWS_2000 = getOsMatchesName("Windows 2000");
        IS_OS_WINDOWS_2003 = getOsMatchesName("Windows 2003");
        IS_OS_WINDOWS_2008 = getOsMatchesName("Windows Server 2008");
        IS_OS_WINDOWS_2012 = getOsMatchesName("Windows Server 2012");
        IS_OS_WINDOWS_95 = getOsMatchesName("Windows 95");
        IS_OS_WINDOWS_98 = getOsMatchesName("Windows 98");
        IS_OS_WINDOWS_ME = getOsMatchesName("Windows Me");
        IS_OS_WINDOWS_NT = getOsMatchesName("Windows NT");
        IS_OS_WINDOWS_XP = getOsMatchesName("Windows XP");
        IS_OS_WINDOWS_VISTA = getOsMatchesName("Windows Vista");
        IS_OS_WINDOWS_7 = getOsMatchesName("Windows 7");
        IS_OS_WINDOWS_8 = getOsMatchesName("Windows 8");
        IS_OS_WINDOWS_10 = getOsMatchesName("Windows 10");
        IS_OS_ZOS = getOsMatchesName("z/OS");
    }
}
