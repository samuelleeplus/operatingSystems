import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

class Table {

	// FOR GUI:
	static JFrame frame; 
	private JLabel[] plates = new JLabel[5];
	private JLabel[] forks = new JLabel[5];
	static Table window;
	static BufferedImage fork;

	/**
	 * Launch the application.

	/**
	 * Create the application.
	 */
	public Table() {


		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		BufferedImage plate = null;
		try {
			plate = ImageIO.read(new File("spaghetti_yellow.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.getContentPane().setLayout(null);
		plates[0] = new JLabel(new ImageIcon(plate));
		plates[0].setBounds(185, 10, 100, 100);
		frame.getContentPane().add(plates[0]);
		plates[0].setVisible(false);

		plates[1] = new JLabel(new ImageIcon(plate));
		plates[1].setBounds(300, 100, 100, 100);
		frame.getContentPane().add(plates[1]);
		plates[1].setVisible(false);

		plates[2] = new JLabel(new ImageIcon(plate));
		plates[2].setBounds(230, 230, 100, 100);
		frame.getContentPane().add(plates[2]);
		plates[2].setVisible(false);

		plates[3] = new JLabel(new ImageIcon(plate));
		plates[3].setBounds(70, 210, 100, 100);
		frame.getContentPane().add(plates[3]);
		plates[3].setVisible(false);

		plates[4] = new JLabel(new ImageIcon(plate));
		plates[4].setBounds(50, 80, 100, 100);
		frame.getContentPane().add(plates[4]);
		plates[4].setVisible(false);

		try {
			fork = ImageIO.read(new File("fork_white_1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		forks[0] = new JLabel(new ImageIcon(fork));
		forks[0].setBounds(250, 40, 100, 100);
		frame.getContentPane().add(forks[0]);

		try {
			fork = ImageIO.read(new File("fork_white_2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		forks[1] = new JLabel(new ImageIcon(fork));
		forks[1].setBounds(280, 170, 100, 100);
		frame.getContentPane().add(forks[1]);

		try {
			fork = ImageIO.read(new File("fork_white_3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		forks[2] = new JLabel(new ImageIcon(fork));
		forks[2].setBounds(150, 220, 100, 100);
		frame.getContentPane().add(forks[2]);

		try {
			fork = ImageIO.read(new File("fork_white_4.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		forks[3] = new JLabel(new ImageIcon(fork));
		forks[3].setBounds(60, 145, 100, 100);
		frame.getContentPane().add(forks[3]);

		try {
			fork = ImageIO.read(new File("fork_white_5.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		forks[4] = new JLabel(new ImageIcon(fork));
		forks[4].setBounds(95, 15, 100, 100);
		frame.getContentPane().add(forks[4]);
	}


	public void PutPlate_GUI(int i)
	{
		//plate color will be yellow is philosopher is waiting
		plates[i].setVisible(true);
	}

	public void StartDining_GUI(int i)
	{		//color will be black or white
		try {
			plates[i].setIcon(new ImageIcon(ImageIO.read(new File("spaghetti_white.jpg"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Hungry_GUI(int i) {
			// will turn red if philosopher is hungry
		try {
			plates[i].setIcon(new ImageIcon(ImageIO.read(new File("spaghetti_red.jpg"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void ForkTake_GUI(int i) {
		try{
			if (i == 0)
			{
				forks[0].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_1.jpg"))));
				forks[4].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_5.jpg"))));
			}
			else if (i == 1)
			{
				forks[0].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_1.jpg"))));
				forks[1].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_2.jpg"))));
			}
			else if (i == 2)
			{
				forks[1].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_2.jpg"))));
				forks[2].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_3.jpg"))));
			}
			else if (i == 3)
			{
				forks[2].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_3.jpg"))));
				forks[3].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_4.jpg"))));
			}
			else if (i == 4)
			{
				forks[3].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_4.jpg"))));
				forks[4].setIcon(new ImageIcon(ImageIO.read(new File("fork_red_5.jpg"))));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void Eating_GUI(int i) {
		//when philosopher start eating, call this function
		//to turn plate color to blue
		try {
			plates[i].setIcon(new ImageIcon(ImageIO.read(new File("spaghetti_blue.jpg"))));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			sleep(3000);
		} catch (InterruptedException ex){
			ex.printStackTrace();
		}
	}

	public void ForkPut_GUI(int i) {
		try{
			if (i == 0)
			{
				forks[0].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_1.jpg"))));
				forks[4].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_5.jpg"))));
			}
			else if (i == 1)
			{
				forks[0].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_1.jpg"))));
				forks[1].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_2.jpg"))));
			}
			else if (i == 2)
			{
				forks[1].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_2.jpg"))));
				forks[2].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_3.jpg"))));
			}
			else if (i == 3)
			{
				forks[2].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_3.jpg"))));
				forks[3].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_4.jpg"))));
			}
			else if (i == 4)
			{
				forks[3].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_4.jpg"))));
				forks[4].setIcon(new ImageIcon(ImageIO.read(new File("fork_white_5.jpg"))));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void StopEating_GUI(int i) {
		try {
			plates[i].setIcon(new ImageIcon(ImageIO.read(new File("spaghetti_white.jpg"))));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class Barrier {
	public CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
}

class StatePhil {
	//initialize to thinking
	public int[] state = {0,0,0,0,0} ;
	//only 1 thread can enter critical region
	public Semaphore mut = new Semaphore(1) ;
	//initializing 5 semaphores, one for each philosopher
	public Semaphore [] s = new Semaphore [] {new Semaphore(1),
			new Semaphore(1), new Semaphore(1), new Semaphore(1), new Semaphore(1)};
	public Table table = new Table();
	private Random random = new Random();
	public Barrier barrier ;



	void think(int i){
		//System.out.println("Philospher " + i + " is inside THINK function.");
		//think only after eating
		//if he is hungry or state[i]==1, he will not think again
		if(state[i] == 0){
			Integer thinkNum = random.nextInt(10000);
			try {
				//System.out.println("Philosopher " + i + " thinks for " + thinkNum);
				sleep(thinkNum);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	void take_fork(int i){
		//enter critical region
		//System.out.println("Philospher " + i + " is inside take_fork function.");

		try {
			mut.acquire();
			System.out.println("Philospher " + i + " acquired Mutex & is INSIDE critical region.");
			table.Hungry_GUI(i);
			//make state of philsopher i to hungry
			state[i] = 1 ;

			test(i);
			mut.release();
			System.out.println("Philospher " + i + " released Mutex & is OUTSIDE critical region.");
			//down is to release forks
			//if it was not able to acquire during test, then it will block it
			s[i].acquire();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void put_forks(int i){

		try {
			mut.acquire();
			//philosopher is thinking
			state[i] = 0;
			table.StopEating_GUI(i);
			table.ForkPut_GUI(i);
			//check left neighbor
			System.out.println("Philospher " + i + " stopped EATING!");
			test((i+5-1)%5) ;
			//check right neighbor
			test((i+1)%5);
			mut.release();   //exit critical region

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	void test(int i){
		//System.out.println("Testing for Philosopher " + i);
		if(state[i] == 1 && state[((i+5-1)%5)] != 2 && state[((i+1)%5)]!= 2 ){
			System.out.println("Philospher " + i + " passed test and is now EATING!");
			state[i] = 2 ;
			//take the fork
			table.ForkTake_GUI(i);
			//and start eating
			table.Eating_GUI(i);
			//up is to acquire forks
			s[i].release();
		}
	}
}



public class Philosopher extends Thread {

	private static Table table;

	private int THINKING = 0;
	private int HUNGRY = 1;
	private int EATING = 2;
	private static StatePhil statePhil ;

	//barrier variables

	private Random random = new Random();
	private int threads = 5 ;

	private Semaphore[] sem;
	private Semaphore[] barriers;
	private Semaphore mutex;
	private int id;


	private int[] state;
	private int N;

	public Philosopher(int i, Semaphore[] s, int[] mystate, int philnumber, Semaphore mu, Semaphore[] bar) {
		id = i;
		sem = s;
		state = mystate;
		N = philnumber;
		mutex = mu;
		barriers = bar;
	}
	public static void main(String args[]) {

		//Fall 2020 - CS307 HW 2
		//Samuel LEE


		// Java threads and semaphores to implement a solution
		//for a modified version of famous Dining Philosophers problem

		// uses of Cyclic barrier in java.util.concurrent package
		// uses of semaphores and mutexes for deadlock prevention & starvation

		// run with images for GUI class


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					table = new Table();
					table.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});
		int N = 5;
		int[] state = new int[N];
		Semaphore mutex = new Semaphore(1);
		Semaphore[] semarray = new Semaphore[N];
		Semaphore[] bararray = new Semaphore[N];
		Philosopher[] oldies = new Philosopher[N];
		//creating necessary objects
		statePhil = new StatePhil();
		statePhil.barrier = new Barrier();

		for (int i = 0; i < N; i++) {
			semarray[i] = new Semaphore(0, true);
			//System.out.println("The semarray of " + i + " index is  "+semarray[i].toString());
			bararray[i] = new Semaphore(0, true);
		}
		for (int i = 0; i < N; i++) {
			//creating philosophers
			oldies[i] = new Philosopher(i, semarray, state, N, mutex, bararray);
			System.out.println("Starting the Philosopher " + oldies[i].id );
			oldies[i].start();
		}

	}

	@Override
	public void run() {

		// Homework 2 Dining Philosophers

		// Barrier
		String thisThreadName = Thread.currentThread().getName() ;
		Integer num = random.nextInt(10000);
		try {
			System.out.println("Philosopher " + this.id + " takes " + num + " milliseconds to walk to table");
			sleep(num);
			System.out.println("Philosopher " + this.id + " reached the table & put his plate on table");
			//table.PutPlate_GUI(this.id);
			statePhil.table.PutPlate_GUI(this.id);
			System.out.println(thisThreadName + " waiting for others to reach barrier");

			System.out.println(Thread.currentThread().getName() +
					" is waiting for "+(statePhil.barrier.cyclicBarrier.getParties()- statePhil.barrier.cyclicBarrier.getNumberWaiting()-1)+
					" other threads to reach common barrier point");

			statePhil.barrier.cyclicBarrier.await();

		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		//barrier is reached!
		// Start Dining
		//Plate is white
		statePhil.table.StartDining_GUI(this.id);

		while (true) {
			//think from 0-10 seconds
			statePhil.think(this.id);
			//finishes thinking

			//take_fork
			statePhil.take_fork(this.id);

			//put fork down
			statePhil.put_forks(this.id);

		}
	}
}


