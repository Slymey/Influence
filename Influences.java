public class Influences{
    public class Influence<T>{
        private Influenced<T> influenced;
        private Influence<T> next;
        private Influence<T> previous;
        private T previousValue;

        public Influence(Influenced<T> target,T value){
            synchronized(this){
                previousValue = target.value;
                target.value=value;
                influenced = target;
                previous = target.influence;
                if(previous!=null){
                    previous.next=this;
                }
                target.influence=this;
            }
        }
        public Influenced<T> getInfluenced(){return influenced;}
        public T getPreviousValue(){return previousValue;}

        public synchronized boolean reset(){
            if(influenced==null)return false;
            if(next==null&&previous==null){
                influenced.value=previousValue;
                influenced.influence=null;
            }else if(next==null&&previous!=null){
                influenced.value=previousValue;
                influenced.influence=previous;
                previous.next=null;
            }else if(next!=null&&previous==null){
                next.previousValue=previousValue;
                next.previous=null;
            }else if(next!=null&&previous!=null){
                next.previousValue=previousValue;
                previous.next=next;
                next.previous=previous;
            }
            influenced=null;
            previous=null;
            next=null;
            previousValue = null;
            return true;
        }

        public synchronized boolean reInfluence(Influenced<T> target,T value){
            boolean b = this.reset();
            if(!b)return b;
            synchronized(this){
                previousValue = target.value;
                target.value=value;
                influenced = target;
                previous = target.getInfluence();
                if(previous!=null){
                    previous.next=this;
                }
                target.influence=this;
            }
            return true;
        }
    }
    
    public class Influenced<T>{
        public T value = null;//you can just refer to value directly or use get/set
        public Influenced(){}
        public Influenced(T a){value=a;}
        
        private Influence<T> influence=null;
        public synchronized T get(){return value;}
        public synchronized Influence<T> getInfluence(){return influence;}
        public synchronized void set(T v){value = v;}

        public synchronized void removeInfluences(){
            Influence inf=influence;
            while(inf!=null){
                inf.influenced=null;
                inf = inf.previous;
            }
        }
        public synchronized void setInnitialValue(T innit){
            Influence inf=influence;
            Influence inf2=null;
            while(inf!=null){
                inf2 = inf;
                inf = inf2.previous;
            }
            if(inf2!=null){
                inf2.previousValue=innit;
            }
        }
        public synchronized T getInnitialValue(){
            Influence inf=influence;
            Influence inf2=null;
            while(inf!=null){
                inf2 = inf;
                inf = inf2.previous;
            }
            if(inf2!=null){
                return (T)inf2.previousValue;
            }
            return null;
        }
        @Override
        public String toString(){
            return ""+value;
        }
    }
}
