/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.pkg2;

/**
 *
 * @author mjz4777
 */
public class Stack<E> extends SingleLinkedList{
    
    public void push(E value) {
        addFirst(value);
    }
    
    public E pop() {
        return (E) removeFirst();
    }
}
