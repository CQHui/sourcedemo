package com.qihui.sourcedemo.util;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * @author chenqihui
 * @date 2019/4/23
 */
public class HashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
    //1. 定义table 存放HshMap数组元素
    Node<K, V>[] table = null;
    //2. 实际table存储容量大小
    int size;
    //3. 负载因子 负载因子越小，hash冲突越小
    float DEFAULT_LOAD_FACTOR = 0.75f;
    //4. table默认初始值大小16
    static int DEFAULT_INITIAL_CAPACITY = 16;


    @Override
    public V put(K key, V value) {
        // 1.判断table 数组大小是否为空（如果为空的情况下 ，做初始化操作）
        if (table == null) {
            table = new Node[DEFAULT_INITIAL_CAPACITY];
        }
        // 2. hashMap 扩容机制 为什么要扩容？扩容数组之后，有什么影响？ hahsmap 中是从什么时候开始扩容
        // 实际存储大小=负载因子*初始容量=DEFAULT_LOAD_FACTOR0.75*DEFAULT_INITIAL_CAPACITY16=12
        // 如果size>12的时候就需要开始扩容数组,扩容数组大小之前两倍
        if (size > (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY)) {
            // 需要开始对table进行属数组扩容
            resize();
        }

        // 3.计算hash值指定下标位置
        int index = getIndex(key, DEFAULT_INITIAL_CAPACITY);
        Node<K, V> node = table[index];
        if (node == null) {
            // 没有发生hash冲突问题--- index冲突
            node = new Node<K, V>(key, value, null);
            size++;
        } else {

            Node<K, V> newNode = node;
            while (newNode != null) {
                // 已经发生hash冲突问题key 直接添加(冲突node)到前面了 不是往后面加
                if (newNode.getKey().equals(key) || newNode.getKey() == key) {
                    // hashCodoe 相同，而且equals 相等情况 说明是同一个对象 修改值
                    // node.value = value;
                    return newNode.setValue(value);
                } else {
                    // 继续添加，排在前面 hascode 取模余数相同 index 存放在链表 或者hashCode 相同但是对象不同
                    // 新的node 的next 原来的node
                    if (newNode.next == null) {
                        // 说明遍历到最后一个node ,添加node
                        node = new Node<K, V>(key, value, node);
                        size++;
                    }

                }
                newNode = newNode.next;
            }

        }
        table[index] = node;
        return null;
    }

    // 对table进行扩容
    private void resize() {

        // 1.生成新的table 是之前的两倍的大小 DEFAULT_INITIAL_CAPACITY*2
        Node<K, V>[] newTable = new Node[DEFAULT_INITIAL_CAPACITY << 1];
        // 2.重新计算index索引,存放在新的table里面
        for (int i = 0; i < table.length; i++) {
            // 存放在之前的table 原来的node
            Node<K, V> oldNode = table[i];
            // a 的index=1 b 的index=1
            // a ##
            while (oldNode != null) {
                table[i] = null;// 赋值为null---为了垃圾回收机制能够回收 将之前的node删除
                // 存放在之前的table 原来的node key
                K oldK = oldNode.key;
                // 重新计算index
                int index = getIndex(oldK, newTable.length);
                // 存放在之前的table 原来的node next

                Node<K, V> oldNext = oldNode.next;
                // 如果node下标在新newTable发生相同的index时候,以链表进行存储 //
                // 原来的node的下一个是最新的（原来的node存放下新的node下一个）
                oldNode.next = newTable[index];
                // 将之前的node赋值给 newTable[index]
                newTable[index] = oldNode;
                // 判断是否继续循环遍历
                oldNode = oldNext;

            }
        }
        // 3.将newtable赋值给老的table
        table = newTable;
        DEFAULT_INITIAL_CAPACITY = newTable.length;
     }

    @Override
    public V get(Object key) {
        Node<K, V> node = getNode(table[getIndex(key, DEFAULT_INITIAL_CAPACITY)], key);
        return node == null ? null : node.value;
    }

    public Node<K, V> getNode(Node<K, V> node, Object k) {
        while (node != null) {
            if (node.getKey().equals(k)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return super.size();
    }

    class Node<K, V> implements Entry<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            super();
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    // 测试方法.打印所有的链表元素
    public void print() {

        for (int i = 0; i < table.length; i++) {
            Node<K, V> node = table[i];
            System.out.print("下标位置[" + i + "]");
            while (node != null) {
                System.out.print("[ key:" + node.getKey() + ",value:" + node.getValue() + "]");
                node = node.next;
            }
            System.out.println();
        }

    }

    public int getIndex(Object k, int length) {
        int hashCode = k.hashCode();
        return hashCode % length;
    }
}
