/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Seth
 */
public class HuffmanEncoder {
    
    
    public static Map<Character, Integer> countCharacterInstances(char[] input)
    {
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0;i<input.length;i++)
        {   if(map.containsKey(input[i]))
            {   int value = map.get(input[i]);
                value++;
                map.replace(input[i], value);
            }
            else{
                map.put(input[i], 1);
            }
        }
        return map;
    }
    
    /*
    Create a leaf node for each symbol and add it to the priority queue.
    While there is more than one node in the queue:
        Remove the two nodes of highest priority (lowest probability) from the queue
        Create a new internal node with these two nodes as children and with probability equal to the sum of the two nodes' probabilities.
         Add the new node to the queue.
        The remaining node is the root node and the tree is complete.
    */
    public static HuffmanNode buildHuffmanTree(Map<Character,Integer> map)
    {
        Set<Entry<Character,Integer>> set = map.entrySet();
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        for(Entry<Character,Integer> entry : set)
        {
            queue.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        while(queue.size() > 1)
        {    
            HuffmanNode left = queue.poll();
            HuffmanNode right = null;
            int newWeight = left.getFrequency();
            if(!queue.isEmpty()) 
            {   right = queue.poll();
                newWeight += right.getFrequency();
            }
            HuffmanNode parent = new HuffmanNode();
            parent.attachLeftChild(left);
            parent.attachRightChild(right);
            parent.setFrequency(newWeight);
            queue.offer(parent);
        }
        
        return queue.poll();
    }
    
    public static void main(String[] args)
    {
      // String input = "A_DEAD_DAD_CEDED_A_BAD_BABE_A_BEADED_ABACA_BED";
        //String input = "The quick brown fox jumped over the lazy lazy dog.";
        
        String input = "A scrub is a guy that thinks he's fly\n" +
"And is, also known as a buster\n" +
"Always talking about what he wants\n" +
"And just sits on his broke ass, so\n" +
"No, I don't want your number\n" +
"No, I don't want to give you mine and\n" +
"No, I don't want to meet you nowhere\n" +
"No, I don't want none of your time\n" +
"And no, I don't want no scrub\n" +
"A scrub is a guy that can't get no love from me\n" +
"Hanging out the passenger side of his best friend's ride\n" +
"Trying to holler at me\n" +
"I don't want no scrub\n" +
"A scrub is a guy that can't get no love from me\n" +
"Hanging out the passenger side of his best friend's ride\n" +
"Trying to holler at me\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"Trying to holler at me\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"But a scrub is checkin' me, but his game is kinda weak\n" +
"And I know that he cannot approach me\n" +
"'Cause I'm lookin' like glass and he's lookin' like trash\n" +
"Can't get with a deadbeat ass, so\n" +
"No, I don't want your number\n" +
"No, I don't want to give you mine and\n" +
"No, I don't want to meet you nowhere\n" +
"No, I don't want none of your time\n" +
"And no, I don't want no scrub\n" +
"A scrub is a guy that can't get no love from me\n" +
"Hanging out the passenger side of his best friend's ride\n" +
"Trying to holler at me\n" +
"I don't want no scrub\n" +
"A scrub is a guy that can't get no love from me\n" +
"Hanging out the passenger side of his best friend's ride\n" +
"Trying to holler at me\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"Trying to holler at me\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"If you don't have a car and you're walking\n" +
"Oh yeah son, I'm talking to you\n" +
"If you live at home with your momma\n" +
"Oh yes son, I'm talking to you\n" +
"If you have a shorty but you don't show love\n" +
"Oh yes son, I'm talking to you\n" +
"Wanna get with me with no money\n" +
"Oh no, I don't want no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"I don't want no scrub\n" +
"A scrub is a guy that can't get no love from me\n" +
"Hanging out the passenger side of his best friend's ride\n" +
"Trying to holler at me\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"Trying to holler at me\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"No, no, no, no\n" +
"Trying to holler at me";
        System.out.println("Input length size in bytes would be "+(input.length()*2));
        Map<Character,Integer> map = countCharacterInstances(input.toCharArray());
        
        System.out.println(map);
        
        HuffmanNode root = buildHuffmanTree(map);
        System.out.println(root);
        
        Map<Character,String> codes = extractCodeMap(root);
        
        Set<Character> keys = codes.keySet();
        for(Character c:keys)
            System.out.println("CODE FOR "+c+" is: "+codes.get(c));
        
        String encoding = huffmanEncode(input,codes);
        System.out.println(encoding);
        System.out.println("COMPRESSED NUMBER OF BITS: "+encoding.length());
        System.out.println("COMPRESSED NUMBER OF BYTES: "+(encoding.length()/8));
        String decoded = huffmanDecode(root,encoding);
        System.out.println(decoded);
        
    }
    
    public static String huffmanDecode(HuffmanNode root,String encoding)
    {
        String output = "";
        HuffmanNode current = root;
        for(int i=0;i<encoding.length();i++)
        {
            
            if(encoding.charAt(i) == '0' && current != null)
            {   //go left
                current = current.getLeftChild();
            }
            else if(encoding.charAt(i) == '1' && current != null)
            {   //go right
                current = current.getRightChild();
            }
            if(current != null && current.isLeaf())
            {
                output += current.getSymbol();
                current = root; //start again from root
            }
            
        }
        return output;
    }
    
    public static String huffmanEncode(String input, Map<Character,String> codeMap)
    {
        String code = "";
        
        for(int i=0;i<input.length();i++)
        {
            code+=codeMap.get(input.charAt(i));
        }
        
        return code;
    }
    
    
    public static Map<Character,String> extractCodeMap(HuffmanNode root) 
    {
        Map<Character,String> codeMap = new HashMap<>();
        
        if(root != null)
            recursiveExtractCode(codeMap,root,"");
        return codeMap;
    }
    
    private static void recursiveExtractCode(Map<Character,String> map, HuffmanNode current, String code) 
    {
        if(current.isLeaf())
        {    
            map.put(current.getSymbol(), code);
        }
        else
        {
            HuffmanNode leftChild = current.getLeftChild();
            if(leftChild != null)
                recursiveExtractCode(map, leftChild, code+"0");
            
            HuffmanNode rightChild = current.getRightChild();
            if(rightChild != null)
                recursiveExtractCode(map, rightChild, code+"1");
        }
    }
}
