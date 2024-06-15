import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;



public class KdTree {
    private Node root;
    private int size;

    public KdTree(){
        this.root = null;
    }
    private static class Node{
        private final Point2D point;
        private Node left,right;
        private boolean useX;
        public Node(Point2D p,Node lt,Node rt,boolean alt){
            this.point = p;
            this.left = lt;
            this.right = rt;
            this.useX = alt;
        }

    }
    public boolean isEmpty(){
        return this.root==null;
    }
    // number of points in the set
    public int size(){
        return this.size;
    }
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        if(this.root==null){
            this.size+=1;
            this.root = new Node(p,null,null,true);
        }
        else if(!this.contains(p)){
            this.size+=1;
            insert(this.root,p);
        }
    }
    private void insert(Node parentNode,Point2D point){
        Comparator<Point2D> comparator = parentNode.useX ? Point2D.X_ORDER : Point2D.Y_ORDER;
        if(comparator.compare(parentNode.point,point)>0){
            if(parentNode.left==null){
                parentNode.left = new Node(point,null,null,!parentNode.useX);
            }
            else{
                insert(parentNode.left,point);
            }
        }
        else{
            if(parentNode.right==null){
                parentNode.right = new Node(point,null,null,!parentNode.useX);
            }
            else{
                insert(parentNode.right,point);
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        return contains(root,p);
    }
    private boolean contains(Node root,Point2D p){
        if(root==null)return false;
        if(root.point.equals(p))return true;

        Comparator<Point2D> comp = root.useX? Point2D.X_ORDER: Point2D.Y_ORDER;
        if(comp.compare(root.point,p)>0) return contains(root.left,p);
        else return contains(root.right,p);
    }

    public void draw(){
        draw(root,new RectHV(0,0,1,1));
    }
    private void draw(Node n,RectHV rect){
        if(n==null)return;

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        n.point.draw();

        if (n.useX) { // vertical
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.point.x(), rect.ymin(), n.point.x(), rect.ymax());
            draw(n.left, new RectHV(rect.xmin(), rect.ymin(), n.point.x(), rect.ymax()));
            draw(n.right, new RectHV(n.point.x(), rect.ymin(), rect.xmax(), rect.ymax()));
        }
        else { // horizontal
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), n.point.y(), rect.xmax(), n.point.y());
            draw(n.left, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), n.point.y()));
            draw(n.right, new RectHV(rect.xmin(), n.point.y(), rect.xmax(), rect.ymax()));
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null) throw new IllegalArgumentException();
        ArrayList<Point2D> result = new ArrayList<Point2D>();
        range(root,rect,result);
        return result;
    }
    private void range(Node current,RectHV rect, ArrayList<Point2D>points){
        if (current == null)
            return;

        if (rect.contains(current.point))
            points.add(current.point);

        if (current.useX) { // vertical
            if (current.point.x() >= rect.xmin() && current.point.x() <= rect.xmax()) {
                range(current.left, rect, points);
                range(current.right, rect, points);
            }
            else if (current.point.x() <= rect.xmin()) {
                range(current.right, rect, points);
            }
            else if (current.point.x() >= rect.xmax()) {
                range(current.left, rect, points);
            }
        }
        else { // horizontal
            if (current.point.y() >= rect.ymin() && current.point.y() <= rect.ymax()) {
                range(current.left, rect, points);
                range(current.right, rect, points);
            }
            else if (current.point.y() <= rect.ymin()) {
                range(current.right, rect, points);
            }
            else if (current.point.y() >= rect.ymax()) {
                range(current.left, rect, points);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p==null) throw new IllegalArgumentException();
        return nearest(root,root,p,new RectHV(0,0,1,1));
    }
    private Point2D nearest(Node current,Node currentMin,Point2D p,RectHV nodeRect){
        if(current==null) return currentMin == null ? null : currentMin.point;

        double distanceCurrent = p.distanceSquaredTo(current.point);
        double minDistance = p.distanceSquaredTo(currentMin.point);
        if(distanceCurrent<minDistance){
            currentMin = current;
        }
        RectHV leftRect, rightRect;
        if(current.useX){
            leftRect = new RectHV(nodeRect.xmin(),nodeRect.ymin(),current.point.x(),
                    nodeRect.ymax());
            rightRect = new RectHV(current.point.x(), nodeRect.ymin(), nodeRect.xmax(),
                    nodeRect.ymax());
        }
        else{
            leftRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(),
                    current.point.y());
            rightRect = new RectHV(nodeRect.xmin(), current.point.y(), nodeRect.xmax(),
                    nodeRect.ymax());
        }

        Point2D rightNearest = nearest(current.right,currentMin,p,rightRect);
        Point2D leftNearest = nearest(current.left,currentMin,p,leftRect);

        return p.distanceSquaredTo(rightNearest) < p.distanceSquaredTo(leftNearest)?
                rightNearest : leftNearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args){

    }
}
