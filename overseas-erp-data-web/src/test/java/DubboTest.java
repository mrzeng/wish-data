import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Description:
 * User: ouzhouyou@raycloud.com
 * Date: 16/8/22
 * Time: 下午3:06
 * Version: 1.0
 */
public class DubboTest {
    public static void main(String[] args) throws IOException {
        //需要web工程用 -Pdev-dubbo启动
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{
                "/spring-test-dubbo.xml"
        });
        applicationContext.start();

//        TaskInfoService taskInfoService = applicationContext.getBean(TaskInfoService.class);
//        System.out.println(taskInfoService.getTaskInfoList(new TaskInfoQuery()).size());
    }
}
