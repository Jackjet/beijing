package com.ysbd.beijing.directorDesktop.bean;

/**
 * Created by lcjing on 2018/7/24.
 */

public class Desk2Bean {
    private String name;
    private String route;
    private boolean isDir;//文件夹
    private boolean isParent;//在根目录
    private boolean hasMore;//在根目录中是否还有更多子项
    private boolean order;// 文件名称是否包含顺序
    private String ex;//扩展名


    public Desk2Bean(String name, String route, boolean isDir, boolean isParent, String ex) {
        this.name = name;
        this.route = route;
        this.isDir = isDir;
        this.isParent = isParent;
        this.ex = ex;
    }

    public Desk2Bean(String name, String route, boolean isDir, boolean isParent, String ex, boolean order) {
        this.name = name;
        this.route = route;
        this.isDir = isDir;
        this.isParent = isParent;
        this.order = order;
        this.ex = ex;
    }

    public Desk2Bean() {
    }

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {

        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }
}
