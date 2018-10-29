import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.*;
public class calculater
{
    static String KeywordTable[]={"+","-","*","/","<<",">>","(",")","#"};
    ////index is                   0   1   2   3   4   5    6    7   8

    static ArrayList<String> ExprList=new ArrayList<String>();

    int IndexofKey(String key)//return the index of key word, otherwise -1
    {
        for(int i=0;i<KeywordTable.length;i++)
        {
            if(KeywordTable[i].equals(key))
            {
                return i;
            }
        } 
        return -1;      
    }
    char PriorityRank(String a,String b)
    {
        //System.out.println("prio"+a+b);
        //a compare to b
        //a already in stack, b is comming op
        //if a<b,then push b into stack
        //if a==b,then pop a from stack,means meet of '()'
        //if a>b,do some operate on data stack with op'a' 
        char[][] mylist={//+   -   *   /  <<  >>   (   )   #
               /* a  + */{'>','>','<','<','<','<','<','>','>'},
               /*    - */{'>','>','<','<','<','<','<','>','>'},
               /*    * */{'>','>','>','>','<','<','<','>','>'},
               /*    / */{'>','>','>','>','<','<','<','>','>'},
               /*   << */{'>','>','>','>','>','>','<','>','>'},
               /*   >> */{'>','>','>','>','>','>','<','>','>'},
               /*    ( */{'<','<','<','<','<','<','<','=', 0 },//meet of one pair of (),then equal.otherwise error
               /*    ) */{'>','>','>','>','>','>', 0 ,'>','>'},
               /*    # */{'<','<','<','<','<','<','<', 0 ,'='}};//there's a ) token before (,error
                         
        int n1=IndexofKey(a);
        int n2=IndexofKey(b);
        return mylist[n1][n2];
    }
    int Getexpress()
    {
        System.out.println("please enter your expression , each divide with a \" \" \nand end with a '#' ");
        System.out.println("use 'H' or 'L' before number to represent high and low bits");
        System.out.println("-------------------enter input------------------");
        Scanner s = new Scanner(System.in);
        //ArrayList<String> ExprList=new ArrayList<String>();
        int cou=0;
        
        while (s.hasNext()) {
            String str1 = s.next();
            //System.out.println("---input----"+str1);
            if(str1.endsWith("#"))
            {
                //System.out.println("readin complete");
                break;
            }
            if(str1.startsWith("H"))
            {
                String strn=str1.substring(1);
                int n=Integer.parseInt(strn);
                n=n>>16;
                strn = Integer.toString(n);
                ExprList.add(strn);
                cou++;
                continue;
                //System.out.println("*"+strn);

                //System.out.println(strn);
                //System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh");
            }
            else if(str1.startsWith("L"))
            {
                String strn=str1.substring(1);
                int n=Integer.parseInt(strn);
                n=n&0x0000ffff;
                strn = Integer.toString(n);
                ExprList.add(strn);
                cou++;
                continue;
                //System.out.println("*"+strn);
                //System.out.println("LLLLLLLLLLLLLLLLLLLLLLL");
            }
            else if(str1.startsWith("0x"))
            {
                String strn=str1.substring(2);
                //System.out.println(strn+"$$$$$$$$$$");
                int n=Integer.parseInt(strn,16);
                //System.out.println(n);
                strn = Integer.toString(n);
                //System.out.println(strn);
                ExprList.add(strn);
                cou++;
                continue;
                //System.out.println("0xxxxxxxxxxxxxxxxxxx");
            }
            ExprList.add(str1);
            
            cou++;
            //System.out.println("input=" + "--"+str1+"---"+ExprList.get(ExprList.size()-1)+"size="+ExprList.size());
        }
         /** 
        for(int i=0;i<cou;i++)
        {
            System.out.println("--------stored ----------"+ExprList.get(i));
        }*/
        s.close();
        return cou;
    }
    int Checkexpress()
    {
        //System.out.println("//----enter check mode---//");
        //System.out.println(IndexofKey("("));
        //tghj tghj ghj ( ( ) #
        int count1=0;
        int count2=0;
        for(int i=0;i<ExprList.size();i++)
        {
            //String aaa="(";
            //System.out.println("---"+IndexofKey(   aaa   ));
            //System.out.println("***"+ExprList.get(i));
            //System.out.println("***"+IndexofKey(ExprList.get(i)));
            if(IndexofKey(ExprList.get(i))==4)
            {
                count1++;//for"("
            }
            else if(IndexofKey(ExprList.get(i))==5)
            {
                count2++;//for")"
            }

        }
        if(count1!=count2)
        {
            System.out.println("----error on '('s and ')'s---");
        }
        //System.out.println("//----return check mode--//");
        return 0;
    }
    int Operator(String A,String B,int op)//for string + - * / calculate
    {
        int temp=0;
        int a=0;
        int b=0;
        try{
            a=Integer.parseInt(A);
            b=Integer.parseInt(B);
        }catch(NumberFormatException e){
            System.out.println("!!--number type error--");
        }
        

        switch(op){
            case 0: temp=a+b;break;
            case 1: temp=a-b;break;
            case 2: temp=a*b;break;
            case 3: temp=a/b;break;
            case 4: temp=a<<b;break;
            case 5: temp=a>>b;break;
        }
        return temp;
    }

