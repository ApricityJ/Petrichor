package sophie.searchtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class SearchTree<T> {

    private final TreeNode<T> root;

    public SearchTree(TreeNode<T> root) {
        this.root = root;
    }

    public void insert(List<T> items) {
        TreeNode<T> parentNode = root;
        for (T item : items) {
            TreeNode<T> currentNode = new TreeNode<>(item);
            List<TreeNode<T>> childNodes = parentNode.getChildNodes();
            if (Objects.isNull(childNodes)) {
                childNodes = new ArrayList();
                childNodes.add(currentNode);
                parentNode.setChildNodes(childNodes);
                parentNode = currentNode;
            } else {
                int index = childNodes.indexOf(currentNode);
                if (index == -1) {
                    childNodes.add(currentNode);
                    parentNode = currentNode;
                } else {
                    parentNode = childNodes.get(index);
                }
            }
        }
    }

    public void show() {
        show(this.root);
    }

    // maybe overflow?
    public void show(TreeNode<T> startNode) {
        LinkedBlockingQueue<TreeNode<T>> queue = new LinkedBlockingQueue<>();
        queue.add(startNode);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode<T> node = queue.poll();
                System.out.print(Objects.requireNonNull(node).getId() + "\t");
                if (!Objects.isNull(node.getChildNodes())) {
                    queue.addAll(node.getChildNodes());
                }
            }
            System.out.println();
        }
    }

    public boolean search(List<T> items) {
        TreeNode<T> parentNode = root;
        for (T item : items) {
            TreeNode<T> currentNode = new TreeNode<>(item);
            int index = parentNode.getChildNodes().indexOf(currentNode);
            if (index == -1) {
                return false;
            } else {
                parentNode = parentNode.getChildNodes().get(index);
            }
        }
        return true;
    }
}
