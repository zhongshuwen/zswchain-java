package org.zhongshuwen.zswjavaabieosserializationprovider;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class EmbeddedLibraryTools {

    public static final boolean LOADED_EMBEDDED_LIBRARY;

    static {
        LOADED_EMBEDDED_LIBRARY = loadEmbeddedLibrary();
    }

    private EmbeddedLibraryTools() {
    }
    private static URL getNativeLibraryURL(String lib) {
        URL nativeLibraryUrl = null;

// attempt to locate embedded native library within JAR at following location:
        String[] allowedExtensions = new String[]{"dylib", "so", "dll"};
        String[] allowedPlatforms = new String[]{"", "macos/", "linux/", "windows/"};
        StringBuilder url = new StringBuilder();
        url.append("/debug/");

        // loop through extensions, stopping after finding first one
        for (String platform : allowedPlatforms) {
            for (String ext : allowedExtensions) {
                nativeLibraryUrl = AbiZswSerializationProviderImpl.class.getResource(url.toString() + platform + lib + "." + ext);
                if (nativeLibraryUrl != null)
                    return nativeLibraryUrl;
            }
        }
        return null;
    }
    private static boolean loadEmbeddedLibrary() {

        boolean usingEmbedded = false;

        // attempt to locate embedded native library within JAR at following location:
        String[] allowedExtensions = new String[]{"dylib", "so", "dll"};
        String[] allowedPlatforms = new String[]{"", "/macos", "/linux", "/windows"};
        String[] libs = new String[]{"libzswjavaabieos"};
        StringBuilder url = new StringBuilder();
        url.append("/debug/");
        for (String lib : libs) {
            URL nativeLibraryUrl = getNativeLibraryURL(lib);
            if (nativeLibraryUrl != null) {
                // native library found within JAR, extract and load
                try {

                    final File libfile = File.createTempFile(lib, ".lib");
                    libfile.deleteOnExit(); // just in case

                    final InputStream in = nativeLibraryUrl.openStream();
                    final OutputStream out = new BufferedOutputStream(new FileOutputStream(libfile));

                    int len = 0;
                    byte[] buffer = new byte[8192];
                    while ((len = in.read(buffer)) > -1)
                        out.write(buffer, 0, len);
                    out.close();
                    in.close();
                    System.load(libfile.getAbsolutePath());

                    usingEmbedded = true;

                } catch (IOException x) {
                    // mission failed, do nothing
                }

            } // nativeLibraryUrl exists
        }
        return usingEmbedded;
    }
}
