
package TDAs;

import java.util.Iterator;

public class ArrayList<E> implements List<E>{

    private int effectiveSize;
    private int capacity;
    private E[] elements;
    
    public ArrayList(int capacity){
        this.capacity = capacity;
        elements = (E[]) (new Object[capacity]);
        effectiveSize = 0;
    }
    
    private boolean isFull(){
        return effectiveSize==capacity;
    }
    
    private void addCapacity(){
        capacity += capacity/2;
        E[] new_array = (E[]) (new Object[capacity]);
        System.arraycopy(elements, 0, new_array, 0, effectiveSize);
        
        elements = new_array;
    }
    
    public ArrayList(){
        this(18);
    }
    
    @Override
    public boolean addFirst(E e) {
        
        if (e == null){
            return false;
            
        } else if(isEmpty()){
            elements[effectiveSize++] = e;
            return true;
           
        } else {
            
            if(isFull()) addCapacity();
            
            for(int i = effectiveSize-1; i>=0; i--){
                elements[i+1] = elements[i];
            }
            elements[0] = e;
            effectiveSize++;
            return true;
        }
        
    }

    @Override
    public boolean addLast(E e) {
        
        if (e == null){
            return false;
            
        } else if(isEmpty()){
            elements[effectiveSize++] = e;
            return true;
           
        } else {
            
            if(isFull()) addCapacity();
            
            elements[effectiveSize++] = e;
            return true;
        }
        
    }

    @Override
    public int size() {
        return effectiveSize;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) throw new EmptyListException();
        
        E e = elements[0];
        
        for(int i = 0; i<effectiveSize-2; i++){
            elements[i] = elements[i+1];
        }
        
        elements[--effectiveSize] = null;
            
        return e;
    }

    @Override
    public E removeLast() {
        if(isEmpty()) throw new EmptyListException();
        
        E e = elements[effectiveSize-1];
        elements[--effectiveSize] = null;
        
        return e;
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize==0;
    }

    @Override
    public E remove(int index) {
        if(isEmpty()) throw new EmptyListException();
        if(index > effectiveSize-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == 0) return removeFirst();
        if(index == -1 || index == effectiveSize-1) return removeLast();
        
        E e = elements[index];
        
        for(int i = index; i<effectiveSize-2; i++){
            elements[i] = elements[i+1];
        }
        
        elements[--effectiveSize] = null;
        return e;
    }

    @Override
    public E get(int index) {
        
        if(isEmpty()) throw new EmptyListException();
        if(index > effectiveSize-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == 0) return elements[0];
        if(index == -1 || index == effectiveSize-1) return elements[effectiveSize-1];
        
        return elements[index];
        
    }
    
    @Override
    public E set(int index, E e){
        E replaced_element = get(index);
        elements[index] = e;
        return replaced_element;
    }

    @Override
    public boolean add(E e, int index) {
        if(e == null) return false;
        if(isEmpty()) throw new EmptyListException();
        if(index > effectiveSize-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == 0) return addFirst(e);
        if(index == -1 || index == effectiveSize-1) return addLast(e);
        
        if(isFull()) addCapacity();
        
        for(int i = effectiveSize-1; i>=index; i--){
           elements[i+1] = elements[i];
        }
        
        elements[index] = e;
        return true;
    }

    @Override
    public void clear() {
        if(isEmpty()) return;
        capacity = effectiveSize;
        elements = (E[]) new Object[capacity];
        effectiveSize = 0;
    }

    @Override
    public int indexOf(E e) {
        if(e == null) return -2;
        if(isEmpty()) throw new EmptyListException();
        if(e == elements[effectiveSize-1]) return effectiveSize-1;
        
        for(int i = 0; i<effectiveSize-1; i++){
            if(elements[i] == e) return i;
        }
        
        return -2;
    }
    
    @Override
    public boolean contains(E e){
        for(int i = 0; i<effectiveSize; i++){
            if(elements[i].equals(e)) return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        String s = "";
        for (int i = 0;i < effectiveSize; i++){
            s += elements[i] + ", ";
        }
        return s;
    }
    
    @Override
    public Iterator<E> iterator() {
        
        Iterator<E> it = new Iterator<E>(){
            
            int cursor = 0;
            
            @Override
            public boolean hasNext(){
                return cursor<effectiveSize;
            }
            
            @Override
            public E next(){
                return elements[cursor++];
            }
            
        };
        return it;
    }
    
}
