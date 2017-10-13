 

package android.os;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

 
public class Environment {
    private static final String TAG = "Environment";

    private static final String ENV_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final String ENV_ANDROID_ROOT = "ANDROID_ROOT";
    private static final String ENV_ANDROID_DATA = "ANDROID_DATA";
    private static final String ENV_ANDROID_STORAGE = "ANDROID_STORAGE";
    private static final String ENV_OEM_ROOT = "OEM_ROOT";
    private static final String ENV_VENDOR_ROOT = "VENDOR_ROOT";

     
    public static final String DIR_ANDROID = "Android";
    private static final String DIR_DATA = "data";
    private static final String DIR_MEDIA = "media";
    private static final String DIR_OBB = "obb";
    private static final String DIR_FILES = "files";
    private static final String DIR_CACHE = "cache";

     
    @Deprecated
    public static final String DIRECTORY_ANDROID = DIR_ANDROID;

    private static final File DIR_ANDROID_ROOT = getDirectory(ENV_ANDROID_ROOT, "/system");
    private static final File DIR_ANDROID_DATA = getDirectory(ENV_ANDROID_DATA, "/data");
    private static final File DIR_ANDROID_STORAGE = getDirectory(ENV_ANDROID_STORAGE, "/storage");
    private static final File DIR_OEM_ROOT = getDirectory(ENV_OEM_ROOT, "/oem");
    private static final File DIR_VENDOR_ROOT = getDirectory(ENV_VENDOR_ROOT, "/vendor");

    private static final String SYSTEM_PROPERTY_EFS_ENABLED = "persist.security.efs.enabled";

    private static UserEnvironment sCurrentUser;
    private static boolean sUserRequired;

    static {
        initForCurrentUser();
    }

     
    public static void initForCurrentUser() {
        final int userId = UserHandle.myUserId();
        sCurrentUser = new UserEnvironment(userId);
    }

     
    public static class UserEnvironment {
        private final int mUserId;

        public UserEnvironment(int userId) {
            mUserId = userId;
        }

        public File[] getExternalDirs() {
            final StorageVolume[] volumes = StorageManager.getVolumeList(mUserId,
                    StorageManager.FLAG_FOR_WRITE);
            final File[] files = new File[volumes.length];
            for (int i = 0; i < volumes.length; i++) {
                files[i] = volumes[i].getPathFile();
            }
            return files;
        }

        @Deprecated
        public File getExternalStorageDirectory() {
            return getExternalDirs()[0];
        }

        @Deprecated
        public File getExternalStoragePublicDirectory(String type) {
            return buildExternalStoragePublicDirs(type)[0];
        }

        public File[] buildExternalStoragePublicDirs(String type) {
            return buildPaths(getExternalDirs(), type);
        }

        public File[] buildExternalStorageAndroidDataDirs() {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_DATA);
        }

