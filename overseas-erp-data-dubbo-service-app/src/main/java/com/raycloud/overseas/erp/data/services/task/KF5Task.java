package com.raycloud.overseas.erp.data.services.task;

import com.alibaba.fastjson.JSON;
import com.kf5.support.controller.KF5Support;
import com.kf5.support.model.KF5PaginationEntity;
import com.kf5.support.model.Post;
import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.common.domain.UserAccount;
import com.raycloud.overseas.erp.data.services.util.Sdk;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanxf on 17/2/28.
 */
@Component(value = "kf5")
public class KF5Task   {

    private static Logger logger= Logger.getLogger(KF5Task.class);

    public static List<Post> postList = new ArrayList<Post>();

    public List<Post> getPostList(){
        if(postList.size() == 0){
            refreshNewsPost();
        }
        return postList;
    }

    /**
     * 刷新行业新闻
     */
    //@Scheduled(cron = "0 0 0 12 * ?")
    public void refreshNewsPost(){
        logger.biz("刷新行业新闻");
        postList.clear();
        KF5PaginationEntity<List<Post>> entity = null;
        try{
            final KF5Support kf5Support = new KF5Support();

            UserAccount userAccount = getAdminUserAccount();
            kf5Support.initWithApiToken(Sdk.HOST,userAccount.getEmail(),Sdk._secretKey);
            String query = "filter=is_dashboard:1&sort=updated_at:desc";
            entity = kf5Support.getForumPostListWithQuery("102811",query);
            if(entity.getResultCode() != 0){
                logger.error("用户id:"+userAccount.getId()+","+ JSON.parseObject(entity.getMessage()).getString("message"));
            }else{
                List<Post> _postList = entity.getData();
                for(int i = 0;i<_postList.size();i++){
                    if(i>=10){
                        break;
                    }
                    postList.add(_postList.get(i));
                }
            }
        }catch (Exception e){
            logger.error(e);
        }
    }

    public UserAccount getAdminUserAccount(){
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("yangnan@raycloud.com");
        userAccount.setUname("crystal");
        userAccount.setId(53L);
        return userAccount;
    }




}
