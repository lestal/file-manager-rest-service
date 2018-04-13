package com.smartvid.directory.service.impl;

import com.smartvid.directory.exceptions.DirNotFoundException;
import com.smartvid.directory.exceptions.UnexpectedErrorException;
import com.smartvid.directory.model.DirectoryItem;
import com.smartvid.directory.model.FileAttributes;
import com.smartvid.directory.model.FileItem;
import com.smartvid.directory.model.inits.InitialDirs;
import com.smartvid.directory.model.interfaces.Item;
import com.smartvid.directory.service.FileManagerService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("fileManagerService")
@Transactional
public class FileManagerServiceImpl implements FileManagerService {
    private final static String BASE_DIR = "target";
    private final static URL ROOT_RESOURCE = getRoot();
    private final static Logger LOGGER = Logger.getLogger(FileManagerServiceImpl.class.getName());

    static {
        populateInitialDirs();
    }

    private File joinWithRoot(String dirName) {
        String resource = (ROOT_RESOURCE != null ? ROOT_RESOURCE.getPath() : null) + dirName;
        return new File(resource);
    }

    @Override
    public List<DirectoryItem> findByDirName(String dirName) {
        List<DirectoryItem> directoryItems;
        try (Stream<Path> walks = Files.walk(Paths.get(BASE_DIR, dirName))) {
            directoryItems = walks.filter(p -> !p.toString().equals(BASE_DIR + File.separator + dirName))
                    .filter(Files::isDirectory)
                    .map(p -> new DirectoryItem(getSubDirs(p.toFile()), p.toFile().getAbsolutePath(),
                            getFilesCountInDir(p.toFile())))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new UnexpectedErrorException(dirName);
        }
        return directoryItems;
    }

    @Override
    public List<FileItem> findFilesByDirName(String dirName) {
        File files = joinWithRoot(dirName);
        return Stream.of(Objects.requireNonNull(files.listFiles()))
                .filter(File::isFile)
                .map(f -> new FileItem(f.getAbsolutePath()))
                .collect(Collectors.toList());
    }

    @Override
    public FileAttributes findFileAttributesByPath(String dirName) {
        File f = joinWithRoot(dirName);
        if (!f.exists()) {
            throw new DirNotFoundException(dirName);
        }
        Path file = f.toPath();
        FileAttributes.FileAttributesBuilder builder = FileAttributes.newBuilder();
        try {
            BasicFileAttributes bfa = Files.readAttributes(file, BasicFileAttributes.class);
            builder.setCreationTime(bfa.creationTime().toString());
            builder.setModifiedDate(bfa.lastModifiedTime().toString());
            builder.setHidden(f.isHidden());
            builder.setFileSize(f.getUsableSpace() / 1024);
            builder.setRights(readFilePermissions(f));

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new UnexpectedErrorException(dirName);
        }
        return builder.build();
    }

    @Override
    public String getRootResource() {
        return BASE_DIR;
    }

    private String readFilePermissions(File file) {
        return "--" + (file.canExecute() ? "-x" : "")
                + (file.canRead() ? "-r" : "")
                + (file.canWrite() ? "-w" : "");
    }

    private List<Item> getSubDirs(File file) {
        return Stream.of(Objects.requireNonNull(file.listFiles()))
                .filter(File::isDirectory)
                .map(f -> new DirectoryItem(getSubDirs(f), f.getAbsolutePath(), getFilesCountInDir(f)))
                .collect(Collectors.toList());
    }

    private Integer getFilesCountInDir(File file) {
        return Stream.of(Objects.requireNonNull(file.listFiles()))
                .filter(File::isFile)
                .collect(Collectors.toList()).size();
    }

    private static void populateInitialDirs() {
        Stream.of(InitialDirs.RootDirs.values()).forEach(d -> {
            boolean newFile = new File(
                    (ROOT_RESOURCE != null ? ROOT_RESOURCE.getPath() : "") + d.getDir()).mkdirs();
            logIfNotCreated(newFile, d.getDir());
        });
        Stream.of(InitialDirs.SubRootFiles.values()).forEach(d -> {
            try {
                boolean newFile = new File(
                        (ROOT_RESOURCE != null ? ROOT_RESOURCE.getPath() : "") + d.getDir()).createNewFile();
                logIfNotCreated(newFile, d.getDir());
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private static void logIfNotCreated(boolean newFile, String dir) {
        if (!newFile) {
            LOGGER.log(Level.WARNING, MessageFormat.format("File or dir {0} is not created.", dir));
        }
    }

    private static URL getRoot() {
        try {
            return Paths.get(BASE_DIR, "").toUri().toURL();
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

}
