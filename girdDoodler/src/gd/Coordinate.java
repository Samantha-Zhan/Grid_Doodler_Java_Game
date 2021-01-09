package gd;
public class Coordinate
{
  private static int row;
  private static int col;
  
  public Coordinate(int theRow, int theCol)
  {
    row = theRow;
    col = theCol;
  }
  
  public static int getRow()
  {
    return row;
  }
    
  public static int getCol()
  {
    return col;
  }
} 
