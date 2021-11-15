
package TDAs;

public interface List<E> extends Iterable<E> {
    
    boolean addFirst(E Element);
    boolean addLast(E Element);
    
    int size();
    
    E removeFirst();
    E removeLast();
    
    boolean isEmpty();
    
    E remove(int index);
    E get(int index);
    
    boolean add(E Element, int index);
    void clear();
    
    int indexOf(E Element);
    
}
