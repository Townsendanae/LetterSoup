
package TDAs;

import java.util.Comparator;
import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E>{

    private DoublyNodeList<E> last;
    private DoublyNodeList<E> first;
   
    DoublyNodeList<E> getLast(){
        return last;
    }
    
    DoublyNodeList<E> getFirst(){
        return first;
    }
    
    @Override
    public boolean addFirst(E element) {
        if(element != null){
            if(size() == 0) {
                first = new DoublyNodeList();
                first.setContent(element);
                last = first;
            }
            else{
                DoublyNodeList<E> node = new DoublyNodeList();
                node.setContent(element);
                node.setNext(first);
                first.setPrevious(node);
                first = node;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addLast(E element) {
        if(element != null){
            if(size() == 0) {
                first = new DoublyNodeList();
                first.setContent(element);
                last = first;
            }
            else{
                DoublyNodeList<E> node = new DoublyNodeList();
                node.setContent(element);
                node.setPrevious(last);
                last.setNext(node);
                last = node;
            }
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        int counter = (first != null)? 1: 0;
        if(counter == 0) return counter;
        
        DoublyNodeList<E> node = first;
        while(node != last){
            node = node.getNext();
            counter++;
        }
        return counter;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) throw new EmptyListException();
        
        DoublyNodeList<E> node = first;
        first = node.getNext();
        first.setPrevious(null);
        
        return node.getContent();
    }

    @Override
    public E removeLast() {
        if(isEmpty()) throw new EmptyListException();
        
        DoublyNodeList<E> node = last;
        last = node.getPrevious();
        last.setNext(null);
        
        return node.getContent();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public E remove(int index) {
        if(isEmpty()) throw new EmptyListException();
        if(index > size()-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == 0) return removeFirst();
        if(index == -1 || index == size()-1) return removeLast();
        
        DoublyNodeList<E> node = first;
        for(int i = 0; i<index; i++){
            node = node.getNext();
        }
        
        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
        
        return node.getContent();
    }

    @Override
    public boolean add(E element, int index) {
        if(element == null) return false;
        if(index > size()-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == 0) return addFirst(element);
        if(index == -1 || index == size()-1) return addLast(element);
        
        DoublyNodeList<E> new_node = new DoublyNodeList();
        new_node.setContent(element);
        
        DoublyNodeList<E> node = first;
        for(int i = 0; i<index; i++){
            node = node.getNext();
        }
        
        new_node.setPrevious(node.getPrevious());
        node.getPrevious().setNext(new_node);
        new_node.setNext(node);
        node.setPrevious(new_node);
        
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        if(isEmpty()) throw new EmptyListException();
        return new Iterator<E>(){
            
            DoublyNodeList<E> node = first;
            
            @Override
            public boolean hasNext(){
                return node != null;
            }
            
            @Override
            public E next(){
                if(node == last){
                    node = null;
                    return last.getContent();
                }
                node = node.getNext();
                
                return node.getPrevious().getContent();
            }
        
        };
    }

    @Override
    public E get(int index) {
        if(isEmpty()) throw new EmptyListException();
        if(index > size()-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == 0) return getFirst().getContent();
        if(index == -1) return getLast().getContent();
        
        DoublyNodeList<E> node = first;
        for(int i = 0; i<index; i++){
            node = node.getNext();
        }
        
        return node.getContent();
    }
    
    @Override
    public E set(int index, E e){
        if(isEmpty()) throw new EmptyListException();
        if(index > size()-1 || index < -1) throw new IndexOutOfBoundsException("Índice no admitido.");
        if(index == -1) index = size()-1;
        
        DoublyNodeList<E> node = first;
        for(int i = 0; i<index; i++){
            node = node.getNext();
        }
        
        E replaced_element = node.getContent();
        node.setContent(e);
        
        return replaced_element;
    }
    
    @Override
    public void clear(){
        if(isEmpty()) return;
        
        first = last = null;
    }
    
    @Override
    public String toString(){
        if(isEmpty()) return "[Empty List]";
        DoublyNodeList<E> node = first;
        String s = "["+node.getContent().toString();
        
        while(node != last){
            node = node.getNext();
            s += ", "+node.getContent().toString();
        }
        return s+"]";
    }

    @Override
    public int indexOf(E e) {
        if(e == null) return -2;
        if(isEmpty()) throw new EmptyListException();
        if(e == last.getContent()) return size()-1;
        
        DoublyNodeList<E> node = first;
        int index = 0;
        while(node != last){
            if(node.getContent() == e) return index;
            index++;
            node = node.getNext();
        }
        
        return -2;
    }
    
    @Override
    public boolean contains(E e){
        if(isEmpty()) return false;
        DoublyNodeList<E> node = first;
        while(node!=null){
            if(e.equals(node.getContent())) return true;
            node = node.getNext();
        }
        return false;
    }
    
    public boolean contains(E e, Comparator<E> cmp){
        if(isEmpty()) return false;
        DoublyNodeList<E> node = first;
        while(node!=null){
            if(cmp.compare(e,node.getContent()) == 0) return true;
            node = node.getNext();
        }
        return false;
    }
}

class DoublyNodeList<E> {
    
    private E content;
    private DoublyNodeList<E> next;
    private DoublyNodeList<E> previous;
    
    public DoublyNodeList(){
        next = null;
        previous = null;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public DoublyNodeList<E> getNext() {
        return next;
    }

    public void setNext(DoublyNodeList<E> next) {
        this.next = next;
    }
    
    public DoublyNodeList<E> getPrevious() {
        return previous;
    }

    public void setPrevious(DoublyNodeList<E> previous) {
        this.previous = previous;
    }
    
}
