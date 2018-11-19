package enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Description:
 * @Author: changrong.zeng
 * @Date: Created in 下午5:29 2018/11/19 .
 */
@Getter
@RequiredArgsConstructor
public enum ActionEnum {

    ADD("ADD", "占用库存"),
    DEL("DEL", "释放库存");

    private final String code;
    private final String message;

    public static ActionEnum getByCode(String code) {
        for (ActionEnum e : ActionEnum.values()) {
            if (e.getCode().equals(code.toUpperCase())) {
                return e;
            }
        }
        return null;
    }
}
