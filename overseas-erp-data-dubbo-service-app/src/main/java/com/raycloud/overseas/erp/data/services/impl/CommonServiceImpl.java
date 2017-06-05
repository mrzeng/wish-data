package com.raycloud.overseas.erp.data.services.impl;

import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.common.dao.ColumnConditionDAO;
import com.raycloud.overseas.erp.data.common.dao.DataMarkInfoDAO;
import com.raycloud.overseas.erp.data.common.dao.FocusMarkMapDAO;
import com.raycloud.overseas.erp.data.common.dao.SearchConditionDAO;
import com.raycloud.overseas.erp.data.common.pojo.ColumnCondition;
import com.raycloud.overseas.erp.data.common.pojo.DataMarkInfo;
import com.raycloud.overseas.erp.data.common.pojo.FocusMarkMap;
import com.raycloud.overseas.erp.data.common.pojo.SearchCondition;
import com.raycloud.overseas.erp.data.common.query.ColumnConditionQuery;
import com.raycloud.overseas.erp.data.common.query.DataMarkInfoQuery;
import com.raycloud.overseas.erp.data.common.query.FocusMarkMapQuery;
import com.raycloud.overseas.erp.data.common.query.SearchConditionQuery;
import com.raycloud.overseas.erp.data.common.util.ListUtil;
import com.raycloud.overseas.erp.data.constant.DataConstant;
import com.raycloud.overseas.erp.data.domain.UserData;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.services.api.CommonService;
import com.raycloud.overseas.erp.data.common.session.impl.OcsSession;
import com.raycloud.overseas.usercenter.web.api.pojo.Wallet;
import com.raycloud.overseas.usercenter.web.api.service.IAbConfigService;
import com.raycloud.overseas.usercenter.web.api.service.IWalletService;
import com.raycloud.overseas.usercenter.web.api.vo.DealResult;
import com.raycloud.overseas.usercenter.web.api.vo.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zhanxf on 16/7/17.
 */
@Service
public class CommonServiceImpl implements CommonService {


    private static Logger logger= Logger.getLogger(CommonServiceImpl.class);

    @Resource
    private FocusMarkMapDAO focusMarkMapDAO;

    @Autowired
    private DataMarkInfoDAO dataMarkInfoDAO;

    @Autowired
    private SearchConditionDAO searchConditionDAO;

    @Autowired
    private ColumnConditionDAO columnConditionDAO;

    @Autowired
    private OcsSession ocsSession;

    @Resource
    private IWalletService walletService;

    @Resource
    private IAbConfigService abConfigService;

    @Resource
    private String env;

    /**
     * 显示标签信息
     * @return
     */
    public Map<String, Object> labelManageShow(UserData curUser, String type){

        DataMarkInfoQuery dataMarkInfoQuery = new DataMarkInfoQuery();
        dataMarkInfoQuery.setUserId(curUser.getUserId());
        dataMarkInfoQuery.setFounderId(curUser.getFounderId());
        dataMarkInfoQuery.setType(type);
        List<DataMarkInfo> dataMarkInfoList = dataMarkInfoDAO.getDataMarkInfoList(dataMarkInfoQuery);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("markInfoList", dataMarkInfoList);


        return resultMap;
    }

