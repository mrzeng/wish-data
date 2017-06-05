package com.raycloud.overseas.erp.data.services.api;

import com.raycloud.overseas.erp.data.common.pojo.*;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.usercenter.web.api.vo.DealResult;
import com.raycloud.overseas.usercenter.web.api.vo.ResultObject;

import java.util.List;
import java.util.Map;


/**
 * Created by zhanxf on 16/7/16.
 */
public interface CommonService {


    /**
     * 保存标签信息
     * @param dataMarkInfoList
     */
    public void saveMarkInfo(List<DataMarkInfo> dataMarkInfoList, UserData curUser, String type);

    /**
     * 显示标签信息
     * @param curUser
     * @param type
     * @return
     */
    public Map<String, Object> labelManageShow(UserData curUser, String type);

    /**
     * 添加店铺或宝贝的关注标签
     * @param markId
     * @param focusIdList
     * @param type
     * @param selected
     * @param  userData
     */
    public void addFocusMarks(Integer markId,List<String> focusIdList,int type,boolean selected,UserData userData);

    /**
     * 保存筛选项
     * @param condition
     * @param curUser
     */
    public ResultObject saveFilterCondition(SearchCondition condition, UserData curUser);

    /**
     * 删除筛选项
     * @param id
     * @param curUser
     */
    public void deleteFilterCondition(String id, UserData curUser);

    /**
     * 查询筛选项
     * @param userAccount
     * @param status
     * @return
     */
    public List<SearchCondition> querySearchCondition(UserData userAccount, String status);

    /**
     * 保存选中的列
     * @param param
     * @param status
     * @param curUser
     */
    public void saveColumnCondition(String param, String status, UserData curUser);

    /**
     * 获取选择列
     * @param status
     * @param curUser
     * @return
     */
    public ColumnCondition getColumnCondition(String status, UserData curUser);

    /**
     * 同步设置
     * @param progress
//     * @param total
     * @param type
     * @param userData
     */
    public void setSynProcess(int progress, String type, UserData userData,Map<String,Object> param);

    /**
     * 同步查询
     * @param type
     * @param userData
     * @return
     */
    public Map<String, Object> querySynResult(String type,UserData userData);

    /**
     * 判断是否有足够的豆子
     * @param founderId
     * @param needBeans
     * @return
     */
    public boolean hasEnoughBeans(Integer founderId, Integer needBeans);

    /**
     * 消费超级豆
     * @param userId
     * @param founderId
     * @param needBeans
     * @param ruleId
     * @param usage
     * @return
     */
    public DealResult consumeBeans(Integer userId, Integer founderId, Integer needBeans, Integer ruleId, String usage);

    /**
     * 预消费
     * @param type
     * @param userData
     * @param beans
     */
    public void preConsumeBeans(String type,UserData userData, Integer beans) throws BizException;

    /**
     * 消费后清除缓消费信息
     * @param type
     * @param userData
     * @param beans
     */
    public void afterConsumeBeans(String type,UserData userData, Integer beans) throws BizException;

    /**
     * 校验慢操作是否正在运行
     * @param type
     * @param userData
     * @return
     */
    public boolean checkSlowOpIsRunning(String type,UserData userData);

    /**
     * 校验慢操作是否正在运行
     * @param type
     * @param userData
     * @return
     */
    public void removeSession(String type,UserData userData);

    /**
     * 校验是否是普通版用户
     * @param userData
     * @return
     */
    public int getUserLevel(UserData userData);
}
