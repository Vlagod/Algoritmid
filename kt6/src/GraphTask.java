import java.util.*;
//Find two vertices that have the longest distance between them
/** Container class to different classes, that makes the whole
 * set of classes one class formally.
 */
public class GraphTask {

   /**
    * Main method.
    */
   public static void main(String[] args) {
      GraphTask a = new GraphTask();
      a.run();
//      throw new RuntimeException ("Nothing implemented yet!"); // delete this
   }

   /**
    * Actual main method to run examples and everything.
    */
   public void run() {
      Graph g = new Graph("G");
      g.createRandomSimpleGraph(3, 2);
      System.out.println(g);
      System.out.println(Arrays.deepToString(g.createAdjMatrix()));
//      System.out.println(Arrays.deepToString(g.createAdjMatrix()));
//      g.findlongestway();
      long time = System.currentTimeMillis();
//      System.out.println(g.findlongestway());
      System.out.println(g.representLongestway());
      System.out.println(String.format("Time: %s milli seconds", System.currentTimeMillis() - time));

      Graph c = new Graph("C");
      c.createRandomSimpleGraph(50, 51);
      System.out.println(c);
      time = System.currentTimeMillis();
//      System.out.println(c.findlongestway());
      System.out.println(c.representLongestway());
      System.out.println(String.format("Time: %s milli seconds", System.currentTimeMillis() - time));

      Graph b = new Graph("B");
      b.createRandomSimpleGraph(100, 500);
      System.out.println(b);
      time = System.currentTimeMillis();
//      System.out.println(b.findlongestway());
      System.out.println(b.representLongestway());
      System.out.println(String.format("Time: %s milli seconds", System.currentTimeMillis() - time));

g.representLongestway();

g.findlongestway();

      Graph d = new Graph("D");
      d.createRandomSimpleGraph(71, 2485);
      System.out.println(d);
      time = System.currentTimeMillis();
//      System.out.println(d.findlongestway());
      System.out.println(d.representLongestway());
      System.out.println(String.format("Time: %s milli seconds", System.currentTimeMillis() - time));


      Graph a = new Graph("A");
      a.createRandomSimpleGraph(500, 1000);
      System.out.println(a);
//      time = System.currentTimeMillis();
////      a.findlongestway();
//      System.out.println(String.format("Time: %s milli seconds", System.currentTimeMillis() - time));
      time = System.currentTimeMillis();
//      System.out.println(a.findlongestway());
      System.out.println(a.representLongestway());
      System.out.println(String.format("Time: %s milli seconds", System.currentTimeMillis() - time));

      // TODO!!! Your experiments here
   }

   // TODO!!! add javadoc relevant to your problem
   /**
    * Program implements an application that
    * Create random simple graph
    * Finds critical paths in the graph
    * Since the complexity algorithm is np algorithm can take a relatively long time to solve
    * @author  Vladislav Konstantinov
    * @author  Jaanus Pöial
    * @version 1.0
    * @since   2020-11-20
    */
   class Vertex {

      private String id;
      private Vertex next;
      private Arc first;
      private int info = 0;
      // You can add more fields, if needed

