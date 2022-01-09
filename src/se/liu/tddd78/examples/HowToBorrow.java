package se.liu.tddd78.examples;

import se.liu.jonkv82.annotations.BorrowedCode;

@SuppressWarnings("InnerClassFieldHidesOuterClassField")
public class HowToBorrow
{
    @BorrowedCode(source = "Somewhere") private int x = 10;

    @BorrowedCode(source = "Somewhere") public HowToBorrow() {
    }

    /**
     * Javadoc for this method
     *
     * @param args
     */
    @BorrowedCode(source = "Somewhere") public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    @BorrowedCode(source = "Somewhere")
    @SuppressWarnings("InnerClassFieldHidesOuterClassField")
    private class Foo
    {
        @SuppressWarnings("InnerClassFieldHidesOuterClassField")
        // noinspection Foo , Bar
        int x;
    }
}
