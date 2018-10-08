import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import static java.lang.System.out;

public class RPS
{
	static int tc = 0, fM = 0;
	public static void main(String[] args)throws IOException
	{
		ArrayList<ArrayList<Integer>> dataSet = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> record = new ArrayList<Integer>();
		int cc = 0, hc = 0;
		String[] ch = {"", "Rock", "Paper", "Scissors"};
		boolean playing = true;
		Scanner kb = new Scanner(System.in);
		dataSet = readData();

		while(playing)
		{
			out.print("Select:\n");
			out.print("1. Rock\t\t");
			out.print("2. Paper\t");
			out.print("3. Scissors\t\n");

			int cCh = compIn(dataSet, record);
			record.add(cCh);
			tc++;
//			int cCh = kb.nextInt();
			int input = kb.nextInt();
			if(tc == 1)
				fM = input;
			record.add(input);

			if(input == 0)
			{
				System.out.println("");
				break;
			}

			int win = checkWin(input,cCh);

			out.println("\nYou chose: " + ch[input] + "   ~~~  Computer chose: " + ch[cCh] );
			if(win > 0)
			{
				out.println("You WON!");
				hc++;
			}
			else if(win == 0)
			{
				out.println("You TIED!");
			}
			else
			{
				out.println("You LOST! (LOSER)!");
				cc++;
			}

			if(cc>=3 || hc>=3)
				playing = false;

			out.println("");
			tc++;

		}

		if(hc == 3)
		{
			out.println("You are AWESOME! YOu wON");
			saveData(dataSet, record);
		}
		else if(cc == 3)
		{
			out.println("sORry You are bAd");
			saveData(dataSet, record);
		}
		else
			out.println("suCh a qUItTEr!");

	}
	public static int checkWin(int x, int y)
	{
		if(x == y)
			return 0;
		else if (x == 1 && y == 2)
			return -1;
		else if (x == 1 && y == 3)
			return 1;
		else if (x == 2 && y == 1)
			return 1;
		else if (x == 2 && y == 3)
			return -1;
		else if (x == 3 && y == 1)
			return -1;
		else if (x == 3 && y == 2)
			return 1;
		else
			return 0;
	}
	public static int compIn(ArrayList<ArrayList<Integer>> dataSet, ArrayList<Integer> record)
	{
		ArrayList<ArrayList<Integer>> copy = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < dataSet.size(); i++)
		{
			ArrayList<Integer> row = new ArrayList<Integer>();
			for(int j = 0; j < dataSet.get(i).size(); j++)
			{
				row.add(dataSet.get(i).get(j));
			}
			copy.add(row);
		}

		boolean wake = false;
		if(tc == 0)
			return inProb();
		else
		{
			for(int i=0; i<copy.size(); i++)
			{
				wake = false;
				for(int j=0; j < record.size() && j < copy.get(i).size(); j++)
				{
					if(copy.get(i).get(j) != record.get(j))
					{
						copy.remove(i);
						if(copy.size() == 0 || copy.size() <= i)
							wake = true;
						break;
					}
				}
				if(wake)
				{
					break;
				}
			}

			if(copy.isEmpty() == true)
				return rand();
			int rEntry = 0, pEntry = 0, sEntry = 0;
			for(ArrayList<Integer> i : copy)
			{
				int x = i.get(i.size()-1);
				if( tc > x)
					break;
				if(i.get(tc+1) == 1)
					rEntry += x;
				else if(i.get(tc+1) == 2)
					pEntry += x;
				else if(i.get(tc+1) == 3)
					sEntry += x;
			}

			if(rEntry > pEntry && rEntry > sEntry)
				return 2;
			else if(pEntry > sEntry)
				return 3;
			else if(sEntry > pEntry)
				return 1;
			else
				return rand();

		}
	}
	public static int rand()
	{
		int x = (int)(Math.random()*10)+1;
		if(x <= 2)
			return inProb();
		else
			return (int)(Math.random()*2)+1;
	}
	public static int inProb()
	{
		try
		{
			Scanner file = new Scanner(new File("FMove.txt"));
			int a = file.nextInt();
			int b = file.nextInt();
			int c = file.nextInt();

			if(a > b && a > c)
				return 2;
			else if(b > a && b > c)
				return 3;
			else if(c > a && c > b)
				return 1;
			else
				return((int)(Math.random()*3+1));
		}
		catch (IOException e)
		{return -1;}
	}
	public static ArrayList<ArrayList<Integer>> readData()throws IOException
	{
		Scanner read = new Scanner(new File("MatchData.txt"));
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
		while(read.hasNextLine())
		{
			Scanner ln = new Scanner(read.nextLine());
			ArrayList<Integer> row = new ArrayList<Integer>();
			while(ln.hasNext())
			{
				row.add(ln.nextInt());
			}
			temp.add(row);
		}
		return temp;
	}
	public static void saveData(ArrayList<ArrayList<Integer>> dataSet, ArrayList<Integer> record)
	{
		try
		{
			boolean of = false;
			int save = -1;
			for(int i = 0; i < dataSet.size(); i++)
			{
				of = true;
				for(int j = 0; j < dataSet.get(i).size() && j < record.size(); j++)
				{
					if(record.get(j) != dataSet.get(i).get(j))
						of = false;
				}
				if(of)
				{
					dataSet.get(i).set(dataSet.get(i).size()-1, dataSet.get(i).get(dataSet.get(i).size()-1) + 1);
					break;
				}
			}
			if(!of)
			{
				record.add(1);
				dataSet.add(record);
			}

			PrintWriter pW = new PrintWriter("MatchData.txt");
			for(int i = 0; i < dataSet.size(); i++)
			{
				for(int j = 0; j < dataSet.get(i).size(); j++)
				{
					pW.print(dataSet.get(i).get(j) + " ");
				}
				pW.println("");
			}
			pW.close();

			Scanner iN = new Scanner(new File("FMove.txt"));
			int a = iN.nextInt();
			int b = iN.nextInt();
			int c = iN.nextInt();

			if(fM == 1)
				a++;
			else if(fM == 2)
				b++;
			else if(fM == 3)
				c++;

			pW = new PrintWriter("FMove.txt");
			pW.println(a);
			pW.println(b);
			pW.println(c);
			pW.close();
		}
		catch(IOException e)
		{System.out.println("Could not save");}
	}
}