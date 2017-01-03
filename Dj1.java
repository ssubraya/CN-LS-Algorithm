package lab14;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

/**
 * 
 * @author Shashank Subraya
 *
 */

public class Dj1 {
	static int option;
	public static String File=null;
	public static int[][] mainMat=null;
	public static int column;
	public static int sum=0;
	static int NodeAfter;
	static int NodeAfterloc;
	static int val;
	static int src=0;
	static ArrayList<Integer> prevnode = new ArrayList<Integer>();	
	public static int RemoveNode=0;
	public static int[] temp;
	static ArrayList<Integer> nodestrav = new ArrayList<Integer>();
	public static Scanner sc=new Scanner(System.in);
	static Boolean cas=true;//flag to generate the options automatically
	static int s1=0;
		static ArrayList<Integer> nroutval = new ArrayList<Integer>();
		static int [][] tempmat= null;
		static ArrayList<Integer> nodesinbtw = new ArrayList<Integer>();

		static int temprt=0;
		static int temprt1=0;
		static int temprt2=0;
		static int PresentMinVal []= new int[1000];
		static int parnode []= new int[1000];
		public static int sourcetemp=0;
		public static int desttemp=0;
		public static int sel3=0;
		static int sum1[]=new int[1000];
	
	public static void main(String[] args)  {
disp();
	}
public static void disp() throws InputMismatchException{
			try{
				
				while(cas){
					System.out.println();
					System.out.println();
					System.out.println("*******************Welcome************************");
					System.out.println();
					System.out.println("1: Create a Network Topology");
					System.out.println("2: Build a connection table");
					System.out.println("3: Shortest path to the destination router");
					System.out.println("4: Modify a Topology");
					System.out.println("5: Best Router for Broadcast");
					System.out.println("6: Exit");
					Scanner sc1= new Scanner(System.in);
					int rt= sc1.nextInt();
					//**************Network topology creation by displaying file as input and assigning it to a matrix ***********//
					if(rt==1){
						temprt1=1;
						System.out.println("Enter the name of the input file");
						Scanner ff= new Scanner(System.in);
						File=ff.next();
						column=reviewmainMatPrompt(File);//to display the file
						mainMat=reviewMatrixPrompt(File);// assign to matrix
					}
					//****************to Build a Connection table for a desired source *****************//
					try{
					if (rt==2){
						System.out.println("Please enter the source");
						temprt=0;
						Scanner sc2= new Scanner(System.in);
						int srrc=sc2.nextInt();
						int src1=srrc-1;
						s1=srrc;
						temprt=2;
						if(src1>=0 && src1<column){
							connBuild(src1,rt);// value in the routing table
							
						}
						else if(src1<0 || src1>column){
							System.out.println("Please enter the source between 1 and "+column);
						}

					}
					}
					catch( InputMismatchException e){
						System.err.println("Not a proper input: Try Again");
						disp();
					}
					//***********************To find the shortest path by providing the source and destination routers as inputs *************//
					if(rt==3){
						try{
							if(temprt==2){
								src=s1-1;
							}
							else{
						System.out.println("Please enter the source");
						Scanner sc2= new Scanner(System.in);
						sel3=rt;
						int srrc=sc2.nextInt();
						src=srrc-1;
						sourcetemp=src;
							}
							sel3=rt;
						System.out.println("Please enter the destination");
						Scanner sc3= new Scanner(System.in);
						int dest=sc3.nextInt();
						desttemp=dest;
//						if (dest <= 0 || dest > column) {
//							System.err.println("Value of destination node must be between 1 and "+column);
//					}
						connBuild(src,rt);
						CostPath(src,dest-1);// print the shortest path and its cost
					}
						catch(ArrayIndexOutOfBoundsException e){
							System.err.println("Values entered must be between 1 and "+column);
							disp();
						}
						catch(IndexOutOfBoundsException e){
							System.err.println("Values entered must be between 1 and "+column);
							disp();
						}
						catch(InputMismatchException e){
							System.err.println("Not a proper input: Try Again");
							disp();
						}
					}
					//************************ To delete the desired node and obtain the connection table and shortest path for previous source 
					// and destination inputs after deletion   ************//
					
					if(rt==4){
						try{
							int appl=0;
							temprt1=4;
							RemoveNode=0;
							System.out.println("Enter the Node to be deleted");
							RemoveNode=sc.nextInt();
							RemoveNode=RemoveNode-1;
						column=reviewmainMatPrompt(File);
						if(tempmat!=null){
							for(int i=0;i<column;i++){
								for(int j=0;j<column;j++){
									mainMat[i][j]=mainMat[i][j];
								}
								
							}
							tempmat=RemoveNode();
						}
						else{
						mainMat=reviewMatrixPrompt(File);
						tempmat=RemoveNode();
						}
						System.out.println();
						System.out.println("************Routing table for other nodes************");
						for(int i=1;i<=column;i++)
						{
								connBuild(i-1,rt);
						}
						System.out.println();
						System.out.println();
						if(sel3==3){  /* new shortest path after deletion based on previous source and destination input if any */
							rt=3;
							if(appl!=0){
							if(RemoveNode<desttemp){
								desttemp=desttemp-1;
								if(RemoveNode<sourcetemp){
									sourcetemp=sourcetemp-1;
									connBuild(sourcetemp,rt);
									CostPath(sourcetemp,desttemp-1);
								}
							}
							else if(RemoveNode>desttemp){
									if(RemoveNode>sourcetemp){
										connBuild(sourcetemp,rt);
										CostPath(sourcetemp,desttemp-1);
									}
							}
							else if (RemoveNode<desttemp){
								desttemp=desttemp-1;
								if(RemoveNode>sourcetemp){
									connBuild(sourcetemp,rt);
									CostPath(sourcetemp,desttemp-1);
								}
							}
							else if(RemoveNode>desttemp){
								if(RemoveNode<sourcetemp){
									sourcetemp=sourcetemp-1;
									connBuild(sourcetemp,rt);
									CostPath(sourcetemp,desttemp-1);
								}
							}
							else if(RemoveNode==desttemp || RemoveNode==sourcetemp){
								System.out.println("Shortest path cannot be determined as either source router or destination router or both have been removed");
							}
							}	
							connBuild(sourcetemp,rt);
							CostPath(sourcetemp,desttemp-1);
							
							}
						else {
							System.out.println("Shortest path was not evaluated previously. Therefore shortest path - and router -");
						}
						sum1=new int [100];
					
					}
					catch(ArrayIndexOutOfBoundsException e){
						System.err.println("Values entered must be between 1 and "+column);
						
					}
					catch(IndexOutOfBoundsException e){
						System.err.println("Values entered must be between 1 and "+column);
						
					}
					catch(InputMismatchException e){
						System.err.println("Not a proper input: Try Again");
						
					}
					}
					//************** To Broadcast the router which has shortest paths compared to all routers along with the cost
					if(rt==5){
						temprt=rt;
						int min=0;
						int router=0;
						for(int i=0;i<column;i++){
							
							for(int j=0;j<column;j++){
								connBuild(i,rt);
						printCost(i,j);
						}
							}
						min=getMinValue(sum1);
						router= getMinRouter(sum1);
						if(min==9999){
							System.out.println("No Best Router for Broadcast");
						}else{
						System.out.println("Router "+router+" has the shortest paths to all other router in the netwoerk with the cost "+min);
						}
						}
					if(rt==6){
						cas=false;
						System.out.println("Good Bye");
						System.exit(0);
					}
					}
				
						
				
			}//try
			catch(InputMismatchException e){
				System.err.println("Enter proper input");
				disp();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	//***** To Read a the input from a file ************//
	private static int reviewmainMatPrompt(String File) throws IOException {
		// TODO Auto-generated method stub
		int col=0;
	    BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(File));
			String line;
			
		    while ((line = br.readLine()) != null) {
		    	if(temprt1==1){
		       System.out.println(line);
}
		       col++;}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: Try Again");
			disp();
		}
		return col; // Returns the number of columns of the file
	}
	//********** To convert the input file into suitable matrix ************//
	private static int[][] reviewMatrixPrompt(String File) throws IOException {
		 int[][] matrix = new int[column][column];


		 BufferedReader buffer =new BufferedReader(new FileReader(File));
        String line;
        int m = 0;
try{
        while ((line = buffer.readLine()) != null) {
            String[] vals = line.trim().split(" ");
            
            for (int n = 0; n < vals.length; n++) {
                matrix[m][n] = Integer.parseInt(vals[n]);
                	if(m==n){
                		if(matrix[m][n]!=0){
                			System.err.println("Enter proper Input ");
                			System.exit(0);
                		}
                	}
                
            }

            m++;
            
            
        }
        for(m=0;m<column;m++){
        for (int n = 0; n < column; n++) {
        if(matrix[m][n]!=matrix[n][m] ){
        	System.err.println("Enter proper Input ");
        	System.exit(0);
        }
        }
        }

}
catch(NumberFormatException e){
	System.err.println("Incorrect input");
	System.exit(0);
}
catch(NullPointerException e){
	System.err.println("Incorrect input");
	System.exit(0);
}
return matrix;  //returns the matrix from the input
	}

	
		public static void connBuild(int src, int opt) {

			clear();

			parNode();
			PresentMinVal(src);
			
			nodestrav.add(src);
			NodeAfterloc = src;
			while (nodestrav.size() != column) {
				int j=0;
				nroutval.clear();
				while(j>=0 && j<column)
				{
					if (!nodestrav.contains(j)) 
					{
						if ((mainMat[NodeAfterloc][j] != -1) && (mainMat[NodeAfterloc][j] > 0)) 
						{
							nroutval.add(j);  //adding all elements which are not -1 or 0
						}
					}
				j++;} 
				 j = 0;
				while(j>=0 && j<nroutval.size())
				{
					if ((mainMat[NodeAfterloc][nroutval.get(j)] + NodeAfter) < PresentMinVal[nroutval.get(j)]) { //if next minimum is less than present minimum value then the present minimum is assigned minimum
						PresentMinVal[nroutval.get(j)]=(mainMat[NodeAfterloc][nroutval.get(j)] + NodeAfter);
						prevnode.add(nroutval.get(j), NodeAfterloc);
						prevnode.remove(nroutval.get(j) + 1);
					}//if
				j++;} 
				int k = 0;
				int min = 9999;
				int x=0;
				while(x>=0 && x<PresentMinVal.length)
				 {
					if ((PresentMinVal[x] != -1) && (PresentMinVal[x] >= 1)) {  // when present minimum value is not -1 or 0
						if (!(nodestrav.contains(k)) && PresentMinVal[x] <= min  ) { // condition for getting minimum value
							NodeAfter = PresentMinVal[x];  //Node next
							NodeAfterloc = k;
							min = PresentMinVal[x];   //minimum is the new present value if the old present minimum value was more than present minimum value
						}
					}
					k++;
				x++;} 
				nodestrav.add(NodeAfterloc);
			}
			int node=0;
			while(node>=0 && node<column) {
				
				
				if (src==prevnode.get(node)) 
				{
					nodesinbtw.add(node+1);
				} 
				else 
				{
					if (prevnode.get(node)==-1)
						nodesinbtw.add(-1);
					else
						nodesinbtw.add(0);
				}
			node++;}
		
			val = 0;
			while (val != 1) {
				//
				int k,n,i=0;
				k=0;
				while(k>=0 && k<column) 
				{
					parnode[k]=prevnode.get(k);
					if (nodesinbtw.get(k)==0)  //load the interface values for every router when they are wither not 0 or -1
					{
						n=parnode[k];
						i=0;
						while (i!=1) {
							if (nodesinbtw.get(n) == (n + 1)) 
							{
								nodesinbtw.remove(k);
								nodesinbtw.add(k, n + 1); //Add the router into the array list
								i = 1;
							} 
							else 
							{
								n = nodesinbtw.get(n);
								if (n == 0) {
									nodesinbtw.add(k, 0); //Add the router into the array list
									nodesinbtw.remove(k+1);
									
									i = 1;
								} else {
									n--;
								}
							}
						}
					}
				k++;}
				//
				if (nodesinbtw.contains(0)) 
					val = 0;
				 else 
					val = 1;
				
			}
			if(opt==2 || opt==4)
			{
				dispconntab(src);
			}
		}

