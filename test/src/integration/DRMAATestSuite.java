package integration;

import junit.framework.Test;

import org.ogf.saga.job.JobDescriptionTest;
import org.ogf.saga.job.JobListTest;
import org.ogf.saga.job.JobRunDescriptionTest;
import org.ogf.saga.job.JobRunInfoTest;
import org.ogf.saga.job.JobRunInteractiveTest;
import org.ogf.saga.job.JobRunMinimalTest;
import org.ogf.saga.job.JobRunOptionalTest;
import org.ogf.saga.job.JobRunRequiredTest;
import org.ogf.saga.job.JobRunSandboxTest;
import org.ogf.saga.job.JobRunWithPrequisiteTest;

public class DRMAATestSuite extends JSAGATestSuite {
    /** create test suite */
    public static Test suite() throws Exception { return new DRMAATestSuite(); }
    /** index of test cases */
    public static class index extends IndexTest { public index(){ super(DRMAATestSuite.class); } }

    /** test cases */
    public static class DRMAA_JobRunWithPrequisiteTest extends JobRunWithPrequisiteTest {
        public DRMAA_JobRunWithPrequisiteTest() throws Exception { super("DRMAA"); }
        public void test_run_MPI() throws Exception { super.ignore("MPI support not yet implemented in this first version"); }
    }
    
    public static class DRMAA_JobRunMinimalTest extends JobRunMinimalTest {
        public DRMAA_JobRunMinimalTest() throws Exception { super("DRMAA"); }
        //public void test_run() throws Exception { super.ignore("Explain why this test is ignored..."); }
    }
    
    public static class DRMAA_JobRunInteractiveTest extends JobRunInteractiveTest {
        public DRMAA_JobRunInteractiveTest() throws Exception { super("DRMAA"); }
        //public void test_setStdin() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_getStdout() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_getStderr() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_run_environnement() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_simultaneousStdin() throws Exception { super.ignore("Explain why this test is ignored..."); }
    }
    
    public static class DRMAA_JobRunInfoTest extends JobRunInfoTest {
        public DRMAA_JobRunInfoTest() throws Exception { super("DRMAA"); }
        //public void test_exitcode() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_created() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_dates() throws Exception { super.ignore("Explain why this test is ignored..."); }
        public void test_execution_hosts() throws Exception { super.ignore("Information about execution hosts not yet implemented in this first version."); }
    }
    
    public static class DRMAA_JobRunOptionalTest extends JobRunOptionalTest {
        public DRMAA_JobRunOptionalTest() throws Exception { super("DRMAA"); }
        //public void test_suspend_running() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_suspend_done() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_resume_running() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_resume_done() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_listJob() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_simultaneousLongJob() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_simultaneousShortJob() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_TaskContainer_ShortJob() throws Exception { super.ignore("Explain why this test is ignored..."); }
    }
    
    public static class DRMAA_JobRunRequiredTest extends JobRunRequiredTest {
        public DRMAA_JobRunRequiredTest() throws Exception { super("DRMAA"); }
        //public void test_run_long() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_run_error() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_cancel_running() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_cancel_new() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_cancel_done() throws Exception { super.ignore("Explain why this test is ignored..."); }
    }
    
    public static class DRMAA_JobRunSandboxTest extends JobRunSandboxTest {
        public DRMAA_JobRunSandboxTest() throws Exception { super("DRMAA"); }
        public void test_remote_input_explicit() throws Exception { super.ignore("Input sandbox not yet implemented in this first version."); }
        public void test_remote_output_explicit() throws Exception { super.ignore("Output sandbox not yet implemented in this first version."); }
        public void test_input_output_explicit() throws Exception { super.ignore("Input/Output sandbox not yet implemented in this first version."); }
        public void test_input_output_implicit() throws Exception { super.ignore("Input/Output sandbox not yet implemented in this first version."); }
        public void test_output_only_implicit() throws Exception { super.ignore("Output sandbox not yet implemented in this first version."); }
    }
    
    public static class DRMAA_JobListTest extends JobListTest {
        public DRMAA_JobListTest() throws Exception { super("DRMAA"); }
        //public void test_list() throws Exception { super.ignore("Explain why this test is ignored..."); }
    }
    
    public static class DRMAA_JobDescriptionTest extends JobDescriptionTest {
        public DRMAA_JobDescriptionTest() throws Exception { super("DRMAA"); }
        //public void test_executable() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_arguments() throws Exception { super.ignore("Explain why this test is ignored..."); }
        public void test_spmdVariation() throws Exception { super.ignore("SPMD variation not yet implemented in this versison."); }
        public void test_totalCPUCount() throws Exception { super.ignore("Total CPU count not yet implemented in this versison."); }
        public void test_numberOfProcesses() throws Exception { super.ignore("Number of processes not yet implemented in this versison."); }
        public void test_processesPerHost() throws Exception { super.ignore("Processes per host not yet implemented in this versison."); }
        public void test_threadsPerProcess() throws Exception { super.ignore("Thread per process not yet implemented in this versison."); }
        //public void test_environment() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_workingDirectory() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_input() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_output() throws Exception { super.ignore("Explain why this test is ignored..."); }
        //public void test_error() throws Exception { super.ignore("Explain why this test is ignored..."); }
        public void test_fileTransfer() throws Exception { super.ignore("File transfer not yet implemented in this versison."); }
        public void test_cleanup() throws Exception { super.ignore("Cleanup not yet implemented in this versison."); }
        public void test_wallTimeLimit() throws Exception { super.ignore("Wall time limit not yet implemented in this versison."); }
        public void test_totalCPUTime() throws Exception { super.ignore("Total CPU time not yet implemented in this versison."); }
        public void test_totalPhysicalMemory() throws Exception { super.ignore("Total physical memory not yet implemented in this versison."); }
        public void test_cpuArchitecture() throws Exception { super.ignore("CPU architecture not yet implemented in this versison."); }
        public void test_operatingSystemType() throws Exception { super.ignore("Operating system type not yet implemented in this versison."); }
        public void test_candidateHosts() throws Exception { super.ignore("Candidate hosts not yet implemented in this versison."); }
        public void test_queue() throws Exception { super.ignore("Queues not yet implemented in this versison."); }
    }
    
    public static class DRMAA_JobRunDescriptionTest extends JobRunDescriptionTest {
        public DRMAA_JobRunDescriptionTest() throws Exception { super("DRMAA"); }
        //public void test_run_inWorkingDirectory() throws Exception { super.ignore("Explain why this test is ignored..."); }
        public void test_run_queueRequirement() throws Exception { super.ignore("Queue requirement not yet implemented in this versison."); }
        public void test_run_cpuTimeRequirement() throws Exception { super.ignore("CPU time requirement not yet implemented in this versison."); }
        public void test_run_memoryRequirement() throws Exception { super.ignore("memory requirement not yet implemented in this versison."); }
    }
    
}