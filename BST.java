class TreeNode {
    int data;
    TreeNode left, right;

    public TreeNode(int item) {
        data = item;
        left = right = null;
    }
}

class BinaryTree {
    TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public TreeNode insert(TreeNode node, int data) {
        if (node == null) return new TreeNode(data);

        if (data < node.data) {
            node.left = insert(node.left, data);
        } else {
            node.right = insert(node.right, data);
        }

        return node;
    }

    public void levelOrder(TreeNode node) {
        if (node == null) return;

        TreeNode[] queue = new TreeNode[100];
        int front = 0;
        int rear = 0;

        queue[rear++] = node;

        while (front < rear) {
            TreeNode current = queue[front++];
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue[rear++] = current.left;
            }

            if (current.right != null) {
                queue[rear++] = current.right;
            }
        }
    }
}

public class BST {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        tree.root = tree.insert(tree.root, 50);
        tree.insert(tree.root, 30);
        tree.insert(tree.root, 70);
        tree.insert(tree.root, 20);
        tree.insert(tree.root, 40);
        tree.insert(tree.root, 60);
        tree.insert(tree.root, 80);

        System.out.print("Level Order Traversal: ");
        tree.levelOrder(tree.root);
    }
}