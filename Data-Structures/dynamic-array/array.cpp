#include <iostream>


constexpr int INITIAL_CAPACITY = 1;

class Vector
{
public:

    // Constructors

    Vector()
        :
        capacity(INITIAL_CAPACITY),
        size(0)
    { 
        arr = new int[capacity];
    }

    Vector(int capacity)
        :
        capacity(capacity),
        size(0)
    {
        arr = new int[this->capacity];
    }

    // Modifiers

    void push_back(int value)
    {
        if (size == capacity) { resize(); }
        arr[size++] = value;
    }

    void pop_back()
    {
        arr[(size--) - 1] = 0;
    }

    void insert(int index, int value)
    {
        if (!(index >= size))
        {
            if (size == capacity) { resize(); }
            size++;
            for (int i = size - 1; i > index; i--) { arr[i] = arr[i - 1]; }
            arr[index] = value;
        }
    }

    void remove(int index)
    {
        if (!(index >= size))
        {
            for (int i = index; i < size - 1; i++) { arr[i] = arr[i + 1]; }
            size--;
        }
    }

    // Size

    int get_size() const
    {
        return size;
    }

    bool is_empty() const 
    {
        return (get_size() == 0);
    }

    // Element access

    int at(int index) const
    {
        return arr[index];
    }

    int& operator[](int index) const
    {
        return arr[index];
    }

    void print() const
    {
        for (int i = 0; i < size; i++)
        {
            std::cout << arr[i] << " ";
        }
        std::cout << std::endl;
    }

private:
    void resize()
    {
        capacity *= 2;
        int* temp = new int[capacity];
        for (int i = 0; i < size; i++) { temp[i] = arr[i]; }
        delete[] arr;
        arr = temp;
        for (int i = size; i < capacity; i++) { arr[i] = 0; }
    }

private:
    int* arr;
    int size;
    int capacity;
};


int main()
{
    Vector vec;

    vec.push_back(0);
    vec.push_back(1);
    vec.push_back(3);
    vec.push_back(4);
    vec.push_back(5);
    vec.push_back(6);
    
    vec.remove(0); 
    vec.insert(1, 2); 
    vec.pop_back();

    vec.print();
    std::cout << vec.get_size() << std::endl;

    /*
        Output:
        1 2 3 4 5
        5
    */

    return 0;
}
