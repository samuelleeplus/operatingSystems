#include <stdio.h> 
#include <time.h> 
  
int main() 
{ 
	// CS307 Hw 4
	// Samuel Lee
	
	
	// Speed & Performance comparison
	// C language using fopen 
	
	
	
    FILE* fp; 
    clock_t start, end;
  
    // Character counter (result) 
    int count = 0; 
  
    char filename[25] = "loremipsum.txt"; 
  
    // To store a character read from file 
    char c; 
    
    start = clock(); 
    
    // Open the file 
    fp = fopen(filename, "r"); 
  
    // Check if file exists 
    if (fp == NULL) { 
        printf("Could not open file %s", 
               filename); 
        return 0; 
    } 
  
    // Extract characters from file 
    // and store in character c 
    for (c = getc(fp); c != EOF; c = getc(fp)) 
    
        // Increment count for this character 
        if(c == 'a'){
            count = count + 1; 
        }
        
    
    // Close the file 
    fclose(fp); 
    end = clock(); 
    double time_taken = ((double)end-start) / CLOCKS_PER_SEC ;
    printf("Took %f seconds to execute \n", time_taken); 


  
   // double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
//	cout << "Time taken by program is : " << fixed
//		<< time_taken; 
//	cout << " sec " << endl;
    
    
    // Print the count of characters 
    printf("The file %s has %d characters\n ", 
           filename, count); 
  
    return 0; 
} 
