class Solution {
    public ListNode partition(ListNode head, int x) {
        if(head == null) return null;

        ListNode d1 = new ListNode(0);
        ListNode prev1 = d1;
        ListNode d2 = new ListNode(0);
        ListNode prev2 = d2;
        ListNode current = head;

        while(current != null){
            if(current.val < x){
                prev1.next = current;
                prev1 = current;
            } else{
                prev2.next = current;
                prev2 = current;
            }
            current = current.next;
        }
        prev2.next = null;
        prev1.next = d2.next;

        head = d1.next;

        return head;
    }
}
