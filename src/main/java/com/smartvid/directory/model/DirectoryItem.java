package com.smartvid.directory.model;

import com.smartvid.directory.model.abstracts.AbstractTreeNode;
import com.smartvid.directory.model.enums.ItemType;
import com.smartvid.directory.model.interfaces.ITreeItem;

import java.util.List;
import java.util.Objects;

public class DirectoryItem extends AbstractTreeNode<ITreeItem> implements ITreeItem {
    private Integer filesCount;
    public DirectoryItem(List<ITreeItem> children, String name, Integer filesCount) {
        super(children, name);
        this.filesCount = filesCount;
    }

    @Override
    public ItemType getType() {
        return ItemType.DIR;
    }

    public Integer getFilesCount() {
        return filesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectoryItem)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DirectoryItem that = (DirectoryItem) o;
        return Objects.equals(filesCount, that.filesCount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), filesCount);
    }
}
