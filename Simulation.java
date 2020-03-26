// Name: Ruben Calderon
// CruzID: rucalder
// Date: May 19, 2019
// Class: 12B/M
// Description: Reads integers from input file, parses them into Jobs, and completes those jobs by order of arrival and duration times
// File Name: Simulation.java

import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;


public class Simulation {

	public static void main(String[] args) throws IOException {
		Scanner in = null;
		PrintWriter trace = null;
		PrintWriter report = null;
		int m;
		int time;

		if(args.length < 1){
			System.out.println("Usage: Simulation <input file> <output file>");
			System.exit(1);
		}
		
		//open files for reading and writing
		in = new Scanner(new File(args[0]));
		trace = new PrintWriter(new FileWriter(args[0] + ".trc"));
		report = new PrintWriter(new FileWriter(args[0] + ".rpt"));

		//read m jobs froum input file;
		m = in.nextInt();
		
		//create starting Queue and read the job arrival/duration times from input file
		Queue line = new Queue();
		for(int i = 0; i < m; i++){
			int arrival = in.nextInt();
			int duration = in.nextInt();
			in.nextLine();
			Job job = new Job(arrival, duration);
			line.enqueue(job);
		}
		trace.println("Trace file: " + args[0] + ".trc");
		trace.println(m + " Jobs:");
		trace.println(line);
		trace.println();
		
		report.println("Report file: " + args[0] + ".rpt");
		report.println(m + " Jobs:");
		report.println(line);
		report.println();
		report.println("***********************************************************");

		int k = line.length();

		//run simulation with n processors for n=1 to n=m-1
		for(int n = 1; n < m; n++){
			trace.println("*****************************");
			if(n == 1){
				trace.println("1 processor:");
			}
			else{
				trace.println(n + " processors:");
			}
			trace.println("*****************************");
			//Queue array
			Queue[] Q = new Queue[n];
			Queue temp = new Queue();
			for(int i = 0; i < line.length(); i++) {
                        	temp.enqueue(line.getObj(i));
                        }
			Queue storage = new Queue();
			time = 0;

			for(int j = 0; j < n; j++) {
                        	Q[j] = new Queue();
                        }
			
			//determine the time of the next arrival or finish event and update time
			while(storage.length() != k){
				trace.println("time=" + time);
				trace.println("0: " + temp + storage);
				for(int w = 1; w < Q.length+1; w++){
					trace.println(w + ": " + Q[w-1]);
				}
				trace.println();
				int arrival;
                        		Job next;
                        		if(!(temp.isEmpty())) {
	                                next = (Job) temp.peek();
	                                arrival = next.getArrival();
                        		}
                        		else {
                        			arrival = 100000;
                        		}
                                
                                int finish = findEarliestFinish(Q, time);

				//if arrival is earlier than finish, check for shortest queue
				//enqueue() next to Q[i]
				if(arrival < finish){
					time = arrival;
					while(temp.length() != 0 && ((Job)temp.peek()).getArrival() == arrival) {
					int shortestQ = 0;
					for(int i = 0; i < n; i++){
						if(Q[i].length() < Q[shortestQ].length()){
							 shortestQ = i;
						}
					}
					Job done = (Job) temp.dequeue();
					Q[shortestQ].enqueue(done);
					if(done == Q[shortestQ].peek()) {
                                        	((Job) Q[shortestQ].peek()).computeFinishTime(time);
                                        }
					}
				}

				//if finish is earlier than arrival, 
				//iterate through Q, dequeueing any job that has finish time
				//each job that's dequeued, 
				//compute the respectve queue's next peek's finish time
				//store all dequeued jobs in storage
				else if(finish <= arrival){
					time = finish;
					for(int i = 0; i < n; i++){
						Job check;
						if(!(Q[i].isEmpty())) {
                                        		check = (Job)Q[i].peek();
							
							if(check.getFinish() == finish){
								Job done = (Job)Q[i].dequeue();
								storage.enqueue(done);
								if(!(Q[i].isEmpty())) {
									((Job)Q[i].peek()).computeFinishTime(time);
						}
						}
						}
					}
					if(finish == arrival){
						while(temp.length() != 0 && ((Job)temp.peek()).getArrival() == arrival) {
						int shortestQ = 0;
                                        	for(int i = 0; i < n; i++){
                                                        if(Q[i].length() < Q[shortestQ].length()){
                                                        	shortestQ = i;
                                                        }
                                        	}
						Job done = (Job) temp.dequeue();
                                        	Q[shortestQ].enqueue(done);
						if(done == Q[shortestQ].peek()){
							((Job) Q[shortestQ].peek()).computeFinishTime(time);
						}
					}
				}
				}
			}
			trace.println("time=" + time);
                                trace.println("0: " + temp + storage);
                                for(int w = 1; w < Q.length+1; w++){
                                        trace.println(w + ": " + Q[w-1]);
                                }
			trace.println();
			int totalWait = 0;
			int maxWait = 0;
			double averageWait = 0;
			for(int i = 0; i < storage.length(); i++){
				totalWait += ((Job)storage.getObj(i)).getWaitTime();
				if(((Job)storage.getObj(i)).getWaitTime() > maxWait){
					maxWait = ((Job)storage.getObj(i)).getWaitTime();
				}
			}
			averageWait = (double)totalWait/(storage.length());
			DecimalFormat round = new DecimalFormat("0.00");
			if(n==1){
			report.println("1 processor: totalWait=" + totalWait + ", maxWait=" + maxWait + ", averageWait=" + round.format(averageWait));
			}
			else{
			report.println(n + " processors: totalWait=" +totalWait+ ", maxWait=" + maxWait + ", averageWait=" + round.format(averageWait));
			}
			for(int i = 0; i < line.length(); i++) {
                               ((Job)line.getObj(i)).resetFinishTime();
                        }
		}
	in.close();
	trace.close();
	report.close();
	}

	public static int findEarliestFinish(Queue[] Q, int time) {
				int finish = 100000;
				for(int i = 0; i < Q.length; i++) {
					if(!(Q[i].isEmpty()) && ((Job) Q[i].peek()).getFinish()==-1) {
						((Job) Q[i].peek()).computeFinishTime(time);
					}
					if(!(Q[i].isEmpty()) && ((Job) Q[i].peek()).getFinish() < finish) {
                                        	finish = ((Job) Q[i].peek()).getFinish();
                                        }
				}
				return finish;
			}
}




