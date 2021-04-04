#include <stdio.h> 
#include <time.h> 
#include <sys/mman.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h> 
 

int main() 
{ 


	// CS307 Hw 4
	// Samuel Lee
	
	// Speed & Performance comparison
	// C language using memory mapping / mmap  
	
	
	
	
    int fd = open("loremipsum.txt", O_RDONLY) ; 
    char filename[25] = "loremipsum.txt"; 
    struct stat s ; 
    size_t size ; 
    int count = 0 ; 
    int status = fstat(fd, &s);
    clock_t start, end;
    size = s.st_size ; 
    
    start = clock(); 
    char *ptr = mmap(0, size, PROT_READ, MAP_PRIVATE, fd, 0) ;
    
    if(ptr == MAP_FAILED){
        close(fd);
        printf("Mapping Failed\n") ;
        return 1 ; 
    }
    
    
    for (off_t i = 0 ; i < size ; i++){
        if (ptr[i] == 'a'){
            
            count = count + 1 ; 
            
        }
        }
       
    end = clock(); 
    double time_taken = ((double)end-start) / CLOCKS_PER_SEC ;
    printf("Took %f seconds to execute \n", time_taken); 
    
    
    printf("The file %s has %d characters\n ", 
           filename, count); 
           
    close(fd);
  
    return 0; 
    
    
    
   
} 
