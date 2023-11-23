package com.zyplayer.doc.wiki.batch.strategy.comb;

import com.zyplayer.doc.wiki.batch.entry.DocEntry;
import com.zyplayer.doc.wiki.batch.strategy.base.IConditionalStrategy;

import java.io.File;
import java.util.ArrayList;


/**
 * 条件控制依赖梳理策略接口
 *
 * @author Sh1yu
 * @since 20230717
 */
public interface ICombDependencyStrategy extends IConditionalStrategy {
    void comb(ArrayList<DocEntry> docs, File file);
}

