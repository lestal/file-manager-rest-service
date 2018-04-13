package com.smartvid.directory.model.interfaces;

import java.util.List;

public interface TreeNode<T extends TreeNode> extends Node {

    void add(T child);

    void remove(T child);

    List<T> getChildren();

    T getChild(String name);

    boolean hasChildren();

}
