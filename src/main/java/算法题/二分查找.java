package 算法题;

import org.junit.jupiter.api.Test;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-07-07  17:50
 */
public class 二分查找 {
    @Test
    public void binarySearch() {
        System.out.println((2 + 3) / 2);
        int[] a = {1, 2, 3, 5};
        int i = binarySearch(a, 0, a.length - 1, 3);
        System.out.println(i);

        System.out.println(binarySearch(a, 3));
        System.out.println(binarySearch(a, 4));
    }


    /*
    1.定义左中右三个变量
    2.在循环内 计算middleIndex  比较中间值和target  三种情况
     */
    public static int binarySearch(int[] arr, int target) {

        int left = 0;
        int right = arr.length - 1;
        int middleIndex;
        while (left <= right) {
//            middleIndex = left+(right-left) / 2;
            middleIndex = (left + right) >>> 1;//无符号右移1位,解决/2整数的整数溢出问题
            if (arr[middleIndex] == target) return middleIndex;
            if (arr[middleIndex] > target) right = middleIndex - 1;
            if (arr[middleIndex] < target) left = middleIndex + 1;
        }
        return -1;
    }

    public static int binarySearch(int[] arr, int left, int right, int target) {
        int middleIndex = (left + right) >>> 1;//无符号右移1位,解决/2整数的整数溢出问题


        if (left > right) { //最后给递归结束条件  剪枝 避免 lr 错位了还在走
            return -1;
        }
        if (arr[middleIndex] == target) return middleIndex;
        if (arr[middleIndex] > target) return binarySearch(arr, left, middleIndex - 1, target);
        if (arr[middleIndex] < target) return binarySearch(arr, middleIndex + 1, right, target);
        return -1;
    }

}

