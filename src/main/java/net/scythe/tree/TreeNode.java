package net.scythe.tree;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface TreeNode<T> extends Iterable<TreeNode<T>> {

    TreeNode getChildAt(int childIndex);

    int getChildCount();

    TreeNode getParent();

    T getData();

    boolean isLeaf();

    List<TreeNode<T>> getChildren();

    List<T> getChildrenData(Predicate<T> predicate);

    <R> TreeNode<R> transfer(Function<T, R> transfer);

    String draw();
}
