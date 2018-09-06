package com.example.demo;

import com.example.demo.statemachine.MassOutHeadStatus;
import com.example.demo.statemachine.MassOutHeadStatusCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:状态机测试入口
 * @Author: changrong.zeng
 * @Date: Created in 19:53 2018/9/6 .
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MassOutHeadStatusTest {

    @Test
    public void test() throws Exception{
        //状态机修改状态,从状态2改为状态3
        MassOutHeadStatus massOutHeadStatus = new MassOutHeadStatus();
        massOutHeadStatus.setState(MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYSUCC);
        massOutHeadStatus.doAction(MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_CONFIRMED, null);
        //获取修改后的状态机
        System.out.println(massOutHeadStatus.getState().getId());

    }
}
