package br.ufes.inf.nemo.vpzy.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class for file operations.
 *
 * @author Igor Sunderhus e Silva
 * @version 0.0.1
 */
public final class FileUtils {
    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Copies a folder recursively. It will create the target folder and any subfolder if it doesn't exist.
     *
     * @param source  Source folder
     * @param target  Target folder
     * @param options Copy options
     * @throws IOException If an I/O error occurs
     */
    public static void copyFolder(Path source, Path target, CopyOption... options) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(target.resolve(source.relativize(dir).toString()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(source.relativize(file).toString()), options);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Extracts a zip file to a target folder.
     *
     * @param zip    Zip file
     * @param target Target folder
     * @throws IOException If an I/O error occurs
     */
    public static void extract(ZipInputStream zip, File target) throws IOException {
        try (zip) {
            ZipEntry entry;

            while ((entry = zip.getNextEntry()) != null) {
                final File file = new File(target, entry.getName());

                if (!file.toPath().normalize().startsWith(target.toPath())) {
                    throw new IOException("Bad zip entry");
                }

                if (entry.isDirectory()) {
                    System.out.println(file.getName() + " created: " + file.mkdirs());
                    continue;
                }

                final byte[] buffer = new byte[1024];
                System.out.println(file.getName() + " created parent dirs: " + file.getParentFile().mkdirs());
                try (final BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
                    int count;

                    while ((count = zip.read(buffer)) != -1) {
                        out.write(buffer, 0, count);
                    }

                }
            }
        }
    }
}
