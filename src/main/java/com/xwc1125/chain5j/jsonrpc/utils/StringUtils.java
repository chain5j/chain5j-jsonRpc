package com.xwc1125.chain5j.jsonrpc.utils;

/**
 * Description: <br>
 *
 * @author xwc1125 <br>
 * @Copyright: Copyright (c) 2019 <br>
 * @date 2019-01-04  20:17 <br>
 */
public class StringUtils {
    /**
     * @param @param  str
     * @param @return
     * @return Boolean
     * @Title isNotEmpty
     * @Description 是否全部不为空
     * @author xwc1125
     * @date 2016年4月1日 上午9:25:28
     */
    public static Boolean isNotEmpty(String... str) {
        if (str == null) {
            return false;
        }
        for (String s : str) {
            if (s == null || s.length() == 0 || s.trim().length() == 0 || s.equals("null") || s.equals("")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param @param  ss
     * @param @return
     * @return boolean
     * @Title isEmpty
     * @Description TODO(describe the methods)
     * @author xwc1125
     * @date 2016年1月22日 下午2:36:09
     */
    public static boolean isEmpty(String... ss) {
        if (ss == null) {
            return true;
        }
        for (String s : ss) {
            if (s == null || s.length() == 0 || s.trim().length() == 0 || s.equals("null") || s.equals("")) {
                return true;
            }
        }
        return false;
    }
}
