package 算法题;

import org.junit.jupiter.api.Test;

/**
 * By ouyangyu369@gmail.com On 2022-07-16  21:58
 */
public class L62不同路径 {
    static int count;
    int m = 3;
    int n = 7;
    @Test
    public void 递归() {

        dfs(0, 0);
        System.out.println(count);
    }
    @Test
    public void dp() {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1; //初始化 第0行
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;//
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];// from left + from up
            }
        }
        System.out.println(dp[m-1][n-1]);
    }

    private  void dfs(int i, int j) {
        if (i == m && j == n) { //终点判断
            count++;
            return;
        }
        if (i > m || j > n) {  //超出边界判断      不能相等,边缘不能停
            return ;
        }
        dfs(i + 1, j); //right
        dfs(i, j+1); //down

    }


}
