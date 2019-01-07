package main.java.memoranda.util;

import nu.xom.Element;

public class PriorityQueue {
        
        /* Cola de Prioridad implementada con Estructura de Datos Heap,
         * para ordenar las anotaciones por prioridad 
         */
        
        private Pair[] a;
        private int n;
        
        public PriorityQueue(int size){
                a = new Pair[size+2];
                n = 0;
        }
        public void insertar(Pair x){
                ++n;
                a[n]=x;
                for(int j=n; j>1 && a[j].getPriority() < a[j/2].getPriority(); j/=2)
                {
                        Pair t = a[j];
                        a[j] = a[j/2];
                        a[j/2] = t;
                }
        }
        
        public Element extraer(){
                if(!this.Vacia()){
                        Element m = a[1].getElement();  
                        a[1] = a[n];
                        --n;
                        int j = 1;
                        while(2*j<=n)
                        {
                                int k=2*j;
                                if(k+1<=n && a[k+1].getPriority() < a[k].getPriority())
                                        k=k+1;  
                                if(a[j].getPriority() < a[k].getPriority())
                                        break;
                                Pair t = a[j]; 
                                a[j] = a[k];
                                a[k] = t;  
                                j = k;
                        }
                        return m;
                }
                else 
                        return null;
        }
        
        public boolean Vacia(){
                return n==0;
        }

}