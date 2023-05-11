#include <stdio.h>
#include "deque.h"

int isEmpty(Deque* deque)
{
	return (deque->front == NULL);
}

int isFull()
{
	Node* test = (Node *)malloc(sizeof(Node));
	if (test == NULL)
		return 1;
	free(test);
	return 0;
}

void pushFront(Deque** deque, int data)
{
	Node* newNode = (Node*)malloc(sizeof(Node));

	newNode->next = NULL;
	newNode->data = data;
	newNode->prev = NULL;

	if ((*deque)->front == NULL)
		(*deque)->back = (*deque)->front = newNode;
	else
	{
		newNode->next = (*deque)->front;   // connect new element with list's front element (newNode->next = front)
		((*deque)->front)->prev = newNode; // connect list's front element with new element (front->prev = newNode)
		((*deque)->front) = newNode;       // reset (deque front) to be the front element (front = newNode)
	}
}

void pushBack(Deque **deque, int data)
{
	Node* newNode = (Node *)malloc(sizeof(Node));
	
	newNode->next = NULL;
	newNode->data = data;
	newNode->prev = NULL;
	
	if ((*deque)->back == NULL)
		(*deque)->front = (*deque)->back = newNode;
	else
	{
		((*deque)->back)->next = newNode;	// connect list's back element with new element (back->next = newNode)
		newNode->prev = ((*deque)->back);	// connect new element with list's back element (newNode->prev = back)
		((*deque)->back) = newNode;			// reset (deque back) to be the back element (back = newNode)
	}
}

void deleteFront(Deque** deque)
{
	if ((*deque)->front == NULL)
		printf("There are no elements to delete\n");
	else
	{
		Node* temp = (*deque)->front;               // save front node as temporary element
		(*deque)->front = ((*deque)->front)->next;  // reset front to be the next element
		if ((*deque)->front == NULL)                // if next element is NULL then back must also be NULL
			(*deque)->back = NULL;
		else
			((*deque)->front)->prev = NULL;
		free(temp);                                 // deallocate space for previously saved temporary element
	}
}

void deleteBack(Deque** deque)
{
	if ((*deque)->front == NULL)
		printf("There are no elements to delete\n");
	else
	{
		Node* temp = (*deque)->back;                // save back node as temporary element
		(*deque)->back = ((*deque)->back)->prev;    // reset back to be prev element
		if ((*deque)->back == NULL)                 // if prev element is NULL then front must also be NULL
			(*deque)->front = NULL;
		else
			((*deque)->back)->next = NULL;
		free(temp);                                 // deallocate space for previously save temporary element
	}
}

int getFrontElement(Deque* deque)
{
	if (isEmpty(deque))
		return -1;
	return (deque->front)->data;
}

int getBackElement(Deque* deque)
{
	if (isEmpty(deque))
		return -1;
	return (deque->back)->data;
}

int getSize(Deque *deque)
{
	Node* currentElement = deque->front;
	if (currentElement == NULL)
		return 0;

	int size = 0;
	while (currentElement != NULL)
	{
		currentElement = currentElement->next;
		++size;
	}
	return size;
}

void deleteDeque(Deque*** deque)
{
	(**deque)->back = NULL;
	while ((**deque)->front != NULL)
	{
		Node* temp = (**deque)->front;
		(**deque)->front = ((**deque)->front)->next;
		free(temp);
	}
}

void print(Deque* deque)
{
	Node* currentElement = deque->front;
	if (currentElement == NULL)
	{
		printf("The deque is empty\n");
		return;
	}
	while (currentElement != NULL)
	{
		printf("%d -> ", currentElement->data);
		currentElement = currentElement->next;
	}
	printf("NULL\n\n");
}

void delete(Deque **deque)
{
	deleteDeque(&deque);
	*deque = NULL;
	free(*deque);
}
