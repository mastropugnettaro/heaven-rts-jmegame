package rts.core.conditions;

/**
 *
 * @author hungcuong
 */
public interface GenericCondition<T> {
    public abstract boolean accept(T obj);
}
