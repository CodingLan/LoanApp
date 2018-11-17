package com.zhenxing.loanapp.http;

import java.util.List;

/**
 * 列表分页类
 * Created by xtdhwl on 24/01/2018.
 */

public class TBPage {

    private static int PAGE_INIT = 1;
    private static int PAGE_SIZE = 10;

    /**
     * 分页接口.
     */
    //由于BaseBean中data返回类型不确定.确定分页数据
    // data instanceof List
    // {
    //     "data": [
    //           {
    //                 "name": ""
    //            }
    //      ]
    // }
    //  data.bill instanceof PageList
    // {
    //     "data": {
    //         "bill": [
    //              {
    //                  "name": ""
    //              }
    //          ]
    //      }
    // }
    public interface PageList {
        /**
         * 分页数据
         *
         * @return
         */
        List getPageData();
    }

    /**
     * 初始化页
     */
    private int valueInit = -1;
    /**
     * 页大小
     */
    private int valueSize = -1;
    /**
     * 当前页
     */
    private int value = -1;
    /**
     * 标志, 辅助判断当前页是否加加
     */
    private boolean isCurrentSucceed = true;

    public TBPage() {
        this(PAGE_INIT, PAGE_SIZE);
    }

    public TBPage(int pageInt, int pageSize) {
        this.valueInit = pageInt;
        this.valueSize = pageSize;
        this.value = pageInt;
    }

    public boolean isCurrentSucceed() {
        return isCurrentSucceed;
    }

    public void setCurrentSucceed(boolean currentSucceed) {
        isCurrentSucceed = currentSucceed;
    }

    /**
     * 恢复,重新加载
     */
    public void reset() {
        isCurrentSucceed = true;
        value = valueInit;
    }

    /**
     * 当前页加加
     */
    public void increment() {
        value++;
    }

    /**
     * 是否是第一页
     *
     * @return
     */
    public boolean isFirst() {
        return value == valueInit;
    }

    /**
     * 是否加载完成
     *
     * @param size
     * @return
     */
    public boolean isFinish(int size) {
        return size < valueSize;
    }

    /**
     * 获取当前页
     *
     * @return
     */
    public int get() {
        return value;
    }

    /**
     * 获取当前页字符串
     *
     * @return
     */
    public String getString() {
        return String.valueOf(value);
    }

    /**
     * 配置默认大小
     *
     * @param pageInt
     * @param pageSize
     */
    public static void setConfig(int pageInt, int pageSize) {
        PAGE_INIT = pageInt;
        PAGE_SIZE = pageSize;
    }
}
