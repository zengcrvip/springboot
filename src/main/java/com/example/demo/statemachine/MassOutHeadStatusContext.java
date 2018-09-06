package com.example.demo.statemachine;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 19:40 2018/9/6 .
 */
@SuppressWarnings("serial")
public final class MassOutHeadStatusContext extends statemap.FSMContext{

    public MassOutHeadStatusContext(MassOutHeadStatus owner) {
        super();

        _owner = owner;
        setState(MassOutHeadMap.Create);
        MassOutHeadMap.Create.Entry(this);
    }

    public MassOutHeadStatusContext(MassOutHeadStatus owner, MassOutHeadStatusState initState) {
        super();
        _owner = owner;
        setState(initState);
        initState.Entry(this);
    }

    public void applySucc() {
        _transition = "applySucc";
        getState().applySucc(this);
        _transition = "";
        return;
    }

    public void detailCreate() {
        _transition = "detailCreate";
        getState().detailCreate(this);
        _transition = "";
        return;
    }

    public void fail() {
        _transition = "fail";
        getState().fail(this);
        _transition = "";
        return;
    }

    public void pay() {
        _transition = "pay";
        getState().pay(this);
        _transition = "";
        return;
    }

    public void process() {
        _transition = "process";
        getState().process(this);
        _transition = "";
        return;
    }

    public void reply() {
        _transition = "reply";
        getState().reply(this);
        _transition = "";
        return;
    }

    public MassOutHeadStatusState getState() throws statemap.StateUndefinedException {
        if (_state == null) {
            throw (new statemap.StateUndefinedException());
        }

        return ((MassOutHeadStatusState) _state);
    }

    protected MassOutHeadStatus getOwner() {
        return (_owner);
    }

    public void setOwner(MassOutHeadStatus owner) {
        if (owner == null) {
            throw (new NullPointerException("null owner"));
        } else {
            _owner = owner;
        }

        return;
    }

    //---------------------------------------------------------------
    // Member data.
    //

    transient private MassOutHeadStatus _owner;

    //---------------------------------------------------------------
    // Inner classes.
    //

    public static abstract class MassOutHeadStatusState extends statemap.State {
        //-----------------------------------------------------------
        // Member methods.
        //

        protected MassOutHeadStatusState(String name, int id) {
            super(name, id);
        }

        protected void Entry(MassOutHeadStatusContext context) {
        }

        protected void Exit(MassOutHeadStatusContext context) {
        }

        protected void applySucc(MassOutHeadStatusContext context) {
            Default(context);
        }

        protected void detailCreate(MassOutHeadStatusContext context) {
            Default(context);
        }

        protected void fail(MassOutHeadStatusContext context) {
            Default(context);
        }

        protected void pay(MassOutHeadStatusContext context) {
            Default(context);
        }

        protected void process(MassOutHeadStatusContext context) {
            Default(context);
        }

        protected void reply(MassOutHeadStatusContext context) {
            Default(context);
        }

        protected void Default(MassOutHeadStatusContext context) {
            throw (new statemap.TransitionUndefinedException("State: "
                    + context.getState().getName() + ", Transition: " + context.getTransition()));
        }

        //-----------------------------------------------------------
        // Member data.
        //
    }

    /* package */static abstract class MassOutHeadMap {
        //-----------------------------------------------------------
        // Member methods.
        //

        //-----------------------------------------------------------
        // Member data.
        //

        //-------------------------------------------------------
        // Constants.
        //
        public static final MassOutHeadMap_Default.MassOutHeadMap_Create Create = new MassOutHeadMap_Default.MassOutHeadMap_Create(
                "MassOutHeadMap.Create", MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_CREATE);
        public static final MassOutHeadMap_Default.MassOutHeadMap_ApplyFail ApplyFail = new MassOutHeadMap_Default.MassOutHeadMap_ApplyFail(
                "MassOutHeadMap.ApplyFail",
                MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYFAIL);
        public static final MassOutHeadMap_Default.MassOutHeadMap_ApplySucc ApplySucc = new MassOutHeadMap_Default.MassOutHeadMap_ApplySucc(
                "MassOutHeadMap.ApplySucc",
                MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_APPLYSUCC);
        public static final MassOutHeadMap_Default.MassOutHeadMap_Confirmed Confirmed = new MassOutHeadMap_Default.MassOutHeadMap_Confirmed(
                "MassOutHeadMap.Confirmed",
                MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_CONFIRMED);
        public static final MassOutHeadMap_Default.MassOutHeadMap_DetailCreate DetailCreate = new MassOutHeadMap_Default.MassOutHeadMap_DetailCreate(
                "MassOutHeadMap.DetailCreate",
                MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_DETAILCREATE);
        public static final MassOutHeadMap_Default.MassOutHeadMap_Payed Payed = new MassOutHeadMap_Default.MassOutHeadMap_Payed(
                "MassOutHeadMap.Payed", MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_PAYED);
        public static final MassOutHeadMap_Default.MassOutHeadMap_Replied Replied = new MassOutHeadMap_Default.MassOutHeadMap_Replied(
                "MassOutHeadMap.Replied",
                MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_REPLIED);
        @SuppressWarnings("unused")
        private static final MassOutHeadMap_Default Default = new MassOutHeadMap_Default(
                "MassOutHeadMap.Default",
                MassOutHeadStatusCommand.MASSOUTHEADSTATUS_COMMAND_DEFAULT);

    }

    protected static class MassOutHeadMap_Default extends MassOutHeadStatusState {
        //-----------------------------------------------------------
        // Member methods.
        //

        protected MassOutHeadMap_Default(String name, int id) {
            super(name, id);
        }

        //-----------------------------------------------------------
        // Inner classse.
        //

        private static final class MassOutHeadMap_Create extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_Create(String name, int id) {
                super(name, id);
            }

            @Override
            protected void applySucc(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.ApplySucc);
                (context.getState()).Entry(context);
                return;
            }

            @Override
            protected void fail(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.ApplyFail);
                (context.getState()).Entry(context);
                return;
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        private static final class MassOutHeadMap_ApplyFail extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_ApplyFail(String name, int id) {
                super(name, id);
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        private static final class MassOutHeadMap_ApplySucc extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_ApplySucc(String name, int id) {
                super(name, id);
            }

            @Override
            protected void pay(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.Confirmed);
                (context.getState()).Entry(context);
                return;
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        private static final class MassOutHeadMap_Confirmed extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_Confirmed(String name, int id) {
                super(name, id);
            }

            @Override
            protected void detailCreate(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.DetailCreate);
                (context.getState()).Entry(context);
                return;
            }

            @Override
            protected void fail(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.ApplyFail);
                (context.getState()).Entry(context);
                return;
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        private static final class MassOutHeadMap_DetailCreate extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_DetailCreate(String name, int id) {
                super(name, id);
            }

            @Override
            protected void process(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.Payed);
                (context.getState()).Entry(context);
                return;
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        private static final class MassOutHeadMap_Payed extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_Payed(String name, int id) {
                super(name, id);
            }

            @Override
            protected void reply(MassOutHeadStatusContext context) {

                (context.getState()).Exit(context);
                context.setState(MassOutHeadMap.Replied);
                (context.getState()).Entry(context);
                return;
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        private static final class MassOutHeadMap_Replied extends MassOutHeadMap_Default {
            //-------------------------------------------------------
            // Member methods.
            //

            private MassOutHeadMap_Replied(String name, int id) {
                super(name, id);
            }

            //-------------------------------------------------------
            // Member data.
            //
        }

        //-----------------------------------------------------------
        // Member data.
        //
    }
}
