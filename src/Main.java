import com.sun.source.tree.Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int level=1;
    static int width=0;
    static int count=1;
    static StringBuffer sb;
    static int[][] insert;
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        sb = new StringBuffer();
        insert = new int[n+1][2];
        StringTokenizer st;
        int value;
        int left;
        int right;
        List<Integer> parent = new ArrayList<>();
        HashSet child = new HashSet();
        for(int i=1;i<=n;i++){
            st = new StringTokenizer(br.readLine());
            value = Integer.parseInt(st.nextToken());
            left = Integer.parseInt(st.nextToken());
            right = Integer.parseInt(st.nextToken());
            insert[value][0]=left;
            insert[value][1]=right;
            parent.add(value);
            child.add(left);
            child.add(right);
        }
        int rootnum=0;
        for(int i: parent){
            if(!child.contains(i)){
                rootnum=i;
                break;
            }

        }

        TreeNode root = new TreeNode(rootnum,null,null);
        makeTreeNode(root,rootnum,insert[rootnum][0],insert[rootnum][1]);
        positionCount(root);
        bfs(root);
        System.out.println(level+" "+(width+1));
    }
    static void makeTreeNode(TreeNode Node,int value,int left,int right){
        if(Node.value==value){
            if(left!=-1){
                Node.leftChild = left ==-1?null:new TreeNode(left,null,null);
            }
            if(right!=-1){
                Node.rightChild = right ==-1?null:new TreeNode(right,null,null);
            }
        }
        if(Node.leftChild!=null)makeTreeNode(Node.leftChild,Node.leftChild.value,insert[Node.leftChild.value][0],insert[Node.leftChild.value][1]);
        if(Node.rightChild!=null)makeTreeNode(Node.rightChild,Node.rightChild.value,insert[Node.rightChild.value][0],insert[Node.rightChild.value][1]);



    }
    static void bfs(TreeNode root){
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int levelcount=1;
        while(!q.isEmpty()){
            int size = q.size();
            int x=0;
            for(int i=0;i<size;i++){
                TreeNode node = q.poll();
                if(node.leftChild!=null) {
                    q.offer(node.leftChild);
                }
                if(node.rightChild!=null) {
                    q.offer(node.rightChild);
                }
                if(i==0){
                    x=node.x;
                }
                if(i==size-1){
                    x = node.x-x;
                    if(width<x){
                        width=x;
                        level=levelcount;
                    }
                }
            }
            levelcount++;
        }
    }
    static void positionCount(TreeNode Node){
        if(Node.leftChild!=null){
            positionCount(Node.leftChild);
        }
        Node.position(count);
        count++;
        if(Node.rightChild!=null){
            positionCount(Node.rightChild);
        }
    }
    static class TreeNode{
        int value;
        TreeNode leftChild;
        TreeNode rightChild;
        int x;
        TreeNode(int value,TreeNode left,TreeNode right){
            this.value=value;
            this.leftChild=left;
            this.rightChild=right;

        }
        void position(int x){
            this.x=x;
        }

    }
}