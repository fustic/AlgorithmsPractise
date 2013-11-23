package prev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ScheduleJobs {
    public static void main(String[] args) throws IOException {
        ScheduleJobs scheduleJobs = new ScheduleJobs("/Users/vadimivanov/Downloads/jobs.txt");
        Date start = new Date();
        System.out.println("time by diff: "+ scheduleJobs.getSumOfWeightedTimesByDiff());
        Helpers.getDiffTime(start, "Q1: ");
        start = new Date();
        System.out.println("time by ratio: "+ scheduleJobs.getSumOfWeightedTimesByRatio());
        Helpers.getDiffTime(start, "Q2: ");
        System.exit(0);
    }


    private List<Job> jobs;
    public ScheduleJobs(String filePath) throws FileNotFoundException {
        jobs = getJobs(filePath);
    }
    private List<Job> sortJobsByDiff(List<Job> jobs){
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job job, Job job2) {
                int diffJob1 = job.weight - job.len;
                int diffJob2 = job2.weight - job2.len;
                int diff = diffJob2 - diffJob1;
                if(diff == 0) {
                    return job2.weight - job.weight;
                } else {
                    return diff;
                }
            }
        });
        return jobs;
    }
    private List<Job> sortJobsByRatio(List<Job> jobs){
        Collections.sort(jobs, new Comparator<Job>() {
            @Override
            public int compare(Job job, Job job2) {
                double ratioJob1 = (double) job.weight / (double) job.len;
                double ratioJob2 = (double) job2.weight / (double) job2.len;
                double diff = ratioJob2 - ratioJob1;
                if (diff == 0) {
                    return job2.weight - job.weight;
                } else {
                    if (diff < 0) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return jobs;
    }

    private long getSumOfWeightedTimes(List<Job> jobs){
        long sum = 0;
        long time = 0;
        for(Job job : jobs){
            time += job.len;
            sum += job.weight * time;
        }
        return sum;
    }

    public long getSumOfWeightedTimesByDiff(){
        return getSumOfWeightedTimes(sortJobsByDiff(jobs));
    }
    public long getSumOfWeightedTimesByRatio(){
        return getSumOfWeightedTimes(sortJobsByRatio(jobs));
    }

    private List<Job> getJobs(String filePath) throws FileNotFoundException {
        Date start = new Date();
        Scanner s = new Scanner(new File(filePath)).useDelimiter(System.getProperty("line.separator"));
        List<Job> jobs = new ArrayList<Job>(Integer.valueOf(s.next()));

        String[] numbers;
        String str;
        int jobId = 0;
        while (s.hasNext()){
            str=s.next().replace("\r","").replace("\t","");
            numbers = str.split(" ");
            jobs.add(new Job(jobId, Integer.valueOf(numbers[0]), Integer.valueOf(numbers[1])));
            jobId++;
        }

        s.close();
        Helpers.getDiffTime(start, "Numbers creation time is: ");
        return jobs;
    }




    private class Job {
        public Job(int i, int w, int l){
            id = i;
            weight = w;
            len = l;
        }
        public int id;
        public int weight;
        public int len;
    }

}
