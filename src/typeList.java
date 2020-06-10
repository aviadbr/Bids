



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author אביעד
 */
public class typeList {
    typeNode head;
    
    public static class typeNode {
        String name;
        int t_id;
        typeNode next;

        typeNode(int t_id,String name)
        {
            this.name = name;
            this.t_id = t_id;
        }
        public String getName() { return name; }
        public int getID() { return t_id; }
        public typeNode getNext() { return next; }
        public void setName(String name) { this.name = name; }
        public void setCode(int t_id) { this.t_id = t_id; }
        public void setNext(typeNode tn) { this.next = tn; }

    }
    public typeNode getHead() { return head; }
    public static typeList insertTypeList(typeList list, int t_id,String t_name) 
    { 
        // Create a new node with given data 
        typeNode new_node = new typeNode(t_id,t_name); 
        new_node.next = null; 
  
        // If the type List is empty, 
        // then make the new node as head 
        if (list.head == null) { 
            list.head = new_node; 
        } 
        else { 
            // Else traverse till the last node 
            // and insert the new_node there 
            typeNode last = list.head; 
            while (last.next != null) { 
                last = last.next; 
            } 
  
            // Insert the new_node at last node 
            last.next = new_node; 
        } 
  
        // Return the list by head 
        return list;  
    } 
}
