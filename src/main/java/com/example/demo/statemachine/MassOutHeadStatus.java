package com.example.demo.statemachine;

import com.example.demo.statemachine.MassOutHeadStatusContext.MassOutHeadMap;
import com.example.demo.statemachine.exception.InvalidStatusCommandException;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:43 2018/9/6 .
 */
public class MassOutHeadStatus {

    private MassOutHeadStatusContext _fsm;

    public MassOutHeadStatus() {
        _fsm = new MassOutHeadStatusContext(this);
    }

    public MassOutHeadStatusContext.MassOutHeadStatusState getState() {
        return _fsm.getState();
    }

    public void setState(int state) {
        switch (state) {
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_CREATE:
                _fsm.setState(MassOutHeadMap.Create);
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYFAIL:
                _fsm.setState(MassOutHeadMap.ApplyFail);
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYSUCC:
                _fsm.setState(MassOutHeadMap.ApplySucc);
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_DETAILCREATE:
                _fsm.setState(MassOutHeadMap.DetailCreate);
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_CONFIRMED:
                _fsm.setState(MassOutHeadMap.Confirmed);
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_PAYED:
                _fsm.setState(MassOutHeadMap.Payed);
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_REPLIED:
                _fsm.setState(MassOutHeadMap.Replied);
                break;

            default:
                _fsm.setState(MassOutHeadMap.Create);
                break;
        }
    }


    public void doAction(int commandId, String key) throws InvalidStatusCommandException {
        switch (commandId) {
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYFAIL:
                _fsm.fail();
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYSUCC:
                _fsm.applySucc();
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_DETAILCREATE:
                _fsm.detailCreate();
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_CONFIRMED:
                _fsm.pay();
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_PAYED:
                _fsm.process();
                break;
            case MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_REPLIED:
                _fsm.reply();
                break;
            default:
                _fsm.setState(MassOutHeadMap.Create);
                break;
        }

    }
}
