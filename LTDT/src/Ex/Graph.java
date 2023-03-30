package Ex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class Graph {
	protected int numVertex;
	protected int[][] matrix;
	protected String filePath;
	protected boolean[] visited;
	protected int[] result;
	protected int index = 0;
	protected int soThanhPhanLienThong;
	protected boolean doThiLienThong;
	protected int[][] maTranChuaCacDinhLienThong;
	protected int[] checkBipartite;

	public Graph(String filePath) {
		this.filePath = filePath;
	}

	public Graph() {
	}

	public Graph(int numVertex) {
		this.numVertex = numVertex;
	}

	public void loadFile(String fileName) {
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			numVertex = Integer.valueOf(br.readLine());
			matrix = new int[numVertex][numVertex];
			int row = 0;
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] token = line.split(" ");
				for (int i = 0; i < token.length; i++) {
					matrix[row][i] = Integer.valueOf(token[i]);
				}
				row++;
			}
			visited = new boolean[numVertex];
			result = new int[numVertex];
		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void print(int[][] matrix) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public boolean checkValid(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != 0)
					return false;
			}
		}
		return true;
	}

	public boolean checkUnGraph(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 1) {
					if (matrix[j][i] != 1)
						return false;
				}
			}
		}
		return true;
	}

	public abstract void addEdge(int[][] matrix, int v1, int v2);

	public abstract void removeEdge(int[][] matrix, int v1, int v2);

	public int numVertexs() {
		return numVertex;
	}

	public abstract int numEdges();

	public abstract int deg(int v);

	public abstract int sumDeg();

	public void diTimCacDinhLienThong() {
		int index = 0;
		maTranChuaCacDinhLienThong = new int[demSoThanhPhanLienThong()][];
		reset();
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i]) {
				int[] arr = BFSGraph(i);
				maTranChuaCacDinhLienThong[index++] = arr;
			}
		}
		for (int[] arr : maTranChuaCacDinhLienThong) {
			System.out.println("cac dinh lien thong: " + Arrays.toString(arr));
		}
	}

	public void reset() {
		visited = new boolean[numVertex];
		result = new int[numVertex];
	}

	public int demSoThanhPhanLienThong() {
		int res = 1;
		if (isConnect()) {
			return res;
		} else {
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) {
					res++;
					BFSGraph(i);
				}
			}
			return res;
		}
	}

	public boolean isConnect() {
		// implement code
		BFSGraph();
		doThiLienThong = true;
		for (boolean check : this.visited) {
			if (!check) {

				doThiLienThong = false;
				return doThiLienThong;
			}
		}
		return doThiLienThong;
	}

	public int[] BFSGraph() {
		// implement code
		return BFSGraph(0);
	}

	public int[] BFSGraph(int startVex) {
		// implement code
		reset();
		int index = 0;
		Queue<Integer> queue = new LinkedList<Integer>();
		int[] result = new int[numVertex];
		int[] subRes = new int[numVertex];
		queue.offer(startVex);
		while (!queue.isEmpty()) {
			int currentRow = queue.poll();
			result[index++] = currentRow;
			visited[currentRow] = true;
			for (int i = 0; i < numVertex; i++) {
				int curentValue = matrix[currentRow][i];
				if (curentValue != 0 && visited[i] == false && !queue.contains(i)) {
					queue.offer(i);
				}
			}
		}

		subRes = new int[index];
		this.result = result;
		return subRes;
	}

	public int[] DFSGraph_Recursion() {
		// implement code
		return DFSGraph_Recursion(0);
	}

	public int[] DFSGraph_Recursion(int startVex) {
		// implement code
		visited[startVex] = true;
		result[index++] = startVex;

		for (int i = 0; i < numVertex; i++) {
			int curent = matrix[startVex][i];
			if (curent != 0 && visited[i] == false) {
				DFSGraph_Recursion(i);
			}
		}

		return result;
	}

	public int[] DFSGraph() {
		// implement code
		return DFSGraph(0);
	}

	public int[] DFSGraph(int startVex) {
		// implement code
		reset();
		int index = 0;
		Stack<Integer> stack = new Stack<Integer>();
		int[] subRes = new int[numVertex];
		int[] result = new int[numVertex];
		stack.push(startVex);
		while (!stack.isEmpty()) {
			int currentStack = stack.pop();
			result[index++] = currentStack;
			visited[currentStack] = true;
			for (int i = this.numVertex - 1; i >= 0; i--) {
				int currentValue = matrix[currentStack][i];
				if (currentValue != 0 && visited[i] == false && !stack.contains(i)) {
					stack.push(i);
				}
			}
		}

		subRes = new int[index];
		System.arraycopy(result, 0, subRes, 0, index);

		this.result = result;

		return subRes;
	}

	public int[] findPathTwoVexsBFS(int s, int t) {
		// implement code
		int[] result = new int[numVertex];
		int[] subRes = BFSGraph(s);
		for (int i = 0; i < subRes.length; i++) {
			if (subRes[i] == t) {
				result = new int[i + 1];
				System.arraycopy(subRes, 0, result, 0, i + 1);
			}
		}

		return result;
	}

	public int[] findPathTwoVexsDFS(int s, int t) {
		// implement code
		int[] result = new int[numVertex];
		int[] subRes = DFSGraph(s);
		for (int i = 0; i < subRes.length; i++) {
			if (subRes[i] == t) {
				result = new int[i + 1];
				System.arraycopy(subRes, 0, result, 0, i + 1);
			}
		}

		return result;
	}

	public boolean checkBipartiteGraph() {
		// implement code
		checkBipartite = new int[numVertex];
		for (int i = 0; i < checkBipartite.length; i++) {
			checkBipartite[i] = -1;
		}

		boolean[] hasVisit = new boolean[numVertex];
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.offer(0);
		checkBipartite[0] = 0;
		hasVisit[0] = true;
		while (!queue.isEmpty()) {

			int currentRow = queue.poll();
			for (int i = 0; i < numVertex; i++) {

				int curentValue = matrix[currentRow][i];

				if (curentValue != 0 && !hasVisit[i] && !queue.contains(i)) {
					queue.offer(i);
					hasVisit[i] = true;

					checkBipartite[i] = generateValue(checkBipartite[currentRow]);
				} else if (curentValue != 0 && hasVisit[i] && checkBipartite[i] == checkBipartite[currentRow]) {

					return false;
				}
			}
		}

		return true;
	}

	private int generateValue(int value) {
		// TODO Auto-generated method stub
		return value == 0 ? 1 : 0;
	}

	public abstract boolean isEulerCycle();

	public abstract boolean isPathEuler();

	public abstract int[] findEulerCycle();

	public abstract int[] findEulerPath();

	public boolean isHamiltonPath() {
        int n = matrix.length;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (isHamiltonPathSub(i, matrix, visited, 1)) {
                return true;
            }
        }

        return false;
    }

    public boolean isHamiltonPathSub(int i, int[][] graph, boolean[] visited, int count) {
        visited[i] = true;

        if (count == graph.length) {
            return true; 
        }

        for (int j = 0; j < graph.length; j++) {
            if (graph[i][j] == 1 && !visited[j]) {
                if (isHamiltonPathSub(j, graph, visited, count + 1)) {
                    return true;
                }
            }
        }

        visited[i] = false;
        return false;
    }
    
    
    public boolean isHamiltonCycle() {
        int n = matrix.length;
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (isHamiltonCycleSub(i, i, matrix, visited, 1)) {
                return true;
            }
        }

        return false;
    }

    public boolean isHamiltonCycleSub(int start, int i, int[][] graph, boolean[] visited, int count) {
        visited[i] = true;

        if (count == graph.length && graph[i][start] == 1) {
            return true; 
        }

        for (int j = 0; j < graph.length; j++) {
            if (graph[i][j] == 1 && !visited[j]) {
                if (isHamiltonCycleSub(start, j, graph, visited, count + 1)) {
                    return true;
                }
            }
        }

        visited[i] = false;
        return false;
    }
    
    public int[] getHamiltonCycle() {
        ArrayList<Integer> path = new ArrayList<Integer>();
        int start = 0;
        path.add(start);

        if (findHamiltonCycle(start, matrix, path)) {
        	int[] res = path.stream().mapToInt( i -> i).toArray();
        	System.out.println("Chu trinh Halminton la: " + Arrays.toString(res));
             return res;
        } else {
        	System.out.println("Khong co chu trinh Halminton");
            return null;
        }
    }

    private boolean findHamiltonCycle(int current, int[][] graph, ArrayList<Integer> path) {
        if (path.size() == graph.length) { 
            if (graph[current][path.get(0)] == 1) {
                return true; 
            } else {
                return false;
            }
        }

        for (int i = 0; i < graph.length; i++) { 
            if (graph[current][i] == 1 && !path.contains(i)) {
                path.add(i); 
                if (findHamiltonCycle(i, graph, path)) { 
                    return true;
                }
                path.remove((Integer)i); 
            }
        }

        return false;
    }

	public int[] getHamiltonPath() {
        ArrayList<Integer> path = new ArrayList<Integer>();
        int start = 0;
        path.add(start);

        if (findHamiltonPath(start, matrix, path)) {
        	int[] res = path.stream().mapToInt( i -> i).toArray();
        	System.out.println("Duong di Halminton la: " + Arrays.toString(res));
             return res;
        } else {
        	System.out.println("Khong co duong di Halminton");
            return null;
        }
    }

    private boolean findHamiltonPath(int current, int[][] graph, ArrayList<Integer> path) {
        if (path.size() == graph.length) { 
            return true;
        }

        for (int i = 0; i < graph.length; i++) { 
            if (graph[current][i] == 1 && !path.contains(i)) {
                path.add(i); 
                if (findHamiltonPath(i, graph, path)) {
                    return true;
                }
                path.remove(path.size() - 1); 
            }
        }

        return false;
    }
	public int[][] SpanningTreeByDFSRecur(int[][] matrix, int v) {
		// implement code
		return null;
	}

	public int[][] SpanningTreeByDFS(int[][] matrix, int v) {
		// implement code
		return null;
	}

	public int[][] SpanningTreeByBFS(int[][] matrix, int v) {
		// implement code
		return null;
	}

	public boolean checkCycle(int[][] matrix, int v1, int v2) {
		// implement code
		return true;
	}

	public int[][] SpanningTreeByKruskal(int[][] matrix) {
		// implement code
		return null;
	}

	public int[][] SpanningTreeByPrim(int[][] matrix, int verStart) {
		// implement code
		return null;
	}

	public void algoDisktra(int[][] matrix, int verStart) {
		// implement code
	}

	public abstract void findABbyDisktra(int[][] matrix, int src, int des);
	// implement code

	public abstract void algoFloyd1(int[][] matrix);
	// implement code

	public abstract void algoFloyd(int[][] matrix);
	// implement code

	public abstract void algoFloyd(int[][] matrix, int startV, int endV);
	// implement code

	public abstract void algoBellmanFord(int[][] matrix, int startV);
	// implement code

	public abstract void algoWarShall(int[][] matrix, int startV);
	// implement code

	public static void main(String[] args) {

	}

}