    /**
     * 保存标签信息
     * @param markInfoDomainList
     */
    public void saveMarkInfo(List<DataMarkInfo> markInfoDomainList, UserData curUser, String type){

        DataMarkInfoQuery dataMarkInfoQuery = new DataMarkInfoQuery();
        dataMarkInfoQuery.setType(type);
        dataMarkInfoQuery.setUserId(curUser.getUserId());
        dataMarkInfoQuery.setFounderId(curUser.getFounderId());
        List<DataMarkInfo> markList = dataMarkInfoDAO.getDataMarkInfoList(dataMarkInfoQuery);

        List<DataMarkInfo> insertMarkList = new ArrayList<DataMarkInfo>();
        List<DataMarkInfo> updateMarkList = new ArrayList<DataMarkInfo>();

        List<Integer> removeIdList = new ArrayList<Integer>();
        if(markList != null && markList.size() > 0){
            for (DataMarkInfo mark : markList){
                removeIdList.add(mark.getId());
            }
        }

        if (markInfoDomainList != null && markInfoDomainList.size() > 0){

            for (DataMarkInfo mark: markInfoDomainList){
                mark.setType(type);
                mark.setUserId(curUser.getUserId());
                mark.setFounderId(curUser.getFounderId());
                if (removeIdList.size() > 0 && removeIdList.contains(mark.getId())){
                    removeIdList.remove(mark.getId());
                }

                if (mark.getId() == null){
                    insertMarkList.add(mark);
                }else{
                    updateMarkList.add(mark);
                }
            }



            if (insertMarkList.size() >  0)
                dataMarkInfoDAO.batchAddDataMarkInfo(insertMarkList);

            if (updateMarkList.size() > 0)
                dataMarkInfoDAO.batchUpdateDataMarkInfoByKey(updateMarkList);
        }

        if (removeIdList.size() > 0){

            dataMarkInfoDAO.deleteByKeys(removeIdList);

            FocusMarkMapQuery focusMarkMapQuery1 = new FocusMarkMapQuery();
            focusMarkMapQuery1.setMarkIdList(removeIdList);
            focusMarkMapDAO.deleteFocusMarkMapList(focusMarkMapQuery1);
        }
    }

    @Override
    public void addFocusMarks(Integer markId,List<String> focusIdList,int type,boolean selected,UserData userData) {

        if(!selected){

            FocusMarkMapQuery focusMarkMapQuery = new FocusMarkMapQuery();
            focusMarkMapQuery.setUserId(userData.getUserId());
            focusMarkMapQuery.setFounderId(userData.getFounderId());
            focusMarkMapQuery.setMarkId(markId);
            focusMarkMapQuery.setFocusIdList(focusIdList);
            focusMarkMapDAO.deleteFocusMarkMapList(focusMarkMapQuery);
        }else{

            //筛选出已添加过该标签的宝贝

            FocusMarkMapQuery focusMarkMapQuery = new FocusMarkMapQuery();
            focusMarkMapQuery.setUserId(userData.getUserId());
            focusMarkMapQuery.setFounderId(userData.getFounderId());
            focusMarkMapQuery.setMarkId(markId);
            focusMarkMapQuery.setFocusIdList(focusIdList);
            focusMarkMapQuery.setFocusType(type);
            List<FocusMarkMap> addedList = focusMarkMapDAO.getFocusMarkMapList(focusMarkMapQuery);

            Map<String,String> addedIdMap = new HashMap<String, String>();
            for(FocusMarkMap focusMarkMap:addedList){
                addedIdMap.put(focusMarkMap.getFocusId(),focusMarkMap.getFocusId());
            }

            List<FocusMarkMap> focusMarkInfos = new ArrayList<FocusMarkMap>();
            for(String focusId:focusIdList){
                if(!addedIdMap.containsKey(focusId)){
                    FocusMarkMap focusMarkInfo = new FocusMarkMap();
                    focusMarkInfo.setUserId(userData.getUserId());
                    focusMarkInfo.setFounderId(userData.getFounderId());
                    focusMarkInfo.setFocusId(focusId);
                    focusMarkInfo.setFocusType(type);
                    focusMarkInfo.setMarkId(markId);
                    focusMarkInfos.add(focusMarkInfo);
                }
            }
            focusMarkMapDAO.batchAddFocusMarkMap(focusMarkInfos);
        }
    }

    /**
     * 保存筛选项
     * @param searchCondition
     * @param curUser
     */
    public ResultObject saveFilterCondition(SearchCondition searchCondition, UserData curUser){

        ResultObject result = new ResultObject();

        searchCondition.setUserId(curUser.getUserId());
        searchCondition.setTeamFounderId(curUser.getFounderId());

        if(searchCondition.getId() == null){ //add
            searchCondition.setId(searchConditionDAO.addSearchCondition(searchCondition));
        }else {
            searchConditionDAO.updateSearchConditionByKey(searchCondition);
        }
        result.setData(searchCondition);
        return result;
    }

    /**
     * 删除筛选项
     * @param id
     * @param curUser
     */
    public void deleteFilterCondition(String id, UserData curUser){
        searchConditionDAO.deleteByKey(Integer.parseInt(id));
    }

