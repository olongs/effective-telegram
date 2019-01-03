package com.test.mapper;

import com.test.model.GoodInfo;
import com.test.model.GoodType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: GoodMapper
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 9:09
 */
@Mapper
public interface GoodMapper {
    List<GoodInfo> findGoods(String qname);
    GoodInfo findGoodById(@Param("id") Integer id);
    void saveGoods(GoodInfo goodInfo);
    void updateGood(GoodInfo goodInfo);
    void deleteGoodById(Integer id);
    void saveZhong(@Param("gid") Integer gid, @Param("tid") Integer tid);
    void deleteZhong(@Param("gid") Integer id);
    List<GoodType> getTypeList();
}
