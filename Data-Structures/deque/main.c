#include <stdio.h>
#include "deque.h"

int main()
{
	Deque* deque = (Deque *)malloc(sizeof(deque));
	deque->front = NULL;
	deque->back = NULL;

	pushFront(&deque, 2);
	pushFront(&deque, 1);
	pushBack(&deque, 3);
	pushBack(&deque, 4);
	pushBack(&deque, 5);

	print(deque);

	deleteBack(&deque);
	deleteFront(&deque);
	print(deque);

	delete(&deque);

	return 0;
}
