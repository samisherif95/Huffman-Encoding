package com.company;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class Node {

    int freq;
    char letter;

    Node left;
    Node right;
}

class MyComparator implements Comparator<Node> {
    public int compare(Node first, Node second)
    {

        return first.freq - second.freq;
    }
}

public class Main {

    public static void printCode(Node root, String encode)
    {
        if (root.left == null && root.right == null) {

            System.out.println(root.letter + " " + encode);
            return;
        }
        printCode(root.left, encode + "0");
        printCode(root.right, encode + "1");
    }

    public static void main(String[] args)
    {
        try {

            FileInputStream file = new FileInputStream("huffman.txt");
            DataInputStream dis = new DataInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String Contents;
            String str="";

            while ((Contents = br.readLine()) != null) {
                str+=Contents;
            }

            char[]char_array =str.toCharArray();
            char ch;
            Map<Character,Integer> charCounter=new HashMap<>();
            for(int i=0;i<str.length();i++)
            {
                ch = char_array[i];
                if(charCounter.containsKey(char_array[i]))
                {
                    charCounter.put(ch, charCounter.get(ch)+1);
                }
                else
                {
                    charCounter.put(ch, 1);
                }
            }

            System.out.println("letter frequency");

            for(Character key:charCounter.keySet())
            {
                System.out.println(key+"        "+charCounter.get(key));
            }
            System.out.println("");

            System.out.println("Huffman encoding");

            PriorityQueue<Node> queue = new PriorityQueue<Node>(new MyComparator());

            for (Character key:charCounter.keySet()) {

                Node newnode = new Node();

                newnode.letter = key;
                newnode.freq =charCounter.get(key);

                newnode.left = null;
                newnode.right = null;

                queue.add(newnode);
            }

            Node root = null;

            while (queue.size() > 1) {

                Node first = queue.poll();
                
                Node second = queue.poll();

                Node sum = new Node();

                sum.freq = first.freq + second.freq;

                sum.left = first;

                sum.right = second;

                root = sum;

                queue.add(sum);
            }

            printCode(root, "");
        }
        catch(IOException e1){
            System.out.println(e1);
        }
    }
}