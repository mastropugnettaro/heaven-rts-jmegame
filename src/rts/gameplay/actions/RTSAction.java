package rts.gameplay.actions;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSAction {
    public enum RTSActionType{Targeted,NonTarget}
    RTSActionType type;
    
    String name;
    String icon;
    boolean isEnable = false;
    float recoverTime = 0;
}
