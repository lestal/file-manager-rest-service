package com.smartvid.directory.model;

public class FileAttributes {

    private Long size;
    private Boolean hidden;
    private String createdDate;
    private String modifiedDate;
    private String rights;

    public FileAttributes() {
    }
    public static FileAttributesBuilder newBuilder() {
        return new FileAttributes().new FileAttributesBuilder();
    }
    public class FileAttributesBuilder {
        public FileAttributesBuilder() {
        }
        public FileAttributesBuilder setCreationTime(String creationTime) {
            FileAttributes.this.createdDate = creationTime;
            return this;
        }
        public FileAttributesBuilder setFileSize(Long fileSize) {
            FileAttributes.this.size = fileSize;
            return this;
        }
        public FileAttributesBuilder setHidden(Boolean hidden) {
            FileAttributes.this.hidden = hidden;
            return this;
        }

        public FileAttributesBuilder setModifiedDate(String modifiedDate) {
            FileAttributes.this.modifiedDate = modifiedDate;
            return this;
        }

        public FileAttributesBuilder setRights(String rights) {
            FileAttributes.this.rights = rights;
            return this;
        }
        public FileAttributes build() {
            return FileAttributes.this;
        }
    }
    public Long getSize() {
        return size;
    }


    public Boolean getHidden() {
        return hidden;
    }


    public String getCreatedDate() {
        return createdDate;
    }


    public String getModifiedDate() {
        return modifiedDate;
    }


    public String getRights() {
        return rights;
    }




}
