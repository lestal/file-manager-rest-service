package com.smartvid.directory.controller;

import com.smartvid.directory.exceptions.DirNotFoundException;
import com.smartvid.directory.model.DirectoryItem;
import com.smartvid.directory.model.FileAttributes;
import com.smartvid.directory.model.FileItem;
import com.smartvid.directory.service.FileManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FileManagerController {

    @Autowired
    FileManagerService fileManagerService;

    @RequestMapping(value = "/dir/{dirName}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<List<DirectoryItem>> findAll(@PathVariable String dirName) {
        this.validateDir(dirName);
        List<DirectoryItem> dirs = fileManagerService.findByDirName(dirName);
        return new ResponseEntity<>(dirs, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/**", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<FileItem>> findAllFiles(HttpServletRequest request) {
        String dirName = extractDirNameFromRequest(request);
        this.validateDir(dirName);
        List<FileItem> dirs = fileManagerService.findFilesByDirName(dirName);
        return new ResponseEntity<>(dirs, HttpStatus.OK);
    }


    @RequestMapping(value = "/attributes/**", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FileAttributes> findFileAttributes(HttpServletRequest request) {
        String fileName = extractDirNameFromRequest(request);
        this.validateDir(fileName);
        FileAttributes attr = fileManagerService.findFileAttributesByPath(fileName);
        return new ResponseEntity<>(attr, HttpStatus.OK);
    }

    private String extractDirNameFromRequest(HttpServletRequest request) {
        String restOfTheUrl = (String) request.getAttribute(
                HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(restOfTheUrl,
                request.getServletPath());
    }


    public void validateDir(String path) {
        if (StringUtils.isEmpty(path) || !isDirExist(path)) {
            throw new DirNotFoundException(path);
        }
    }

  private boolean isDirExist(String path) {
        try {
            return Files.exists(Paths.get(fileManagerService.getRootResource(), path));
        } catch (InvalidPathException ipe) {
            return false;
        }
    }
}
