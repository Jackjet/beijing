package com.ysbd.beijing.directorDesktop.utils;

import com.ysbd.beijing.directorDesktop.bean.AttachmentBean;
import com.ysbd.beijing.directorDesktop.bean.Desk2Bean;
import com.ysbd.beijing.utils.FileUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcjing on 2018/7/23.
 */

public class FileQueryUtil {

    public static void getAllFile(File dir, String catalog) {

        try {
            // 列出所有文件
            File[] files = dir.listFiles();
            // 将所有文件存入list中
            if (files != null) {
                int count = files.length;// 文件个数
                AttachmentBean attachmentBean;
                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    attachmentBean = new AttachmentBean();
                    attachmentBean.setFile(file.isDirectory());
                    attachmentBean.setName(file.getName());
                    attachmentBean.setRout(file.getAbsolutePath());
                    attachmentBean.setParent(dir.getAbsolutePath());
                    attachmentBean.setCatalog(catalog);
                    if (!attachmentBean.isFile()) {
                        String type = FileUtils.getInstance().getExtension(file.getName());
                        attachmentBean.setExtension(type);
                        attachmentBean.save();
                    } else {
                        attachmentBean.setExtension("dir");
                        attachmentBean.save();
                        getAllFile(file, catalog);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Map<String, Desk2Bean>> getCatalogFile(File dir, int size) {
        List<Map<String, Desk2Bean>> desk2Beans = new ArrayList<>();
        try {
            // 列出所有文件
            File[] files = dir.listFiles();
            boolean order=false;
            // 将所有文件存入list中
            if (files != null) {
                int count = files.length;// 文件个数
                Map<String, Desk2Bean> map = new HashMap<>();
                boolean hasMore = false;
                if (size > 0 && count > size) {
                    count = size;
                    hasMore = true;
                }
                if (count > 0) {
                    try {
                        int a = Integer.parseInt(files[0].getName().substring(0, 2));
                        if (a > -1) {
                            files = order(files);
                            order=true;
                        } else {
                            files = order1(files);
                            order=false;
                        }
                    } catch (Exception e) {
                        files = order1(files);
                        order=false;
                    }

                }

                for (int i = 0; i < count; i++) {
                    File file = files[i];
                    if (i % 2 == 0) {
                        map = new HashMap<>();
                        map.put("1", new Desk2Bean(file.getName(),
                                file.getAbsolutePath(), file.isDirectory(), false, FileUtils.getInstance().getExtension(file.getName()),order));
                        if (i == count - 1) {
                            desk2Beans.add(map);
                        }
                    } else {
                        map.put("2", new Desk2Bean(file.getName(),
                                file.getAbsolutePath(), file.isDirectory(), false, FileUtils.getInstance().getExtension(file.getName()),order));
                        desk2Beans.add(map);
                    }
                }
                if (desk2Beans.size() > 0) {
                    desk2Beans.get(0).get("1").setHasMore(hasMore);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return desk2Beans;
    }


//    //文件名顺序
//    public static File[] order(File[] files) {
//
//        File[] newFiles = new File[files.length];
//        List<Ord> ords = new ArrayList<>();
//        try {
//
//            for (int i = 0; i < files.length; i++) {
//                String a = files[i].getName().substring(0, 2);
//                int order = Integer.parseInt(a);
//                ords.add(new Ord(order, i));
//            }
//
//            for (int i = 0; i < ords.size(); i++) {
//                for (int j = i; j < ords.size(); j++) {
//                    if (ords.get(j).ord < ords.get(i).ord) {
//                        Collections.swap(ords, i, j);
//                    }
//                }
//            }
//            for (int i = 0; i < ords.size(); i++) {
//                newFiles[i] = files[ords.get(i).index];
//            }
//
//        } catch (Exception e) {
//            return files;
//        }
//
//        return newFiles;
//
//    }

    //文件名顺序
    public static File[] order(File[] files) {

        File[] newFiles = new File[files.length];
        List<Ord> ords = new ArrayList<>();
        try {

            for (int i = 0; i < files.length; i++) {
                String a = files[i].getName().substring(0, 2);
                int order = Integer.parseInt(a);
                ords.add(new Ord(order, i));
            }

            for (int i = 0; i < ords.size()-1; i++) {
                for (int j = i+1; j < ords.size(); j++) {
                    if (ords.get(j).ord < ords.get(i).ord) {
                        Collections.swap(ords, i, j);
                    }
                }
            }
            for (int i = 0; i < ords.size(); i++) {
                newFiles[i] = files[ords.get(i).index];
            }

        } catch (Exception e) {
            return files;
        }

        return newFiles;

    }

    //倒叙
    private static File[] order1(File[] files) {
        File[] newFiles = new File[files.length];
        for (int i = 0; i < files.length; i++) {
            newFiles[i] = files[files.length - i - 1];
        }
        return newFiles;
    }

    public static class Ord {
        private int ord;
        private int index;

        public int getOrd() {
            return ord;
        }

        public void setOrd(int ord) {
            this.ord = ord;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Ord(int ord, int index) {
            this.ord = ord;
            this.index = index;
        }
    }

//
//    public static List<CatalogBean> getCatalogs() {
//        List<CatalogBean> catalogBeans = new ArrayList<>();
//        catalogBeans.add(new CatalogBean("收入", "收入"));
//        catalogBeans.add(new CatalogBean("支出", "支出"));
//        return catalogBeans;
//    }
//
//    public static List<CatalogBean> getCatalogs2() {
//        List<CatalogBean> catalogBeans = new ArrayList<>();
//        catalogBeans.add(new CatalogBean("财政收入数据", "财政收入数据", 8));
//        catalogBeans.add(new CatalogBean("财政支出数据", "财政支出数据", 8));
//        catalogBeans.add(new CatalogBean("财政改革进展", "财政改革进展", 6));
//        catalogBeans.add(new CatalogBean("财政专题工作", "财政专题工作", 6));
//        catalogBeans.add(new CatalogBean("其他数据", "其他数据", 4));
//        return catalogBeans;
//    }
//
//    public static List<CatalogBean> getCatalogs3() {
//        List<CatalogBean> catalogBeans = new ArrayList<>();
//        catalogBeans.add(new CatalogBean("财政资金管理", "财政资金管理", 8));
//        catalogBeans.add(new CatalogBean("财政重点专题工作", "财政重点专题工作", 8));
//        catalogBeans.add(new CatalogBean("国有资产管理", "国有资产管理", 6));
//        catalogBeans.add(new CatalogBean("会计、资产评估行业管理", "会计、资产评估行业管理", 6));
//        catalogBeans.add(new CatalogBean("其他数据", "其他数据", 4));
//        return catalogBeans;
//    }
//
//    public static List<CatalogBean> getCatalogsFromFile() {
//        File dir = new File("/storage/emulated/0/bjczjdesk/desk");
//        List<CatalogBean> catalogBeans = new ArrayList<>();
//        File[] files = dir.listFiles();
//        for (int i = 0; i < files.length; i++) {
//            int index=files[i].getName().lastIndexOf('.');
//            String name;
//            if (index>0) {
//                name =files[i].getName().substring(0,index);
//            }else {
//                name=files[i].getName();
//            }
//            catalogBeans.add(new CatalogBean(name,name,6));
//        }
//        return catalogBeans;
//    }

    public static String getCatalogId(String name) {
        switch (name) {
            case "收入":
                return "收入";
            case "支出":
                return "支出";
        }
        return "";
    }


    public static List<AttachmentBean> getFiles(String name) {
        List<AttachmentBean> attachments = DataSupport.select("extension", "file", "rout", "fileName", "name").where("name like ?", "%" + name + "%").find(AttachmentBean.class);
        return attachments;
    }

    public static List<AttachmentBean> getFiles(String name, boolean includeDir) {
        List<AttachmentBean> attachments;
        attachments = DataSupport.select("parent", "catalog", "extension", "file", "rout", "fileName", "name").where("name like ?", "%" + name + "%").find(AttachmentBean.class);
        if (!includeDir) {
            List<AttachmentBean> dirs = new ArrayList<>();
            for (AttachmentBean attachmentBean : attachments) {
                if (attachmentBean.isFile()) {
                    dirs.add(attachmentBean);
                }
            }
            attachments.removeAll(dirs);
        }
        return attachments;
    }


    /**
     * @param name
     * @param includeDir 是否包含文件夹
     * @param catalog
     * @return
     */
    public static List<AttachmentBean> getFiles(String name, boolean includeDir, String catalog) {
        List<AttachmentBean> attachments;
        attachments = DataSupport.select("parent", "catalog", "extension", "file", "rout", "fileName", "name").where("name like ?", "%" + name + "%").find(AttachmentBean.class);
        if (!includeDir) {
            List<AttachmentBean> dirs = new ArrayList<>();
            for (AttachmentBean attachmentBean : attachments) {
                if (attachmentBean.isFile() || !attachmentBean.getCatalog().equals(catalog)) {
                    dirs.add(attachmentBean);
                }
            }
            attachments.removeAll(dirs);
        }
        return attachments;
    }

    public static List<AttachmentBean> getFile(String name, boolean includeDir) {
        List<AttachmentBean> attachments;
        attachments = DataSupport.select("catalog", "extension", "file", "rout", "fileName", "name").where("name like ?;file", "%" + name + "%;").find(AttachmentBean.class);
        if (!includeDir) {
            List<AttachmentBean> dirs = new ArrayList<>();
            for (AttachmentBean attachmentBean : attachments) {
                if (attachmentBean.isFile()) {
                    dirs.add(attachmentBean);
                }
            }
            attachments.removeAll(dirs);
        }
        return attachments;
    }
}
