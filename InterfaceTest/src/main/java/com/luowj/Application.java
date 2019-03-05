package com.luowj;

import com.luowj.Utils.MailUtil;
import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main (String args[])throws  Exception{

                //执行testng文件
                TestNG testNG = new TestNG();
                List<String> suites = new ArrayList<String>();
                suites.add("./src/main/resources/testng.xml");
                testNG.setTestSuites(suites);
                testNG.run();

                //发送报告至邮箱
                 MailUtil.sendTestReportMail();
    }
}