		//*********Entering the present minimum value
		public static void PresentMinVal(int source){
			int i=0;
			while(i>=0 && i<column) 
			{
				if (i!=source) 
				{
					PresentMinVal[i]=1000;
					
				} 
				else 
				{
					PresentMinVal[i]=0;
					
				}
			i++;}
		}
		//*******************visited nodes  **************//
		public static void parNode(){
			int j=0;
			while(j>=0 && j<column)
			 {
				parnode[j]=-1;
				prevnode.add(-1);
			j++;}
		}
		
		public static void clear(){
			
			prevnode.clear();
			nodestrav.clear();
			NodeAfterloc = 0;
			nodesinbtw.clear();
			PresentMinVal=new int [column];
			parnode= new int [column];
			NodeAfter = 0;
		}
		
		//**************** To Display the connection table   *******************//
		public static void dispconntab(int src){
			System.out.println("___________________________________");
			System.out.println("|\tRouting table for node "+(src+1)+"  |");
			System.out.println("|_________________________________|");
			System.out.println("| Destination Node "+"|"+"Interface     |");
			System.out.println("|_________________________________|");
			for (int k=0; k < column; k++) 
			{
				if(nodesinbtw.get(k)==-1){
					System.out.println("|\t"+(k+1) + "\t   |\t-\t  |");
				}else{
				System.out.println("|\t"+(k+1) + "\t   |\t" + nodesinbtw.get(k)+"\t  |");
				}
				}
			System.out.println("|_________________________________|");
		}


//************ to print he path and cost if the  input source and destination *****************//
		public static void CostPath(int srcNode,int de) {
			int rout = de;
			int root=srcNode;
			int k = 0;
			int presrc;
			int minCost = 0;
			parnode[de]=prevnode.get(de);
			ArrayList<Integer> tempArray= new ArrayList<Integer>(); // we use the array list to store the router number
			if (parnode[de] == root) {
				minCost = minCost + mainMat[root][de]; //get the minimum cost and then store the router info in arraylist
				tempArray.add(0, de);
			} else {
				if (parnode[de] != -1) {
					while (k != 999) {
						tempArray.add(0, rout);
						presrc = parnode[rout];
						minCost = minCost + mainMat[presrc][rout];//get the minimum cost and then store the router info in arraylist

						if (parnode[presrc] == root) {
							minCost = minCost + mainMat[root][presrc];//get the minimum cost and then store the router info in arraylist
							tempArray.add(0, presrc);
							k = 999;
						} else {
							rout = presrc;
						}
					}
				}
			}
			tempArray.add(0,root);
			System.out.print("The shortest path from router "+ (src + 1) + " to router " + (de + 1)+ ", ");

			if (tempArray.size() == 1) {
				System.out.println("  There is no path between node "+ (src + 1) + " to node " + (de + 1));
			} else {
				int g=0;
				
				while ( g>=0 && g<tempArray.size()) {
					int x= tempArray.get(g);
					tempArray.remove(g);
					x=x+1;;
					System.out.print(x);
					if (tempArray.size()>0)
						System.out.print("-->");
				}
			}
			System.out.print(".  The total cost is : " + minCost);
		}

		
		//*************** Delete the desired node  *************//
		public static int[][] RemoveNode(){
			int m=0;
			int n=0;
			int[][]mat=new int[column][column];
			int temp=column;
			while(m>=0 && m<column)
			{
				n=0;
				while(n>=0 && n<column)
				
				{
					mat[m][n]=mainMat[m][n];  // assigning matrx to local matrix
				n++;}
			m++;}
			
			n=0;
			while(n>=0 && n<temp){
				mat[RemoveNode][n]=-1;   // inserting -1 into remove node
		n++;	}
			m=0;
			while(m>=0 && m<temp){
				mat[m][RemoveNode]=-1; // inserting -1 into remove node
			m++;}


			System.out.println();
			m=0;
			while(m>=0 && m<column)
			{
				n=0;
				while(n>=0 && n<column)
				
				{
					mainMat[m][n]=mat[m][n]; //loading back to the main matrix
					if(m==n)
					mainMat[m][m]=0;  // assigning 0 for same m and n i.e like mainMat[1][1]=0
				n++;}
			m++;}
			System.out.println("The Total Number of Routers present after deletion of a node are:"+column);
			System.out.println("The modified matrix is : ");
			for(int i=0;i<column;i++){
				for(int j=0;j<column;j++){
					System.out.print(mainMat[i][j]);
					System.out.print("\t");
				}
				System.out.println();
			}
			return mainMat;

		}
		
