#include <iostream>
#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <string>
#include <stdlib.h> 
#include <queue> 
#include <semaphore.h>
using namespace std;

#define NUM_THREADS 10
#define MEMORY_SIZE 150

int serverLoopCheck= 0 ; 

struct node
{
	int id;
	int size;
};


queue<node> myqueue; // shared que
pthread_mutex_t sharedLock = PTHREAD_MUTEX_INITIALIZER; // mutex
pthread_t server; // server thread handle
sem_t semlist[NUM_THREADS]; // thread semaphores

int thread_message[NUM_THREADS]; // thread memory information
char  memory[MEMORY_SIZE]; // memory size

void my_malloc(int thread_id, int size)
{
	//This function will add the struct to the queue
	//cout << "Inside my_malloc function for thread id: \t" << thread_id << endl; 
	//create new node & initialize values
	pthread_mutex_lock(&sharedLock);
	struct node addNode; 
	addNode.id = thread_id; 
	addNode.size = size; 
	//add node to the queue 
	//cout << "Request memory for thread id :\t" << thread_id << "\t is :\t" << size << endl; 

	myqueue.push(addNode); 
	pthread_mutex_unlock(&sharedLock);
}

void * server_function(void *)
{

	//This function should grant or decline a thread depending on memory size.
	//run until all threads are done 
	int currentIndex = 0; 
	while (::serverLoopCheck < NUM_THREADS) {
		
		//checking if queue is empty
		if (!myqueue.empty()) {
			//locking or entering critical region
			pthread_mutex_lock(&sharedLock);
			//increment server check value = so that while loop will terminate when all threads are processed
			::serverLoopCheck++; 
			//creating new value & popping the queue
			struct node check = myqueue.front();
			myqueue.pop();
			int requestedSize = check.size; 
			int remainingSpace = MEMORY_SIZE - currentIndex; 
			//check if there is memory available
			if (requestedSize <= remainingSpace) {
				//setting thread_message[id] to currentIndex = first available index
				thread_message[check.id] = currentIndex; 
				//cout << "Starting point for thread id:\t" << check.id << "\t is :\t" << currentIndex << endl; 
				//update currentIndex 
				currentIndex = currentIndex + requestedSize ; 
			}
			else {
				//if no memory space, then set it to -1
				thread_message[check.id] = -1;
			}
			//release thread
			sem_post(&semlist[check.id]);
			//exit critical region
			pthread_mutex_unlock(&sharedLock);
			
		}
	}

}

	


void * thread_function(void * id) 
{
	//This function will create a random size, and call my_malloc
	//Block
	//Then fill the memory with id's or give an error prompt
	
	// use random std library
	//creating random integer from 1 - 25
	int randomMemory = rand() % ((MEMORY_SIZE / 6) + 1); 

	//getting ID of the thread
	int * idPtr = (int *)id;

	//cout << "Requested random memory is for thread id: \t" << *idPtr << " is \t" << randomMemory << endl;

	//saving it to the threadId variable
	int threadId = *idPtr; 
	//calling my_malloc function with necessary params

	my_malloc(*idPtr , randomMemory);

	//Block until server thread handles memory request
	sem_wait(&semlist[*idPtr]);

	//enter critical region
	pthread_mutex_lock(&sharedLock);
	//print error message 
	if (thread_message[*idPtr] == -1) {
		cout << "Thread ID: " << *idPtr << " Not enough memory" << endl; 
	}
	else if (thread_message[*idPtr] > -1) {
		//set all bytes allocate to it to the character value of its own id 
		
		int startingPoint = thread_message[*idPtr];
		char ch = '0' + *idPtr ; 
		
		for (int i = startingPoint; i < (startingPoint + randomMemory); i++) {
			//setting memory to ch values 
			memory[i] = ch; 
		}
		
	}

	pthread_mutex_unlock(&sharedLock);
	//exit critical region 
	sem_post(&semlist[*idPtr]);

}

void init()	 
{
	pthread_mutex_lock(&sharedLock);	//lock
	for(int i = 0; i < NUM_THREADS; i++) //initialize semaphores
		{
			sem_init(&semlist[i],0,0);
	}
	for (int i = 0; i < MEMORY_SIZE; i++)	//initialize memory 
  	{
		char zero = '0'; 
		//initializing memory[index] to zero 
		memory[i] = zero;
	}

   	pthread_create(&server,NULL,server_function,NULL); //start server 

	pthread_mutex_unlock(&sharedLock); //unlock

	cout << "Done initializing" << endl; 
}



void dump_memory() 
{
 // You need to print the whole memory array here.

	cout << "Printing the entire content of memory array" << endl; 
	//printing memory
	for (int i = 0; i < MEMORY_SIZE; i++)	
	{
		cout << "The memory at index \t" << i << "\tis :\t"<<  memory[i] << endl ;
	}

}

int main (int argc, char *argv[])
 {
	 // Samuel LEE
	 // CS307 - OS HW 3 - Memory Management API
	 
	 
	 //  implement a memory management API
	 //	create a shared memory, in order to manage
	//	memory requests coming from multiple threads and grant or decline requests based on free
	//	space available.
	
	//	Also manage all mutexes and semaphores for shared
	//	memory access.
	 

 	//You need to create a thread ID array here
	 pthread_t t[NUM_THREADS];
	 int thread_id[NUM_THREADS];

	 //initializing threadID array
	 for (int i = 0; i < NUM_THREADS; i++) {
		 thread_id[i] = i;
		 //cout << "The thread Id of index " << i << "\tis: \t" << thread_id[i] << endl;
	 }
	 


 	init();	// call init

 	//You need to create threads with using thread ID array, using pthread_create()


	for (int i = 0; i < NUM_THREADS; i++) {
		pthread_create(&t[i], NULL, thread_function, (void*) &thread_id[i]);

	}

 	//You need to join the threads
	for (int i = 0; i < 10; i++) {
		pthread_join(t[i], NULL);

	}
	//joining the server thread in main
	pthread_join(server, NULL);

 	dump_memory(); // this will print out the memory

 	printf("\nMemory Indexes:\n" );
 	for (int i = 0; i < NUM_THREADS; i++)
 	{
 		printf("[%d]" ,thread_message[i]); // this will print out the memory indexes
 	}

 	printf("\nTerminating...\n");
 }