        public File[] buildExternalStorageAndroidObbDirs() {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_OBB);
        }

        public File[] buildExternalStorageAppDataDirs(String packageName) {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_DATA, packageName);
        }

        public File[] buildExternalStorageAppMediaDirs(String packageName) {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_MEDIA, packageName);
        }

        public File[] buildExternalStorageAppObbDirs(String packageName) {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_OBB, packageName);
        }

        public File[] buildExternalStorageAppFilesDirs(String packageName) {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_DATA, packageName, DIR_FILES);
        }

        public File[] buildExternalStorageAppCacheDirs(String packageName) {
            return buildPaths(getExternalDirs(), DIR_ANDROID, DIR_DATA, packageName, DIR_CACHE);
        }
    }

     
    public static File getRootDirectory() {
        return DIR_ANDROID_ROOT;
    }

     
    public static File getStorageDirectory() {
        return DIR_ANDROID_STORAGE;
    }

     
    public static File getOemDirectory() {
        return DIR_OEM_ROOT;
    }

     
    public static File getVendorDirectory() {
        return DIR_VENDOR_ROOT;
    }

     
    public static File getSystemSecureDirectory() {
        if (isEncryptedFilesystemEnabled()) {
            return new File(SECURE_DATA_DIRECTORY, "system");
        } else {
            return new File(DATA_DIRECTORY, "system");
        }
    }

     
    public static File getSecureDataDirectory() {
        if (isEncryptedFilesystemEnabled()) {
            return SECURE_DATA_DIRECTORY;
        } else {
            return DATA_DIRECTORY;
        }
    }

     
    public static File getUserSystemDirectory(int userId) {
        return new File(new File(getSystemSecureDirectory(), "users"), Integer.toString(userId));
    }

     
    public static File getUserConfigDirectory(int userId) {
        return new File(new File(new File(
                getDataDirectory(), "misc"), "user"), Integer.toString(userId));
    }

     
    public static boolean isEncryptedFilesystemEnabled() {
        return SystemProperties.getBoolean(SYSTEM_PROPERTY_EFS_ENABLED, false);
    }

    private static final File DATA_DIRECTORY
            = getDirectory("ANDROID_DATA", "/data");

     
    private static final File SECURE_DATA_DIRECTORY
            = getDirectory("ANDROID_SECURE_DATA", "/data/secure");

    private static final File DOWNLOAD_CACHE_DIRECTORY = getDirectory("DOWNLOAD_CACHE", "/cache");

     
    public static File getDataDirectory() {
        return DATA_DIRECTORY;
    }

     
    public static File getDataDirectory(String volumeUuid) {
        if (TextUtils.isEmpty(volumeUuid)) {
            return new File("/data");
        } else {
            return new File("/mnt/expand/" + volumeUuid);
        }
    }

     
    public static File getDataAppDirectory(String volumeUuid) {
        return new File(getDataDirectory(volumeUuid), "app");
    }

     
    public static File getDataUserDirectory(String volumeUuid) {
        return new File(getDataDirectory(volumeUuid), "user");
    }

     
    public static File getDataUserDirectory(String volumeUuid, int userId) {
        return new File(getDataUserDirectory(volumeUuid), String.valueOf(userId));
    }

     
    public static File getDataUserPackageDirectory(String volumeUuid, int userId,
            String packageName) {
        // TODO: keep consistent with installd
        return new File(getDataUserDirectory(volumeUuid, userId), packageName);
    }

     
    public static File getExternalStorageDirectory() {
        throwIfUserRequired();
        return sCurrentUser.getExternalDirs()[0];
    }

     
    public static File getLegacyExternalStorageDirectory() {
        return new File(System.getenv(ENV_EXTERNAL_STORAGE));
    }

     
    public static File getLegacyExternalStorageObbDirectory() {
        return buildPath(getLegacyExternalStorageDirectory(), DIR_ANDROID, DIR_OBB);
    }

     
    public static String DIRECTORY_MUSIC = "Music";
    
     
    public static String DIRECTORY_PODCASTS = "Podcasts";
    
     
    public static String DIRECTORY_RINGTONES = "Ringtones";
    
     
    public static String DIRECTORY_ALARMS = "Alarms";
    
     
    public static String DIRECTORY_NOTIFICATIONS = "Notifications";
    
     
    public static String DIRECTORY_PICTURES = "Pictures";
    
     
    public static String DIRECTORY_MOVIES = "Movies";
    
     
    public static String DIRECTORY_DOWNLOADS = "Download";
    
     
    public static String DIRECTORY_DCIM = "DCIM";

     
    public static String DIRECTORY_DOCUMENTS = "Documents";

     
    public static File getExternalStoragePublicDirectory(String type) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(type)[0];
    }

     
    public static File[] buildExternalStorageAndroidDataDirs() {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAndroidDataDirs();
    }

     
    public static File[] buildExternalStorageAppDataDirs(String packageName) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppDataDirs(packageName);
    }
    
     
    public static File[] buildExternalStorageAppMediaDirs(String packageName) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppMediaDirs(packageName);
    }
    
     
    public static File[] buildExternalStorageAppObbDirs(String packageName) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppObbDirs(packageName);
    }
    
     
    public static File[] buildExternalStorageAppFilesDirs(String packageName) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppFilesDirs(packageName);
    }

     
    public static File[] buildExternalStorageAppCacheDirs(String packageName) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppCacheDirs(packageName);
    }
    
     
    public static File getDownloadCacheDirectory() {
        return DOWNLOAD_CACHE_DIRECTORY;
    }

     
    public static final String MEDIA_UNKNOWN = "unknown";

     
    public static final String MEDIA_REMOVED = "removed";

     
    public static final String MEDIA_UNMOUNTED = "unmounted";

     
    public static final String MEDIA_CHECKING = "checking";

     
    public static final String MEDIA_NOFS = "nofs";

     
    public static final String MEDIA_MOUNTED = "mounted";

     
    public static final String MEDIA_MOUNTED_READ_ONLY = "mounted_ro";

     
    public static final String MEDIA_SHARED = "shared";

     
    public static final String MEDIA_BAD_REMOVAL = "bad_removal";

     
    public static final String MEDIA_UNMOUNTABLE = "unmountable";

     
    public static final String MEDIA_EJECTING = "ejecting";

     
    public static String getExternalStorageState() {
        final File externalDir = sCurrentUser.getExternalDirs()[0];
        return getExternalStorageState(externalDir);
    }

     
    @Deprecated
    public static String getStorageState(File path) {
        return getExternalStorageState(path);
    }

     
    public static String getExternalStorageState(File path) {
        final StorageVolume volume = StorageManager.getStorageVolume(path, UserHandle.myUserId());
        if (volume != null) {
            return volume.getState();
        } else {
            return MEDIA_UNKNOWN;
        }
    }

     
    public static boolean isExternalStorageRemovable() {
        if (isStorageDisabled()) return false;
        final File externalDir = sCurrentUser.getExternalDirs()[0];
        return isExternalStorageRemovable(externalDir);
    }

     
    public static boolean isExternalStorageRemovable(File path) {
        final StorageVolume volume = StorageManager.getStorageVolume(path, UserHandle.myUserId());
        if (volume != null) {
            return volume.isRemovable();
        } else {
            throw new IllegalArgumentException("Failed to find storage device at " + path);
        }
    }

     
    public static boolean isExternalStorageEmulated() {
        if (isStorageDisabled()) return false;
        final File externalDir = sCurrentUser.getExternalDirs()[0];
        return isExternalStorageEmulated(externalDir);
    }

     
    public static boolean isExternalStorageEmulated(File path) {
        final StorageVolume volume = StorageManager.getStorageVolume(path, UserHandle.myUserId());
        if (volume != null) {
            return volume.isEmulated();
        } else {
            throw new IllegalArgumentException("Failed to find storage device at " + path);
        }
    }

    static File getDirectory(String variableName, String defaultPath) {
        String path = System.getenv(variableName);
        return path == null ? new File(defaultPath) : new File(path);
    }

     
    public static void setUserRequired(boolean userRequired) {
        sUserRequired = userRequired;
    }

    private static void throwIfUserRequired() {
        if (sUserRequired) {
            Log.wtf(TAG, "Path requests must specify a user by using UserEnvironment",
                    new Throwable());
        }
    }

     
    public static File[] buildPaths(File[] base, String... segments) {
        File[] result = new File[base.length];
        for (int i = 0; i < base.length; i++) {
            result[i] = buildPath(base[i], segments);
        }
        return result;
    }

     
    public static File buildPath(File base, String... segments) {
        File cur = base;
        for (String segment : segments) {
            if (cur == null) {
                cur = new File(segment);
            } else {
                cur = new File(cur, segment);
            }
        }
        return cur;
    }

    private static boolean isStorageDisabled() {
        return SystemProperties.getBoolean("config.disable_storage", false);
    }

     
    public static File maybeTranslateEmulatedPathToInternal(File path) {
        return StorageManager.maybeTranslateEmulatedPathToInternal(path);
    }
}
