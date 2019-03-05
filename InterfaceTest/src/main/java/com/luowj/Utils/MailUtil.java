package com.luowj.Utils;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailUtil {
    public static void sendTestReportMail() throws  Exception{
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、连上邮件服务器
        ts.connect("smtp.163.com", "lwjzy160", "luo123105");
        //发送地址添加到list中
        List<String> addrList = new ArrayList<String>();
        //addrList.add("FintechTest@tebon.com.cn");
        addrList.add("29089800@qq.com");
        //4、创建邮件
        Message message = createAttachMail(session,addrList);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }
    private static MimeMessage createAttachMail(Session session,List<String> list) throws Exception{
        MimeMessage message = new MimeMessage(session);

        //设置邮件的基本信息
        //发件人
        message.setFrom(new InternetAddress("lwjzy160@163.com"));
        //收件人
        List<InternetAddress>   addressList  = new ArrayList<InternetAddress>();
        for(String add:list){
            addressList.add(new InternetAddress(add));
        }

        //改为地址数组

        InternetAddress [] addressArray = new InternetAddress[addressList.size()];
        addressList.toArray(addressArray);
        //InternetAddress [] addressArray_ = addressList.toArray(addressArray);

        message.addRecipients(Message.RecipientType.TO,addressArray);
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("FintechTest@tebon.com.cn"));
        //邮件标题
        message.setSubject("JavaMail接口测试报告");

        //创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("测试报告如附件", "text/html;charset=UTF-8");

        //创建邮件附件
        String filePath="test-output/interTestReport.html";
        MimeBodyPart attach = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource(new File(filePath)));

        attach.setDataHandler(dh);
        attach.setFileName(dh.getName());  //

        //创建容器描述数据关系
        MimeMultipart mp = new MimeMultipart();
        mp.addBodyPart(text);
        mp.addBodyPart(attach);
        mp.setSubType("mixed");

        message.setContent(mp);
        message.saveChanges();
        //将创建的Email写入到E盘存储
        //message.writeTo(new FileOutputStream("E:\\attachMail.eml"));
        //返回生成的邮件
        return message;
    }
}
