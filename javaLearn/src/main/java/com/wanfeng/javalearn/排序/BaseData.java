package com.wanfeng.javalearn.排序;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
@Slf4j
public class BaseData {
    public static int[] arr = new int[]{12,1,2,3,12,523,12,2,643,1,12,12};

    public static void swap(int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void print(){
        System.out.println(Arrays.toString(arr));
    }
}
