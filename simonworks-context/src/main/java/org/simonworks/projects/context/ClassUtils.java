package org.simonworks.projects.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class ClassUtils {

    private ClassUtils() {}

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException If will not be possible to recover classes from a directory
     * @throws IOException If will not be possible to get package as {@link Enumeration<URL>}
     */
    static List<Class<?>> getClasses(String packageName) throws IOException, URISyntaxException, ClassNotFoundException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Scanning package for classes <{}>", packageName);
        }
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Package <{}> found in following resources:", packageName);
            Enumeration<URL> forTrace = classLoader.getResources(path);
            while(forTrace.hasMoreElements()) {
                URL url = forTrace.nextElement();
                LOGGER.trace("\n\tURL -> {}\n\tgetUserInfo -> {}\n\tgetFile -> {}" +
                        "\n\tgetPath -> {}\n\tgetProtocol -> {}\n\tgetContent -> {}\n\ttoExternalForm -> {}" +
                        "\n\tgetAuthority -> {}\n\tgetRef -> {}",
                        url, url.getUserInfo(), url.getFile(), url.getPath(),
                        url.getProtocol(), url.getContent(), url.toExternalForm(),
                        url.getAuthority(), url.getRef());
            }
        }
        List<Class<?>> classes = new ArrayList<>();
        while(resources.hasMoreElements()) {
            classes.addAll( findClasses(resources.nextElement(), packageName) );
        }
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given container and subdirs.
     *
     * @param resource   The base container
     * @param packageName The package name for classes found inside the base container
     * @return The classes
     * @throws ClassNotFoundException If will not be possible to recover a Class from its name
     */
    private static List<Class<?>> findClasses(URL resource, String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        List<Class<?>> classes = null;
        if(isJar(resource)) {
            classes = getClassesFromJarFile(resource);
        } else {
            classes = getClassesFromFolder(new File(resource.getFile()), packageName);
        }
        return classes;
    }

    private static List<Class<?>> getClassesFromFolder(File container, String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File[] files = container.listFiles();
        if( files != null ) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(getClassesFromFolder(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Classes belonging to package <{}> inside <{}> folder are <{}>", packageName, container, classes);
        }
        return classes;
    }

    private static List<Class<?>> getClassesFromJarFile(URL resource) throws IOException, URISyntaxException, ClassNotFoundException {
        JarURLConnection connection = (JarURLConnection) resource.openConnection();
        File file = new File(connection.getJarFileURL().toURI());
        return getClassesFromJarFile(file);
    }

    private static boolean isJar(URL resource) {
        return "jar".equals(resource.getProtocol()) || "JAR".equals(resource.getProtocol());
    }

    private static List<Class<?>> getClassesFromJarFile(File path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("getClassesFromJarFile: Getting classes for <{}>", path);
        }

        try (JarFile jar = new JarFile(path)){
            Enumeration<JarEntry> en = jar.entries();
            while (en.hasMoreElements()) {
                JarEntry entry = en.nextElement();
                if (entry.getName().endsWith("class")) {
                    String className = fromFileToClassName(entry.getName());
                    if(LOGGER.isTraceEnabled()) {
                        LOGGER.trace("getClassesFromJarFile: found <{}>", className);
                    }
                    Class claz = Class.forName(className);
                    classes.add(claz);
                }
            }
        }

        return classes;
    }

    private static String fromFileToClassName(final String fileName) {
        return fileName.substring(0, fileName.length() - 6).replaceAll("/|\\\\", "\\.");
    }

    public static boolean isJar(File file) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Checking if file <{}> is a Jar...", file.toURI().getPath());
        }
        Optional<String> extension = getExtension(file.toURI().getPath());
        boolean isJar = extension.isPresent() && "jar".equals( extension.get() );
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("{}", isJar);
        }
        return isJar;
    }

    public static Optional<String> getExtension(String filename) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("\tGetting extension of file <{}>", filename);
        }
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf('.') + 1));
    }
}
