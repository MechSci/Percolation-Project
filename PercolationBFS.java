import java.util.LinkedList;
import java.util.Queue;

/* * * * * * * * * * *
* @author Ethan Yu
* * * * * * * * * * * */

public class PercolationBFS extends PercolationDFSFast{
    private int[] rowChanger = {1, -1, 0, 0};
    private int[] colChanger = {0, 0, 1, -1};

    public PercolationBFS(int size){
        super(size);
    }

    @Override
    protected void dfs(int row, int col){
        if(! inBounds(row, col))
            return;
        if(isFull(row, col) || ! isOpen(row, col))
            return;

        myGrid[row][col] = FULL;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{row, col});
        while(q.size() != 0){
            int[] current = q.remove();
            for(int i=0; i < 4; i++){
                row = current[0] + rowChanger[i];
                col = current[1] + colChanger[i];
                if(inBounds(row, col) && myGrid[row][col] == OPEN){
                    q.add(new int[]{row, col});
                    myGrid[row][col] = FULL;
                }
            }
        }
    }
}