   String AnalyzeExpress()
    {
        
        Stack<String> OPTR = new Stack<String>();
        Stack<String> OPND = new Stack<String>();
        String sum="";
        int LenOfExpress=ExprList.size();
        
        boolean judge=true;
        OPTR.push("#");//as stop symble
        OPND.push("0");
        
        for(int i=0 ; judge&& (   (i<LenOfExpress)||(OPTR.peek().charAt(0)!='#') ) ; ){
            //System.out.println(i+"circle");
            //System.out.println("----------------------------------");
            if(i==LenOfExpress)
            {
                /* 
                System.out.println("----------------------------------");
                int size=OPND.size();
                for(int j=0;j<(OPND.size()+1);j++)
                {
                    System.out.println(size+"of"+j+"number of stack is  "+OPND.pop());
                }
               System.exit(-1);*/
                //System.out.println("((((((((((((((LAST Round"+"-----"+OPTR.pop()+OPND.pop()+OPND.pop());
                //char threta=thretax.charAt(0);
               // System.out.println("((((((((((((((LAST Round"+"-----"+opnowl+OPTR.pop()+OPTR.pop()+al+bl+OPND.pop()+"size=="+OPND.size());
               while(OPTR.size()!=1)
               {
                String opnowl=OPTR.pop();               
                String bl=OPND.pop();
                String al=OPND.pop();
                int templ=Operator(al, bl, IndexofKey(opnowl));            
                String numberxl=String.valueOf(templ);
                OPND.push(numberxl);
                }
                break;
            }
            if( IndexofKey(ExprList.get(i))==-1 ){
                //System.out.println("--step1--"+ExprList.get(i));
                  OPND.push(ExprList.get(i));
                  //System.out.println("size=="+OPND.size());
                  i++;
            }
            else{
                //System.out.println("priority check"+"--"+OPTR.peek()+"--"+ExprList.get(i)+"--"+PriorityRank(OPTR.peek(),ExprList.get(i)) );
                switch(PriorityRank(OPTR.peek(),ExprList.get(i))){
                    case '<':
                                OPTR.push(ExprList.get(i));
                                i++;
                             
                             break;
                    case '=':
                                OPTR.pop();
                                i++;        
                             break;
                    case '>':String opnow=OPTR.pop();
                             //char threta=thretax.charAt(0);
                             String b=OPND.pop();
                             String a=OPND.pop();
                        
                             int temp=Operator(a, b, IndexofKey(opnow));
                            // System.out.println("now value=="+temp+"num in data stack=");
                             String numberx=String.valueOf(temp);
                             OPND.push(numberx);
                             break;
                    case '0':judge=false;break;   
                }
            }
            /*System.out.print(i);
            System.out.print("   ");
            OPND.trevalstack();
            System.out.print("   ");
            OPTR.trevalstack();
            System.out.println("   ");*/
            //System.out.println("----------------------------------------------------------");
        }
        sum=OPND.peek();


        //System.out.println(LenOfExpress);
        //st.push("aaa");
        //st.push("bbb");
        //System.out.println(st.pop());
        //System.out.println(st.pop());
        return sum;
    }

    public static void main(String[] args)
    {

        calculater c=new calculater();   //new 
        int count=c.Getexpress();//read in as an array of string,which returns count
        int err=c.Checkexpress();//check express, simply for '()' number FOR NOW
        System.out.println("result="+c.AnalyzeExpress());

        //System.out.println("$$$$$$$$$$$$$$$$$$$$$"+c.Operator("123","123",0));
        //System.out.println(count);
        //String a=KeywordTable[0];
        //System.out.println(KeywordTable[0]+"-------"+a);
        //int b=c.IndexofKey("*");
         System.out.println("complete");
            
    }
   



}