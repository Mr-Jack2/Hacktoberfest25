import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {
        // Create a Stack of integers
        Stack<Integer> stack = new Stack<>();

        // 1. PUSH - Add elements to the stack
        System.out.println("Pushing elements: 10, 20, 30, 40");
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        System.out.println("Current Stack: " + stack);

        // 2. PEEK - View the top element (without removing)
        System.out.println("\nTop element using peek(): " + stack.peek());
        System.out.println("Stack after peek (unchanged): " + stack);

        // 3. POP - Remove the top element
        System.out.println("\nPopping top element: " + stack.pop());
        System.out.println("Stack after pop: " + stack);

        // 4. SEARCH - Find position of an element
        int elementToSearch = 20;
        int position = stack.search(elementToSearch);
        if (position == -1) {
            System.out.println("\nElement " + elementToSearch + " not found in stack");
        } else {
            System.out.println("\nElement " + elementToSearch + " found at position (from top): " + position);
        }

        // 5. SIZE - Get the number of elements
        System.out.println("\nCurrent size of stack: " + stack.size());

        // 6. ISEMPTY - Check if the stack is empty
        System.out.println("\nIs stack empty? " + stack.isEmpty());

        // 7. POP all elements (LIFO)
        System.out.println("\nPopping all elements:");
        while (!stack.isEmpty()) {
            System.out.println("Popped: " + stack.pop() + " | Remaining Stack: " + stack);
        }

        // 8. Final check
        System.out.println("\nIs stack empty now? " + stack.isEmpty());
    }
}
