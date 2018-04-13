package com.smartvid.directory.model.inits;

import java.io.File;

import static com.smartvid.directory.model.inits.InitialDirs.RootDirs.ONE;

public class InitialDirs {
   public enum RootDirs {
        ONE("one"+ File.separator+"sub_one"),
        TWO("one"+ File.separator+"sub_two"),
        THREE("one"+ File.separator+"sub_three");
        private String dir;
        RootDirs(String dir) {
            this.dir = dir;
        }

       public String getDir() {
           return dir;
       }
   }
   public enum SubRootFiles {
        FILE1(ONE.getDir()+File.separator+"file.mp3"),
        FILE2(ONE.getDir()+File.separator+"file.json"),
        FILE3(ONE.getDir()+File.separator+"file.txt");
        private String dir;
        SubRootFiles(String dir) {
            this.dir = dir;
        }
        public String getDir() {
            return dir;
        }
    }
}
