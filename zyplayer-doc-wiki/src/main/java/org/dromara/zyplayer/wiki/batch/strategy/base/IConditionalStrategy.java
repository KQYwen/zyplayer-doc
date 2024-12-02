package org.dromara.zyplayer.wiki.batch.strategy.base;


/**
 * 条件控制策略接口
 *
 * @author Sh1yu
 * @since 20230713
 */
public interface IConditionalStrategy {
    String getCondition();

    default boolean matchCondition(String key){
        return key.equals(getCondition());
    }
}
