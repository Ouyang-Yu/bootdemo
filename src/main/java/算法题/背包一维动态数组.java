package 算法题;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/10/27 10:41
 */
public class 背包一维动态数组 {
    public static int dp2(int[] weight, int[] value, int bagweight) {
        int[]dp = new int[bagweight + 1];    //定义dp数组: dp[j]表示背包容量为j时，能获得的最大价值

        for (int i = 0; i < weight.length; i++) {
            for (int j = bagweight; j >= weight[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }

        return 1;

    }











    }
//     int n = weight.length;
//     int max = Integer.MIN_VALUE;//能装的最大价值
//     //定义dp数组: dp[j]表示背包容量为j时，能获得的最大价值
//     int[] dp = new int[bagweight + 1];
// //遍历顺序:先遍历物品，再遍历背包容量
// for (int i= 0;i< n; i++){
//         for (int j = bagweight; j >= weight[il; j--)
//             dp[j] = Math.max(dp[jl, dp[j - weight[i]] + value[il);
// //打印dp数组
//         for (int j = 0; j <= bagweight; j++){
//             System.out.print(dp[j] + ");
//             for (int i = 0; i <= bagweight; i++) [
//             if (dp[i] > max)
//                 max = dp[il;
//             return max;
//         }
// }

