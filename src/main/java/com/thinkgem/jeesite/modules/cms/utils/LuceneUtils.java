package com.thinkgem.jeesite.modules.cms.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;


/**
 * @项目名称：lucene
 * @类名称：LuceneUtils
 * @类描述：获取分词器和索引位置
 * @创建人：HAILOU
 * @创建时间：2016年8月31日 上午9:48:06
 * @version 1.0.0
 */
public class LuceneUtils {


    private static FSDirectory directory;
    private static Analyzer analyzer;
    static {
        try {
            directory = FSDirectory.open(Paths.get("./tmp/articleindex"));
             //analyzer = new StandardAnalyzer();
            analyzer = new SmartChineseAnalyzer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FSDirectory getDirectory() {
        return directory;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static void closeIndexWriter(IndexWriter indexWriter) {
        if (indexWriter != null) {
            try {
                indexWriter.close();
            } catch (Exception e2) {
                 e2.printStackTrace();
            }
        }
    }

}
