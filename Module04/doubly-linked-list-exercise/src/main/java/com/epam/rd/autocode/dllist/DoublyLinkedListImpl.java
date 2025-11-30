package com.epam.rd.autocode.dllist;

import java.util.Iterator;
import java.util.Optional;

public class DoublyLinkedListImpl implements DoublyLinkedList {

	private Node head;
	
	private Node tail;

	private static class Node {

		Object element;

		Node next;

		Node prev;

		Node(Object obj, Node prev, Node next) {
			this.element = obj;
			this.next = next;
			this.prev = prev;
		}

	}

    private int getSize() {
        int size = 0;
        Node curr = head;

        while (curr != null) {
            size++;
            curr = curr.next;
        }
        return (size);
    }

    private Node getNode(int index) {
        if (index < 0 || index >= getSize()) {
            throw (new IndexOutOfBoundsException());
        }

        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return (curr);
    }

	@Override
	public boolean addFirst(Object element) {
		if (element == null)
            throw (new NullPointerException());

        Node addNode = new Node(element, null, head);
        if (head == null) {
            head = addNode;
            tail = addNode;
        } else {
            head.prev = addNode;
            head = addNode;
        }
        return (true);
	}

	@Override
	public boolean addLast(Object element) {
        if (element == null)
            return (false);

        Node addNode = new Node(element, tail, null);
        if (tail == null) {
            head = addNode;
            tail = addNode;
        } else {
            tail.next = addNode;
            tail = addNode;
        }
		return (true);
	}

	@Override
	public void delete(int index) {
        if (index < 0 || index >= getSize())
            throw (new IndexOutOfBoundsException());

        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        if (curr.prev != null)
            curr.prev.next = curr.next;
        else
            head = curr.next;

        if (curr.next != null)
            curr.next.prev = curr.prev;
        else
            tail = curr.prev;
	}
	
	@Override
	public Optional<Object> remove(Object element) {
        if (element == null)
            throw (new NullPointerException());

        int i = 0;
        int idx = -1;
        Node curr = head;
        while (curr != null) {
            if (curr.element.equals(element)) {
                idx = i;
                break;
            }
            i++;
            curr = curr.next;
        }
        if (idx >= 0) {
            Node node = getNode(idx);
            delete(idx);
            return (Optional.of(node.element));
        }
		return (Optional.empty());
	}

	@Override
	public boolean set(int index, Object element) throws IndexOutOfBoundsException {
        if (element == null)
            return (false);

        Node setNode = getNode(index);
        setNode.element = element;
        return (true);
	}

	@Override
	public int size() { return (getSize()); }

	@Override
	public Object[] toArray() {
		int size = getSize();
        Object[] arr = new Object[size];

        Node curr = head;
        int i = 0;
        while (curr != null) {
            arr[i++] = curr.element;
            curr = curr.next;
        }
        return (arr);
	}

	@Override
	public String toString() {
		if (head == null)
            return ("");

        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.element.toString()).append(" ");
            curr = curr.next;
        }
        return (sb.toString().trim());
	}

}
