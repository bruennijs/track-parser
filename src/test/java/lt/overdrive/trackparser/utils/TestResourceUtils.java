package lt.overdrive.trackparser.utils;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

public class TestResourceUtils {
    public static File getFile(String name) {
        try {
            return new File(Thread.currentThread().getContextClassLoader().getResource(name).toURI());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static InputStream getStream(String name)
    {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader.getResourceAsStream(name);
    }
}
