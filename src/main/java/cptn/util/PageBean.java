package cptn.util;

import java.util.List;

/**
 * 用于翻页的基本类
 * @param <T>
 */
public class PageBean<T> {
    // 当前页
    private int currentPage = 1;
    // 每页显示的总条数
    private int pageSize = 10;
    // 总条数
    private int totalNum;
    // 是否有下一页
    private int isMore;
    // 总页数
    private int totalPage;
    // 开始索引
    private int startIndex;
    // 分页结果
    private List<T> items;

    public PageBean() {
        super();
    }

    public PageBean(int currentPage, int pageSize, int totalNum) {
        super();
        this.setCurrentPage(currentPage);
        this.setPageSize(pageSize);
        this.totalNum = totalNum;
        this.totalPage = (this.totalNum + this.pageSize - 1) / this.pageSize;
        this.startIndex = (this.currentPage - 1) * this.pageSize;
        this.isMore = this.currentPage >= this.totalPage ? 0 : 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = (currentPage > 1 ? currentPage : 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize > 1 ? pageSize : 1);
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getIsMore() {
        return isMore;
    }

    public void setIsMore(int isMore) {
        this.isMore = isMore;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
