package com.mcxtzhang.indexlib.IndexBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PolyphoneUtil {

    public static boolean isPolyphone(String name) {
        List<String> names = new ArrayList<>();
        names.add("解");
        names.add("仇");
        names.add("查");
        names.add("盖");
        names.add("区");
        names.add("逄");
        names.add("种");
        names.add("缪");
        names.add("朴");
        names.add("便");
        names.add("覃");
        names.add("单");
        names.add("折");
        names.add("参");
        names.add("牟");
        names.add("不");
        names.add("曾");
        names.add("长");
        names.add("员");
        names.add("乘");
        names.add("乜");
        names.add("会");
        names.add("句");
        names.add("召");
        names.add("宓");
        names.add("弗");
        names.add("洗");
        names.add("祭");
        names.add("秘");
        names.add("繁");
        names.add("能");
        names.add("蕃");
        names.add("谌");
        names.add("适");
        names.add("都");
        names.add("阿");
        names.add("难");
        names.add("黑");
        return names.contains(name);
    }

    public static String getPinYin(String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("解", "XIE");
        map.put("仇", "QIU");
        map.put("查", "ZHA");
        map.put("盖", "GE");
        map.put("区", "OU");
        map.put("逄", "PANG");
        map.put("种", "CHONG");
        map.put("缪", "MIAO");
        map.put("朴", "PIAO");
        map.put("便", "PIAN");
        map.put("覃", "TAN");
        map.put("单", "SHAN");
        map.put("折", "SHE");
        map.put("参", "SHEN");
        map.put("牟", "MOU");
        map.put("不", "FOU");
        map.put("曾", "ZENG");
        map.put("长", "ZHANG");
        map.put("员", "YUN");
        map.put("乘", "SHENG");
        map.put("乜", "NIE");
        map.put("会", "GUI");
        map.put("句", "GOU");
        map.put("召", "SHAO");
        map.put("宓", "FU");
        map.put("弗", "FEI");
        map.put("洗", "XIAN");
        map.put("祭", "ZHAI");
        map.put("秘", "BI");
        map.put("繁", "PO");
        map.put("能", "NAI");
        map.put("蕃", "PI");
        map.put("谌", "SHAN");
        map.put("适", "KUO");
        map.put("都", "DU");
        map.put("阿", "E");
        map.put("难", "NING");
        map.put("黑", "HE");

        return map.get(name);
    }
}
