package rts.gameplay.actions;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class RTSActionTarget {

    public enum RTSActionTargetType {

        Single, Multi, Area, Point, ClassBase, Conditional
    }
    RTSActionTargetType type;
}
