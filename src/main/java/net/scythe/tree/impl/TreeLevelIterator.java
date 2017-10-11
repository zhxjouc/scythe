package net.scythe.tree.impl;

import net.scythe.tree.TreeNode;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by xinjian on 9/28/17.
 */
public class TreeLevelIterator<T> implements Iterator<TreeNode<T>> {

    private Queue<TreeNode<T>> queue;

    public TreeLevelIterator(TreeNode<T> treeNode) {
        queue = new LinkedList<>();
        queue.add(treeNode);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public TreeNode<T> next() {
        TreeNode<T> node = queue.poll();
        if (CollectionUtils.isNotEmpty(node.getChildren())) {
            queue.addAll(node.getChildren());
        }
        return node;
    }
}
