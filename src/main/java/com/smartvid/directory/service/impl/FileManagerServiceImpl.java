package com.smartvid.directory.service.impl;

import com.smartvid.directory.exceptions.DirNotFoundException;
import com.smartvid.directory.exceptions.UnexpectedErrorException;
import com.smartvid.directory.model.*;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component("fileManagerService")
@Transactional
public class FileManagerServiceImpl implements FileManagerService {
    private final static String BASE_DIR = "target";
    private final static URL ROOT_RESOURCE = getRoot();

    static {
        populateInitialDirs();
    }

    private File joinWithRoot(String dirName) {
        String resource = ROOT_RESOURCE.getPath() + dirName;
        return new File(resource);
    }

    @Override
    public List<DirectoryItem> findByDirName(String dirName) {
        List<DirectoryItem> directoryItems = null;
        try {
            directoryItems = Files.walk(Paths.get(BASE_DIR, dirName))
                    .filter(p -> !p.toString().equals(BASE_DIR + File.separator + dirName))
                    .filter(Files::isDirectory)
                    .map(p -> new DirectoryItem(getSubDirs(p.toFile()), p.toFile().getAbsolutePath(),
                            getFilesCountInDir(p.toFile())))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
           throw new UnexpectedErrorException(dirName);
        }
        return directoryItems;
    }

    @Override
    public List<FileItem> findFilesByDirName(String dirName) {
        File files = joinWithRoot(dirName);
        List<FileItem> fileItems = Stream.of(files.listFiles())
                .filter(f -> f.isFile())
                .map(f -> new FileItem(f.getAbsolutePath()))
                .collect(Collectors.toList());
        return fileItems;
    }

    @Override
    public FileAttributes findFileAttributesByPath(String dirName) {
        File f = joinWithRoot(dirName);
        if (!f.exists()) {
            throw new DirNotFoundException(dirName);
        }
        Path file = f.toPath();
        FileAttributes.FileAttributesBuilder builder = new FileAttributes().newBuilder();

        try {
            BasicFileAttributes bfa = Files.readAttributes(file, BasicFileAttributes.class);
            builder.setCreationTime(bfa.creationTime().toString());
            builder.setModifiedDate(bfa.lastModifiedTime().toString());
            builder.setHidden(f.isHidden());
            builder.setFileSize(f.getUsableSpace() / 1024);
            builder.setRights(readFilePermissions(f));

        } catch (IOException e) {
            System.out.println(e);
            throw new UnexpectedErrorException(dirName);
        }
        return builder.build();
    }

    @Override
    public String getRootResource() {
        return BASE_DIR;
    }

    private String readFilePermissions(File file) {
        StringBuilder sb = new StringBuilder("--");
        sb.append(file.canExecute() ? "-x" : "");
        sb.append(file.canRead() ? "-r" : "");
        sb.append(file.canWrite() ? "-w" : "");
        return sb.toString();
    }

    private List<Item> getSubDirs(File file) {
        return Stream.of(file.listFiles())
                .filter(File::isDirectory)
                .map(f -> new DirectoryItem(getSubDirs(f), f.getAbsolutePath(), getFilesCountInDir(f)))
                .collect(Collectors.toList());
    }

    private Integer getFilesCountInDir(File file) {
        return Stream.of(file.listFiles())
                .filter(File::isFile)
                .collect(Collectors.toList()).size();
    }

    private static void populateInitialDirs() {
        Stream.of(InitialDirs.RootDirs.values()).forEach(d -> new File(ROOT_RESOURCE.getPath() + d.getDir()).mkdirs());
        Stream.of(InitialDirs.SubRootFiles.values()).forEach(d -> {
            try {
                new File(ROOT_RESOURCE.getPath() + d.getDir()).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static URL getRoot() {
        try {
            return Paths.get(BASE_DIR, "").toUri().toURL();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
