#include <iostream>

template<typename type>
struct Node
{
public:
    Node()
        : data(0), next(nullptr) {}

    Node(type value)
        : data(value), next(nullptr) {}

public:
    type data;
    Node* next;
};

template<typename type>
class List
{
public:
    List()
        : head(nullptr), tail(nullptr), size(0) {}

    // Modifiers

    type push_back(type value)
    {
        Node<type>* temp = new Node<type>(value);
        if (size == 0) { head = temp; }
        else { tail->next = temp; }
        tail = temp;
        size++;
        return value;
    }

    void remove_back()
    {
        Node<type>* temp = head;
        if (size == 0) { std::cout << "The list is empty" << '\n'; }
        else if (size == 1)
        {
            delete tail;
            head = tail = temp = nullptr;
            size--;
        }
        else
        {
            while (temp->next->next != nullptr) { temp = temp->next; }
            tail = temp;
            delete tail->next;
            tail->next = nullptr;
            size--;
        }
    }

    type push_front(type value)
    {
        Node<type>* temp = new Node<type>(value);
        temp->next = head;
        head = temp;
        if (size == 0) { tail = temp; }
        size++;
        return value;
    }

    void remove_front()
    {
        if (size == 0) { std::cout << "The list is empty" << '\n'; }
        Node<type>* temp = head;
        head = head->next;
        delete temp;
        if (--size == 0) { tail = nullptr; }
    }

    // Capacity

    size_t get_size() const
    {
        return size;
    }

    bool is_empty() const
    {
        return (get_size() == 0);
    }

    // Display and access

    void print() const
    {
        Node<type>* temp = new Node<type>;
        temp = head;
        while (temp != nullptr)
        {
            std::cout << temp->data << std::endl;
            temp = temp->next;
        }
    }
    
    type front() const
    {
        return head->data;
    }

    type back() const
    {
        return tail->data;
    }

private:
    Node<type>* head;
    Node<type>* tail;
    size_t size;
};

int main()
{
    List<int> list;
    list.push_front(3);
    list.push_front(2);
    list.push_front(1);
    list.print();
    std::cout << "---------------------> " << list.get_size() << std::endl;

    list.remove_front();
    list.remove_front();
    list.print();
    std::cout << "---------------------> " << list.get_size() << std::endl;

    list.push_back(2);
    list.push_back(1);
    list.print();
    std::cout << "---------------------> " << list.get_size() << std::endl;

    list.remove_back();
    list.remove_back();
    list.remove_back();
    list.remove_back();
    list.print();
    std::cout << "---------------------> " << list.get_size() << std::endl;
   

    return 0;
}
