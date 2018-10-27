package com.ysbd.beijing.directorDesktop.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by lcjing on 2018/7/24.
 */

public class CatalogBean extends DataSupport {
    private String name;
    private String catalog;
    private int size=-1;
    private int index;

    public CatalogBean() {
    }

    public CatalogBean(String name, String catalog) {
        this.name = name;
        this.catalog = catalog;
    }

    public CatalogBean(String name, String catalog, int size) {
        this.name = name;
        this.catalog = catalog;
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
