package 算法题;

import org.junit.jupiter.api.Test;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-07-07  18:43
 */
public class 快排_分而治之_10steps {
    @Test
    public void s() {
        int[] a = {};
        quickSort(a, 0, a.length - 1);
    }

    /**
     *定义递归方法,获取中间下标方法,
     */
    //分而治之   选择一个pivot 小于他的放左边  大于它的放右边  最后pivot所在的位置就是最后的下标
    private static void quickSort(int[] arr, int begin, int end) {
        if (begin >= end) return;
        int midIndex = getMidIndex(arr, begin, end);//保证左边元素都小于基准点  右边都大于基准点
        quickSort(arr, begin, midIndex - 1);
        quickSort(arr, midIndex + 1, end);
    }

    //6 steps 一次分区,参数为分区左右边界 ,返回基准点下标
    public static int getMidIndex(int[] a, int begin, int end) {
        int pivotValue = a[begin]; //基准点选择最左边
        int left = begin;
        int right = end;
        //while 里面 右左指针移动 和交换   出来再交换基准点和中间元素 并返回
        while (left < right) {
            while (left < right && a[right] > pivotValue) {
                //1.右指针下面元素 如果 大于基准点那就向左走 ,碰到小的就停下来
                //必须先右边先开始
                right--;
            }
            while (left < right && a[left] <= pivotValue) {
                //2.左边一直向右走,直到碰到比基准点大的就停下来  即如果小于基准点,那就向右走
                left++;
            }

            //3.然后交换 左右指针 下面的元素  完成一次交换
            int t = a[left];
            a[left] = a[right];
            a[right] = t;

        }
        //4.最后交换基准点元素 和中间元素  完成一次分区操作
        int t = a[begin];
        a[begin] = a[right];
        a[right] = t;

        return right;//中间元素下标
    }


}
