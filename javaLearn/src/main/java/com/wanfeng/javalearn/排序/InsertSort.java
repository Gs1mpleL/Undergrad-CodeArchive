package com.wanfeng.javalearn.排序;

public class InsertSort extends BaseData {
    public static void main(String[] args) {
        print();
        for (int i = 1;i<arr.length;i++){
            int pos = i;
            while (pos >= 1 && arr[pos] < arr[pos-1]){
                swap(pos,pos-1);
                pos--;
            }
        }
        print();
    }
}
