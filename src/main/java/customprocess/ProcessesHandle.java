package customprocess;

import org.hyperic.sigar.Sigar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ProcessesHandle {

    public Sigar sigarObs;
    public String processLine;
    public String processLineAll;
    private String separator;
    public String[] processCurrentArray;
    public List<List<String>> processCurrentTrimmed;
    ProcessesHandle() {
        this.separator = "%%%";
        this.processCurrentTrimmed = new ArrayList<List<String>>();
    }

    private void filterProcesses(String[] prs) {
        System.out.println(prs);
        for (int i =0; i< prs.length; i++) {
            String[] one = prs[i].trim().split(" ");
            List<String> pure = new ArrayList<String>();
            for (String j: one) {
                String a = j.trim();
                if (!a.isEmpty())
                pure.add(a);
            }
            this.processCurrentTrimmed.add(pure);
        }
    }

    public void getAllCurrentProcesses() throws IOException, NullPointerException {
        // get all running process in batch mode in single iteration
        ProcessBuilder procSys = new ProcessBuilder("top", "-b" ,"-n", "1");
        ProcessBuilder procMain = new ProcessBuilder("ps", "-e");
        Process proc = procMain.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        this.processLine = br.readLine();
        try {
            while (true) {
                this.processLine = br.readLine();
                if (this.processLine.isEmpty()) {
                    break;
                }
                System.out.println(this.processLine);
                this.processLineAll += this.processLine + this.separator;
            }
        } catch (NullPointerException e) {
            System.out.println("Finished scanning");
        }
        this.processCurrentArray = this.processLineAll.split(separator);
        this.filterProcesses(this.processCurrentArray);
    }

    public void displayAllFunctionalities() {
//        System.out.println(Arrays.toString(this.processCurrentArray));
        System.out.println(this.processCurrentTrimmed);
    }
}

