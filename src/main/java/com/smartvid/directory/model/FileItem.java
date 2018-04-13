package com.smartvid.directory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smartvid.directory.model.abstracts.AbstractTreeNode;
import com.smartvid.directory.model.enums.ItemType;
import com.smartvid.directory.model.interfaces.Item;

import java.util.List;

public class FileItem  extends AbstractTreeNode<Item> implements Item {
    public FileItem(String name) {
        super(name);
    }

    @JsonIgnore
    @Override
    public List<Item> getChildren() {
        return super.getChildren();
    }
    @Override
    public ItemType getType() {

        return ItemType.FILE;
    }
}
