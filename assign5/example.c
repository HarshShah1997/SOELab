# include <stdio.h>
# include <conio.h>
void fun1(int, int, int);
void fun2(int, int, int);
int main()
{
    int a, b, c;
    a = 10;
    b = 20;
    c = 30;
    fun1(a, b, c);
    fun2(a, b, c);
return 0;
}
void fun1(int a, int b, int c)
{
    if(a > b)
    {
        if(a > c)
        {
            printf("%d", a);
        }
        else
        {
            printf("%d", c);
        }
    }
    else
    {
        if(b > c)
        {
            printf("%d", b);
        }
        else
        {
            printf("%d", c);
        }
    }
}
void fun2(int a, int b, int c)
{
    if(a == 354)
    {
        if(b > c)
        {
            a = b;
        }
        else
        {
            a = c;
        }
    }
    printf("%d", a);
}

