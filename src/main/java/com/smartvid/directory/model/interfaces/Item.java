package com.smartvid.directory.model.interfaces;

import com.smartvid.directory.model.enums.ItemType;

public interface Item extends TreeNode<Item> {
    ItemType getType();
}
