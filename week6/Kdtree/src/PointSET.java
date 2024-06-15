import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> point2DTreeSet;
    public PointSET(){
        this.point2DTreeSet = new TreeSet<Point2D>();
    }
    public boolean isEmpty(){
        return this.point2DTreeSet.isEmpty();
    }
    // number of points in the set
    public  int size(){
        return this.point2DTreeSet.size();
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        point2DTreeSet.add(p);
    }
    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        return point2DTreeSet.contains(p);
    }
    public void draw(){
        StdDraw.setPenRadius(0.015);
        StdDraw.setPenColor(StdDraw.BLUE);
        this.point2DTreeSet.forEach(t-> StdDraw.point(t.x(),t.y()));
        StdDraw.show();
    }
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null) throw new IllegalArgumentException();
        TreeSet<Point2D> pointInRectHv = new TreeSet<Point2D>();
        this.point2DTreeSet.forEach(t->{
            if(rect.contains(t)) pointInRectHv.add(t);
        });
        return pointInRectHv;
    }
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        if(this.point2DTreeSet.isEmpty()) return null;
        Point2D neighbour = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Point2D candidate : point2DTreeSet) {
            double distance = p.distanceSquaredTo(candidate);
            if (distance < minDistance) {
                neighbour = candidate;
                minDistance = distance;
            }
        }

        return neighbour;
    }
    // unit testing of the methods (optional)
    public static void main(String[] args){

    }
}