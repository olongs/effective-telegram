package com.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.test.util.ResultInfo;

@Component
public class RedisUtil {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redis;

    /**
     * Redis String类型
     * @param key
     * @param obj
     */
    public void addObject(String key,Object obj)
    {
        redis.opsForValue().set(key, obj);
    }

    /**
     * Redis String类型
     * @param key
     * @param obj
     */
    public Object getObject(String key)
    {
        return redis.opsForValue().get(key);
    }

    /**
     * Redis List
     * @param key
     * @param lst
     */
    public void addList(String key,Object obj)
    {
        redis.opsForList().leftPush(key, obj);
    }

    /**
     * Redis List
     * @param key
     * @param lst
     */
    public void addList(String key,List lst)
    {
        redis.delete(key);
        for(Object obj:lst)
        {
            redis.opsForList().leftPush(key, obj);
        }
    }

    /**
     * Redis List
     * @param key
     * @param lst
     */
    public <T> T getListById(String key,String id)
    {
        try
        {
            List lst = getList(key);
            for(Object obj:lst)
            {
                T t = (T)obj;
                Object idObj = BeanUtils.getProperty(t, "id");
                if(id.equals(idObj.toString()))
                    return t;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T deleteListById(String key,String id)
    {
        try
        {
            T t = null;
            List lst = getList(key);
            for(Object obj:lst)
            {
                t = (T)obj;
                Object idObj = BeanUtils.getProperty(t, "id");
                if(id.equals(idObj.toString()))
                {
                    break;
                }
            }
            //重新添加Redis对象
            lst.remove(t);
            addList(key,lst);

            return t;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Redis List
     * @param key
     * @param lst
     */
    public List getList(String key)
    {
        Long size = redis.opsForList().size(key);
        return redis.opsForList().range(key, 0, size);
    }

    /**
     * Redis Map
     * @param key
     * @param map
     */
    public void addMap(String key,Map map)
    {
        redis.opsForHash().putAll(key, map);
    }

    /**
     * Redis Map
     * @param key
     * @param map
     */
    public Map getMap(String key)
    {
        return redis.opsForHash().entries(key);
    }

    /**
     * 需要POM引入依赖
     *   <dependency>
     *       <groupId>commons-beanutils</groupId>
     *       <artifactId>commons-beanutils-core</artifactId>
     *       <version>1.8.3</version>
     *   </dependency>
     * 
     * 
     * 通过Redis分页查询记录 <T>代表存储到Redis实例类型
     * @param redisKey Redis中定义的key，数据类型是List
     * @param name 查询名称
     * @param page 当前页
     * @param rows 每页显示记录数
     * @param clz 实体类对应的Class对象
     * @param name 实体类中用于查询比较的属性名称
     * @return
     */
    public <T> ResultInfo<T> findFromRedis(String redisKey,String query,
                    Integer page,Integer rows,
                    Class clz,String name)
    {
        ResultInfo rtn = new ResultInfo();
        List<T> filterRtn = new ArrayList<T>();
        try
        {
            List<T> allList = getList(redisKey);
            for(T t:allList)
            {
                String val = (String)BeanUtils.getProperty(t, name);
                if((query==null || "".equals(query)) || val.indexOf(query)>=0)
                {
                    filterRtn.add(t);
                }
            }
            Long total = (long)filterRtn.size();
            Integer starts = (page-1)*rows;
            List<T> pageList = new ArrayList<T>();
            for(int i=0;i<rows;i++)
            {
                int pos = starts+i;
                if(filterRtn.size()>pos)
                {
                    T t2 = filterRtn.get(pos);
                    pageList.add(t2);
                }
            }
            rtn.setTotal(total);
            rtn.setList(pageList);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return rtn;
    }
}
