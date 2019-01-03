package com.test.service;

import com.test.model.GoodInfo;
import com.test.model.GoodType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: GoodService
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 9:16
 */
public interface GoodService {
//    void updateGood(GoodInfo goodInfo);
    List<GoodInfo> findGoods(String qname);
    void saveZhong(Integer gid, Integer tid);
    void saveGoods(GoodInfo goodInfo);
    GoodInfo findGoodById(Integer id);
    List<GoodType> getTypeList();

    void deleteGoodById(Integer id);
}
