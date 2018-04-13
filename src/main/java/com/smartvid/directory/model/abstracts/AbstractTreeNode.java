package com.smartvid.directory.model.abstracts;

import com.smartvid.directory.model.interfaces.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public abstract class AbstractTreeNode<T extends TreeNode> implements TreeNode<T>  {
    private String path;
    private List<T> children = new ArrayList<>();



    public AbstractTreeNode(List<T> children, String name) {
        this.children = children;
        this.path = name;
    }


    public AbstractTreeNode() {
    }

    public AbstractTreeNode(String name) {
        this.path = name;
    }

    public AbstractTreeNode(List<T> children) {
        this.children = children;
    }


    @Override
    public void add(T child) {
       children.add(child);
    }

    @Override
    public void remove(T child) {
        children.remove(child);
    }

    @Override
    public List<T> getChildren() {
        return children;
    }


    @Override
    public T getChild(String name) {

        for (T child: children) {
            if (child.getPath() == name){
                return child;
            }
        }

        return null;
    }


    @Override
    public boolean hasChildren(){
        return children !=null && !children.isEmpty();
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractTreeNode)) {
            return false;
        }
        AbstractTreeNode<?> that = (AbstractTreeNode<?>) o;
        return Objects.equals(children, that.children) &&
                Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(children, path);
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.path = name;
    }
}
