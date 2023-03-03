package 算法题;

import java.util.ArrayList;

/**
 * 一棵二叉树原本是搜索二叉树，但是其中有两个节点调换了位置，使得这棵二叉树不再是搜索二叉树，请按升序输出这两个错误节点的值。(每个节点的值各不相同)
 * 搜索二叉树：满足每个节点的左子节点小于当前节点，右子节点大于当前节点。
 *
 *
 *
 * 例如  1 2 3  输出1 2
 */
public class 树 {
    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;

        int[] a = findError(treeNode1);
        System.out.println(a[0]);
        System.out.println(a[1]);

    }



    static ArrayList<Integer> list = new ArrayList<>();

    public static  int[] findError(TreeNode root) {
        // write code here

        dfs(root);
        int[] a = new int[100];
        a[0] = list.get(0);
        a[1] = list.get(1);
        return a;

    }




    public static void dfs(TreeNode root) {

        if (root.left != null) {
            if (root.left.val > root.val) {
//                list.add(root.left.val);
                System.out.println(root.left.val);
            }
            dfs(root.left);
        }
        if (root.right != null) {
            if (root.right.val < root.val) {
//                list.add(root.right.val);
                System.out.println(root.right.val);
            }
            dfs(root.right);
        }

    }


    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

}