		//**************input matrix from entries *************************//
		public static int[][] fill(){ 
	        int[][] data = new int[column][column]; 
	        Scanner in = new Scanner(System.in);
	        for(int row = 0; row< column; row++){ 
	              for(int col = 0 ;col< column; col++){ 
	                  data[row][col] = in.nextInt(); 
	               } System.out.println(); 
	          } 

	           for(int row = 0; row< column; row++){
	       for(int col = 0 ;col< column; col++){ 
	             System.out.println(data[row][col]);
	       } 
	      System.out.println(); 
	   }
	         return data; 
         
	}
		
		void primMST(int graph[][])
		{
			int V=0;
			int p[] = new int[V];

			int key[] = new int [V];

			Boolean mstSet[] = new Boolean[V];

			for (int i = 0; i < V; i++)
			{
				key[i] = Integer.MAX_VALUE;
				mstSet[i] = false;
			}
			
			key[0] = 0;	 
			p[0] = -1; 

			
			for (int count = 0; count < V-1; count++)
			{
				int u = 0;
				mstSet[u] = true;
				for (int v = 0; v < V; v++)
					if (graph[u][v]!=0 && mstSet[v] == false && graph[u][v]<key[v] )
					{
						p[v] = u;
						key[v] = graph[u][v];
					}
			}
						
					}
		
