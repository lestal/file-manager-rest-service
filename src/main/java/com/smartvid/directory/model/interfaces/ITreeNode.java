package com.smartvid.directory.model.interfaces;

import java.util.List;

public interface ITreeNode<T extends ITreeNode> extends INode {
     List<T> getChildren();
}
