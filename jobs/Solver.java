/**
 * $Id: Solver.java, v 1.0 01/07/14 20:38 oscarfabra Exp $
 * {@code Solver} Is a class that reads and solves a greedy algorithm for
 * minimizing the weighted sum of completion sums of jobs to schedule.
 *
 * @author <a href="mailto:oscarfabra@gmail.com">Oscar Fabra</a>
 * @version 1.0
 * @since 01/07/14
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that reads and solves a greedy algorithm for minimizing the weighted
 * sum of completion sums of jobs to schedule.
 */
public class Solver
{
    //-------------------------------------------------------------------------
    // PUBLIC METHODS
    //-------------------------------------------------------------------------

    /**
     * Solves the given instance and prints the solution in standard output.
     * @param lines Input list with the variables for the problem.
     */
    private static void solve(List<String> lines)
    {
        // Gets the number of jobs
        int n = Integer.parseInt(lines.remove(0));

        // Gets the list of jobs from the given lines and makes a copy of it
        List<Job> jobs = JobsScheduler.readJobsList(lines);
        List<Job> jobsAux = new ArrayList<Job>();
        for(Job job : jobs)
        {
            jobsAux.add(new Job(job));
        }

        // Gets the sum of weighted completion times scheduling jobs in
        // decreasing order of their difference (weight - length)
        long sum = JobsScheduler.getSumOfCompletionTimes(1, jobs);

        System.out.println("The sum of completion times scheduling jobs in decreasing ");
        System.out.println("order of their difference (weight - length) is: ");
        System.out.println(sum);

        // Updates the job list with the values of the past list
        for(int i = 0; i < jobsAux.size(); i++)
        {
            jobs.set(i, jobsAux.get(i));
        }

        // Gets the sum of weighted completion times scheduling jobs in
        // decreasing order of their difference (weight - length)
        sum = JobsScheduler.getSumOfCompletionTimes(2, jobs);

        System.out.println("The sum of completion times scheduling jobs in decreasing ");
        System.out.println("order of their ratio (weight/length) is: ");
        System.out.println(sum);
    }

    /**
     * Reads the lines received from standard input and arranges them in a
     * list.
     * @param args Array of String with the filepath of the file to read.
     * @return A list of lines with the data for the problem.
     * @throws FileNotFoundException If the file couldn't be found.
     */
    private static List<String> readLines(String[] args)
            throws FileNotFoundException
    {
        List<String> lines = new ArrayList<String>();
        String filename = null;

        // get the file name
        for(String arg : args)
        {
            if(arg.startsWith("-file="))
            {
                filename = arg.substring(6);
            }
        }

        if(filename == null)
        {
            return null;
        }

        // Reads the lines out of the file
        FileReader fileReader = new FileReader(filename);
        BufferedReader input = new BufferedReader(fileReader);
        String line = null;
        try
        {
            while((line = input.readLine()) != null)
            {
                lines.add(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return lines;
    }

    //-------------------------------------------------------------------------
    // MAIN
    //-------------------------------------------------------------------------

    /**
     * Main test method.
     * @param args filepath relative to the file with the representation of a
     *             directed graph in the form -file=filepath
     */
    public static void main(String [] args)
    {
        List<String> lines = null;
        try
        {
            lines = Solver.readLines(args);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        Solver.solve(lines);
    }
}
