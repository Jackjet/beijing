package com.ysbd.beijing.directorDesktop.utils;

import android.content.ContentValues;

import com.ysbd.beijing.directorDesktop.bean.CatalogBean;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcjing on 2018/7/26.
 */

public class CatalogUtils {




    public static List<CatalogBean> getCatalogs2() {
        List<CatalogBean> catalogBeans = new ArrayList<>();
        catalogBeans.add(new CatalogBean("财政收入数据", "财政收入数据", 8));
        catalogBeans.add(new CatalogBean("财政支出数据", "财政支出数据", 8));
        catalogBeans.add(new CatalogBean("财政改革进展", "财政改革进展", 6));
        catalogBeans.add(new CatalogBean("财政专题工作", "财政专题工作", 6));
        catalogBeans.add(new CatalogBean("其他数据", "其他数据", 4));
        return catalogBeans;
    }

    public static List<CatalogBean> getCatalogs3() {
        List<CatalogBean> catalogBeans = new ArrayList<>();
        catalogBeans.add(new CatalogBean("财政资金管理", "财政资金管理", 8));
        catalogBeans.add(new CatalogBean("财政重点专题工作", "财政重点专题工作", 8));
        catalogBeans.add(new CatalogBean("国有资产管理", "国有资产管理", 6));
        catalogBeans.add(new CatalogBean("会计、资产评估行业管理", "会计、资产评估行业管理", 6));
        catalogBeans.add(new CatalogBean("其他数据", "其他数据", 4));
        return catalogBeans;
    }

    public static List<CatalogBean> getCatalogs4() {
        List<CatalogBean> catalogBeans = new ArrayList<>();
        catalogBeans.add(new CatalogBean("财政重要数据", "财政重要数据", 6));
        catalogBeans.add(new CatalogBean("财政资金管理", "财政资金管理", 6));
        catalogBeans.add(new CatalogBean("国有资产管理", "国有资产管理", 6));
        catalogBeans.add(new CatalogBean("会计、资产评估行业管理", "会计、资产评估行业管理", 6));
        catalogBeans.add(new CatalogBean("财政重点专题工作", "财政重点专题工作", 4));
        catalogBeans.add(new CatalogBean("其他", "其他", 4));
        return catalogBeans;
    }


    public static List<CatalogBean> getCatalogsFromFile() {
        File dir = new File("/storage/emulated/0/bjczjdesk/desk");
        List<CatalogBean> catalogBeans = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files!=null) {
            files=FileQueryUtil.order(files);
        }
        if (files==null) {
            return catalogBeans;
        }
        for (int i = 0; i < files.length; i++) {
            int index=files[i].getName().lastIndexOf('.');
            String name;
            if (index>0) {
                name =files[i].getName().substring(0,index);
            }else {
                name=files[i].getName();
            }
            catalogBeans.add(new CatalogBean(name,name,6));
        }
        return catalogBeans;
    }



    public static CatalogBean getCatalog(String name){
        List<CatalogBean> catalogBeans= DataSupport.select("name", "catalog", "size", "index").where("name = ?",  name ).find(CatalogBean.class);
        if (catalogBeans.size()>0) {
            return catalogBeans.get(0);
        }
        return null;
    }

    public static void upData(CatalogBean catalogBean){
        ContentValues values = new ContentValues();
        values.put("index", catalogBean.getIndex());
        values.put("size", catalogBean.getSize());
        DataSupport.updateAll(CatalogBean.class, values, "name = ?",catalogBean.getName());
    }



}
