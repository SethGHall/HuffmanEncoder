/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author Seth
 */
public class HuffmanNode implements Comparable<HuffmanNode>{
 
    private HuffmanNode left;
    private HuffmanNode right;
    private int frequency;
    private Character symbol;
    
    public HuffmanNode()
    {
        this(null);
    }
    public HuffmanNode(Character symbol)
    {   this(symbol,0);        
    }
    public HuffmanNode(Character symbol, int frequency)
    {   this.symbol = symbol;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        
        if(o.getFrequency() == frequency)
        {   
            if(symbol != null && o.getSymbol() != null)
                return symbol.compareTo(o.getSymbol());
            else return 0;
        }
        else return frequency-o.getFrequency();
    }
    public void setFrequency(int frequency)
    {   this.frequency = frequency;
        
    }
    public int getFrequency()
    {   return frequency;
        
    }
    public Character getSymbol()
    {   return symbol;
        
    }
//    public boolean equals(HuffmanNode n)
//    {   
//        return (n.getSymbol().equals(symbol));
//    }
//    
    @Override
    public String toString()
    {   
        String symbolString = "";
        if(symbol  != null)
            symbolString = ""+symbol;
        String s = symbolString+":"+frequency+"";
        if(left != null)
           s = "["+left+"]<-"+s;
        if(right != null)
           s = s+"->["+right+"]";
        return s;
    }
    public boolean detachLeftChild()
    {
        if(left != null)
        {   left = null;
            return true;
        }
        else return false;
    }
    public boolean attachLeftChild(HuffmanNode child) 
    {
        if(left == null)
        {   left = child;
            return true;
        }else return false;
    }
    public boolean detachRightChild()
    {
        if(right != null)
        {   right = null;
            return true;
        }
        else return false;
    }
    public boolean attachRightChild(HuffmanNode child) 
    {
        if(right == null)
        {   right = child;
            return true;
        }else return false;
    }
    public boolean isLeaf()
    {
        return (left == null && right == null);
    }
    public HuffmanNode getRightChild()
    {
        return right;
    }
    public HuffmanNode getLeftChild()
    {
        return left;
    }
}
