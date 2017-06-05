import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.impl.rocketmq.ONSChannel;
import com.raycloud.overseas.erp.data.common.constant.AppKeyConstant;
import com.raycloud.overseas.erp.data.common.constant.ClientNameEnum;
import com.raycloud.overseas.erp.data.services.ons.PopMsgVo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * Description:
 * User: ouzhouyou@raycloud.com
 * Date: 16/8/22
 * Time: 下午3:06
 * Version: 1.0
 */
public class HttpTest {
    public static void main(String[] args) throws IOException {


        String secret = AppKeyConstant.getAppSecret(ClientNameEnum.SUPERBOSS);
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ProducerId, "PID_superseller_test");
        properties.put(PropertyKeyConst.AccessKey, AppKeyConstant.APP_KEY_SUPERBOSS);
        properties.put(PropertyKeyConst.SecretKey, "c959407b99ffb6bd840185295af2d639");
        properties.put(PropertyKeyConst.OnsChannel, ONSChannel.CLOUD);
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();

        PopMsgVo popMsgVo =  new PopMsgVo();
        popMsgVo.setBody("TEST");
        popMsgVo.setTag("tradeUpdate");

        byte[] bytes = JSON.toJSONString(popMsgVo).getBytes();
        Message msg = new Message(
                //Message Topic
                "superseller_12011554_test",
                //Message Tag,
                //可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
                "tradeUpdate",
                //Message Body
                //任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
                bytes
        );
        SendResult send = producer.send(msg);
        System.out.println(send);


    }

    private static String payLoad(URL url, String payload) throws IOException {
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestProperty("content-type", "text/html");
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bufferedWriter.write(payload);
        bufferedWriter.flush();
        bufferedWriter.close();
        conn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString().trim();
    }
}
