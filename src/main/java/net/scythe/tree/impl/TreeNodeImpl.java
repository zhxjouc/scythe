package net.scythe.tree.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.scythe.tree.TreeNode;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class TreeNodeImpl<T> implements TreeNode<T> {

    private T data;

    private TreeNode<T> parent;

    private List<TreeNode<T>> children;

    public TreeNodeImpl() {
    }

    public TreeNodeImpl(T data) {
        this.data = data;
    }

    public TreeNodeImpl(TreeNode<T> parent, T data) {
        this.data = data;
        this.parent = parent;
    }

    public TreeNodeImpl(TreeNode<T> parent, T data,  List<TreeNode<T>> children) {
        this.data = data;
        this.parent = parent;
        this.children = children;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public void addChild(TreeNode<T> child) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public void addChildren(List<TreeNode<T>> newChildren) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.addAll(newChildren);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        if (!CollectionUtils.isEmpty(children) && childIndex < children.size()) {
            return children.get(childIndex);
        }
        return null;
    }

    @Override
    public int getChildCount() {
        return CollectionUtils.isEmpty(children) ? 0 : children.size();
    }

    @JsonIgnore
    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public boolean isLeaf() {
        return CollectionUtils.isEmpty(children);
    }

    @Override
    public List<TreeNode<T>> getChildren() {
        return children;
    }

    @Override
    public List<T> getChildrenData(Predicate<T> predicate) {
        List<T> childrenData = new ArrayList<T>();
        for (TreeNode<T> node: this) {
            if (predicate.test(node.getData())) {
                childrenData.add(node.getData());
            }
        }
        return childrenData;
    }

    @Override
    public <R> TreeNode<R> transfer(Function<T, R> transfer) {
        TreeNode<R> rootNode = new TreeNodeImpl<>(transfer.apply(data));
        Map<TreeNode<T>, TreeNode<R>> map = new HashMap<>();
        map.put(this, rootNode);
        for (TreeNode<T> node: this) {
            TreeNode<R> newNode = null;
            if (null != node.getParent()) {
                TreeNode<R> parentNode = map.get(node.getParent());
                newNode = new TreeNodeImpl<>(parentNode, transfer.apply(node.getData()));
                if (null != parentNode) {
                    ((TreeNodeImpl<R>)parentNode).addChild(newNode);
                }
            } else {
                newNode = new TreeNodeImpl<>(transfer.apply(node.getData()));
            }
            map.put(node, newNode);
        }
        return map.get(this);
    }

    @Override
    public String draw() {
        // todo toString
        throw new IllegalStateException("Not support right now");
    }

    //just support by level
    //todo pre-order, in-order, post-order
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new TreeLevelIterator<T>(this);
    }
}
