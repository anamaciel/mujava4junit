/*
 * This code was generated by ojc.
 */
package examples.verbose;


import java.awt.Panel;
import java.util.*;
import java.io.PrintStream;


public class Test extends java.awt.Panel
{

    private int iii = 0;

    private examples.verbose.Test n = null;

    private java.lang.String str = "string";

    public Test()
    {
        super();
    }

    /**
     * @param  
     * @return  
     * @exception  
     * @see java.lang.Object
     */
    public static void main( java.lang.String[] argv )
    {
        java.lang.System.out.println( "public static void examples.verbose.Test.main(java.lang.String[]) was called" );
        java.io.PrintStream out = System.out;
        java.io.PrintStream error = java.lang.System.err;
        out.println( "Hello" + " " + "World" );
        examples.verbose.Test n = new examples.verbose.Test();
        java.lang.System.err.println( "done. " );
        examples.verbose.Test test = new examples.verbose.Test();
        test.hoge();
        test.foo();
    }

    public void hoge()
    {
        java.lang.System.out.println( "public void examples.verbose.Test.hoge() was called" );
        examples.verbose.Local l = new examples.verbose.Local();
        System.out.println( l.foo );
        System.out.println( l.bar.str );
    }

    public Test( java.lang.String str )
    {
        this.str = str;
        n = null;
    }

    public int foo()
    {
        java.lang.System.out.println( "public int examples.verbose.Test.foo() was called" );
        return iii;
    }

    public java.lang.String toString()
    {
        java.lang.System.out.println( "public java.lang.String examples.verbose.Test.toString() was called" );
        if (n == null) {
            return str;
        }
        return str + n;
    }

}


class Local
{

    java.lang.String foo = null;

    examples.verbose.Local2 bar = new examples.verbose.Local2();

    public final java.lang.String getFoo( java.lang.Integer i )
    {
        java.lang.System.out.println( "public final java.lang.String examples.verbose.Local.getFoo(java.lang.Integer) was called" );
        return foo;
    }

}


class Local2
{

    java.lang.String str = "" + "TEST";

}