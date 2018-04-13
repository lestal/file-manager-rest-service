package com.smartvid.directory.model;

import com.smartvid.directory.model.abstracts.AbstractTreeNode;
import com.smartvid.directory.model.enums.ItemType;
import com.smartvid.directory.model.interfaces.Item;

import java.util.List;

public class DirectoryItem extends AbstractTreeNode<Item> implements Item {
    private Integer filesCount;
    public DirectoryItem(List<Item> children, String name, Integer filesCount) {
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
}
