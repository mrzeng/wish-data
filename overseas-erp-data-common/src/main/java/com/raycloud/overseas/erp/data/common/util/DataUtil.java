package com.raycloud.overseas.erp.data.common.util;


import com.raycloud.bizlogger.Logger;
import com.raycloud.overseas.erp.data.domain.ItemDomain;
import com.raycloud.overseas.erp.data.domain.ItemEvent;
import com.raycloud.overseas.erp.data.common.pojo.MerchantDomain;
import com.raycloud.overseas.erp.data.constant.TraceOrOrder;
import com.raycloud.overseas.erp.data.exception.BizException;
import com.raycloud.overseas.erp.data.exception.ExceptionCode;
import com.raycloud.overseas.erp.data.vo.MerchantEvent;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

/**
 * Created by zhanxf on 16/7/23.
 */
public class DataUtil {

    private static Logger logger= Logger.getLogger(DataUtil.class);

    public static int ITEM_MAX_DATE = 6;

    public static int INDUSTRY_MAX_DATE = 7;

    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static  java.text.DecimalFormat df =new   java.text.DecimalFormat("#.##");

    public static Date SYSTEM_INIT_DATE = new Date();

     /**
     * 精确到2位小数,并返回double
     * @param source
     * @return
     */
    public static Double getFloor(Double source){
        return Double.valueOf(df.format(source));
    }

    /**
     * 生成精确到2位小数的字符串
     * @param source
     * @return
     */
    public static String sfloor(Double source){
        return df.format(source);
    }

