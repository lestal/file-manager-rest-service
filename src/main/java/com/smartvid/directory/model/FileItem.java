package com.smartvid.directory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartvid.directory.model.abstracts.AbstractTreeNode;
import com.smartvid.directory.model.enums.ItemType;
import com.smartvid.directory.model.interfaces.ITreeItem;

import java.util.List;

public class FileItem extends AbstractTreeNode<ITreeItem> implements ITreeItem {
    public FileItem(String name) {
        super(name);
    }

    @JsonIgnore
    @Override
    public List<ITreeItem> getChildren() {
        return super.getChildren();
    }
    @Override
    public ItemType getType() {
        return ItemType.FILE;
    }
}
