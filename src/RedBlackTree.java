package src;

public class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int value;
        Node left;
        Node right;
        boolean color;

        Node(int value) {
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;

    public void add(int value) {
        root = add(root, value);
        root.color = BLACK;
    }

    private Node add(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = add(node.left, value);
        } else if (value > node.value) {
            node.right = add(node.right, value);
        } else {
            return node;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = swapLeft(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = swapRight(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            swapColors(node);
        }

        return node;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private Node swapLeft(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private Node swapRight(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RED;
        return x;
    }

    private void swapColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public boolean find(int value) {
        Node node = root;
        while (node != null) {
            if (value < node.value) {
                node = node.left;
            } else if (value > node.value) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public void delete(int value) {
        if (root == null) {
            return;
        }

        root = delete(root, value);
        if (root != null) {
            root.color = BLACK;
        }
    }

    private Node delete(Node node, int value) {
        if (value < node.value) {
            if (node.left == null) {
                return node;
            }
            if (!isRed(node.left) && !isRed(node.left.left)) {
                node = moveRedLeft(node);
            }
            node.left = delete(node.left, value);
        } else {
            if (isRed(node.left)) {
                node = swapRight(node);
            }
            if (value == node.value && node.right == null) {
                return null;
            }
            if (node.right != null) {
                if (!isRed(node.right) && !isRed(node.right.left)) {
                    node = moveRedRight(node);
                }
                if (value == node.value) {
                    Node min = min(node.right);
                    node.value = min.value;
                    node.right = deleteMin(node.right);
                } else {
                    node.right = delete(node.right, value);
                }
            }
        }
        return balance(node);
    }

    private Node moveRedLeft(Node node) {
        swapColors(node);
        if (isRed(node.right.left)) {
            node.right = swapRight(node.right);
            node = swapLeft(node);
            swapColors(node);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        swapColors(node);
        if (isRed(node.left.left)) {
            node = swapRight(node);
            swapColors(node);
        }
        return node;
    }

    private Node balance(Node node) {
        if (isRed(node.right)) {
            node = swapLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = swapRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            swapColors(node);
        }
        return node;
    }

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return null;
        }
        if (!isRed(node.left) && !isRed(node.left.left)) {
            node = moveRedLeft(node);
        }
        node.left = deleteMin(node.left);
        return balance(node);
    }
}

class main {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        for (int i = 0; i < 10; i++) {
            tree.add(i);
        }
        tree.add(150);

        System.out.println(tree.find(5));
        tree.delete(5);
        System.out.println(tree.find(5));
    }
}
