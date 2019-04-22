package com.qihui.sourcedemo.util;

import java.util.AbstractSequentialList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class LinkedList<E> extends AbstractSequentialList<E>
        implements List<E>, Cloneable, java.io.Serializable{
    private static final long serialVersionUID = 1103449126579580390L;
    // 第一个元素
    private Node first;
    // 最后一个元素
    private Node last;
    // 实际存放在长度
    private int size;

    class Node {
        // 上一个节点
        Node prev;
        // 节点内容
        Object object;
        // 下一个节点
        Node next;
    }




    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        // 创建新的节点
        Node node = new Node();
        // 节点内容
        node.object = e;
        if (first == null) {

            first = node;
        } else {
            // 存放上一个节点内容
            node.prev = last;
            // 设置上一个节点的next为当前节点
            last.next = node;
        }
        last = node;
        size++;
        return true;
    }

    @Override
    public void add(int index, E e) {
        // 1.循环遍历到当前index位置Node
        // 2.新增当前节点
        Node newNode = new Node();
        newNode.object = e;
        // 获取原来的节点
        Node olNode = getNode(index);
        // 获取原来上一个节点
        Node olNodePrev = olNode.prev;

        // 4.新增节点的上一个还是当前Node节点的 上一个节点,下一个就是原来的节点
        // 原来上一个节点变为当前节点
        olNode.prev = newNode;
        if (olNodePrev == null)
            first = newNode;
        else
            // 原来上一个节点的下一个节点变为当前节点
            olNodePrev.next = newNode;

        // 新节点的下一个节点为原来节点
        newNode.next = olNode;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        LinkedList<E>.Node node = getNode(index);
        return (E) node.object;
    }

    public Node getNode(int index) {
        Node node = null;
        if (first != null) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkElementIndex(index);
        Node node = getNode(index);
        if (node != null) {
            Node prevNode = node.prev;
            Node nextNode = node.next;
            // 设置上一个节点的next为当前删除节点的next
            if (prevNode != null) {
                prevNode.next = nextNode;
            }

            // 判断是否是最后一个节点
            if (nextNode != null) {
                nextNode.prev = prevNode;
            }

        }
        size--;
        return (E) node;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkElementIndex(index);
        //待重写
        return null;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("越界啦!");
    }
}
