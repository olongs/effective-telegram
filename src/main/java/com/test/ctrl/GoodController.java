package com.test.ctrl;

import com.test.model.GoodInfo;
import com.test.service.GoodService;
import com.test.util.mq.MySender;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: GoodController
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 9:17
 */
@Controller
public class GoodController {
    @Autowired
    private GoodService service;
    @Autowired
    private MySender mySender;

    /**
     * @Desc: 跳转商品页面
     * @param
     **/
    @RequestMapping("/good")
    public String toGood(){
        return "/good";
    }

    @RequestMapping("/loadgood")
    @ResponseBody
    public Map findGood(Integer page,Integer rows,
                        @RequestParam(name = "qname",defaultValue = "") String qname){
        if (page==null){
            page = 1;
        }

        if (rows==null){
            rows=10;
        }
        List<GoodInfo> goods = service.findGoods(qname);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total",goods.size());
        map.put("rows",goods);
        return map;
    }

    @RequestMapping("/loadtype")
    @ResponseBody
    public Object getTypeList(){
        return service.getTypeList();
    }

    @RequestMapping("/deleteall")
    @ResponseBody
    public boolean deleteAll(Integer[] ids){
        System.out.println(ids);
        for (Integer id : ids) {
            if (id!=null)
            service.deleteGoodById(id);
        }
        return true;
    }
    @RequestMapping("/savegood")
    @ResponseBody
    public boolean saveGood(GoodInfo goodInfo){
        System.err.println(goodInfo);
        mySender.sendMsg(goodInfo);
        return true;
    }
}
