#ifndef DEQUE_H
#define DEQUE_H

typedef struct Node
{
	struct Node* prev;
	int data;
	struct Node* next;

} Node;

typedef struct Deque
{
	Node *front, *back;
} Deque;

int isEmpty(Deque* deque);
int isFull();

void pushFront(Deque** deque, int data);
void pushBack(Deque** deque, int data);

void deleteFront(Deque** deque);
void deleteBack(Deque** deque);

int getFrontElement(Deque* deque);
int getBackElement(Deque* deque);

int getSize(Deque *deque);
void deleteDeque(Deque*** deque);

void print(Deque* deque);
void delete(Deque** deque);

#endif DEQUE_H
