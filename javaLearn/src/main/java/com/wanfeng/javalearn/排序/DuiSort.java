package com.wanfeng.javalearn.排序;

import lombok.extern.slf4j.Slf4j;

import java.util.PriorityQueue;

@Slf4j
public class DuiSort extends BaseData {
    public static void main(String[] args) {
        print();
        // 构建堆
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int j : arr) {
            queue.add(j);
        }
        // 每次出去一个数据，都会重新维护堆
        for (int i = 0; i < arr.length; i++) {
            if (!queue.isEmpty()){
                arr[i] = queue.poll();
            }
        }

        print();
    }


}
