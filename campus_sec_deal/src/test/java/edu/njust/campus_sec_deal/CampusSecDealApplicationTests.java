package edu.njust.campus_sec_deal;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.njust.campus_sec_deal.entity.*;
import edu.njust.campus_sec_deal.mapper.*;
import edu.njust.campus_sec_deal.service.CampusUserService;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import edu.njust.campus_sec_deal.utils.RandomDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.beans.BeanMap;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@SpringBootTest
class CampusSecDealApplicationTests {

    @Autowired
    private CampusUserMapper stuMapper;
    @Autowired
    CampusWalletMapper walletMapper;

    @Autowired
    CampusOrderMapper orderMapper;

    @Autowired
    CampusPublishMapper publishMapper;

    @Autowired
    CampusChatMapper chatMapper;

    @Test
    void test01() {
        //测试查询所有
        List<CampusUser> stus = stuMapper.selectList(null);
        for (CampusUser stu : stus) {
            System.out.println(stu);
        }
    }

    @Test
    void test02() {
        QueryWrapper<CampusUser> query = new QueryWrapper<>();

//        query.eq("user_pwd", "Admin123");
        query.lambda()
                .eq(CampusUser::getUserId, "20220920192450")
                .eq(CampusUser::getUserTel, "18080808080")
                .eq(CampusUser::getUserMail, "colyn@foxmail.com");
        Long count = stuMapper.selectCount(query);
        CampusUser user = stuMapper.selectById(Long.valueOf("20220920192450"));

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("The count: " + count);
        System.out.println("UserById: " + user);
        System.out.println("--------------------------------------------------------------------------------");
        String str = (String) JSONObject.toJSONString(user);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyMMddHHmmss");
        System.out.println("UserById: " + str + "\nDate: " + format.format(date));

        CampusUser user_info;
        QueryWrapper<CampusUser> get_query = new QueryWrapper<>();
        get_query.lambda().eq(CampusUser::getUserMail, "colyn@foxmail.com");
        user_info = stuMapper.selectOne(get_query);
        System.out.println("get_query_mail: " + user_info);
    }

    @Test
    void test03() {
        CampusWallet wallet = new CampusWallet().setWalletId("20220920192450").setWalletPwd(222222).setWalletBalance(100F + walletMapper.selectById("20220920192450").getWalletBalance());
        int result = walletMapper.updateById(wallet);
        System.out.println(result);
    }

    @Test
    void test04() {
        QueryWrapper<CampusUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name").eq("user_id", "20220921213611");
        CampusUser sysUsers = stuMapper.selectOne(queryWrapper);

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("sysUser: " + sysUsers);
        System.out.println("--------------------------------------------------------------------------------");

    }

    @Test
    void test05() {
        //查询商品价格
        QueryWrapper<CampusPublish> query = new QueryWrapper<>();

        String publish_id = orderMapper.selectById("92934c838031400484abfe1c31601326").getGoodsId();
        float price = publishMapper.selectById(publish_id).getPublishNprice();
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("publish_id: " + publish_id);
        System.out.println("price: " + price);
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Test
    void test06() {
        System.out.println("--------------------------------------------------------------------------------");
        List<PublishCoOder> order = orderMapper.orderListALLByReceiver("20220921213624");
        System.out.println("order: " + order);
//        .forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------------------");
    }

    @Autowired
    CampusUserService service;

//    @Test
//    void test07() {
//        System.out.println("--------------------------------------------------------------------------------");
//        int result = service.Add(1, 3);
//        System.out.println("ServiceResult: " + result);
//        System.out.println("--------------------------------------------------------------------------------");
//    }

//    @Test
//    void test08(){
//
//        CampusUser user = service.getOne(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserId, "20220921112424"),false);
//        System.out.println("--------------------------------------------------------------------------------");
//        System.out.println("service.getOne: " + user);
//        System.out.println("--------------------------------------------------------------------------------");
//    }

    @Test
    void test09() {
        CampusChat chat = new CampusChat()
                .setChatContent("Hello, everyone!")
                .setChatId(RandomDataUtil.getIDByTime())
                .setFromId("20220921112424")
                .setToId("20220924120845")
                .setSendTime(RandomDataUtil.getDateTime());
        int result = chatMapper.insert(chat);

//        CampusUser user = service.getOne(Wrappers.<CampusUser>lambdaQuery().eq(CampusUser::getUserId, "20220921112424"), false);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("service.getOne: " + result);
        System.out.println("--------------------------------------------------------------------------------");
    }

//    @Test
//    void test10(){
//        List<CampusChat> msgs = chatMapper.selectList(new QueryWrapper<CampusChat>().eq("to_id", "20220922154423"));
//        msgs.forEach(System.out::println);
//    }

    @Test
    public void testSelectListPaged() {
        PageHelper.startPage(1, 3);
        List<CampusUser> user = stuMapper.selectList(null);
        PageInfo<CampusUser> pageInfo = new PageInfo<CampusUser>(user);
        System.out.println("totalPage:"+pageInfo.getPages()+",totalInfo:"+pageInfo.getTotal()+",CurrentPage:"+pageInfo.getPageNum());
        for (CampusUser stu : user){
            System.out.println("name:"+stu.getUserName()+",id:"+stu.getUserId());
        }
    }
}
