package StoryMgr;

import java.text.DecimalFormat;
import java.util.TreeSet;

public class Tabular extends Visual {
	
	public Tabular() {
		super();
		PivotTable=null;
	}

	 /**
     * This function works fine when we have in the result two columns only.
     * If we have more than two columns, then the bellow function ignore the columns.
     * We using HashSet<String> because in hash set you have unique values.
     * 
     * Parameters
     *      Rowpivot have the name of each row for PivotTable  
     *      Colpivot have the name of each column for PivotTable
     *      QueryResult have the result of the query
	 * @param extraPivot 
     */
    public void CreatePivotTable(TreeSet<String> RowPivot,TreeSet<String> ColPivot,String QueryResult[][], String[] extraPivot){
        int col=0;
        
        if(extraPivot[0].equals("-2") || extraPivot[0].equals("-3")) col=1;
        
    	this.PivotTable=new String[RowPivot.size()+1][ColPivot.size()+1+col];
        this.PivotTable[0][0]="";
        if(col==1){
        	this.PivotTable[0][1]="";
        	this.PivotTable[0][0]=extraPivot[1];
        	for(int k=1;k<RowPivot.size()+1;k++) this.PivotTable[k][0]="";
        }
        int i=0;
        int j=0+col;
        for(String x : RowPivot) {
            this.PivotTable[i+1][0+col]=x;
            i++;
        }
        
        for(String y:ColPivot){
            this.PivotTable[0][j+1]=y;
            j++;
        }
        i=1;
        
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumFractionDigits(2);
        for(String x:RowPivot){
            j=1+col;
            for(String y:ColPivot){
                for (int r=0;r<QueryResult.length;r++){
                        if((QueryResult[r][0].equals(x) && QueryResult[r][1].equals(y)) || (QueryResult[r][0].equals(y) && QueryResult[r][1].equals(x))){
                            this.PivotTable[i][j]=QueryResult[r][2];
                            if(tryParseFloat(this.PivotTable[i][j])) this.PivotTable[i][j]=df.format(Float.parseFloat(this.PivotTable[i][j]));
                            if(col==1) this.PivotTable[i][j]+=" ("+QueryResult[r][3]+")";
                        }
                        if(this.PivotTable[i][j]==null) {
                        	this.PivotTable[i][j]="-";
                        }
                }
                j++;
            }
            i++;
        }
        
    }

    boolean tryParseFloat(String value){
    	try{
    		Float.parseFloat(value);
    	}
    	catch(Exception ex){
    		return false;
    	}
    	return true;
    }
    
	@Override
	public String[][] getPivotTable() {
		return this.PivotTable;
	}

	@Override
	public void setPivotTable(String[][] pivotTable) {
		this.PivotTable=pivotTable;
	}
}
