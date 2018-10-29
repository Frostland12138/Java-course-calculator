public class EvaluateExpression {
    public String sum;//储存计算的最终结果
    public  boolean jugment=true;//作为表达式是否合法的标志
    String outstr,outstrhasSpace;
    public  EvaluateExpression(String outstr,String outstrhasSpace){
        this.outstr=outstr;
        this.outstrhasSpace=outstrhasSpace;
    }
    public boolean isOP(String x){//判断是否为运算符号的方法
        if(x.charAt(0)=='+'||x.charAt(0)=='-'||x.charAt(0)=='*'||x.charAt(0)=='/'||x.charAt(0)=='('||x.charAt(0)==')'||x.charAt(0)=='#')
            return true;
        else
            return false;
    }
    public boolean doEvaluateExpression(){//求解过程的方法，如果判断表达式错误那么直接结束
        Precede pre=new Precede();
        strStack OPTR=new strStack();
        OPTR.push("#");
        strStack OPND=new strStack();
        cutStr cS=new cutStr(outstr,outstrhasSpace);
        jugment=cS.countsymple();
        String[] mystr=cS.getMystr();
        jugment=cS.khnumber();
        for(int i=0;jugment&&((mystr[i].charAt(0)!='#')||(OPTR.getTop().charAt(0)!='#'));){
            if(!isOP(mystr[i])){
                  OPND.push(mystr[i]);
                  i++;
            }
            else{
                switch(pre.doPrecede(OPTR.getTop().charAt(0),mystr[i].charAt(0))){
                    case '<':
                                OPTR.push(mystr[i]);
                                i++;
                             
                             break;
                    case '=':
                                OPTR.pop();
                                i++;        
                             break;
                    case '>':String thretax=OPTR.pop();
                             char threta=thretax.charAt(0);
                             String b=OPND.pop();
                             String a=OPND.pop();
                             int bb=0;
                             int aa=0;
                             try{
                                 bb=Int.parseint(b);
                                 aa=Int.parseint(a);
                             }catch(NumberFormatException e){
                                 jugment=false;
                             }
                             Operate oper=new Operate(aa,threta,bb);//创建一个操作对象，并初始化
                             String numberx=String.valueOf(oper.doOperaate());
                             OPND.push(numberx);
                             break;
                    case '0':jugment=false;break;   
                }
            }
            /*System.out.print(i);
            System.out.print("   ");
            OPND.trevalstack();
            System.out.print("   ");
            OPTR.trevalstack();
            System.out.println("   ");*/
        }
        sum=OPND.getTop();
        return jugment;
    }
}


public class Precede {
    private char[][] mylist={{'>','>','<','<','<','>','>'},
                             {'>','>','<','<','<','>','>'},
                             {'>','>','>','>','<','>','>'},
                             {'>','>','>','>','<','>','>'},
                             {'<','<','<','<','<','=',0},
                             {'>','>','>','>',0,'>','>'},
                             {'<','<','<','<','<',0,'='}};
    public char doPrecede(char c1,char c2){//查询两者关系
        int c1n,c2n;
        c1n=exchange(c1);
        c2n=exchange(c2);
        return mylist[c1n][c2n];
    }
    public int exchange(char mychar){//将符号转换为自定义数字，用于定位表中位置
        int temp=-1;
        switch(mychar){
            case '+': temp=0; break;
            case '-': temp=1; break;
            case '*': temp=2; break;
            case '/': temp=3; break;
            case '(': temp=4; break;
            case ')': temp=5; break;
            case '#': temp=6; break;
        }
        return temp;
    }
}