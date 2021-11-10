
package TDAs;

import java.util.Iterator;

public class CircularDoublyLinkedList<E> implements List<E>{

    private CircularDoublyNodeList<E> last;
   
    CircularDoublyNodeList<E> getLast(){
        return last;
    }
    
    CircularDoublyNodeList<E> getFirst(){
        return last.getNext();
    }
    
    @Override
    public boolean addFirst(E element) {
        if(element != null){
            if(size() == 0) {
                last = new CircularDoublyNodeList();
                last.setContent(element);
            }
            else{
                CircularDoublyNodeList<E> node = new CircularDoublyNodeList();
                node.setContent(element);
                node.setNext(last.getNext());
                node.setPrevious(last);
                last.getNext().setPrevious(node);
                last.setNext(node);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addLast(E element) {
        if(addFirst(element)){
            last = last.getNext();
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        int counter = (last != null)? 1: 0;
        if(counter == 0) return counter;
        
        CircularDoublyNodeList<E> node = last.getNext();
        while(node != last){
            node = node.getNext();
            counter++;
        }
        return counter;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) throw new EmptyListException();
        
        CircularDoublyNodeList<E> node = last.getNext();
        last.setNext(node.getNext());
        node.getNext().setPrevious(last);
        
        return node.getContent();
    }

    @Override
    public E removeLast() {
        if(isEmpty()) throw new EmptyListException();
        
        CircularDoublyNodeList<E> node = last;
        last.getPrevious().setNext(last.getNext());
        last.getNext().setPrevious(last.getPrevious());
        
        last = last.getPrevious();
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
        
        CircularDoublyNodeList<E> node = last.getNext();
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
        
        CircularDoublyNodeList<E> new_node = new CircularDoublyNodeList();
        new_node.setContent(element);
        
        CircularDoublyNodeList<E> node = last.getNext();
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
        return new Iterator<E>(){
            
            CircularDoublyNodeList<E> node = last.getNext();
            
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
        
        CircularDoublyNodeList<E> node = last.getNext();
        for(int i = 0; i<index; i++){
            node = node.getNext();
        }
        
        return node.getContent();
    }
    
    @Override
    public void clear(){
        if(isEmpty()) return;
        
        last = null;
    }
    
    @Override
    public String toString(){
        if(isEmpty()) return "[Empty List]";
        CircularDoublyNodeList<E> node = last.getNext();
        String s = "["+node.getContent().toString();
        
        while(node != last){
            node = node.getNext();
            s += ", "+node.getContent().toString();
        }
        return s+"]";
    }
    
    public void moveForward(){
        last = last.getPrevious();
    }
    
    public void moveBackwards(){
        last = last.getNext();
    }
}

class CircularDoublyNodeList<E> {
    
    private E content;
    private CircularDoublyNodeList<E> next;
    private CircularDoublyNodeList<E> previous;
    
    public CircularDoublyNodeList(){
        next = this;
        previous = this;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }

    public CircularDoublyNodeList<E> getNext() {
        return next;
    }

    public void setNext(CircularDoublyNodeList<E> next) {
        this.next = next;
    }
    
    public CircularDoublyNodeList<E> getPrevious() {
        return previous;
    }

    public void setPrevious(CircularDoublyNodeList<E> previous) {
        this.previous = previous;
    }
    
}