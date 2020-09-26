package com.test.jlq.controller;

import com.test.jlq.exception.ParseException;
import com.test.jlq.model.Response;
import com.test.jlq.model.UserInfo;
import com.test.jlq.repository.UserInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hao
 * @Date 2020/9/22
 */
@Controller
public class QueryController {

    private Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }


    /**
     *
     * @param type:查询类型,年龄、里程、时间
     * @param condition:查询条件,格式:18-20;21-30;...
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/api/query", method = RequestMethod.GET)
    public Response<Map<String, Object>> queryInfo(String type, String condition) {
        try {
            List<UserInfo> userInfoList = userInfoMapper.selectAll();
            if (userInfoList.isEmpty()) {
                return new Response<>(6, "数据缺失", null);
            }
           List<UserInfo> retList = groupBy(userInfoList, condition, type);
            if (retList.isEmpty()) {
                return new Response<>(404, "暂无数据", null);
            }
            Map<String, Object> retMap = new HashMap<>(16);
            retMap.put("table", retList);
            Map<String, Long> chartMap = retList.stream().collect(Collectors.groupingBy(UserInfo::getKey, Collectors.counting()));
            retMap.put("chart", sortMap(chartMap));
            return new Response<>(0, "查询成功", retMap);
        } catch (ParseException e) {
            return new Response<>(6, e.getMessage(), null);
        } catch (RuntimeException e) {
            return new Response<>(6, "服务异常", null);
        }
    }

    private List<UserInfo> groupBy(List<UserInfo> list, String condition, String type) throws ParseException {
        String[] arr = condition.split(";");
        List<UserInfo> retList = new ArrayList<>(10);
        for (String s : arr) {
            String[] tmp = s.split("-");
            if (tmp.length != 2) {throw new ParseException("数据格式异常");}
            String begin = tmp[0];
            String end = tmp[1];
            if (!StringUtils.isNumeric(begin) || !StringUtils.isNumeric(end)) {throw new ParseException("数据类型异常");}
            if (Long.parseLong(begin) > Long.parseLong(end)) {throw new ParseException("数据范围异常");}
            //这里使用groupBy一步到位或者是使用filter多次collector
//            list.stream().collect(Collectors.groupingBy());\
            List<UserInfo> collect;
            final String key = begin + " ~ " + end;
            if ("age".equals(type)) {
                final int year = Calendar.getInstance().get(Calendar.YEAR);
                collect = list.stream().filter(t -> (year - t.getBirthyear()) >= Integer.parseInt(begin) && (year - t.getBirthyear()) <= Integer.parseInt(end)).peek(e -> e.setKey(key)).collect(Collectors.toList());
            } else if ("length".equals(type)) {
                collect = list.stream().filter(t -> t.getTotalTravelLength() >= Long.parseLong(begin) && t.getTotalTravelLength() <= Long.parseLong(end)).peek(e -> e.setKey(key)).collect(Collectors.toList());
            } else if ("time".equals(type)) {
                collect = list.stream().filter(t -> t.getTotalTravelTime() >= Long.parseLong(begin) && t.getTotalTravelTime() <= Long.parseLong(end)).peek(e -> e.setKey(key)).collect(Collectors.toList());
            } else {
                throw new ParseException("查询类型异常");
            }
            if (collect.isEmpty()) {continue;}
            retList.addAll(collect);
        }
        return retList;
    }

    private Map<String, Long> sortMap(Map<String, Long> map) {
        List<String> sortedKey = map.keySet().stream().sorted((a, b) -> Long.valueOf(a.split("~")[0].trim()).compareTo(Long.valueOf(b.split("~")[0].trim()))).collect(Collectors.toList());
        Map<String, Long> sortedMap = new TreeMap<>();
        sortedKey.forEach(t -> sortedMap.put(t, map.get(t)));
        return sortedMap;
    }

}