      Vertex(String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex(String s) {
         this(s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

      // TODO!!! Your Vertex methods here!
   }


   /**
    * Arc represents one arrow in the graph. Two-directional edges are
    * represented by two Arc objects (for both directions).
    */
   class Arc {

      private String id;
      private Vertex target;
      private Arc next;
      private int info = 0;
      // You can add more fields, if needed

      Arc(String s, Vertex v, Arc a) {
         id = s;
         target = v;
         next = a;
      }

      Arc(String s) {
         this(s, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

      // TODO!!! Your Arc methods here!
   }


   class Graph {

      private String id;
      private Vertex first;
      private int info = 0;
      // You can add more fields, if needed

      Graph(String s, Vertex v) {
         id = s;
         first = v;
      }

      Graph(String s) {
         this(s, null);
      }


      @Override
      public String toString() {
         String nl = System.getProperty("line.separator");
         StringBuffer sb = new StringBuffer(nl);
         sb.append(id);
         sb.append(nl);
         Vertex v = first;
//         System.out.println(v.toString());
         while (v != null) {
            sb.append(v.toString());
            sb.append(" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append(" ");
               sb.append(a.toString());
               sb.append(" (");
               sb.append(v.toString());
               sb.append("->");
               sb.append(a.target.toString());
               sb.append(")");
               a = a.next;
            }
            sb.append(nl);
            v = v.next;
         }
         return sb.toString();
      }

      public Vertex createVertex(String vid) {
         Vertex res = new Vertex(vid);
         res.next = first;
         first = res;
         return res;
      }

      public Arc createArc(String aid, Vertex from, Vertex to) {
         Arc res = new Arc(aid);
         res.next = from.first;
         from.first = res;
         res.target = to;
         return res;
      }

      /**
       * Create a connected undirected random tree with n vertices.
       * Each new vertex is connected to some random existing vertex.
       *
       * @param n number of vertices added to this graph
       */
      public void createRandomTree(int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex[n];
         for (int i = 0; i < n; i++) {
            varray[i] = createVertex("v" + String.valueOf(n - i));
            if (i > 0) {
               int vnr = (int) (Math.random() * i);
               createArc("a" + varray[vnr].toString() + "_"
                       + varray[i].toString(), varray[vnr], varray[i]);
               createArc("a" + varray[i].toString() + "_"
                       + varray[vnr].toString(), varray[i], varray[vnr]);
            } else {
            }
         }
      }

      /**
       * Create an adjacency matrix of this graph.
       * Side effect: corrupts info fields in the graph
       *
       * @return adjacency matrix
       */
      public int[][] createAdjMatrix() {
         info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int[info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res[i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      /**
       * Create a connected simple (undirected, no loops, no multiple
       * arcs) random graph with n vertices and m edges.
       *
       * @param n number of vertices
       * @param m number of edges
       */
      public void createRandomSimpleGraph(int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
         if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);
         first = null;
         createRandomTree(n);       // n-1 edges created here
         Vertex[] vert = new Vertex[n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int) (Math.random() * n);  // random source
            int j = (int) (Math.random() * n);  // random target
            if (i == j)
               continue;  // no loops
            if (connected[i][j] != 0 || connected[j][i] != 0)
               continue;  // no multiple edges
            Vertex vi = vert[i];
            Vertex vj = vert[j];
            createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected[i][j] = 1;
            createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected[j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }



      // TODO!!! Your Graph methods here! Probably your solution belongs here.
      /**
       * Represent all longest ways in this graph that was findby function findlongestway(g).
       * @return String with information about longest ways in this graph g.
       */
      public List<List<Arc>> representLongestway() {
         Graph g = this;
         String nl = System.getProperty("line.separator");
         StringBuffer sb = new StringBuffer(nl);
         List<List<Arc>> listofpathes = findlongestway();
         sb.append(nl);
         sb.append("The longest ways length in not oriented, unweighted graph ");
         sb.append(g.id);
         sb.append(" is: ");
         sb.append(listofpathes.get(0).size());
         sb.append(" arcs");
         sb.append(nl);
         int count = 0;
         for(List<Arc> firstpath: listofpathes) {
            StringBuffer sb2 = new StringBuffer(nl);
            count++;
            sb.append(count);
            sb.append(String.format(". way is: "));
            sb.append(nl);
            for (int arci = 0; arci < firstpath.size(); arci++) {
               if(arci == 0){
                  sb2.append("Vertexes: ");
                  sb2.append(firstpath.get(arci).toString().substring(1, firstpath.get(arci).toString().indexOf("_")));
                  sb2.append(" ==> ");
//                  sb.append(firstpath.get(arci).next.toString());
                  sb.append(firstpath.get(arci).toString().substring(1, firstpath.get(arci).toString().indexOf("_")));
                  sb.append(" (");
                  sb.append(firstpath.get(arci).id);
                  sb.append(")");
                  sb.append(" --> ");
               }
               if(arci == firstpath.size() - 1){
                  sb2.append(firstpath.get(arci).target.id);
               }
               if (arci != 0) {
                  sb.append(" --> ");
               }
               sb.append(firstpath.get(arci).target.id);
               if(arci < firstpath.size() - 1) {

                  sb.append(" (");
                  sb.append(firstpath.get(arci + 1).id);
                  sb.append(")");
               }
            }
            sb.append(sb2);
            sb.append(nl);
//            sb.append(nl);
         }
         System.out.println(sb.toString());
         return listofpathes;
      }

      /**
       * Find all longest ways in this graph.
       * @return list with lists of Arc that form longest ways in this graph.
       */
      public List<List<Arc>> findlongestway(){
         Graph g = this;
         int[][] matrix = g.createAdjMatrix();
         boolean[] visited;

         ArrayList<Integer> path = new ArrayList<>();
         List<Integer> longestpath = new ArrayList<>();
         List<List<Integer>> pathes = new ArrayList<>();
         List<List<Integer>> longestpathes = new ArrayList<>();

         for(int i = 0; i < matrix.length; i++) {
            path.clear();
            visited = new boolean[matrix.length];

            findlong(i, visited, path);

            if(i == 1) {
               longestpath = (List<Integer>) path.clone();
            } else if(longestpath.size() < path.size()){
               longestpath = (List<Integer>) path.clone();
            }
            pathes.add((List<Integer>) path.clone());

         }
         int max = 0;
         for(List<Integer> list: pathes) {
            if (list.size() > max) {
               max = list.size();
            }
         }
         for(List<Integer> list: pathes) {
            if (list.size() == max) {
               longestpathes.add(list);
            }
         }

         List<List<Arc>> pathsarcs = new ArrayList<>();
         for(List<Integer> firstpath: longestpathes) {
            List<Arc> arcs = new ArrayList<>();
            for (int arci = 0; arci < firstpath.size(); arci++) {
               Vertex v = first;
               while (v.next != null && firstpath.get(arci) != v.info) {
                  v = v.next;
               };
               if(arci < firstpath.size() - 1) {

                  Arc a = v.first;
                  while (a != null && a.target != null && a.target.info != firstpath.get(arci + 1)) {
                     a = a.next;
                  }

                  arcs.add(a);

               }
            }

            pathsarcs.add(arcs);
         }
//         System.out.println(pathsarcs.toString());
         return pathsarcs;
      }

      /**
       * Find longest path from start point curr.
       * Auxiliary function
       * @param curr - Start point.
       * @param visited - list with visited graphs.
       * @param path - path that was already passed.
       * @return boolean
       */
//
//inspireeritud sellest koodist https://foxford.ru/wiki/informatika/postroenie-gamiltonova-tsikla, kuid muutis seda palju minu ülesandade jaoks.
      public boolean findlong(int curr, boolean[] visited, List<Integer> path) {
         int [][] matrix = this.createAdjMatrix();
         path.add(curr);
         visited[curr] = true;
         int n = matrix.length;
         if (path.size() == matrix.length) {
            return true;
         }

         for (int i = 0; i < n; i++) {
            if (matrix[curr][i] == 1 && !visited[i]) {
               if (findlong(i,  visited, path)) {
                  return true;
               }

            }
         }
         visited[curr] = false;
         return true;
      }

   }

}