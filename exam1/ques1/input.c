int main()
{
    int a, b, c = 0;
    a = get();
    b = get();
    c = sum(a, b);
    display(c);
    return 0;
}
int sum(int a, int b)
{
    printf("value of a = %d\n", a);
    printf("value of b = %d\n", b);
    display(a);
    display(b);
    return a + b;
}
int get() 
{
    int x;
    scanf("%d", &x);
    return x;
}
void display(int x) 
{
    printf("value = %d\n", x);
}