		boolean checkTrav(int flags[], int ver)
		{
			for(int i=0; i<column; i++) {
				if(i==0) {
				       if(flags[i] == 0)
						return true;
				       else
					       return false;
				}
				
			}
			return false;
		}
		
		
		//************ to find min distance ********************//
		public static int minDistance(int dist[], Boolean sptSet[])
		{
		    // Initialize min value
		    int min = Integer.MAX_VALUE, min_index=0;
		 
		    for (int v = 0; v < column; v++){
		    	//if(dist[v]==-1)
		    		//dist[v]= Integer.MAX_VALUE;
		    	System.out.println("   "+dist[1]+" "+min);
		        if (sptSet[v] == false && dist[v] <= min){
		            min = dist[v];
		    		min_index = v;
		    }
		    }
		    return min_index;
		}
		
		
		//******************* to display the matrix when entered ************//
		public void display() {
			
			System.out.println("\nThe Matrix is :");
			int i=0;
			int j=0;
			while (i>=0 && i<column)
			 {
				while (j>=0 && j<column) {
					
					System.out.print("\t" + mainMat[i][j]);
				}
				System.out.println();
			i++;}
		}
	
		
		
		//********************************  To print the sum of cost for broadcasting by obtaining the router whose total 
		// sum of cost is least when compared to other routers ************//
		public static void printCost(int srcNode,int de) {
			int minCost = 0;
			int root;
			root=srcNode;
			 int i = 0;
			int	rout = de;
			int presrc;
			parnode[de]=prevnode.get(de);// Obtain the node previous to the destination value
			if (parnode[de] == root) {
				minCost = minCost + mainMat[root][de];
			} else {
				if (parnode[de] != -1) {  // consider for those which are not -1

					while (i !=999 ) {
						presrc = parnode[rout];
						minCost = minCost + mainMat[presrc][rout];  // minimum cost obtained

						if (parnode[presrc] == root) {
							minCost = minCost + mainMat[root][presrc];  // minimum cost obtained
							i = 999;
						} else {
							rout = presrc;
						}
					}
				}
			}

			for(int j=0;j<column;j++){
				if(j==srcNode){
					sum1[j]=sum1[j]+minCost;
				}
			
			
			}

			
		}
		//*******************To get the shortest distance router for broadcasting ******************//
		public static int getMinValue(int[] array) {
			for(int i=0;i<column;i++){
				if(array[i]==0){
					array[i]=9999;
				}
			}
		    int minValue = array[0];
		    for (int i = 1; i < column; i++) {
		        if (array[i] < minValue) {
		            minValue = array[i];
		        }
		    }
		    return minValue;  // returns the shortest distance
		}
//*******************To get the best router for broadcasting ******************//
		public static int getMinRouter(int[] array) {
			for(int i=0;i<column;i++){
				if(array[i]==0){
					array[i]=9999;
				}
			}
		    int minValue = array[0];
		    int i=0;
		    int k=0;
		    for (i = 1; i < column; i++) {
		        if (array[i] < minValue) {
		            minValue = array[i];
		            k=i;
		        }
		    }
		    return k+1; // returns the router number which is best for broadcasting
		}
}