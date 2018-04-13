package com.smartvid.directory.controller;

import com.smartvid.directory.exceptions.DirNotFoundException;
import com.smartvid.directory.service.FileManagerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileManagerControllerUnitTest {

    @InjectMocks
    private FileManagerController fileManagerController = new FileManagerController();

    @Mock
    private FileManagerService fileManagerService;

    @Before
    public void before() {

        MockitoAnnotations.initMocks(this);
        when(fileManagerService.getRootResource()).thenReturn("target");
    }

    @Test
    public void validateDir_shouldExists() {

        fileManagerController.validateDir("classes");
        verify(fileManagerService, times(1)).getRootResource();

    }

    @Test(expected = DirNotFoundException.class)
    public void validateDir_shouldNotExists() {

        fileManagerController.validateDir("");
    }
}