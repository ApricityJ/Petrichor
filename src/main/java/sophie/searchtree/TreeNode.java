package sophie.searchtree;

import java.util.List;
import java.util.Objects;

public class TreeNode<T> {

    private T id;
    private List<TreeNode<T>> childNodes;

    public TreeNode(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public List<TreeNode<T>> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<TreeNode<T>> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode<?> treeNode = (TreeNode<?>) o;
        return id.equals(treeNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
