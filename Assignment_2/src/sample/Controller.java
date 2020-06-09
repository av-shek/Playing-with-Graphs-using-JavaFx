package sample;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

//import java.awt.Polygon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.StrictMath.abs;


public class Controller {
    @FXML
    AnchorPane graph;

    ArrayList<AdjList> list=new ArrayList<>();
    @FXML
    TextField name1,X1,Y1;
    @FXML
    TextArea output;
    @FXML
    public void addV(ActionEvent E)
    {
        try {
            String name = name1.getText();
            double X = Double.parseDouble(X1.getText());
            double Y = Double.parseDouble(Y1.getText());

            Vertex v = new Vertex(name, X, Y);

            boolean stored1 = false;
            for (int p = 0; p < list.size(); p++) {
                if (list.get(p).v.name.equals(name)) {
                    stored1 = true;
                }
            }
            if (!stored1) {
                list.add(new AdjList());
                list.get(list.size() - 1).addVertex(v);
                output.setText("Vertex "+"'"+name+"'"+" with coordinates "+"("+X+","+Y+")"+" added successfully.\n");
            }
            else
            {
                output.setText("Vertex already exists");
            }
            drawGraph();
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Invalid Vertex Details");
            alert.setContentText("Please Enter Valid Details");
            alert.showAndWait();
        }
    }
    @FXML
    TextField name2;
    @FXML
    public void delV(ActionEvent E) {
        try {
            String name = name2.getText();
            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(name)) {
                    //list.get(i).adjLists.remove(i);
                    flag = 1;
                    list.remove(i);
                    drawGraph();

                }
            }
            for(int i1=0;i1<list.size();i1++){
                for (int j=0;j<list.get(i1).adjLists.size();j++) {
                    if (list.get(i1).adjLists.get(j).to_name.equals(name)) {
                        flag=1;
                        list.get(i1).adjLists.remove(j);
                        drawGraph();

                    }
                }
            }
            if (flag == 1) {
                output.setText("Vertex " + "'" + name + "'" + "deleted successfully.\n");
            } else {
                throw new Exception();
            }

        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Vertex does not exist");
            alert.setContentText("Please Enter Valid Vertex");
            alert.showAndWait();
        }
    }
    @FXML
    TextField name3,X3,Y3;
    @FXML
    public void modV(ActionEvent E)
    {
        try {
            String name = name3.getText();
            double X = Double.parseDouble(X3.getText());
            double Y = Double.parseDouble(Y3.getText());
            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(name)) {
                    flag = 1;
                    list.get(i).v.X = X;
                    list.get(i).v.Y = Y;
                }
            }
            if (flag == 1) {
                output.setText("Vertex " + "'" + name + "'" + " is modified with updated coordinates" + "(" + X + "," + Y + ")" + " successfully.\n");
            } else {
                throw new Exception();
            }
            drawGraph();
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Vertex does not exist");
            alert.setContentText("Please Enter Valid Details");
            alert.showAndWait();
        }
    }
    @FXML
    TextField name4;
    @FXML
    public void searchV(ActionEvent E)
    {
        try {
            String name = name4.getText();
            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(name)) {
                    flag = 1;
                    output.setText("Search successful\n\nVertex Name:" + list.get(i).v.name + "\n " + "X-Coordinate:" + list.get(i).v.X + " \n" + "Y-Coordinate:" + list.get(i).v.Y);
                }
            }
            if (flag == 0) {
                throw new Exception();
            }
            drawGraph();
        }
        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Vertex does not exist");
            alert.setContentText("Please Enter Valid Vertex");
            alert.showAndWait();
        }
    }
    @FXML
    TextField from_name1,to_name1,w1;
    @FXML
    public void addE(ActionEvent E)
    {
        try {
            String to_name = to_name1.getText();
            String from_name = from_name1.getText();
            double w = Double.parseDouble(w1.getText());
            Edge e = new Edge(from_name, to_name, w);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(from_name)) {
                    list.get(i).addEdge(e);
                    output.setText("Edge from" + "'" + from_name + "'" + " to" + "'" + to_name + "'" + " with weight " + w + " added successfully.\n");
                }
            }
            drawGraph();
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Invalid Edge Details");
            alert.setContentText("Please Enter Valid Details");
            alert.showAndWait();
        }
    }
    @FXML
    TextField from_name2,to_name2,w2;
    @FXML
    public void modE(ActionEvent E)
    {

        try {

            String from_name = from_name2.getText();
            String to_name = to_name2.getText();
            double w = Double.parseDouble(w2.getText());
            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(from_name)) {
                    for (Edge e : list.get(i).adjLists) {
                        if (e.to_name.equals(to_name)) {
                            flag = 1;
                            e.w = w;
                        }
                    }
                }
            }
            if (flag == 1) {
                output.setText("Edge from " + from_name + " to" + to_name + " modified successfully");
            } else {
                throw new Exception();
            }
            drawGraph();
        }
        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Edge does not exist");
            alert.setContentText("Please Enter Valid Details");
            alert.showAndWait();
        }
    }
    @FXML
    TextField from_name3,to_name3;
    @FXML
    public void searchE(ActionEvent E)
    {
        try {
            String to_name = to_name3.getText();
            String from_name = from_name3.getText();
            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(from_name)) {
                    for (Edge e : list.get(i).adjLists) {
                        if (e.to_name.equals(to_name)) {
                            flag = 1;
                            output.setText("Search successfull\n\nFrom_name:" + e.from_name + "\n " + "To_name:" + e.to_name + "\n " + "Weight:" + e.w);
                        }
                    }
                }
            }
            if (flag == 0) {
                throw new Exception();
            }
            drawGraph();
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Edge does not exist");
            alert.setContentText("Please Enter Valid Details");
            alert.showAndWait();
        }
    }
    @FXML
    TextField from_name4,to_name4,w4;
    @FXML
    public void delE(ActionEvent E)
    {
        try {
            String to_name = to_name4.getText();
            String from_name = from_name4.getText();
            int flag = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).v.name.equals(from_name)) {
                    for (int j=0;j<list.get(i).adjLists.size();j++) {
                        if (list.get(i).adjLists.get(j).to_name.equals(to_name)) {
                            flag = 1;
                            list.get(i).adjLists.remove(j);
                        }
                    }
                }
            }
            if (flag == 0) {
                throw new Exception();
            } else {
                output.setText("Edge from " + from_name + " to" + to_name + " deleted successfully");
            }
            drawGraph();
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Edge does not exist");
            alert.setContentText("Please Enter Valid Details");
            alert.showAndWait();
        }
    }
    @FXML
    TextField load,export;
    @FXML
    public void imp_graph(ActionEvent E) throws FileNotFoundException {
        try {
            String path = load.getText();
            File f = new File(path);
            if (f == null) {
                throw new Exception();
            }
            Scanner s = new Scanner(f);
            int vert = s.nextInt();
            String name;
            double X, Y;
            for (int i = 0; i < vert; i++) {
                name = s.next();
                X = s.nextDouble();
                Y = s.nextDouble();
                Vertex v = new Vertex(name, X, Y);

                boolean stored1 = false;
                for (int p = 0; p < list.size(); p++) {
                    if (list.get(p).v.name.equals(name)) {
                        stored1 = true;
                    }
                }
                if (!stored1) {
                    list.add(new AdjList());
                    list.get(list.size() - 1).addVertex(v);

                }
            }
            int edg = s.nextInt();
            String from_name, to_name;
            double w;
            for (int i = 0; i < edg; i++) {
                from_name = s.next();
                to_name = s.next();
                w = s.nextDouble();
                Edge e = new Edge(from_name, to_name, w);
                for (int i1 = 0; i1 < list.size(); i1++) {
                    if (list.get(i1).v.name.equals(from_name)) {
                        list.get(i1).addEdge(e);
                    }
                }
            }
            output.setText("Graph imported successfully");
            s.close();
            drawGraph();
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("File does not exist");
            alert.setContentText("Please Enter Valid Path");
            alert.showAndWait();
        }
    }
    @FXML
    public void exp_graph(ActionEvent E) throws IOException {
        try {
            Collections.sort(list, new C());
            for (int i = 0; i < list.size(); i++) {
                Collections.sort(list.get(i).adjLists, new C1());
            }
            String path = export.getText();
            File f = new File(path);
            FileWriter fw = new FileWriter(f);
            fw.write(list.size()+"\n");
            for(AdjList l:list)
            {
                fw.write(l.v.toString());
            }
            String str ="";
            int k=0;
            for (int i = 0; i < list.size(); i++) {
                for (Edge e : list.get(i).adjLists) {
                    k++;
                    str += e.toString();
                }
            }
            fw.write(k+"\n");
            fw.write(str);
            fw.close();
            output.setText("Graph exported successfully");

        }
        catch (Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Some error occured");

            alert.showAndWait();
        }
    }
    @FXML
    TextField from_name,to_name;
    @FXML
    public void dijkstra(ActionEvent E) {
        try {
            String src = from_name.getText();
            String dest = to_name.getText();
            int flag1 = 1,flag2=1;
            for (AdjList l:list) {
                if(l.v.name.equals(src))
                {
                    flag1=0;
                }
                if(l.v.name.equals(dest))
                {
                    flag2=0;
                }
            }
            if(flag1==1 && flag2==1)
            {
                throw new Exception();
            }
            Dijkstra z = new Dijkstra(src, dest);
            Stack<Edge> st = new Stack<>();
            Stack<Edge> st1 = new Stack<>();
            st = z.dijkstra(list);
            st1= z.dijkstra(list);
            drawPath(st1);
            if (st.isEmpty()) {
                output.setText("No path exists");
            } else {
                String v = "The shortest path between " + src + " and " + dest + " is:\n\n " + src + " ";
                while (!st.isEmpty()) {
                    Edge g = st.pop();
                    v += g.to_name + " ";
                }
                output.setText(v);
            }
        }
        catch(Exception e)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR!!!");
            alert.setHeaderText("Vertices does not exist");
            alert.setContentText("Please Enter Valid Vertices");
            alert.showAndWait();
        }
    }
    @FXML
    public void drawGraph()
    {
        graph.getChildren().clear();
        for(AdjList l:list)
        {
            double a=l.v.X;
            double b=l.v.Y;
            Circle c=new Circle(a*5,b*5,20);
            c.setFill(Color.BLACK);

            c.setStroke(Color.BLACK);
            c.setStrokeWidth(2);
            graph.getChildren().add(c);
            Text t=new Text((a+4)*5,(b+4)*5,l.v.name);
            t.setFill(Color.BLACK);
            t.setFont(Font.font(20));
            graph.getChildren().add(t);
        }
        for(int i=0;i<list.size();i++)
        {
            double c=0,d=0,f=0,g=0;
            for(Edge e:list.get(i).adjLists)
            {
                for(AdjList li:list)
                {
                    if(e.from_name.equals(li.v.name))
                    {
                        c=li.v.X;
                        d=li.v.Y;
                    }
                    if(e.to_name.equals((li.v.name)))
                    {
                        f=li.v.X;
                        g=li.v.Y;
                    }
                }
                Line line=new Line(c*5,d*5,f*5,g*5);
                line.setFill(Color.BLACK);
                Text t1=new Text(((c+f)/2+2)*5,((g+d)/2+2)*5,String.valueOf((int)e.w));
                graph.getChildren().add(line);
                t1.setFill(Color.BLACK);
                t1.setFont(Font.font(20));
                graph.getChildren().add(t1);
            }
        }
    }
    @FXML
    TextField shape;
    @FXML
    public void drawPath(Stack<Edge> path)
    {
        Path p=new Path();
        Edge e=path.peek();
        Vertex v = null;
        for(AdjList l:list)
        {
            if(e.from_name.equals(l.v.name))
            {
                v=l.v;
            }
        }
        String shape_t=shape.getText();
        PathTransition p1=new PathTransition();
        p1.setDuration(Duration.seconds(5));
        p1.setCycleCount(20);
        p1.setAutoReverse(false);
        PathTransition p2=new PathTransition();
        Shape s1,s2;
        switch (shape_t)
        {
            case "Circle":
                s1= new Circle(v.X*5,v.Y*5,10);
                s1.setFill(Color.RED);
                s1.setStrokeWidth(20);
                graph.getChildren().add(s1);
                p1.setNode(s1);
                break;
            case "Square":
                s1= new Rectangle(15,15,15,15);
                s1.setFill(Color.RED);
                s1.setStrokeWidth(20);
                graph.getChildren().add(s1);
                p1.setNode(s1);
                break;
            case "Triangle":
                Polygon p0=new Polygon();
                p0.getPoints().addAll(new Double[]{0.0,0.0,1.0,0.0,0.5,0.5});
                p0.setStroke(Color.RED);
                p0.setStrokeWidth(10);
                graph.getChildren().add(p0);
                p1.setNode(p0);
                break;
            case "Plus":
                s1= new Line(5,10,15,10);
                s2=new Line(10,5,10,15);
                s1.setStroke(Color.RED);
                s2.setStroke(Color.RED);
                s1.setStrokeWidth(5);
                s2.setStrokeWidth(5);
                graph.getChildren().add(s1);
                graph.getChildren().add(s2);
                p1.setNode(s1);
                p2.setNode(s2);
                p2.setDuration(Duration.seconds(5));
                p2.setCycleCount(20);
                p2.setAutoReverse(false);
                break;
            case "Cross":
                s1= new Line(0,0,10,10);
                s2=new Line(10,0,0,10);
                s1.setStroke(Color.RED);
                s2.setStroke(Color.RED);
                s1.setStrokeWidth(5);
                s2.setStrokeWidth(5);
                graph.getChildren().add(s1);
                graph.getChildren().add(s2);
                p1.setNode(s1);
                p2.setNode(s2);
                p2.setDuration(Duration.seconds(5));
                p2.setCycleCount(20);
                p2.setAutoReverse(false);
                break;

        }
        MoveTo start=new MoveTo(v.X*5,v.Y*5);
        p.getElements().add(start);
        while(!path.isEmpty())
        {
            Edge e1=path.pop();
            Vertex v1 = null;
            for(AdjList l2:list)
            {
                if(e1.to_name.equals(l2.v.name))
                {
                    v1=l2.v;
                }
            }
            LineTo lt=new LineTo(v1.X*5,v1.Y*5);
            p.getElements().add(lt);
        }
        p1.setPath(p);
        if(shape_t.equals("Cross")||shape_t.equals("Plus"))
        {
            p2.setPath(p);
            p2.play();
        }
        p1.play();
    }
    @FXML
    public void createVertex(MouseEvent E)
    {
        int x=(int)E.getX();
        int y=(int)E.getY();
        TextInputDialog dia=new TextInputDialog();
        dia.setContentText("Enter Vertex Name");
        Optional<String> result=dia.showAndWait();
        if(result.isPresent())
        {
            String name=result.get();
            Vertex v = new Vertex(name, x/5, y/5);

            boolean stored1 = false;
            for (int p = 0; p < list.size(); p++) {
                if (list.get(p).v.name.equals(name)) {
                    stored1 = true;
                }
            }
            if (!stored1) {
                list.add(new AdjList());
                list.get(list.size() - 1).addVertex(v);
                output.setText("Vertex "+"'"+name+"'"+" with coordinates"+"("+x+","+y+")"+"added successfully.\n");
            }
            drawGraph();
        }
    }
}
class Vertex
{
    String name;
    double X,Y;
    public Vertex(String name,double X,double Y) {
        this.name = name;
        this.X = X;
        this.Y = Y;
    }
    public String toString() {
        return name+" "+(int)X+" "+(int)Y+"\n";
    }
}
class Edge
{
    String from_name,to_name;
    double w;
    public Edge(String from_name,String to_name,double w) {
        this.from_name = from_name;
        this.to_name = to_name;
        this.w = w;
    }
    public String toString()
    {
        return from_name+" "+to_name+" "+(int)w+"\n";
    }
}
class AdjList
{
    Vertex v;
    ArrayList<Edge> adjLists =new ArrayList<>();
    void addVertex(Vertex v)
    {
        this.v=v;
    }
    void addEdge(Edge e)
    {
        adjLists.add(e);
    }


}
class Dijkstra
{
    private String src,dest;
    Dijkstra(String src,String dest)
    {
        this.src=src;
        this.dest=dest;
    }
    HashMap<String, Integer> map = new HashMap<>();
    public Stack<Edge> dijkstra(ArrayList<AdjList> list) {
        Edge[] parent = new Edge[list.size()];
        double[] dist = new double[list.size()];
        int[] colour = new int[list.size()];
        double[] pq = new double[list.size()];
        int i = 0;
        for (AdjList a : list) {
            map.put(a.v.name, i);
            i++;
        }
        for (int i1 = 0; i1 < list.size(); i1++) {
            colour[i1] = 0;
            dist[i1] = Integer.MAX_VALUE;
            parent[i1] = null;
            pq[i1]=Integer.MAX_VALUE;
        }
        colour[map.get(src)] = 1;
        dist[map.get(src)] = 0.0;
        pq[map.get(src)] = 0.0;
        int a=map.get(src);
        for (int i2 = 0; i2 < list.size(); i2++) {
            double min=Integer.MAX_VALUE;
            for(int i8=0;i8<list.size();i8++)
            {
                if(colour[i8]==2)
                    continue;
                if(pq[i8]<min)
                {
                    min = pq[i8];a=i8;
                }
            }
            for(Edge g: list.get(a).adjLists)
            {
                if(g.w>0) {
                    if (colour[map.get(g.to_name)] == 2)
                        continue;
                    if (abs(dist[map.get(g.to_name)]-(min + g.w))>=0.0001 && dist[map.get(g.to_name)] > min + g.w) {
                        dist[map.get(g.to_name)] = min + g.w;
                        parent[map.get(g.to_name)] = g;
                        pq[map.get(g.to_name)] = dist[map.get(g.to_name)];
                    }
                    else if(abs(dist[map.get(g.to_name)]-(min + g.w))<0.0001 && parent[map.get(g.to_name)]!=null)
                    {

                        dist[map.get(g.to_name)] = min + g.w;
                        parent[map.get(g.to_name)] = g;

                    }
                }
            }
            colour[a]=2;
        }
        Stack<Edge> st = new Stack<Edge>();
        int b = map.get(dest);
        if(parent[map.get(dest)]==null)
        {
            return st;
        }
        else {
            int cv = b;

            while(cv!=map.get(src)) {
                st.push(parent[cv]);
                cv = map.get(parent[cv].from_name);
            }
            return st;


        }
    }
}
class C implements Comparator<AdjList>
{

    public int compare(AdjList g1,AdjList g2)
    {
        return g1.v.name.compareTo(g2.v.name);
    }
}
class C1 implements Comparator<Edge>
{

    public int compare(Edge g1,Edge g2)
    {
        if(g1.to_name.compareTo(g2.to_name)==0)
            return Double.compare(g1.w,g2.w);
        else
            return g1.to_name.compareTo(g2.to_name);
    }
}