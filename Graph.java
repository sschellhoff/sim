import java.util.ArrayList;

/**
    * This class represents an undirected graph
    */
class Graph {

    /**
        * the graphs data
        */
    private EdgeValue[] data;

    /**
        * the number of nodes in the graph
        */
    private final int numNodes;

    /**
        * the zero value for an edge in the graph
        */
    private final EdgeValue zero;

    /**
        * the graphs constructor
        * @param numNodes the number of nodes in the graph
        */
    public Graph(final int numNodes) {
        this.numNodes = numNodes;
        this.zero = EdgeValue.ZERO;

        // fill data with zero value
        data = new EdgeValue[numNodes * numNodes];
        for(int i = 0; i < data.length; i++) {
            data[i] = EdgeValue.ZERO;
        }
    }

    /**
        * make a move, set edge between n1 and n2 as value
        * @param n1 node connected by the edge
        * @param n2 other node connected by the edge
        * @param value the value of the edge afterwards
        * @throws InvalidMoveException if the move is invalid
        * @return true if game is not lost after this move, so true if game goes on
        */
    public boolean makeMove(int n1, int n2, EdgeValue value)
        throws InvalidMoveException {
        if(!take(n1, n2, value)) {
            throw new InvalidMoveException("invalid move");
        }
        return !lost(n1, n2);
    }

    /**
        * check if node n is in the graph
        * @return true if n is a node in the graph
        */
    private boolean isInGraph(final int n) {
        return n >= 0 && n <numNodes;
    }

    /**
        * get the value of the edge between n1 and n2
        * @return the value of the edge
        */
    private EdgeValue getValue(final int n1, final int n2) {
        // graph is undirected but we always set both n1-n2 and n2-n1 edge
        // so we only have to check for one
        if(isInGraph(n1) && isInGraph(n2)) {
            return data[n1 * numNodes + n2];
        }
        // if n1 or n2 is not in graph, you should better throw an exception
        return zero;
    }

    /**
        * check if the edge between n1 and n2 isn't zero
        * @return true if the edges value is equal to zero
        */
    private boolean isSet(final int n1, final int n2) {
        // if n1 or n2 is not in graph, you should better throw an exception
        return isInGraph(n1) && isInGraph(n2) && getValue(n1, n2) != zero;
    }

    /**
    * set the edge between n1 and n2 to value
    * @return true if you can take the edge
    */
    private boolean take(final int n1, final int n2, final EdgeValue value) {
        // if n1 or n2 is not in graph, you should better throw an exception
        if(isInGraph(n1) && isInGraph(n2) && !isSet(n1, n2)) {
            // undirected graph, so set both
            data[n1 * numNodes + n2] = value;
            data[n2 * numNodes + n1] = value;
            return true;
        }
        return false;
    }

    /**
    * get all neighbours of node n, which are connected by own value, and exclude node exclude
    * @param n the node whichs neighbours you want
    * @param exclude this node should be excluded
    * @param ownValue the own value
    */
    private ArrayList<Integer> getOwnedNeighbours(int n, int exclude, EdgeValue ownValue) {
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < numNodes; i++) {
            if(i != n && i != exclude && getValue(n, i) == ownValue) {
                result.add(i);
            }
        }
        return result;
    }

    private boolean lost(final int n1, final int n2) {
        // get the value to check
        EdgeValue value = getValue(n1, n2);

        // if the edge isn't set, game isn't lost
        if(value == zero) {
            return false;
        }
        
        // get neighbours of n1 which are connected with edges of own value but exclude n2
        ArrayList<Integer> n1sNeighbours = getOwnedNeighbours(n1, n2, value);

        // foreach of this neighbours
        for(int i = 0; i < n1sNeighbours.size(); i++) {

            // take neighbour as n3 and get all of it's neighbours which are connected with edge of own value but exclude n1
            int n3 = n1sNeighbours.get(i);
            ArrayList<Integer> n3sNeighbours = getOwnedNeighbours(n3, n1, value);

            // check of n2 is included, if so, game is lost
            for(int j = 0; j < n3sNeighbours.size(); j++) {
                // take neighbour as n4, if n4 equals n2, you have a cycle of length 3 and the game is lost
                int n4 = n3sNeighbours.get(j);
                if(n4 == n2) {
                    return true;
                }
            }
        }
        return false;
    }
}