package com.smartvid.directory.model.interfaces;

import java.util.List;

public interface TreeNode<T extends TreeNode> extends Node {
     List<T> getChildren();
}
