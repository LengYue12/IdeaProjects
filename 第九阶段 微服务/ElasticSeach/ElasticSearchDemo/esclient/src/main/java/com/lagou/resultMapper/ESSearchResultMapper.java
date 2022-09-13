package com.lagou.resultMapper;

import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject ElasticSearchDemo
 * @Author lengy
 * @CreateTime 2022/9/5 1:09
 * @Description  自定义结果映射，处理高亮
 * 搜索结果的映射
 */
// 重写了结果映射
public class ESSearchResultMapper implements SearchResultMapper {

    /**
     * 完成结果映射
     * 操作的重点应该是将原有的结果，_source取出来，放入高亮的数据
     * @param searchResponse
     * @param aClass
     * @param pageable
     * @param <T>
     * @return AggregatedPage需要三个参数进行构建：pageable，List集合，总记录数
     */
    // 获得文档的原始_source，获得高亮字段及其对应的值，再放到list
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

        // 获取总记录数，获取所有的文档个数
        long totalHits = searchResponse.getHits().getTotalHits();
        // 记录列表
        List<T> list = new ArrayList<>();
        // 获取原始的搜索结果
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit : hits) {
            if (hits.getHits().length <= 0){
                return null;
            }
            // 获取_source属性中的所有数据
            // 取出source里的数据
            // getSourceAsMap就是从_source 中把原始数据取出来，但是每取出的原数据其实是文档数据
            // map里封装了一个个文档数据
            Map<String, Object> map = hit.getSourceAsMap();

            // 把高亮的字段和值放到map中
            // 获得高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            // 每个高亮字段都需要进行设置

            // 取出高亮的数据，再添加到_source中，等同于把原有的高亮数据给覆盖了
            // 新的map里既有字段名，又有高亮之后的效果
            // 再将map转换JSON，JSON转换为对象，再将对象放到list
            for (Map.Entry<String, HighlightField> highlightField : highlightFields.entrySet()) {
                // 获取高亮的key：高亮字段
                String key = highlightField.getKey();
                // 获取value：高亮之后的效果
                HighlightField value = highlightField.getValue();
                // 将高亮字段和文本效果放入到map中
                map.put(key,value.getFragments()[0].toString());
            }
            // 将map转换为对象
            Gson gson = new Gson();
            // map->jsonString->对象
            T t = gson.fromJson(gson.toJson(map), aClass);

            // 再把实体对象添加到返回的list结果中
            list.add(t);
        }

        return new AggregatedPageImpl<>(list,pageable,totalHits);
    }
}
