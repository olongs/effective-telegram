package com.test.util;

public class PageUtil {
    private Integer page = 1;//默认显示第一页
    private Integer rows = 4;//每页显示记录数
    private Long total = null;//总行数
    private String url = null;//点击页码跳转url

    /**
     * 
     * @param url 点击页码跳转url
     * @param page 当前页码
     * @param rows 每页显示记录数
     * @param total 总行数
     */
    public PageUtil(String url,Integer page,Integer rows,Long total)
    {
        this.url = url;
        this.page = page;
        this.rows = rows;
        this.total = total;
    }

    /**
     * 根据CSS样式定义生成HTML代码片段
     * @return
     */
    public String toHtml()
    {
        StringBuffer sb = new StringBuffer();
        //计算总页数
        int pages = 0;
        if(total % rows == 0)
            pages = total.intValue() / rows;
        else
            pages = (total.intValue() / rows) + 1;
        //定义分页工具条DIV层
        sb.append("<div id='pagediv'>\r\n");
        String firstUrl = null;
        //判断URL是否带有参数，如果带有参数使用&连接分页参数
        //否则使用?连接参数
        if(url.indexOf("?")>0)
            firstUrl = url + "&page=1&rows="+rows;
        else
            firstUrl = url + "?page=1&rows="+rows;
        sb.append("<a href='"+firstUrl+"'>首页</a>\r\n");
        //定义上一页连接
        String backUrl = null;
        if(url.indexOf("?")>0)
            backUrl = url + "&page="+(page==1?1:(page-1))+"&rows="+rows;
        else
            backUrl = url + "?page="+(page==1?1:(page-1))+"&rows="+rows;
        sb.append("<a href='"+backUrl+"'>上一页</a>\r\n");
        //显示页面超链接
        for(int i=1;i<=pages;i++)
        {
            String pageUrl = null;
            if(url.indexOf("?")>0)
                pageUrl = url + "&page="+i+"&rows="+rows;
            else
                pageUrl = url + "?page="+i+"&rows="+rows;
            if(i == page)
                sb.append("<a href='"+pageUrl+"'><b><font color='red'>"+i+"</font></b></a>\r\n");
            else
                sb.append("<a href='"+pageUrl+"'>"+i+"</a>\r\n");
        }
        //定义下一页连接
        String nextUrl = null;
        if(url.indexOf("?")>0)
            nextUrl = url + "&page="+(page==pages?pages:(page+1))+"&rows="+rows;
        else
            nextUrl = url + "?page="+(page==pages?pages:(page+1))+"&rows="+rows;
        sb.append("<a href='"+nextUrl+"'>下一页</a>\r\n");
        //定义尾页连接
        String lastUrl = null;
        if(url.indexOf("?")>0)
            lastUrl = url + "&page="+pages+"&rows="+rows;
        else
            lastUrl = url + "?page="+pages+"&rows="+rows;
        sb.append("<a href='"+lastUrl+"'>尾页</a>\r\n");
        //定义其他显示信息
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;第"+page+"/"+pages+"页\r\n");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;共"+total+"条记录\r\n");
        sb.append("</div>\r\n");
        return sb.toString();
    }
}