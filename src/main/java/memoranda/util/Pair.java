package main.java.memoranda.util;
 
 import nu.xom.Element;
 
 public class Pair {
   private Element element;
   private int priority;
   
   public Pair(Element value, int priority){
     setElement(value);
     setPriority(priority);
   }
   public Element getElement() {
     return element;
   }
   public void setElement(Element value) {
     this.element = value;
   }
   public int getPriority() {
     return priority;
   }
   public void setPriority(int priority) {
     this.priority = priority;
   }
 
 }