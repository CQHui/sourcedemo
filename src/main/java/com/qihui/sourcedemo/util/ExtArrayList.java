package com.qihui.sourcedemo.util;

import com.qihui.sourcedemo.mybatis.Main;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;

/**
 * @author chenqihui
 * @date 2019/4/22
 */
public class ExtArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable {
    private static final long serialVersionUID = 6798341950940775942L;


    // 保存ArrayList中数据的数组
    private transient Object[] elementData;
    // ArrayList实际数量
    private int size;

    public ExtArrayList() {
        this(10);
    }

    public ExtArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        // 初始化数组容量
        elementData = new Object[initialCapacity];
    }

    // 添加方法实现
    @Override
    public boolean add(Object object) {
        ensureExplicitCapacity(size + 1);
        elementData[size++] = object;
        return true;
    }

    @Override
    public void add(int index, Object object) {
        rangeCheck(index);
        ensureExplicitCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = object;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        rangeCheck(index);
        return (E) elementData[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        E object = get(index);
        int numMoved = elementData.length - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return object;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < elementData.length; i++) {
            Object element = elementData[i];
            if (element.equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    public void ensureExplicitCapacity(int minCapacity) {
        // 如果存入的数据,超出了默认数组初始容量 就开始实现扩容
        if (size == elementData.length) {
            // 获取原来数组的长度 2
            int oldCapacity = elementData.length;
            // oldCapacity >> 1 理解成 oldCapacity/2 新数组的长度是原来长度1.5倍
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity < minCapacity) {
                // 最小容量比新容量要小的,则采用初始容量minCapacity
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("数组越界啦!");
        }
    }
}
