**Genetic Algorithm for VM Allocations**  
A stochastic approach to VM allocations that optimizes reliability and latency.  

**Technical Specifications**  
Runs on console only.  

**Version Number**  
1.1  

**Specifications**  
5 Servers (hard-coded reliability & latency values)
1 - 10 Users  
1 - 30 VM Requests per User  
Fitness Function: Reliability - (Latency + Adjustment Value)  
Genetic algorithm runs for 300 generations (MAXGEN = 300), but can be changed as necessary depending on the number of total user requests

**Genetic Algorithm Pseudocode**  
Generate initial population  
While current generation < MAXGEN  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Calculate generation fitness  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Randomly select two parent chromosomes and obtain their bit-string representations  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Perform crossover on the two parent chromosomes and obtain the resulting server number  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;While a crossover results in a server number that is not valid, mutate a random bit  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Insert server number into resulting child chromosomes  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Replace parent chromosomes with child chromosomes and compare fitness values  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;If fitness of population is not improved, restore parent chromosomes  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Increment current generation  

**Challenges**  
1. Determining an effective fitness function, specifically how much to weigh reliability vs. latency  
  E.g. Reliability from 0-1, Latency from 0-1 vs. Reliability from 1-10, Latency from 0-1  
2. Representing a server number in the form of a bit-string. I.e. if # of servers is < max number represented by bit string -> skewed crossover results  
  E.g. While having 5 servers, bit-string of length 3 represents up to 8 servers, but only the following are valid representations:  
  001  
  010  
  011  
  100  
  101  
  
**Example Data**  
S = 5, U = 5, VM per User = 5 (total of 25), MAXGEN = 500 (to better illustrate plateau)  
S1: R = 0.95, L = 0.15  
S2: R = 0.93, L = 0.20  
S3: R = 0.91, L = 0.11  
S4: R = 0.87, L = 0.17  
S5: R = 0.85, L = 0.19  
![image](https://user-images.githubusercontent.com/92837310/206956271-b72c0aa1-75b2-40a4-8236-8d1b384a6a65.png)

