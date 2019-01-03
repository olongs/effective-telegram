package com.test.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolrUtil {
    @Autowired
    private SolrClient client;

    public void addMap(Map<String,Object> map)
    {
		if (map.get("id") == null) {
            System.err.println("solr端添加数据时，map集合中没有“id”键！");
        }
        try
        {
            SolrInputDocument doc = new SolrInputDocument();
            for(String key:map.keySet())
            {
                Object val = map.get(key);
                if(val!=null){
                    doc.addField(key, val);
                }
            }
            client.add(doc);
            UpdateResponse resp = client.commit();
            System.out.println(resp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateMap(Map<String,Object> map)
    {
        try
        {
            Set<String> keySet = map.keySet();
            if(!keySet.contains("id"))
                throw new Exception("更新的Map必须包含id键");
            Object id = map.get("id");
            SolrInputDocument doc = new SolrInputDocument();
            for(String key:keySet)
            {
                if(!key.equals("id"))
                {
                    Object obj = map.get(key);
                    Map oper = new HashMap();
                    oper.put("set", obj);
                    doc.addField(key, oper);
                }
            }
            doc.setField("id", id);
            client.add(doc);
            UpdateResponse resp = client.commit();
            System.out.println(resp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteDoc(String id)
    {
        try
        {
            client.deleteById(id);
            UpdateResponse resp = client.commit();
            System.out.println(resp);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public <T> ResultInfo<T> query(Class cls,String query,Integer starts,
                    Integer rows,boolean isHl,List<String> hlFlds,String sort)
    {
        ResultInfo<T> result = new ResultInfo<T>();
        List<T> rtns = new ArrayList<T>();
        result.setList(rtns);
        try
        {
            SolrQuery q = new SolrQuery();
            q.set("q",query);
            q.set("fl", "*");
            if(sort != null && !"".equals(sort))
            {
                String[] dim = sort.split(",");
                for(String s:dim)
                {
                    String[] dim2 = s.split(" ");
                    if("ASC".equalsIgnoreCase(dim2[1]))
                        q.setSort(dim2[0], ORDER.asc);
                    else
                        q.setSort(dim2[0], ORDER.desc);
                }
            }
            q.setStart(starts);
            q.setRows(rows);
            if(isHl)
            {
                q.setHighlight(true);
                String hlfl = "";
                for(String fname:hlFlds)
                        hlfl = hlfl + fname + ",";
                if(hlfl.endsWith(","))
                        hlfl = hlfl.substring(0, hlfl.length()-1);
                q.set("hl.fl",hlfl);
                q.setHighlightSimplePre("<font color='red'><b>");
                q.setHighlightSimplePost("</b></font>");
            }
            QueryResponse qresp = client.query(q);
            Map<String,Map<String,List<String>>> hlObj = qresp.getHighlighting();
            SolrDocumentList slist = qresp.getResults();
            Long total = slist.getNumFound();
            result.setTotal(total);
            //从Solr端获取Document集合
            for(SolrDocument doc:slist)
            {
                String id = (String)doc.getFieldValue("id");
                Map<String,List<String>> hlObj2 = null;
                if(hlObj != null)
                        hlObj2 = hlObj.get(id);
                T t = (T)cls.newInstance();
                Field[] flds = t.getClass().getDeclaredFields();
                for(Field f:flds)
                {
                    String fname = f.getName();
                    Class fcls = f.getType();
                    List<String> hlObj3 = null;
                    if(hlObj2 != null)
                            hlObj3 = hlObj2.get(fname);
                    String hlObj4 = processStr(hlObj3);
                    //字符串属性
                    if(fcls == String.class)
                    {
                        Object fvalue = doc.getFieldValue(fname);
                        String fvalue2 = processStr(fvalue);
                        System.out.println(fname+",hlObj3="+hlObj3);
                        if(hlObj3 != null)
                            BeanUtils.setProperty(t, fname, hlObj4);
                        else
                            BeanUtils.setProperty(t, fname, fvalue2);
                    }
                    if(fcls == Integer.class)
                    {
                        Object fvalue = doc.getFieldValue(fname);
                        String fvalue2 = processStr(fvalue);
                        try
                        {
                            Integer fvalue3 = new Integer(fvalue2);
                            BeanUtils.setProperty(t, fname, fvalue3);
                        }
                        catch(Exception ig){}
                    }
                    if(fcls == Float.class)
                    {
                        Object fvalue = doc.getFieldValue(fname);
                        String fvalue2 = processStr(fvalue);
                        try
                        {
                            Float fvalue3 = new Float(fvalue2);
                            BeanUtils.setProperty(t, fname, fvalue3);
                        }
                        catch(Exception ig){}
                    }
                    if(fcls == Double.class)
                    {
                        Object fvalue = doc.getFieldValue(fname);
                        String fvalue2 = processStr(fvalue);
                        try
                        {
                            Double fvalue3 = new Double(fvalue2);
                            BeanUtils.setProperty(t, fname, fvalue3);
                        }
                        catch(Exception ig){}
                    }
                    if(fcls == java.sql.Date.class)
                    {
                        Object fvalue = doc.getFieldValue(fname);
                        String fvalue2 = processStr(fvalue);
                        try
                        {
                            java.util.Date fvalue3 = new java.util.Date(fvalue2);
                            java.sql.Date fvalue4 = new java.sql.Date(fvalue3.getTime());
                            BeanUtils.setProperty(t, fname, fvalue4);
                        }
                        catch(Exception ig){}
                    }

                }
                rtns.add(t);
            }
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
        return result;
    }

    private String processStr(Object obj)
    {
        if(obj == null)
            return "";
        String str = obj.toString();
        if(str.startsWith("["))
            str = str.substring(1,str.length());
        if(str.endsWith("]"))
            str = str.substring(0,str.length()-1);
        return str;
    }
}