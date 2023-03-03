package 算法题;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-30  11:02
 */

    /*
    定义两个指针： prepre 和 curcur ；prepre 在前 curcur 在后。
每次让 prepre 的 nextnext 指向 curcur ，实现一次局部反转
局部反转完成之后，prepre 和 curcur 同时往前移动一个位置
循环上述过程，直至 prepre 到达链表尾部

     */
class ListNode {
    int val;
    ListNode next;
}
public class L206反转链表 {

    public ListNode reverseList(ListNode head) {
        ListNode cur = null;  //cur 在左    pre 在右
        ListNode pre = head;

        while (pre != null) {
            ListNode temp = pre.next; //反转前 取出 下一个位置
            pre.next = cur;  //把pre的next 指向 cur   实现反转

            cur = pre;  //cur 往右走一步
            pre = temp; //pre 也往右走一步
        }
        return cur;
    }

}
