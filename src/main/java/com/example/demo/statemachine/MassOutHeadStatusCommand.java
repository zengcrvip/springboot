package com.example.demo.statemachine;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:44 2018/9/6 .
 */
public final  class MassOutHeadStatusCommand {

    /**
     * 批量申请初始状态
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_CREATE = 0;

    /**
     * 批量申请已失败
     */

    public final static int MASSOUTHEADSTATUS_COMMAND_APPLYFAIL = 1;

    /**
     * 批量申请已成功
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_APPLYSUCC = 2;


    /**
     * 已确认
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_CONFIRMED = 3;

    /**
     * 明细入库
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_DETAILCREATE = 4;

    /**
     * 已支付成
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_PAYED = 5;

    /**
     * 已返盘
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_REPLIED = 6;

    /**
     * 默认值
     */
    public final static int MASSOUTHEADSTATUS_COMMAND_DEFAULT = -1;
}
