package enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author: JERRY
 * @description:
 * @date: 2019-07-11 11:07
 */
@Getter
@RequiredArgsConstructor
public enum ErrorEnum {


    DATA_ALREADY_EXIST_ERROR("100001", "当前数据已经存在"),
    DATA_SAVE_ERROR("100002", "数据保存时失败"),
    DATA_NOT_EXISTS_ERROR("100003", "当前数据不存在"),
    DATA_UPDATE_ERROR("100004", "数据更新时失败"),
    OPTIMISTIC_LOCK_ERROR("100005", "乐观锁异常"),
    BEING_PROCESSED_ERROR("100006", "数据处理中"),

    NOT_NULL_ERROR("600001", "必要参数不能为空"),
    PARAM_ILLEGAL_ERROR("600002", "参数不合法"),
    REPEAT_COLUMN_NAME("600003", "重复校验列不能为空"),
    CHECKED_TITLE_LIST("600004", "校验标题列表不能为空"),

    EXCEL_ANALYSIS_ERROR("600100", "解析excel异常"),
    EXCEL_COLUMN_KEY_ERROR("600101", "解析excel列数异常"),
    TEXT_ANALYSIS_ERROR("6001110", "解析text异常"),
    TEXT_COLUMN_KEY_ERROR("600111", "解析text列数异常"),


    DATE_FORMAT_ERROR("600201", "日期格式错误"),
    CALCULATING_ERROR("600202", "计算中，请稍后调用"),
    CREATE_TIME_BLANK("600203", "创建时间为空"),
    TABLE_NAME_BLANK("600204", "表名不能为空"),
    CONTRACT_MODEL_EMPTY("600207", "存在未打标合约信息"),
    DATA_NOT_EXIST("600208", "数据不存在"),

    START_DATE_AFTER_END_DATE("600225", "开始日期不能在结束日期之后"),

    MARKING_CALCULATE_ERROR("700031", "计算拆分错误！"),

    ;


    private final String code;
    private final String msg;
}
