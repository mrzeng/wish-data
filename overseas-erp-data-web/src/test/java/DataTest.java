

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raycloud.overseas.erp.common.domain.UserAccount;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * WishTest.java
 * Created by zhongliang
 * Created on 2016/5/12 上午11:05
 * Copyright(c)2014  版权所有
 */

@ContextConfiguration(locations = "classpath:spring/*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DataTest {

//    @Autowired
//    private ILocalMerchantService localMerchantService;
//
//    @Autowired
//    private ILocalItemService localItemService;
//
//    @Autowired
//    private IBaseDataService baseDataService;
//
//    @Test
//    public void addMarkTest(){
////        localMerchantService.addMarks("25","21,22",0,true,92);
//        localMerchantService.addMarks("25","22",0,false,92);
//    }
//
//    @Test
//    public void showMark(){
//        UserAccount curUser = new UserAccount();
//        curUser.setId(92L);
//        System.out.println(JSON.toJSONString(baseDataService.labelManageShow(curUser,"2")));
//    }
//
//    @Test
//    public void saveMark(){
//        String markInfoList = "[{\"markName\":\"a3\",\"color\":\"red\",\"type\":\"0\"}]";
//
//        UserAccount curUser = new UserAccount();
//        curUser.setId(92L);
//        if(markInfoList!=null){
//
//            List<MarkInfoDomain> markInfoLists= JSONObject.parseArray(markInfoList, MarkInfoDomain.class);
//            baseDataService.saveMarkInfo(markInfoLists, curUser,"2");
//        }
//    }

}
