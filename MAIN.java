/*Write a java program to create a class called Book with attribute for title, author, ISBN and methods to add and remove books from a collection. */
import java.lang.reflect.Array;
import java.util.Scanner;


public class Book{
    private String title;
    private String author;
    private int ISBN;

    public Book(String title, String author, int ISBN){
        this.title=title;
        this.author=author;
        this.age=age;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public void setISBN(int ISBN){
        this.ISBN=ISBN;
    }
    Book addBook(Book b1){
            System.out.println("Enter name of   new book: ");
            String title2=sc.nextLine();
            sc.nextLine();
            System.out.println("Enter author of new book: ");
            String author2=sc.nextLine();
            sc.nextLine();
            System.out.println("Enter ISBN of new book: ");
            int ISBN2=sc.nextInt();
        b[5]=new Book(title2,author2,ISBN2);
        return b;
    }

    Book removeBook(int i){
        b.remove(i);
    }

    void display(){
        System.out.println("Title: "+ this.title);
        System.out.println("Author: "+this.author);
        System.out.println("ISBN"+this.ISBN);
    }
}

public class MAIN{
    public static void main(String[]args)throws Exception{
        Book[] b=new Book[10];
        b[0]=new Book("ABC","DEF",12);
        b[1]=new Book("GHI","JKL",13);
        b[2]=new Book("MNO","PQR",14);
        b[3]=new Book("STU","VWX",15);
        b[4]=new Book("YZA","BCD",16);
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter 1 to add, 2 to remove book from collection and 3 to display");    
        int choice=sc.nextInt();


        if(choice==1){
            addBook();
        }
        if(choice==2){
            System.out.println("Enter which book to remove: ");
            int removeChoice=sc.nextInt();
            removeBook(removeChoice);
        }
        else{
            for(int i = 0; i<b.length; i++) {
                b[i].display();
                System.out.println(" ");
             }
        }
        sc.close();
    }
}