    public List<SearchCondition> querySearchCondition(UserData userAccount, String status){
        SearchConditionQuery searchConditionQuery = new SearchConditionQuery();
        searchConditionQuery.setStatus(status);
        searchConditionQuery.setUserId(userAccount.getUserId());
        searchConditionQuery.setTeamFounderId(userAccount.getFounderId());
        return searchConditionDAO.getSearchConditionList(searchConditionQuery);
    }

    /**
     * 保存 列显示信息
     * @param param
     * @param status
     * @param userData
     */
    public void saveColumnCondition(String param, String status, UserData userData){

        ColumnConditionQuery columnConditionQuery = new ColumnConditionQuery();
        columnConditionQuery.setUserId(userData.getUserId()+"");
        columnConditionQuery.setFounderId(userData.getFounderId()+"");
        columnConditionQuery.setStatus(status);
        columnConditionDAO.deleteColumnConditionList(columnConditionQuery);

        ColumnCondition condition = new ColumnCondition();
        condition.setUserId(userData.getUserId()+"");
        condition.setFounderId(userData.getFounderId()+"");
        condition.setStatus(status);
        condition.setParam(param);
        condition.setCreateTime(new Date());
        columnConditionDAO.addColumnCondition(condition);
    }

    @Override
    public ColumnCondition getColumnCondition(String status, UserData curUser) {
        ColumnConditionQuery columnConditionQuery = new ColumnConditionQuery();
        columnConditionQuery.setStatus(status);
        columnConditionQuery.setUserId(curUser.getUserId()+"");
        columnConditionQuery.setFounderId(curUser.getFounderId()+"");
        List<ColumnCondition> columnConditionList = columnConditionDAO.getColumnConditionList(columnConditionQuery);
        return ListUtil.isBlank(columnConditionList)?null:columnConditionList.get(0);
    }


    /**
     * 设置同步进度
     * @param progress
//     * @param total
     * @param type
     * @param userData
     */
    @Override
    public void setSynProcess(int progress, String type, UserData userData,Map<String,Object> param) {
        String key = env+"-"+type+"-"+ + userData.getUserId()+"-"+userData.getFounderId();
        Map<String,Object> synMap = (Map<String,Object>)ocsSession.getUserObj(key,DataConstant.FIVE_MINUTE);
        if(synMap==null){
            synMap = new HashMap<String, Object>();
        }
        if(progress==100){
            synMap.put("state","finished");
        }else if (progress==0){
            synMap.put("state","running");
            synMap.put("start",System.currentTimeMillis());
        }else{
            synMap.put("state","running");
        }
        if(param!=null){
            synMap.putAll(param);
        }
        synMap.put("progress",progress);
        synMap.put("type",type);
        ocsSession.setUserObj(key,DataConstant.FIVE_MINUTE,synMap);
        logger.biz("类型:{},进度{},用户id:{}",type,progress,userData.getUserId());
    }

    @Override
    public Map<String, Object> querySynResult(String type,UserData userData) {
        String key = env+"-"+type+"-"+ + userData.getUserId()+"-"+userData.getFounderId();
        Map<String,Object> synMap = (Map<String,Object>)ocsSession.getUserObj(key,DataConstant.FIVE_MINUTE);
        if (synMap!=null){
            int process = (Integer)synMap.get("progress");
            if(process == 100){
                ocsSession.removeUserObj(key,-1l);
            }else{
                if(!synMap.containsKey("state")||!synMap.get("state").equals("running")){
                    synMap.put("state","running");
                }
            }
            return synMap;
        }
        logger.biz("暂无同步数据,类型:{},用户id:{}",type,userData.getUserId());
        return null;
    }

