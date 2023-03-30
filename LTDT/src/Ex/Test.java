package Ex;

public class Test {
	
	public static void main(String[] args) {
		Graph graph;
		graph = new UnGraph();
		graph.loadFile("testGraph.txt");

		System.out.println(graph.isHamiltonPath());
		System.out.println(graph.isHamiltonCycle());
		
		graph.getHamiltonPath();
		graph.getHamiltonCycle();

	}

}

