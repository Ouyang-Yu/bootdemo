package 算法题.leetcode;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/9/11 12:52
 */
public class L114 {
    //在原来基础上移动,而不开辟新的空间
    public void flatten(TreeNode root) {
        if (root==null) {
            return;
        }
        TreeNode right = root.right; //将右边保留下来
        flatten(root.right);

        root.right = root.left; // 将左边移动到右边 ,因为新的链表 下一个叫 right
        flatten(root.left);
        root.left = null;//左边置空

        while (root.right != null) {
            root = root.right; //一直找到原来左边 的最后一个 赋值给root
        }
        root.right = right; //把root的下一个指向原来的右边
    }



}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
