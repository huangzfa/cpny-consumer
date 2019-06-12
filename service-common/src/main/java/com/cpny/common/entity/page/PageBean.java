package com.cpny.common.entity.page;

import com.cpny.common.entity.ReqParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 8470697978259453214L;

    private int currentPage; // 当前页
    private int pageSize; // 每页显示多少条

    private int totalCount; // 总记录数
    private List<T> recordList =new  ArrayList<T>(0); // 本页的数据列表

    private int totalPage; // 总页数

    public PageBean() {
    }

    /**
     * @param currentPage
     * @param pageSize
     * @param totalCount
     * @param recordList
     */
    public PageBean(int currentPage, int pageSize, int totalCount,
                    List<T> recordList) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.recordList = recordList;

        // 计算总页码
        totalPage = 1;
        if(pageSize != 0){
            totalPage = (totalCount + pageSize - 1) / pageSize;
        }


    }

    /**
     * 设置mybatis的分页插件参数
     * @param reqParam
     */
    public static void setPageInfo(ReqParam reqParam) {
    	PageHelper.startPage(reqParam.getCurPage(), reqParam.getPageSize(), true, false, false);//设置分页参数
    }

    /**
	 * 封装PageBean对象
	 * @param list
	 */
	public static <T> PageBean<T> getPageInfo(List<T> list){
		//获取查询结果
		PageInfo<T> pageInfo = new PageInfo<T>(list);

//		System.out.println(pageInfo.getTotal());//总条数
//		System.out.println(pageInfo.getPageSize());//每页条数
//		System.out.println(pageInfo.getPageNum());//当前页
//		System.out.println(pageInfo.getPages());//总页数

		return new PageBean<T>(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), list);
	}

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getpageSize() {
        return pageSize;
    }

    public void setpageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
