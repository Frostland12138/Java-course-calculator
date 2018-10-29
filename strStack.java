public class strStack {
    private String[] myStack=new String[80];
    private int StackLength=0;
    private int StackPoint=0;
    public boolean isEmpty(){
        if(StackLength==0)
            return true;
        else
            return false;
    }
    public int getStacklength(){
        return StackLength;
    }
    public boolean push(String str){
        if(StackPoint==80)
            return false;
        else{
        myStack[StackPoint]=str;
        StackLength++;
        StackPoint++;
        return true;
        }
    }
    public String getTop(){
        if(StackPoint==0){
            return null;
        }else{
        return myStack[StackPoint-1];
        }
    }
    public String pop(){
        String popn=myStack[StackPoint-1];
        myStack[StackPoint-1]=null;
        StackLength--;
        StackPoint--;
        return popn;
    }
    public void clearStack(){
        if(!isEmpty()){
        for(int i=StackLength-1;i>=0;i--){
             myStack[i]=null;
        }
        StackLength=0;
        StackPoint=0;
       }
    }
    public void trevalstack(){
        for(int i=0;i<StackPoint;i++){
            System.out.print(" "+myStack[i]);
        }
    }
}