package exceptions;

public class NoSlotFoundException extends RuntimeException {
    public NoSlotFoundException(String errorMsg){
        super(errorMsg);
    }
}