#include <iostream>
#include <stack>
using namespace std;

class Queue {
    stack<int> stack1, stack2;

public:
    void enqueue(int x) {
        // Push element onto stack1
        stack1.push(x);
    }

    int dequeue() {
        if (stack2.empty()) {
            if (stack1.empty()) {
                throw runtime_error("Queue is empty");
            }
            while (!stack1.empty()) {
                stack2.push(stack1.top());
                stack1.pop();
            }
        }
        int x = stack2.top();
        stack2.pop();
        return x;
    }
};

int main() {
    Queue q;
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    cout << q.dequeue() << endl; // 1
    cout << q.dequeue() << endl; // 2
    q.enqueue(4);
    cout << q.dequeue() << endl; // 3
    return 0;
}
