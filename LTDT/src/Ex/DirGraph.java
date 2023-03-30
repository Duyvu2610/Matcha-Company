package Ex;

import java.util.*;
import java.util.stream.Collectors;

public class DirGraph extends Graph {
	public DirGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DirGraph(String filePath) {
		super(filePath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addEdge(int[][] matrix, int v1, int v2) {
		matrix[v1][v2] = 1;

	}

	@Override
	public void removeEdge(int[][] matrix, int v1, int v2) {
		matrix[v1][v2] = 0;
	}

	@Override
	public int numEdges() {
		int edges = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 1) {
					edges += 1;
				}
			}
		}
		return edges;
	}

	@Override
	public int deg(int v) {
		int countIn = 0;
		int countOut = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[v][i] == 1) {
				countOut++;
			}
			if (matrix[i][v] == 1) {
				countIn++;
			}
		}
		return countIn + countOut;
	}

	@Override
	public int sumDeg() {
		int sum = 0;
		int countIn = 0;
		int countOut = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 1) {
					countOut++;
				}
				if (matrix[j][i] == 1) {
					countIn++;
				}
			}
			System.out.println("deg+ " + i + ": " + countIn);
			System.out.println("deg- " + i + ": " + countOut);
			sum += countIn + countOut;
			System.out.println("sum: " + sum);
			countIn = 0;
			countOut = 0;
		}
		return sum;
	}

	@Override
	public boolean isEulerCycle() {
		if (!isConnect())
			return false;
		int countIn = 0;
		int countOut = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != 0) {
					countOut++;
				}
				if (matrix[j][i] != 0) {
					countIn++;
				}
			}
			if (countIn != countOut)
				return false;
			countIn = 0;
			countOut = 0;
		}
		return true;
	}

	@Override
	public boolean isPathEuler() {
		if (isEulerCycle())
			return true;
		if (!isConnect())
			return false;
		int soDinhKhongCanBang = 0;
		int countIn = 0;
		int countOut = 0;
		int[][] cacDinhKhongCanBang = new int[2][2];
		int index = 0;
		int indexDeg = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] != 0) {
					countOut++;
				}
				if (matrix[j][i] != 0) {
					countIn++;
				}
			}
			if (countIn != countOut) {
				soDinhKhongCanBang++;
				if (soDinhKhongCanBang > 2)
					return false;
				cacDinhKhongCanBang[index][indexDeg++] = countOut;
				cacDinhKhongCanBang[index][indexDeg++] = countIn;

				indexDeg = 0;
				index++;
			}

			if (i == matrix.length - 1) {
				if (soDinhKhongCanBang != 2) {
					return false;
				} else {
					for (int k = 0; k < cacDinhKhongCanBang.length; k++) {
						int l = 0;
						if (cacDinhKhongCanBang[k][l++] != cacDinhKhongCanBang[k][l++])
							return false;
					}
				}
			}

			countIn = 0;
			countOut = 0;
		}

		return true;
	}

	@Override
	public int[] findEulerCycle() {
		// TODO Auto-generated method stub
		return findEulerPath(findEulerPathNode()[new Random().nextInt(findEulerPathNode().length)]);
	}

	@Override
	public int[] findEulerPath() {
		// TODO Auto-generated method stub
		return findEulerPath(findEulerPathNode()[new Random().nextInt(findEulerPathNode().length)]);
	}

	public void speacialArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = -10;
		}
	}

	public int[] findEulerPath(int root) {
		// TODO Auto-generated method stub
		int currentNode = root;
		int[] result = null;
		int index = 0;
		int[][] matrix = Arrays.stream(this.matrix).map((int[] row) -> row.clone())
				.toArray((int length) -> new int[length][]);
		if (isPathEuler()) {
			result = new int[this.numEdges() + 1];
			speacialArr(result);
			while (!checkValid(matrix)) {
				if (index == 0) {
					result = insertArray(result, DFSForEuler(matrix, currentNode), 0);

					index++;
					continue;
				}

				if (!checkConnect(matrix, currentNode)) {

					currentNode = chooseNextNode(matrix);

				} else {

					result = insertArray(result, DFSForEuler(matrix, currentNode), findIndexNode(result, currentNode));

				}

			}
		}
		if (result != null) {
			if (isEulerCycle()) {
				System.out.println("Chu trinh Euler: " + Arrays.toString(result));
			} else {

				System.out.println("Duong di Euler: " + Arrays.toString(result));
			}
		} else {
			System.out.println("Do thi khong co duong di Euler");
		}

		return result;
	}

	private int findIndexNode(int[] arr, int node) {
		// TODO Auto-generated method stub

		int res = -1;
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] == node)
				res = i;
		}
		return res;
	}

	private int chooseNextNode(int[][] matrix) {
		int res = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != 0) {
					return i;
				}
			}
		}
		return res;
	}

	private boolean checkConnect(int[][] matix, int root) {
		// TODO Auto-generated method stub
		for (int i = 0; i < matix.length; i++) {
			if (matix[root][i] != 0)
				return true;
		}
		return false;
	}

	public int[] DFSForEuler(int[][] matrix, int root) {
		int index = 0;
		Stack<Integer> stack = new Stack<Integer>();
		int[] subRes = new int[this.numEdges()];
		int[] result = new int[this.numEdges()];
		stack.push(root);
		int firstTime = 0;
		while (!checkValid(matrix)) {
			if (stack.isEmpty())
				break;
			int currentStack = stack.pop();
			result[index++] = currentStack;

			if (firstTime != 0) {
				matrix[result[index - 2]][result[index - 1]] = 0;
			}
			for (int i = 0; i < matrix.length; i++) {
				int currentValue = matrix[currentStack][i];
				if (currentValue != 0) {
					stack.push(i);
					break;
				}
			}
			firstTime++;
		}

		subRes = new int[index];
		System.arraycopy(result, 0, subRes, 0, index);

		this.result = result;

		return subRes;
	}

	private int[] insertArray(int[] org, int[] arrIns, int st) {
		List<Integer> listOfOrg = Arrays.stream(org).boxed().collect(Collectors.toList());
		List<Integer> listOfArrIns = Arrays.stream(arrIns).boxed().collect(Collectors.toList());
		listOfOrg.addAll(st, listOfArrIns);
		listOfOrg.remove(st + listOfArrIns.size());
		for (int i = 0; i < org.length; i++) {
			org[i] = listOfOrg.get(i);
		}
		return org;
	}

	public int[] findEulerPathNode() {
		int[] result = new int[1];

		if (isEulerCycle()) {
			result = new int[matrix.length];
			for (int i = 0; i < matrix.length; i++) {
				int j = i;
				result[i] = j;
			}
		}
		if (isPathEuler()) {
			result = new int[2];
			int index = 0;
			for (int i = 0; i < matrix.length; i++) {
				if (deg(i) % 2 != 0) {
					result[index++] = i;
				}
			}
		}
		return result;
	}

	@Override
	public void findABbyDisktra(int[][] matrix, int src, int des) {
		// TODO Auto-generated method stub

	}

	@Override
	public void algoFloyd1(int[][] matrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void algoFloyd(int[][] matrix) {
		// TODO Auto-generated method stub

	}

	@Override
	public void algoFloyd(int[][] matrix, int startV, int endV) {
		// TODO Auto-generated method stub

	}

	@Override
	public void algoBellmanFord(int[][] matrix, int startV) {
		// TODO Auto-generated method stub

	}

	@Override
	public void algoWarShall(int[][] matrix, int startV) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Graph d1 = new DirGraph("test1.txt");
		d1.loadFile("test1.txt");
		d1.addEdge(d1.matrix, 0, 1);

	}

}

