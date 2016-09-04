package net1.netutils;


import net1.interfaces.Page;

/**
 * @Author 冯高远
 * @Date 2016/7/29 15:04
 * @Description
 */
public class PageRequest extends BasicRequest {
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
