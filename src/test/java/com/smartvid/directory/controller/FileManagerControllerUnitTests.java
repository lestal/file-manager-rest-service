package com.smartvid.directory.controller;

import com.smartvid.directory.exceptions.DirNotFoundException;
import com.smartvid.directory.service.IFileManagerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileManagerControllerUnitTests {

    @InjectMocks
    private FileManagerController fileManagerController = new FileManagerController();

    @Mock
    private IFileManagerService IFileManagerService;

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);
        when(IFileManagerService.getRootResource()).thenReturn("target");
    }

    @Test
    public void validateDir_shouldExists() {

        fileManagerController.validateDir("classes");
        verify(IFileManagerService, times(1)).getRootResource();

    }

    @Test(expected = DirNotFoundException.class)
    public void validateDir_shouldNotExists() {

        fileManagerController.validateDir("");
    }
}