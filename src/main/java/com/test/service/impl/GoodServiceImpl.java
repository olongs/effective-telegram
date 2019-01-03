package com.test.service.impl;

import com.test.mapper.GoodMapper;
import com.test.model.GoodInfo;
import com.test.model.GoodType;
import com.test.service.GoodService;
import com.test.util.IMSGpresccer;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: GoodServiceImpl
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 9:16
 */
@Service
public class GoodServiceImpl implements GoodService, IMSGpresccer {


    @Autowired
    private GoodMapper mapper;


    @Override
    public List<GoodInfo> findGoods(String qname) {
        return mapper.findGoods(qname);
    }

    @Override
    public void saveZhong(Integer gid, Integer tid) {
        mapper.saveZhong(gid, tid);
    }

    @Override
    public void saveGoods(GoodInfo goodInfo) {
        mapper.saveGoods(goodInfo);
    }

    @Override
    public GoodInfo findGoodById(Integer id) {
        return mapper.findGoodById(id);
    }

    @Override
    public List<GoodType> getTypeList() {
        return mapper.getTypeList();
    }

    @Override
    public void deleteGoodById(Integer id) {
        mapper.deleteGoodById(id);
    }

    @Override
    public boolean msgPresccer(Object object) {
        if (object instanceof GoodInfo) {
            GoodInfo goodInfo = (GoodInfo) object;
            System.out.println(goodInfo);
            Integer gid = goodInfo.getId();
            String tids = goodInfo.getTids();
            System.err.println(tids);
            String[] tidss = tids.split(",");
            if (gid == null) {
                this.saveGoods(goodInfo);
                Integer gid1 = goodInfo.getId();
                goodInfo = this.findGoodById(goodInfo.getId());
                for (String s : tidss) {
                    int tid = Integer.parseInt(s);
                    mapper.saveZhong(gid1, tid);
                }
            } else {
                mapper.deleteZhong(gid);
                mapper.updateGood(goodInfo);
                for (String s : tidss) {
                    if (s != null) {
                        try {
                            Integer tid = new Integer(s);
                            this.saveZhong(gid, tid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }
}
