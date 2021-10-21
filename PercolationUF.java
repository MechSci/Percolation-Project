public class PercolationUF implements IPercolate{
    private boolean[][] myGrid;
    private int myOpenCount;
    private IUnionFind myFinder;
    private final int VTOP;
    private final int VBOTTOM;
    private final int gridSize;
    private int currentCell;

    public PercolationUF(IUnionFind finder, int size){
        myGrid = new boolean[size][size];
        myFinder = new QuickUWPC(size*size + 2);
        VTOP = size*size;
        VBOTTOM = VTOP + 1;
        myOpenCount = 0;
        gridSize = size;
    }

    @Override
    public void open(int row, int col) {
        if(! inBounds(row, col)){
            throw new IndexOutOfBoundsException(String.format("(%d, %d) not in bounds", row, col));
        }

        if(isOpen(row, col))
            return;

        myGrid[row][col] = true;
        myOpenCount++;
        currentCell = row * gridSize + col;
        if(row == 0)
            myFinder.union(col, VTOP);
        if(row == myGrid.length - 1)
            myFinder.union(row * gridSize + col, VBOTTOM);
        if(inBounds(row + 1, col) && myGrid[row + 1][col]){
            myFinder.union(currentCell, currentCell + gridSize);
        }
        if(inBounds(row - 1, col) && myGrid[row - 1][col]){
            myFinder.union(currentCell, currentCell - gridSize);
        }
        if(inBounds(row, col + 1) && myGrid[row][col + 1]){
            myFinder.union(currentCell, currentCell + 1);
        }
        if(inBounds(row, col - 1) && myGrid[row][col - 1]){
            myFinder.union(currentCell, currentCell - 1);
        }
    }

    @Override
    public boolean isOpen(int row, int col) {
        if(! inBounds(row, col)){
            throw new IndexOutOfBoundsException(String.format("(%d, %d) not in bounds", row, col));
        }

        return myGrid[row][col];
    }

    @Override
    public boolean isFull(int row, int col) {
        if(! inBounds(row, col)){
            throw new IndexOutOfBoundsException(String.format("(%d, %d) not in bounds", row, col));
        }

        int cellID = row * gridSize + col;
        return myFinder.connected(cellID, VTOP);
    }

    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    private boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
