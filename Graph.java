import javax.swing.*;
import java.util.*;
import java.io.*;

public class Graph {
    public class Vertex
    {
        HashMap<String, Integer> Vmap = new HashMap<>();
    }
    static HashMap<String, Vertex> Gmap;
    public Graph()
    {
        Gmap = new HashMap<>();
    }
    public boolean containsVertex(String vtx)
    {
        return this.Gmap.containsKey(vtx);
    }
    public int numberOfVetex()
    {
        return this.Gmap.size();
    }
    public void removeVertex(String vtx)
    {
        Vertex v = Gmap.get(vtx);
        ArrayList<String> Vlist = new ArrayList<>(v.Vmap.keySet());
        for (String s : Vlist)
        {
            Vertex rmVtx = Gmap.get(s);
            rmVtx.Vmap.remove(vtx);
        }

        Gmap.remove(vtx);
    }
    public void addVertex(String vtx)
    {
        Vertex v = new Vertex();
        Gmap.put(vtx, v);
    }
    public int numberOfEdges() {
        ArrayList<String> Elist = new ArrayList<>(Gmap.keySet());
        int edges = 0;
        for(String s : Elist) {
            Vertex v = Gmap.get(s);
            edges += v.Vmap.size();
        }
        int res = edges/2;
        return res;
    }
    public void addEdges(String vtx1, String vtx2, int dist) {
        Vertex v1 = Gmap.get(vtx1);
        Vertex v2 = Gmap.get(vtx2);
        if(v1 == null || v2 == null) {
            return;
        }
        if(v1.Vmap.containsKey(vtx2)) {
            return;
        }
        v1.Vmap.put(vtx2,dist);
        v2.Vmap.put(vtx1,dist);
    }
    public void removeEdges(String vtx1, String vtx2) {
        Vertex v1 = Gmap.get(vtx1);
        Vertex v2 = Gmap.get(vtx2);
        if(v1 == null || v2 == null) {
            return;
        }
        if(!v1.Vmap.containsKey(vtx2)) {
            return;
        }
        v1.Vmap.remove(vtx2);
        v2.Vmap.remove(vtx1);
    }
    public boolean containsEdges(String vtx1, String vtx2) {
        Vertex v1 = Gmap.get(vtx1);
        Vertex v2 = Gmap.get(vtx2);
        if(v1 == null || v2 == null) {
            return false;
        }
        if(!v1.Vmap.containsKey(vtx2)) {
            return false;
        }
        return true;
    }
    public String showStations() {
        int num = 1;
        ArrayList<String> Slist = new ArrayList<>(Gmap.keySet());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Slist.size(); i++) {
            sb.append(num++ + ": " + Slist.get(i)).append("\n");
        }
        return sb.toString();
    }
    public String display_Map()
    {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> keys = new ArrayList<>(Gmap.keySet());
        int count = 1;
        for (String key : keys)
        {
            String str = key + " =>\n";
            Vertex vtx = Gmap.get(key);
            ArrayList<String> vtxnbrs = new ArrayList<>(vtx.Vmap.keySet());

            for (String s : vtxnbrs)
            {
                str = str + "\t" + s + "\t";
                if (s.length()<16)
                    str = str + "\t";
                if (s.length()<8)
                    str = str + "\t";
                str = str + vtx.Vmap.get(s) + "\n";
            }
            sb.append(count++ + ": " + str).append("\n");
        }
        return sb.toString();
    }
    private class Pair {
        String vtx;
        String str;
        int minDist;
        int minTime;
    }
    private class DPair implements Comparable<DPair>{
        String vname;
        String psf;
        int cost;
        public int compareTo(DPair p) {
            return p.cost - this.cost;
        }
    }
    public int dijAlgo(String src, String des, boolean b) {
        int val = 0;
        ArrayList<String> ans = new ArrayList<>();
        HashMap<String, DPair> map = new HashMap<>();

        Heap<DPair> heap = new Heap<>();

        for (String key : Gmap.keySet()) {
            DPair np = new DPair();
            np.vname = key;
            np.cost = Integer.MAX_VALUE;

            if (key.equals(src)) {
                np.cost = 0;
                np.psf = key;
            }

            heap.add(np);
            map.put(key, np);
        }

        // keep removing the pairs while heap is not empty
        while (!heap.isEmpty()) {
            DPair rp = heap.remove();

            if (rp.vname.equals(des)) {
                val = rp.cost;
                break;
            }

            map.remove(rp.vname);
            ans.add(rp.vname);

            Vertex v = Gmap.get(rp.vname);
            for (String nbr : v.Vmap.keySet()) {
                if (map.containsKey(nbr)) {
                    int oc = map.get(nbr).cost;
                    Vertex k = Gmap.get(rp.vname);
                    int nc = rp.cost + k.Vmap.get(nbr);

                    if (nc < oc) {
                        DPair gp = map.get(nbr);
                        gp.psf = rp.psf + nbr;
                        gp.cost = nc;

                        heap.update(gp);
                    }
                }
            }
        }
        return val;
    }

    public boolean dfsPath(String vtx1, String vtx2, HashMap<String,Boolean> visited) {
        if(containsEdges(vtx1,vtx2)) {
            return true;
        }
        visited.put(vtx1,true);
        Vertex v = Gmap.get(vtx1);
        ArrayList<String> nbrs = new ArrayList<>(v.Vmap.keySet());
        for (String vtx : nbrs) {
            if (!visited.containsKey(vtx)) {
                if (dfsPath(vtx, vtx2, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    public String getMinimumDistance(String start, String finish) {
        int min = Integer.MAX_VALUE;
        String res = "";
        HashMap<String, Boolean> visited = new HashMap<>();
        LinkedList<Pair> st = new LinkedList<>();
        Pair sp = new Pair();
        sp.vtx = start;
        sp.str = start + "  ";
        sp.minDist = 0;
        sp.minTime = 0;
        st.addFirst(sp);
        while (!st.isEmpty()) {
            Pair p1 = st.removeFirst();
            if (visited.containsKey(p1.vtx)) {
                continue;
            }
            visited.put(p1.vtx, true);
            if (p1.vtx.equals(finish)) {
                int temp = p1.minDist;
                if (temp < min) {
                    res = p1.str;
                    min = temp;
                }
                continue;
            }

            Vertex v1 = Gmap.get(p1.vtx);
            ArrayList<String> Vlist = new ArrayList<>(v1.Vmap.keySet());
            for (String s : Vlist) {
                if (!visited.containsKey(s)) {
                    int totalDist = p1.minDist + v1.Vmap.get(s);
                    Pair p2 = new Pair();
                    p2.vtx = s;
                    p2.str = p1.str + s + "  ";
                    p2.minDist = totalDist;
                    st.addFirst(p2);
                }
            }
        }
        res += Integer.toString(min);
        return res;
    }
    public String getMinimumTime(String start, String finish) {
        int min = Integer.MAX_VALUE;
        String res = "";
        HashMap<String,Boolean> visited = new HashMap<>();
        LinkedList<Pair> st = new LinkedList<>();
        Pair sp = new Pair();
        sp.vtx = start;
        sp.str = start + "  ";
        sp.minDist = 0;
        sp.minTime = 0;
        st.addFirst(sp);
        while (!st.isEmpty()) {
            Pair p = st.removeFirst();
            if (visited.containsKey(p.vtx)) {
                continue;
            }
            visited.put(p.vtx,true);
            if (p.vtx.equals(finish)) {
                int temp = p.minTime;
                if(temp<min) {
                    res = p.str;
                    min = temp;
                }
                continue;
            }
            Vertex v = Gmap.get(p.vtx);
            ArrayList<String> list = new ArrayList<>(v.Vmap.keySet());
            for (String s : list) {
                if (!visited.containsKey(s)) {
                    Pair p1 = new Pair();
                    p1.vtx = s;
                    p1.str = p.str + s + "  ";
                    p1.minTime = p.minTime + 120 + 40*v.Vmap.get(s);
                    st.addFirst(p1);
                }
            }
        }
        Double mins = Math.ceil((double)min / 60);
        res += Double.toString(mins);
        return res;
    }
    public ArrayList<String> getInterchanges(String strr) {
        ArrayList<String> resList = new ArrayList<>();
        String res[] = strr.split(" ");
        resList.add(res[0]);

        for (int i = 1; i < res.length; i++) {
            String curr = res[i];
            String prev = res[i - 1];

            if (!curr.equals(prev)) {
                resList.add(res[i]);
            }
        }
        return resList;
    }
    public String getPath(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> str = getInterchanges(getMinimumDistance(s1, s2));
        int len = str.size();
        for(int i=0; i<len-3; i++) {
            sb.append(" ").append(str.get(i));
        }
        sb.append(" ").append(str.get(len - 3));
        return sb.toString();
    }
    public static void createGraph(Graph g) {
        g.addVertex("Betiah");
        g.addVertex("Motihari");
        g.addVertex("Sitamarhi");
        g.addVertex("Samastipur");
        g.addVertex("Gopalganj");
        g.addVertex("Siwan");
        g.addVertex("Patna");
        g.addVertex("Muzzafarpur");
        g.addVertex("Darbhanga");
        g.addVertex("Gaya");
        g.addVertex("Madhubani");
        g.addVertex("Chapra");
        g.addVertex("Buxar");
        g.addVertex("Ara");
        g.addVertex("Arawal");
        g.addVertex("Bihar Sharif");
        g.addVertex("Begusarai");
        g.addVertex("Saharsa");
        g.addVertex("Munger");
        g.addVertex("Bhagalpur");

        g.addEdges("Betiah", "Motihari", 56);
        g.addEdges("Motihari", "Muzzafarpur", 74);
        g.addEdges("Samastipur", "Muzzafarpur", 81);
        g.addEdges("Samastipur", "Darbhanga", 39);
        g.addEdges("Sitamarhi", "Darbhanga", 92);
        g.addEdges("Darbhanga", "Chapra", 90);
        g.addEdges("Chapra", "Patna", 76);
        g.addEdges("Ara", "Patna", 55);
        g.addEdges("Buxar", "Ara", 60);
        g.addEdges("Gaya", "Patna", 101);
        g.addEdges("Arawal", "Gaya", 65);
        g.addEdges("Chapra", "Siwan", 24);
        g.addEdges("Siwan", "Gopalganj", 56);
        g.addEdges("Sitamarhi", "Begusarai", 150);
        g.addEdges("Bihar Sharif", "Begusarai", 114);
        g.addEdges("Gaya", "Bihar Sharif", 137);
        g.addEdges("Saharsa", "Begusarai", 156);
        g.addEdges("Munger", "Saharsa", 173);
        g.addEdges("Bhagalpur", "Munger", 60);
        g.addEdges("Madhubani", "Darbhanga", 38);
        g.addEdges("Betiah","Gopalganj",47);

    }
    public static void main(String[] args) {
        Graph g = new Graph();
        createGraph(g);
        String st1 = "Gaya";
        String st2 = "Gopalganj";
        String ans1 = g.getPath(st1,st2);
        System.out.println(ans1);
        HashMap<String, Boolean> visited = new HashMap<>();

        if (!g.dfsPath(st1, st2, visited)) {
            System.out.println("Error");
        } else {
            int res = g.dijAlgo(st1, st2, false);
            System.out.println(res);

            ArrayList<String> str = g.getInterchanges(g.getMinimumDistance(st1, st2));
            int len = str.size();

            StringBuilder ans = new StringBuilder();
            for (int i = 0; i < len - 1; i++) {
                ans.append(str.get(i)).append(" ==> ");
            }

            ans.append(str.get(len - 1));

            System.out.println(ans.toString());

            String s = g.display_Map();
            System.out.println(s);
        }
    }
}
