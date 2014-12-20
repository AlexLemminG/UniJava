package basicobjects;

/**
 * Created by Lemming on 31.07.2014.
 */
public class GObjectPair implements Comparable<GObjectPair>{
    GObject a, b;

    public GObjectPair(GObject a, GObject b) {
        if(a.ID <= b.ID) {
            this.a = a;
            this.b = b;
        }else{
            this.a = b;
            this.b = a;
        }
    }


    @Override
    public int compareTo(GObjectPair o) {
        if(a.compareTo(o.a) != 0)
            return a.compareTo(o.a);
        else
            return b.compareTo(o.b);
    }
}
