package android.pentodroid.model;

public class Pair<T1,T2> {

    private T1 v1;
    private T2 v2;
    
    Pair(T1 v1, T2 v2) {
	this.v1 = v1;
	this.v2 = v2;
    }

    public T1 fst() { return v1; }
    public T2 snd() { return v2; }

    @SuppressWarnings("unchecked") // Safe because of 'instanceof'
    public boolean equals(Object o) {
	return
	    (o instanceof Pair<?,?>)
	    && v1.equals(((Pair<T1,T2>)o).v1)
	    && v2.equals(((Pair<T1,T2>)o).v2);
    }

}
