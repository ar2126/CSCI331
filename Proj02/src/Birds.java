/**
 * Class Birds.java is an Object representation of the tuples: status, resource, where status is the strategy of the
 * given individual and resource is the total amount of resources acquired
 *
 * @author Aidan Rubenstein
 * @version 04-Apr-2018
 */
public class Birds implements Comparable<Birds>{
    protected String status;
    protected int resource;

    public Birds(String status, int resource){
        this.status = status;
        this.resource = resource;
    }

    public int getResource(){
        return resource;
    }

    @Override
    public int compareTo(Birds birds){
        int compareResource = ((Birds) birds).getResource();
        return compareResource - this.resource;
    }
}