    /**
     * 对基础店铺列表进行某些字段排序
     * @param baseMerchantVoList
     * @param
     * @return
     */
    public static void sortMerchantDomain(List<MerchantDomain> baseMerchantVoList, TraceOrOrder order, final String sortType){
        switch (order){
            case amount_7:Collections.sort(baseMerchantVoList, new Comparator<MerchantDomain>() {
                @Override
                public int compare(MerchantDomain o1, MerchantDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getAvgAmount7()==o2.getAvgAmount7()?0:o1.getAvgAmount7()>o2.getAvgAmount7()?-1:1;
                    }else{
                        return o1.getAvgAmount7()==o2.getAvgAmount7()?0:o1.getAvgAmount7()<o2.getAvgAmount7()?-1:1;
                    }

                }
            });break;
            case wish_save:Collections.sort(baseMerchantVoList, new Comparator<MerchantDomain>() {
                @Override
                public int compare(MerchantDomain o1, MerchantDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getNewSave7()==o2.getNewSave7()?0:o1.getNewSave7()>o2.getNewSave7()?-1:1;
                    }else{
                        return o1.getNewSave7()==o2.getNewSave7()?0:o1.getNewSave7()<o2.getNewSave7()?-1:1;
                    }

                }
            });break;
            case item_count:Collections.sort(baseMerchantVoList, new Comparator<MerchantDomain>() {
                @Override
                public int compare(MerchantDomain o1, MerchantDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getItemCount()==o2.getItemCount()?0:o1.getItemCount()>o2.getItemCount()?-1:1;
                    }else{
                        return o1.getItemCount()==o2.getItemCount()?0:o1.getItemCount()<o2.getItemCount()?-1:1;
                    }

                }
            });break;
            case new_count:Collections.sort(baseMerchantVoList, new Comparator<MerchantDomain>() {
                @Override
                public int compare(MerchantDomain o1, MerchantDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getNewCount7()==o2.getNewCount7()?0:o1.getNewCount7()>o2.getNewCount7()?-1:1;
                    }else{
                        return o1.getNewCount7()==o2.getNewCount7()?0:o1.getNewCount7()<o2.getNewCount7()?-1:1;
                    }

                }
            });break;
            case avg_price:Collections.sort(baseMerchantVoList, new Comparator<MerchantDomain>() {
                @Override
                public int compare(MerchantDomain o1, MerchantDomain o2) {

                    int result = new Double(o1.getAvgPrice7()).compareTo(new Double(o2.getAvgPrice7()));
                    if(sortType.equalsIgnoreCase("DESC")){
                        return result == 0?result:result==-1?1:-1;
                    }
                    return result;

                }
            });break;
            case cat_amount_rank:Collections.sort(baseMerchantVoList, new Comparator<MerchantDomain>() {
                @Override
                public int compare(MerchantDomain o1, MerchantDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getCatAmountRank()==o2.getCatAmountRank()?0:o1.getCatAmountRank()>o2.getCatAmountRank()?-1:1;
                    }else{
                        return o1.getCatAmountRank()==o2.getCatAmountRank()?0:o1.getCatAmountRank()<o2.getCatAmountRank()?-1:1;
                    }

                }
            });break;
        }
    }

    /**
     * 对产品里程碑列表按时间排序
     * @param
     * @return
     */
    public static void sortItemEvent(List<ItemEvent> itemEventList, final String sortType){

        if(!ListUtil.isBlank(itemEventList)){

            Collections.sort(itemEventList, new Comparator<ItemEvent>() {
                @Override
                public int compare(ItemEvent o1, ItemEvent o2) {
                    if(o1.getTime()!=null&&o2.getTime()!=null){
                        if(sortType.equalsIgnoreCase("DESC")){
                            return o2.getTime().compareTo(o1.getTime());
                        }else{
                            return o1.getTime().compareTo(o2.getTime());
                        }
                    }else{
                        return 0;
                    }
                }
            });
        }

    }

    /**
     * 对店铺里程碑列表按时间排序
     * @param
     * @return
     */
    public static void sortMerchantEvent(List<MerchantEvent> merchantEventList, final String sortType){

        Collections.sort(merchantEventList, new Comparator<MerchantEvent>() {
            @Override
            public int compare(MerchantEvent o1, MerchantEvent o2) {
                if(o1.getTime()!=null&&o2.getTime()!=null){
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o2.getTime().compareTo(o1.getTime());
                    }else{
                        return o1.getTime().compareTo(o2.getTime());
                    }
                }else{
                    return 0;
                }
            }
        });
    }

    /**
     * 将行业总数据,从大到小排序
     */
    public static void sortSubCatsData(List<Map<String,Object>> catsData){
        Collections.sort(catsData, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                List<Object> trendList1 = (List<Object>) o1.get("data");
                List<Object> trendList2 = (List<Object>) o2.get("data");
                Double sum1 = getSum(trendList1);
                Double sum2 = getSum(trendList2);
                int result = sum1.compareTo(sum2);
                return result == 0?result:result==-1?1:-1;
            }
        });
    }
    
    public static void sort(List list, Class<?> clazz, final String sortType){
        if(clazz == String.class){
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {

                    String _s1 = s1.toLowerCase();
                    String _s2 = s2.toLowerCase();

                    if (_s1 != null && _s2 != null) {
                        if (sortType.equalsIgnoreCase("DESC")) {
                            return _s2.compareTo(_s1);
                        } else {
                            return _s1.compareTo(_s2);
                        }
                    } else {
                        return 0;
                    }
                }
            });
        }
    }

    public static Double getSum(List<Object> trendList){
        Double sum = 0D;
        for(Object object:trendList){
            sum += Double.parseDouble(object+"");
        }
        return sum;
    }



    /**
     * 两个Double数相加
     * @param v1
     * @param v2
     * @return Double
     */
    public static Double add(Double v1,Double v2){
        if (v1 == null && v2 == null)
            return 0D;
        if (v1 == null)
            return v2;
        if (v2 == null)
            return  v1;
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.add(b2).doubleValue();
    }

    public static String getServerIp(){
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        if (ip.getHostAddress().startsWith("192.") || ip.getHostAddress().startsWith("10.")) {
                            //  System.out.println("获取到内网IP:" + ip.getHostAddress());
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static boolean judgeIsId(String key){
        if(key==null||key.length()==0){
            return false;
        }
        if(key.length()==24){
            Pattern pattern = Pattern.compile("^[a-z0-9]{24}$");
            return pattern.matcher(key).matches();
        }
       return false;
    }

    /**
     * 对基础产品列表根据相应字段进行排序
     * @param baseItemVoList
     * @param
     * @return
     */
    public static void sortItemDomain(List<ItemDomain> baseItemVoList, TraceOrOrder order, final String sortType){
        if(order == null){
            return;
        }
        switch (order){
            case amount_7:
                Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                    @Override
                    public int compare(ItemDomain o1, ItemDomain o2) {
                        if(sortType.equalsIgnoreCase("DESC")){
                            return o1.getAmount7()==o2.getAmount7()?0:o1.getAmount7()>o2.getAmount7()?-1:1;
                        }else{
                            return o1.getAmount7()==o2.getAmount7()?0:o1.getAmount7()<o2.getAmount7()?-1:1;
                        }

                    }
                });break;
            case wish_save_7:Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                @Override
                public int compare(ItemDomain o1, ItemDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getNewSave7()==o2.getNewSave7()?0:o1.getNewSave7()>o2.getNewSave7()?-1:1;
                    }else{
                        return o1.getNewSave7()==o2.getNewSave7()?0:o1.getNewSave7()<o2.getNewSave7()?-1:1;
                    }

                }
            });break;
            case rate_score:Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                @Override
                public int compare(ItemDomain o1, ItemDomain o2) {
                    int result = new Double(o1.getRateScore()).compareTo(new Double(o2.getRateScore()));
                    if(sortType.equalsIgnoreCase("DESC")){
                        return result == 0?result:result==-1?1:-1;
                    }
                    return result;

                }
            });break;
            case seller_price:Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                @Override
                public int compare(ItemDomain o1, ItemDomain o2) {
                    if(o1.getSellerPrice() == null || o2.getSellerPrice() == null || o1.getSellerFeightPrice() == null || o2.getSellerFeightPrice() == null){
                        logger.biz("seller_price或seller_freight_price字段为空,无法支持排序");
                        return 0;
                    }
                    int result = new Double(o1.getSellerPrice()+o1.getSellerFeightPrice()).compareTo(new Double(o2.getSellerPrice()+o2.getSellerFeightPrice()));
                    if(sortType.equalsIgnoreCase("DESC")){
                        return result == 0?result:result==-1?1:-1;
                    }
                    return result;

                }
            });break;
            case wish_price:
                Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                    @Override
                    public int compare(ItemDomain o1, ItemDomain o2) {
                        if(o1.getWishPrice() == null || o2.getWishPrice() == null || o1.getWishFeightPrice() == null || o2.getWishFeightPrice() == null){
                            logger.biz("wish_price或wish_freight_price字段为空,无法支持排序");
                            return 0;
                        }
                        int result = new Double(o1.getWishPrice()+o1.getWishFeightPrice()).compareTo(new Double(o2.getWishPrice()+o2.getWishFeightPrice()));
                        if(sortType.equalsIgnoreCase("DESC")){
                            return result == 0?result:result==-1?1:-1;
                        }
                        return result;
                    }
                });break;
            case cat_names:Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                @Override
                public int compare(ItemDomain o1, ItemDomain o2) {
                    if(o1.getCatNames()!=null&&o2.getCatNames()!=null){
                        if(sortType.equalsIgnoreCase("DESC")){
                            return o2.getCatNames().compareTo(o1.getCatNames());
                        }else{
                            return o1.getCatNames().compareTo(o2.getCatNames());
                        }
                    }else{
                        return 0;
                    }


                }
            });break;
            case already_recommended:Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                @Override
                public int compare(ItemDomain o1, ItemDomain o2) {
                    if(sortType.equalsIgnoreCase("DESC")){
                        return o1.getAlreadyRecommended()==o2.getAlreadyRecommended()?0:o1.getAlreadyRecommended()>o2.getAlreadyRecommended()?-1:1;
                    }else{
                        return o1.getAlreadyRecommended()==o2.getAlreadyRecommended()?0:o1.getAlreadyRecommended()<o2.getAlreadyRecommended()?-1:1;
                    }

                }
            });break;
            case growth:Collections.sort(baseItemVoList, new Comparator<ItemDomain>() {
                @Override
                public int compare(ItemDomain o1, ItemDomain o2) {
                    int result = new Double(o1.getGrowth()).compareTo(new Double(o2.getGrowth()));
                    if(sortType.equalsIgnoreCase("DESC")){
                        return result == 0?result:result==-1?1:-1;
                    }
                    return result;

                }
            });break;
        }
    }

    public static int routeItem(String merchantId){
        char c = merchantId.charAt(merchantId.length()-1);
        return ((int)c%13)%2;
    }

    public static List<List> blockLargeCollection(Collection idCollection,Integer maxSize){
        List<List> blocks = new ArrayList<List>();
        int idx = 0;
        List block = null;
        Iterator it = idCollection.iterator();
        while (it.hasNext()){
            if(idx% caculateBlockSize(idCollection.size(),maxSize) == 0){
                block = new ArrayList(Math.min(maxSize,idCollection.size()));
                blocks.add(block);
            }
            block.add(it.next());
            idx++;
        }
        return blocks;
    }

    public static int caculateBlockSize(int b,int maxSize){
        if(b<=maxSize){
            return b;
        }
        for(int i = 1;i<20;i++){
            if(b/i<=maxSize){
                return b/i;
            }
        }
        return 0;
    }

    public static List<List<String>> splitItemIdAndMerchantId(List<String> itemIdList) throws BizException {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> itemList = new ArrayList<String>();
        List<String> merchantList = new ArrayList<String>();
        if(ListUtil.isBlank(itemIdList)){
            throw new BizException(ExceptionCode.NULL_ERROR,"入参有误");
        }else{
            try{
                for(String s:itemIdList){
                    String[] arr = s.split(":");
                    itemList.add(arr[0]);
                    merchantList.add(arr[1]);
                }
                list.add(itemList);
                list.add(merchantList);
            }catch (Exception e){
                logger.error("解析失败",e);
                throw new BizException(ExceptionCode.NULL_ERROR,"解析失败");
            }
        }
        return list;
    }

    public static void main(String[] args){
        System.out.println(routeItem("58ba6c35cddff25103c39a07"));
    }
}
