import java.util.*;
import java.io.*;

public class Heap<P extends Comparable <P>> {
    ArrayList<P> value = new ArrayList<>();
    HashMap<P,Integer> Vmap = new HashMap<>();
    public void upHeapify(int cIdx) {
        int pIdx = (cIdx-1)/2;
        if(larger(value.get(cIdx),value.get(pIdx)) > 0) {
            swap(pIdx,cIdx);
            upHeapify(pIdx);
        }
    }
    public void downHeapify(int pIdx) {
        int lcIdx = (2*pIdx) + 1;
        int rcIdx = (2*pIdx) + 2;
        int idx = pIdx;
        if (lcIdx < this.value.size() && larger(value.get(lcIdx), value.get(idx)) > 0) {
            idx = lcIdx;
        }
        if (rcIdx < this.value.size() && larger(value.get(rcIdx), value.get(idx)) > 0) {
            idx = rcIdx;
        }
        if (idx != pIdx) {
            swap(idx, pIdx);
            downHeapify(idx);
        }
    }
    public void swap(int first, int second) {
        P a = value.get(first);
        P b = value.get(second);
        value.set(first,b);
        value.set(second,a);
        Vmap.put(a,second);
        Vmap.put(b,first);
    }
    public void add(P val) {
        value.add(val);
        Vmap.put(val,this.value.size()-1);
        upHeapify(value.size()-1);
    }
    public int larger(P p1, P p2) {
        return p1.compareTo(p2);
    }
    public void show() {
        System.out.println(value);
    }
    public int getSize() {
        return this.value.size();
    }
    public boolean isEmpty() {
        return this.value.size() == 0;
    }
    public P remove() {
        swap(0, this.value.size() - 1);
        P p = this.value.remove(this.value.size() - 1);
        downHeapify(0);

        Vmap.remove(p);
        return p;
    }
    public P get() {
        return this.value.get(0);
    }
    public void update(P pair) {
        int idx = Vmap.get(pair);
        upHeapify(idx);
    }
}
