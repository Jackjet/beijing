package com.ysbd.beijing.directorDesktop.utils;

import android.os.Environment;

import com.ysbd.beijing.directorDesktop.bean.CatalogBean;
import com.ysbd.beijing.directorDesktop.bean.Desk2Bean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcjing on 2018/7/24.
 */

public class Dbutil {

    public static List<Map<String, Desk2Bean>> getDesk2Bean() {
        List<Map<String, Desk2Bean>> desk2Beans = new ArrayList<>();

        Map<String, Desk2Bean> map = new HashMap<>();
        map.put("1", new Desk2Bean("财政收入数据",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据", true, true, ""));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("属地税收",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/属地税收", true, false, ""));
        map.put("2", new Desk2Bean("四本预算收入",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/四本预算收入.xlsx", false, false, "xlsx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("主要财源情况",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/主要财源情况", true, false, ""));
        map.put("2", new Desk2Bean("土地收入",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/土地收入.docx", false, false, "docx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("债务收入",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/债务收入.docx", false, false, "docx"));
        map.put("2", new Desk2Bean("非税收入",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/非税收入.docx", false, false, "docx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("中央对地方转移性收入",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/中央对地方转移性收入.docx", false, false, "docx"));
        map.put("2", new Desk2Bean("财力情况、人均财力",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/财力情况、人均财力.xlsx", false, false, "xlsx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("政府人均收入",
                "/storage/emulated/0/bjczjdesk/desk/财政收入数据/政府人均收入.docx", false, false, "docx"));
        desk2Beans.add(map);

        map = new HashMap<>();
        map.put("1", new Desk2Bean("财政支出数据",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据", true, true, ""));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("保障结构",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/保障结构.docx", false, false, "docx"));
        map.put("2", new Desk2Bean("五大领域支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/五大领域支出.xlsx", false, false, "xlsx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("基本建设支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/基本建设支出.docx", false, false, "docx"));
        map.put("2", new Desk2Bean("土储支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/土储支出.docx", false, false, "docx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("补贴支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/补贴支出.docx", false, false, "docx"));
        map.put("2", new Desk2Bean("重点科目支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/重点科目支出.xlsx", false, false, "xlsx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("首都功能定位支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/首都功能定位支出.xlsx", false, false, "xlsx"));
        map.put("2", new Desk2Bean("重点大事支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/重点大事支出.xlsx", false, false, "xlsx"));
        desk2Beans.add(map);
        map = new HashMap<>();
        map.put("1", new Desk2Bean("社会保险基金支出",
                "/storage/emulated/0/bjczjdesk/desk/财政支出数据/社会保险基金支出.docx", false, false, "docx"));
        desk2Beans.add(map);

        return desk2Beans;
    }

    public static List<Map<String, Desk2Bean>> getDeskBeans() {
        List<Map<String, Desk2Bean>> desk2Beans = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + File.separator + "bjczjdesk" + File.separator + "desk";
        List<CatalogBean> catalogBeans = CatalogUtils.getCatalogsFromFile();
        Map<String, Desk2Bean> map;
        for (int i = 0; i < catalogBeans.size(); i++) {
            String dir = path + File.separator + catalogBeans.get(i).getName();
            map = new HashMap<>();
            map.put("1", new Desk2Bean(catalogBeans.get(i).getName(),
                    "/storage/emulated/0/bjczjdesk/desk/" + catalogBeans.get(i).getName(), true, true, ""));
            List<Map<String, Desk2Bean>> list=FileQueryUtil.getCatalogFile(new File(dir),catalogBeans.get(i).getSize());

            if (list.size()>0) {
                map.get("1").setHasMore(list.get(0).get("1").isHasMore());
            }
            desk2Beans.add(map);
            desk2Beans.addAll(list);
        }

        return desk2Beans;
    }

    public static List<Map<String, Desk2Bean>> getDeskBeans(String name) {
        List<Map<String, Desk2Bean>> desk2Beans = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().toString() + File.separator + "bjczjdesk" + File.separator + "desk";
        Map<String, Desk2Bean> map;
        String dir = path + File.separator + name;
        map = new HashMap<>();
        map.put("1", new Desk2Bean(name,
                "/storage/emulated/0/bjczjdesk/desk/" + name, true, true, ""));
        desk2Beans.add(map);
        desk2Beans.addAll(FileQueryUtil.getCatalogFile(new File(dir),-1));
        return desk2Beans;
    }

    public static List<String> getFileOrder(String cate) {
        List<String> list = new ArrayList<>();
        switch (cate) {
            case "财政收入数据":
//                一、属地税收（中央与地方收入结构）
//                二、四本预算收入（全市收入）
//                三、主要财源情况（产业、行业、企业）
//                四、土地收入
//                五、债务收入
//                六、非税收入
//                七、中央对地方转移性收入
//                八、财力情况、人均财力
//                九、政府人均收入


                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                list.add("属地税收");
                return list;
            case "财政支出数据":
                return list;
            case "财政改革进展":
                return list;
            case "财政专题工作":
                return list;
            case "其他数据":
                return list;
        }
        return list;
    }

}
