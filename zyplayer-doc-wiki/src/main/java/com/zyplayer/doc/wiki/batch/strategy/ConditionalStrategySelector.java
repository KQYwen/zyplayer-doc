package com.zyplayer.doc.wiki.batch.strategy;

import com.zyplayer.doc.wiki.batch.strategy.base.IConditionalStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * 策略选择器
 * 只要是实现自IConditionalStrategy的策略，都能通过这个类按规则匹配
 * @author Sh1yu
 * @since 2023年7月13日
 */
@Component
public class ConditionalStrategySelector {
    final Map<Class<?>,ConditionalStrategySeletorUnit> cache = new HashMap<>();

    /**
     * 注册所有策略
     * @author Sh1yu
     * @since 2023年7月13日
     */
    public ConditionalStrategySelector(ObjectProvider<List<IConditionalStrategy>> allStrategyObjectProvider) {
        List<IConditionalStrategy> allStrategy = allStrategyObjectProvider.getIfAvailable();
        Map<? extends Class<?>, List<IConditionalStrategy>> units = allStrategy.stream().collect(Collectors.groupingBy(iterm -> iterm.getClass().getInterfaces()[0]));
        Set<? extends Map.Entry<? extends Class<?>, List<IConditionalStrategy>>> entries = units.entrySet();
        //策略分类到一层缓存
        for (Map.Entry<? extends Class<?>, List<IConditionalStrategy>> entry : entries) {
            cache.put(entry.getKey(), new ConditionalStrategySeletorUnit(entry.getValue()));
        }

    }

    public <T> T getStrategy(String condition,Class<?> clazz){
        ConditionalStrategySeletorUnit conditionalStrategySeletorUnit= cache.get(clazz);
        if (null == conditionalStrategySeletorUnit){
            return null;
        }
        T strategy = (T) conditionalStrategySeletorUnit.getStrategy(condition);
        return strategy;
    }

    static class ConditionalStrategySeletorUnit<T extends IConditionalStrategy> {
        final List<T> strategys;

        //策略集体实现到二层缓存
        final ConcurrentHashMap<String, T> cache = new ConcurrentHashMap<>();

        public ConditionalStrategySeletorUnit(List<T> strategys) {
            this.strategys = strategys;
        }

        public T getStrategy(String condition) {
            T cacheStrategy = cache.get(condition);
            if (null != cacheStrategy) {
                return cacheStrategy;
            }
            for (T strategy : strategys) {
                if (strategy.matchCondition(condition)) {
                    cache.put(condition, strategy);
                    return strategy;
                }
            }
            return null;
        }

    }
}
