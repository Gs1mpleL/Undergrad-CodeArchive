package com.wanfeng.javalearn.设计模式.strategy;

import com.wanfeng.javalearn.设计模式.strategy.impl.AbstractStrategy;
import com.wanfeng.javalearn.设计模式.strategy.impl.StrategyA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DemoService {

    @Resource
    private Map<String, IStrategy> loginStrategyMap;

    public IStrategy getStrategy(String a){
        if (loginStrategyMap.get(a) == null) {
            System.out.println("执行默认策略");
            return new StrategyA();
        }

        return loginStrategyMap.get(a);
    }

}