    /**
     * 校验慢操作是否正在运行
     *
     * @param type
     * @param userData
     * @return
     */
    @Override
    public boolean checkSlowOpIsRunning(String type, UserData userData) {
        String key = env+"-"+type+"-"+ + userData.getUserId()+"-"+userData.getFounderId();
        Map<String,Object> map = (Map<String,Object>)ocsSession.getUserObj(key,DataConstant.FIVE_MINUTE);
        if(map == null){
            return false;
        }
        if(map.containsKey("state") && map.get("state").equals("running")){
            if(!map.containsKey("start")||Math.abs((Long)map.get("start")-System.currentTimeMillis())>1000*60*5){
                logger.biz("用户id:{},进度条操作:{}超时,进行重试操作,开始时间:{},当前时间:{}",userData.getUserId(),type,map.get("start"),System.currentTimeMillis());
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 校验慢操作是否正在运行
     *
     * @param type
     * @param userData
     * @return
     */
    @Override
    public void removeSession(String type, UserData userData) {
        String key = env+"-"+type+"-"+ + userData.getUserId()+"-"+userData.getFounderId();
        ocsSession.removeUserObj(key,-1l);
        logger.biz("移除用户:{},type:{}操作",userData.getUserId(),key);
    }

    /************************************钱包服务******************************************/
    //判断是否有足够的豆子
    @Override
    public boolean hasEnoughBeans(Integer founderId, Integer needBeans) {
        Wallet wallet = walletService.getUserWallet(Long.parseLong(founderId+""));
        Integer leftBeans = wallet.getBeans();
        if (leftBeans >= needBeans) {
            return true;
        }
        return false;
    }

    /**
     * 预消费
     * @param type
     * @param userData
     * @param beans
     */
    public void preConsumeBeans(String type,UserData userData, Integer beans) throws BizException {
        String key = env+"-"+type+"-"+userData.getFounderId();
        Map<String,Object> map = (Map<String,Object>)ocsSession.getAppObj(key);
        int sum = 0;
        if(map==null){
            map = new HashMap<String, Object>();
            sum = beans;
        }else{
            Iterator it = map.values().iterator();
            while (it.hasNext()){
                sum+=(Integer) it.next();
            }
            sum+=beans;
        }
        if(hasEnoughBeans(userData.getFounderId(),sum)){
            map.put(type+"-"+userData.getUserId(),beans);
            ocsSession.setAppObj(key,map);
        }else{
            throw new BizException(ExceptionCode.SYS_ERROR_MQ8,"余额不足");
        }
    }

    /**
     * 预消费
     * @param type
     * @param userData
     * @param beans
     */
    public void afterConsumeBeans(String type,UserData userData, Integer beans) throws BizException {
        String key = env+"-"+type+"-"+userData.getFounderId();
        Map<String,Object> map = (Map<String,Object>)ocsSession.getAppObj(key);
        if(map==null){
            throw new BizException(ExceptionCode.NULL_ERROR,"无消费信息");
        }else{
            map.remove(type+"-"+userData.getUserId());
            ocsSession.setAppObj(key,map);
        }
    }

    //调接口消费豆子
    @Override
    public DealResult consumeBeans(Integer userId, Integer founderId, Integer needBeans, Integer ruleId, String usage) {
        DealResult result = null;

        if (needBeans.equals(0)) {
            result = new DealResult();
            result.setSuccess(true);
            return result;
        }

        if (!this.hasEnoughBeans(founderId, needBeans)) {
            result = new DealResult();
            result.setSuccess(false);
            result.setErrorMsg("没有足够的超级豆");
            logger.biz("没有足够超级豆,需要消耗豆数:{},用户id:{}",needBeans,userId);
            return result;
        }

        try {
            result = walletService.consume(ruleId, needBeans, Long.parseLong(userId+""), usage);
        } catch (Exception e) {
            try {
                Thread.sleep(500);
                result = walletService.consume(ruleId, needBeans, Long.parseLong(userId+""), usage);
            } catch (Exception e1) {
                logger.error("用户中心消费豆子出错,userId:{},usage:{},beans:{},errMsg:{}", userId, usage, needBeans, e1);
            }
        }
        return result;
    }



    /**
     * 校验是否是普通版用户
     *
     * @param userData
     * @return
     */
    @Override
    public int getUserLevel(UserData userData) {
        List<Integer> list = abConfigService.getTagIdListByUserId(userData.getLongFounderId());
        if(ListUtil.isBlank(list)){
            return DataConstant.VIP_0;
        }else{
            return DataConstant.VIP_8;
        }
    }

    public static void main(String[] args){
        System.out.println((System.currentTimeMillis()-1496373370929l)/1000);
    }
}
