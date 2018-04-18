package com.github.soonboylena.myflow.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyConflictCollection<D> extends HashMap<String, D> {

    private final static Logger logger = LoggerFactory.getLogger(KeyConflictCollection.class);
    private final static Pattern keyPattern = Pattern.compile("(\\S+)\\[(\\d+)]");

    /**
     * Hash列表
     */
    private Map<String, List<D>> origData = new HashMap<>();

    /**
     * 列表中加数据，保证key不被覆盖
     *
     * @param key
     * @param datum
     */
    @Override
    public D put(String key, D datum) {

        List<D> ds = origData.get(key);
        if (ds == null) ds = new ArrayList<>();
//        // 记录之前的size
//        int indexBefore = ds.size();
        ds.add(datum);
//        // 记录之后的size
//        int indexAfter = ds.size();
        origData.put(key, ds);
        return datum;

/*        // 看来是加进去了
        if (indexAfter == indexBefore + 1) {
            return indexedKey(key, indexBefore); //  相当于size - 1
        } else {
            logger.warn("索引没变，加了重复内容到列表中");
            // 估计是重复了,还得找一圈 (hashCode equal 写的有问题？)
            int actureIndex = ds.indexOf(datum);
            return indexedKey(key, actureIndex);
        }*/
    }

    /**
     * 列表中加数据，保证key不被覆盖
     * 与上个方法类似，但是key是包含index的
     *
     * @param indexedKey
     * @return 如果没有冲突，返回key。如果冲突了，返回新的不重复的key
     */
    public void putByIndexedKey(String indexedKey, D value) {

        String key = indexedKey;
        Matcher matcher = keyPattern.matcher(indexedKey);
        if (matcher.matches()) {
            // index 不要了，重排 ?? 可能会有bug,两个数据顺序被交换了
            key = matcher.group(1);
        }
        put(key, value);
    }

    private String indexedKey(String key, int index) {
        if (index == 0) return key;
        return String.format("%s[%d]", key, index);
    }

    @Override
    public D get(Object indexedKey) {
        if (indexedKey == null) return null;

        List<D> ds = origData.get(indexedKey);
        if (ds != null && ds.size() == 1) {
            // 只有一个，不冲突
            return ds.get(0);
        }

        Matcher matcher = keyPattern.matcher(indexedKey.toString());
        if (!matcher.matches()) {
            logger.info("不认识的格式： {}", indexedKey);
            return null;
        }

        String key = matcher.group(1);
        String index = matcher.group(2);

        List<D> list = origData.get(key);
        if (list == null || list.isEmpty()) {
            logger.info("不存在的key： {}. 原始indexedKey： {}", key, indexedKey);
            return null;
        }

        int intIndex = Integer.valueOf(index);
        if (list.size() > intIndex) {
            return list.get(intIndex);
        }
        logger.info("index太大： {}. 原始indexedKey： {}", index, indexedKey);
        return null;
    }

    /**
     * 根据key和index取 value
     *
     * @param key
     * @param index
     * @return
     */
    public D get(String key, int index) {
        List<D> ds = origData.get(key);
        if (ds != null && ds.size() > index) return ds.get(index);
        return null;
    }

    public Map<String, D> noConflictMap() {

        Map<String, D> noConflictMap = new HashMap<>();

        for (Map.Entry<String, List<D>> stringListEntry : origData.entrySet()) {

            String key = stringListEntry.getKey();
            List<D> list = stringListEntry.getValue();

            for (int i = 0; i < list.size(); i++) {
                noConflictMap.put(indexedKey(key, i), list.get(i));
            }
        }
        return noConflictMap;
    }

    public List<D> entryList() {
        List<D> list = new ArrayList<>(origData.size() * 2);
        for (List<D> ds : origData.values()) {
            list.addAll(ds);
        }
        return list;
    }
}
