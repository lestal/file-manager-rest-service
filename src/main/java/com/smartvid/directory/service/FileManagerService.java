package com.smartvid.directory.service;

import com.smartvid.directory.model.DirectoryItem;
import com.smartvid.directory.model.FileAttributes;
import com.smartvid.directory.model.FileItem;

import java.util.List;

public interface FileManagerService {
    List<DirectoryItem> findByDirName(String dirName);

    List<FileItem> findFilesByDirName(String dirName);

    FileAttributes findFileAttributesByPath(String dirName);

    String getRootResource();
}
