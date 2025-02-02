package com.wanfeng.javalearn.敏感词过滤;

import java.util.*;

public class SensitiveFilter {
    public static void initTree(){
        List<String> list = Arrays.asList("傻子", "傻逼", "圣诞", "你好");
        Map<Character,Object> preFixTree = buildTree(list);
        System.out.println(preFixTree);
        System.out.println("触发敏感词"+scan("我是大傻逼，你好圣诞呀", preFixTree));
    }

    /**
     * 创建树
     */
    private static Map<Character, Object>  buildTree(List<String> list) {
        HashMap<Character, Object> baseMap = new HashMap<>();
        for (String s : list) {
            HashMap<Character, Object> currMap = baseMap;
            char[] chs = s.toCharArray();
            for (int i = 0; i < chs.length; i++) {
                // 当前层级不存在该字符
                currMap.computeIfAbsent(chs[i], k -> new HashMap<Character, Object>());
                // 进入下一层
                currMap = (HashMap<Character, Object>) currMap.get(chs[i]);
                // 最后一个字符
                if (i == chs.length-1){
                    currMap.put('$',chs);
                }
            }
        }
        return baseMap;
    }

    /**
     * 检测句子
     */
    public static List<String> scan(String text,Map<Character, Object>wordTree){

        char[] chs = text.toCharArray();
        Map<Character, Object> currMap = wordTree;
        List<String> triggerWords = new ArrayList<>();
        for (char ch : chs) {
            // 当前字符无敏感词
            if (currMap.get(ch) == null){
                currMap = wordTree;
                continue;
            }
            currMap = (HashMap<Character, Object>) currMap.get(ch);
            // 到达了敏感词结尾 表明触发了敏感词
            if (currMap.get('$') != null) {
                triggerWords.add(new String((char[])currMap.get('$')));
                currMap = wordTree;
            }

        }
        return triggerWords;
    }



    public static void main(String[] args) {
        initTree();
    }

}
