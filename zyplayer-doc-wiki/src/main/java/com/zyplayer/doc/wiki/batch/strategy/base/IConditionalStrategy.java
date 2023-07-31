package com.zyplayer.doc.wiki.batch.strategy.base;


/**
 * 条件控制策略接口
 *
 * @author Sh1yu
 * @since 20230713
 */
public interface IConditionalStrategy {
    public String getCondition();

    public default boolean matchCondition(String key){
        return key.equals(getCondition());
    }
}
