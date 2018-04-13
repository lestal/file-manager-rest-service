package com.smartvid.directory.model.interfaces;

import com.smartvid.directory.model.enums.ItemType;

public interface ITreeItem extends ITreeNode<ITreeItem> {
    ItemType getType();
}
