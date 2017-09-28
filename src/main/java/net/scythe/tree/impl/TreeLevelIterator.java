package net.scythe.tree.impl;

import net.scythe.tree.TreeNode;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by xinjian on 9/28/17.
 */
public class TreeLevelIterator<T> implements Iterator<TreeNode<T>> {

    private Stack<TreeNode<T>> stack;

    public TreeLevelIterator(TreeNode<T> treeNode) {
        stack = new Stack<>();
        stack.add(treeNode);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TreeNode<T> next() {
        TreeNode<T> node = stack.pop();
        if (CollectionUtils.isNotEmpty(node.getChildren())) {
            stack.addAll(node.getChildren());
        }
        return node;
    }
}
