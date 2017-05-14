package assignment.pkg2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author jmssmith047
 */
public class CircularlyLinkedList<E> implements Iterable{
    
	protected transient Element<E> head;
        protected transient Element<E> tail;
	protected transient int size;
	
	public CircularlyLinkedList() {
		head = null;
		size = 0;
                tail = null;
	}
	
	public int size() {
		return size;
	}
	
	public E getFirst() {
		return head.value;
	}
	
	public E removeFirst() {
		if (size == 0)
			throw new NoSuchElementException();
		
		Element<E> temp = head;
		head = head.next;
		size--;
		return temp.value;
	}
	
	public void addFirst(E value) {
		head = new Element<E>(value, head, null);
                if (tail == null) 
                    tail = head;
		size++;
	}
	
	public void addLast(E value) {
		Element<E> temp = new Element<E>(value, null, null);
		if (head != null) {
			Element<E> finger = head;
			while(finger.next != null) {
				finger = finger.next;
			}
			finger.next = temp;
		} else {
			head = temp;
		}
		size++;
	}
	
	public E removeLast() {
		if (size == 0)
			throw new NoSuchElementException();
		
		Element<E> finger = head;
		Element<E> previous = null;
		
		while(finger.next != null) {
			previous = finger;
			finger = finger.next;
		}
		
		if(previous == null) {
			head = null;
		} else {
			previous.next = null;
		}
		size--;
		return finger.value;
	}
	
	public E getLast() {
		if (size == 0)
			throw new NoSuchElementException();
		
		Element<E> finger = head;
		
		while(finger.next != null) {
			finger = finger.next;
		}
                
		return finger.value;
		
	}
	
	public boolean contains(E value) {
		Element<E> finger = head;
		
		while(head.next != null && !finger.value.equals(value)) {
			finger = finger.next;
		}
		
		return finger != null;
	}
	
	public E remove(E value) {
		Element<E> finger = head;
		Element<E> previous = null;
		
		while(head.next != null && !finger.value.equals(value)) {
			previous = finger;
			finger = finger.next;
		}
		
		if(finger != null) {
			if(previous == null) {
				head = finger.next;
			} else {
				previous = finger.next;
			}
			size--;
			return finger.value;
		}
		
		return null;
	}
	
	public E getValueAtIndex(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		
		if (index == 0) {
			getFirst();
		} else if (index == size -1) {
			return getLast();
		}
		Element<E> finger = head;
		while (index > 0) {
			finger = finger.next;
			index--;
		}
		return finger.value;
	}
	
	public void insertValueAtIndex(int index, E value) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		
		Element<E> temp = new Element<E>(value, null, null);
		
		if (index == 0) {
			temp.next = head;
			head = temp;
		} else {
			Element<E> finger = head;
			Element<E> previous = null;
			while (index > 0) {
				previous = finger;
				finger = finger.next;
				index--;
			}
			previous.next = temp;
			temp.next = finger;
		}
		
		size++;
	}
	
	public void setValueAtIndex(int index, E value) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		
		if (index == 0) {
			head.value = value;
		} else {
			Element<E> finger = head;
			while (index > 0) {
				finger = finger.next;
				index--;
			}
			finger.value = value;
		}
	}
	
	public void clear() {
		head = null;
		size = 0;
	}
	
	public String toString() {
		String string = "[ ";
		
		Element<E> finger = head;
		while (finger != null && finger.next != null) {
			string += finger.value + ", ";
			finger = finger.next;
		}
		
		if(head != null) string += finger.value + " ]";
		else string += "]";
		
		
		return string;
	}
	
	private void reverseOrderIteratively() {
		if (head == null || head.next == null)
			return;
		
		Element<E> second = head.next;
		Element<E> third = second.next;
		
		second.next = head;
		head.next = null;
		
		if (third == null)
			return;
		
		Element<E> currentNode = third;
		Element<E> previousNode = second;
		
		while(currentNode != null) {
			Element<E> nextNode = currentNode.next;
			currentNode.next = previousNode;
			previousNode = currentNode;
			currentNode = nextNode;
		}
		
		head = previousNode;
	}
	
	public void reverseOrder(boolean isRecursive) {
		if(isRecursive)
			recursiveReverseOrder(head);
		else
			reverseOrderIteratively();
	}
	
        public Iterator<E> iterator()
	//post: return an iterator of this vector
	{
		return new CircularlyLinkedList.ListIterator<E>(this);
	}

        
	private void recursiveReverseOrder(Element<E> element) {
		
		if (element == null) return;
		
		if (element.next == null) {
			head = element;
			return;
		}
		
		recursiveReverseOrder(element.next);
		element.next.next = element;
		element.next = null;
	}
	

	protected static class Element<E> {
		
		public E value;
                public Element<E> previous;
		public Element<E> next;
		
		public Element(E value, Element<E> next, Element<E> previous) {
			this.value = value;
                        
			this.next = next;
                        if(next != null) {
                            next.previous = this;
                        }
                        
                        this.previous = previous;
                        if(previous != null) {
                            previous.next = this;
                        }
                        
                      
                        
		}
                
                
	}
        
        protected static class ListIterator<E> implements Iterator<E>{
		
		public int current;
		public CircularlyLinkedList<E> data;
		
		public ListIterator(CircularlyLinkedList<E> data) {
			this.data = data;
			current = 0;
		}

                @Override
                public boolean hasNext() {
                    return current < data.size;
                }

                @Override
                public E next() {
                    return data.getValueAtIndex(current++);
                }

                @Override
                public void remove() {
                    
                }
	}
        
}

