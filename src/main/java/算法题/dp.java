package 算法题;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/10/22 20:59
 */
public class dp {
    // 普通递归有重复计算，那就备忘录递归，但递归是有开销的，那就dp：备忘录+状态转移方程
    // 递归是自上而下，注意最后的剪枝和出口   状态转移方程是自下而上，注意初始化
    public int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public int fib2(int n) {

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

}
