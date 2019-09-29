package cn.uetec.light.common.exception;

/**
 * 系统内部异常
 */
public class LightException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public LightException(String message) {
        super(message);
    }
}
