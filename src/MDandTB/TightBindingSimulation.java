/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions, and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions, and the disclaimer that follows
these conditions in the documentation and/or other materials
provided with the distribution.

3. The names of the author may not be used to endorse or promote
products derived from this software without specific prior written
permission.

In addition, we request (but do not require) that you include in the
end-user documentation provided with the redistribution and/or in the
software itself an acknowledgement equivalent to the following:
"This product includes software developed by the
Abinit Project (http://www.abinit.org/)."

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

For more information on the Abinit Project, please see
<http://www.abinit.org/>.
 */

package MDandTB;

import core.Atom;
import core.Exec;
import core.MainFrame;
import core.RetMSG;
import core.Utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import projects.LocalMachine;
import projects.Machine;
import projects.RemoteJob;
import projects.RemoteJob;
import projects.Simulation;
import projects.SubmissionScript;

public class TightBindingSimulation extends Simulation {

    public final static int TB_DOSKNEE1AND2 = 101, TB_DOSKNEE3 = 102, TB_BNDSTR = 103;
    
    private String name = "default";
    private String tbenerFileName = "tbener";
    private RemoteJob job;
    public static int RUNNING = 1;
    public static int FINISHED = 2;
    public static int READY = 0;
    private int status;
    private boolean sendTBSources;
    private boolean compileTB;
    private boolean graphite;
    private int typeDosknee; // 1 or 2
    private boolean useLANDM;
    private int L, M;
    private int typeGlobal;
    private boolean use66, use110, use120;

    public TightBindingSimulation() {
        status = READY;

        job = new RemoteJob();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getInputFileName() {
        return getTbenerFileName();
    }

    @Override
    public void setInputFileName(String fileName) {
        this.setTbenerFileName(fileName);
    }

    @Override
    public String toString() {
        return name;
        //return "Simulation(name = " + name + "; fileName = " + tbenerFileName + ")";
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public RemoteJob getRemoteJob() {
        return job;
    }

    @Override
    public void setRemoteJob(RemoteJob job) {
        this.job = job;
    }

    void updateStatus() {
        job.updateStatus();
    }
    
    @Override
    public void createFileTree(Machine mach, MainFrame mf)
    {
        String path = mach.getSimulationPath();
        if (path.equals("")) {
            path = ".";
        }
        mach.createTree(path, mf);
        if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
        {
            mf.getLocalExec().createTree(path);
        }
        
    }
    
    @Override
    public boolean submit(MainFrame mf)
    {
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());

        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return false;
        }

        if(!mach.isConnected())
        {
            mach.connection(mf);
        }

        if (mach.getType() == Machine.LOCAL_MACHINE && Utils.osName().startsWith("Windows")) {
            mf.printERR("Please connect to a remote host before submitting a simulation on Windows platform !");
            return false;
        }

        this.createFileTree(mach, mf);

        String rootPath = mach.getSimulationPath();

        SubmissionScript script = getRemoteJob().getScript();

        String TBfolderL = "Tight-Binding";

        if (sendTBSources) {

            if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
            {
                // Envoie du code source pour les calculs TB (remote ou local)
                mach.putFile("./Tight-Binding_src.tar.gz ./Tight-Binding_src.tar.gz", mf);
            }

            // Unzip the compressed file CLUSTEP_src.tar.gz
            mach.sendCommand("tar -zxf ./Tight-Binding_src.tar.gz", mf);

            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {
                mach.sendCommand("rm -f ./Tight-Binding_src.tar.gz", mf);
            }
        }
        

        String cwd = "";

        String CMD = "pwd";

        RetMSG retmsg = mach.sendCommand(CMD, mf);
        if(retmsg.getRetCode() == RetMSG.SUCCES)
        {
            mf.printOUT("PWD: " + retmsg.getRetMSG());
            cwd = Utils.removeEndl(retmsg.getRetMSG());
        }
        else
        {
            mf.printERR("Not able to get working directory !");
            return false;
        }
        
        String simName = name.replace(" ", "_");
            
            
        String TBrootR = "";
        String TBrootL = "";
        if(this.typeGlobal == TB_DOSKNEE1AND2 || this.typeGlobal == TB_DOSKNEE3)
        {
            TBrootR = "./Tight-Binding_src/DOS";
            TBrootL = TBfolderL + "/DOS";
        }
        else if(typeGlobal == TB_BNDSTR)
        {
            TBrootR = "./Tight-Binding_src/BS";
            TBrootL = TBfolderL + "/BS";
        }

        // ***************************************************************
        // Creation du dossier Tight-Binding local
        mf.getLocalExec().mkdir(rootPath + "/" + TBfolderL);
        // Creation du dossier DOS dans Tight-Binding local
        mf.getLocalExec().mkdir(rootPath + "/" + TBrootL);
        // ***************************************************************


        int compile;
        if (compileTB) {
            compile = 1;
        } else {
            compile = 0;
        }

        int option = 1;

        //**************


        String exec = "";
        if(typeGlobal == TB_DOSKNEE1AND2 || typeGlobal == TB_DOSKNEE3)
        {
            if(typeGlobal == TB_DOSKNEE1AND2)
                option = typeDosknee;
            else if(typeGlobal == TB_DOSKNEE3)
                option = 3;


            String L_str = "3";
            String M_str = "3";
            try {
                if (useLANDM) {
                    L_str = "" + L;
                    M_str = "" + M;
                }
                else
                {
                    L_str = "-1";
                    if (graphite) 
                    {
                        M_str = "graphite";
                    }
                    else
                    {
                        M_str = "pentahept";
                    }
                }
            } catch (Exception e) {
                mf.printDEB("L = 5, M = 5 (default taken!)");
                L_str = "5";
                M_str = "5";
            }
            script.setAbinitPath("bash " + "execDOS.sh" + " " + compile + " " + L_str + " " + M_str + " " + option + "");
        }
        else if(typeGlobal == TB_BNDSTR)
        {
            if (use66) {
                option = 1;
            }
            if (use120) {
                option = 2;
            }
            if (use110) {
                option = 3;
            }

            String inputFile = tbenerFileName;

            String inputFN = Utils.getLastToken(inputFile.replace('\\', '/'), "/");
            
            // Test de l'existance de inputfile
            if (!Utils.exists(inputFile)) {
                mf.printERR("The file " + inputFile + " doesn't exist !");
                return false;
            }
            
            String inputFileR = TBrootR + "/" + inputFN;
            if (Utils.osName().startsWith("Windows")) {
               Utils.dos2unix(new File(inputFile));
            }
            mach.putFile(inputFile + " " + inputFileR, mf);


            script.setAbinitPath("bash " + "execBS.sh" + " " + compile + " " + inputFN + " " + option + "");
        }
            
        script.setInputPath("/dev/null");
        script.setLogPath(simName + ".log");

        script.setPreProcessPart("cd "+TBrootR);

        if (script.getSystem().equals("SGE")) {
            String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
            script.writeToFile(PBSfileName);
        } else if (script.getSystem().equals("SLURM")) {
            String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
            script.writeToFile(PBSfileName);
        } else if (script.getSystem().equals("Frontend")) {
            String SHfileName = rootPath + "/" + TBrootL + "/" + simName + ".sh";
            script.writeToFile(SHfileName);
        }

        if (script.getSystem().equals("SGE")) {
            String sgeSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
            //String sgeSHFileR = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
            String sgeSHFileR = TBrootR + "/" + simName + ".SGE.sh";
            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {

                // Envoie du fichier SGE
                mach.putFile(sgeSHFile + " " + sgeSHFileR, mf);

                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + sgeSHFileR, mf);
                }
            }
            // lancement des commandes d'ex�cution de la simulation
            mach.sendCommand("qsub " + sgeSHFileR, mf);
        } else if (script.getSystem().equals("Frontend")) {
            String SHFile = rootPath + "/" + TBrootL + "/" + simName + ".sh";
            //String SHFileR = rootPath + "/" + TBrootL + "/" + simName + ".sh";
            String SHFileR = TBrootR + "/" + simName + ".sh";

            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {
                /*if (Utils.osName().startsWith("Windows")) {
                 Utils.dos2unix(new File(SHFileR));
                 }*/
                
                // Envoie du fichier BASH
                mach.putFile(SHFile + " " + SHFileR, mf);

                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + SHFileR, mf);
                }
            }
            // lancement des commandes d'ex�cution de la simulation
            mach.sendCommand("bash "+SHFileR, mf);
        } else if (script.getSystem().equals("SLURM")) {
            String slurmSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
            //String slurmSHFileR = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
            String slurmSHFileR = TBrootR + "/" + simName + ".SLURM.sh";
            if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {

                // Envoie du fichier SGE
                mach.putFile(slurmSHFile + " " + slurmSHFileR, mf);

                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + slurmSHFileR, mf);
                }
            }
            // lancement des commandes d'ex�cution de la simulation
            mach.sendCommand("sbatch " + slurmSHFileR, mf);
        }

        if (mach.getType() == Machine.LOCAL_MACHINE) {
            mf.printOUT("The simulation was submitted to the local Tight-Binding server.");
        } else {
            mf.printOUT("The simulation was submitted to the remote Tight-Binding server " + mach.getName());
        }
        mf.printDEB("The submission thread ended successfully! (Tight-Binding)");

        return true;
        
    }

    /**
     * @return the sendTBSources
     */
    public boolean isSendTBSources() {
        return sendTBSources;
    }

    /**
     * @param sendTBSources the sendTBSources to set
     */
    public void setSendTBSources(boolean sendTBSources) {
        this.sendTBSources = sendTBSources;
    }
    
    @Override
    public void downloadLog(MainFrame mf)
    {
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return;
        }
        
        if(!mach.isConnected())
        {
            mach.connection(mf);
        }
        
        String rootPath = mach.getSimulationPath();
        String TBrootR = "./Tight-Binding_src/DOS";
        String TBrootL = rootPath + "/Tight-Binding/DOS";
        
        if(typeGlobal == TB_DOSKNEE1AND2 || typeGlobal == TB_DOSKNEE3)
        {
            TBrootR = "./Tight-Binding_src/DOS";
            TBrootL = rootPath + "/Tight-Binding/DOS";
        }
        else if(typeGlobal == TB_BNDSTR)
        {
            TBrootR = "./Tight-Binding_src/BS";
            TBrootL = rootPath + "/Tight-Binding/BS";
        }
        String simName = name.replace(" ", "_");
        String logfileNameR = TBrootR + "/"+simName+".log";
        String logfileNameL = TBrootL + "/"+simName+".log";

        mach.getFile(logfileNameR + " " + logfileNameL, mf);
        if (Utils.osName().startsWith("Windows")) {
            Utils.unix2dos(new File(logfileNameL));
        }

        mf.editFile(logfileNameL.replace("/./", "/"), false);
    }
    
    @Override
    public void downloadOutput(MainFrame mf)
    {
        /**
        * Download result 1
        */
        
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return;
        }
        
        if(!mach.isConnected())
        {
            mach.connection(mf);
        }
        
        String TBrootR = "";
        String TBrootL = "";
        String rootPath = mach.getSimulationPath();
        
        if(typeGlobal == TB_DOSKNEE1AND2 || typeGlobal == TB_DOSKNEE3)
        {
            TBrootR = "./Tight-Binding_src/DOS";
            TBrootL = rootPath + "/Tight-Binding/DOS";
        }
        else
        {
            TBrootR = "./Tight-Binding_src/BS";
            TBrootL = rootPath + "/Tight-Binding/BS";
        }

        String DOS = ""+typeDosknee;

            //**************

        int L = 3, M = 3;
        String LMParams = "3,3";
        if(typeGlobal == TB_DOSKNEE1AND2 || typeGlobal == TB_DOSKNEE3)
        {
            try {
                if(useLANDM)
                {
                    LMParams = L + "," + M;
                }
                else
                {
                    if(graphite)
                    {
                        LMParams= "graphite";
                    }
                    else
                    {
                        LMParams = "pentahept";
                    }
                }
            } catch (Exception e) {
                mf.printDEB("L = 3, M = 3 (default taken!)");
                LMParams = "3,3";
            }
        }
        else
        {
            LMParams = "6,6";

            if (use66) {
                LMParams = "6,6";
            }
            if (use120) {
                LMParams = "12,0";
            }
            if (use110) {
                LMParams = "11,0";
            }
        }

        String fileNameR = "";
        String fileNameL = "";
        if(typeGlobal == TB_DOSKNEE1AND2)
        {
            fileNameR = TBrootR + "/results/DOS" + DOS + "_res-" + LMParams;
            fileNameL = TBrootL + "/DOS" + DOS + "_res-" + LMParams;
        }
        else if(typeGlobal == TB_DOSKNEE3)
        {

            fileNameR = TBrootR + "/results/DOS3_res-" + LMParams;
            fileNameL = TBrootL + "/DOS3_res-" + LMParams;
        }
        else if(typeGlobal == TB_BNDSTR)
        {
            fileNameR = TBrootR + "/results/result-" + LMParams;
            fileNameL = TBrootL + "/result-" + LMParams;
        }

        if (!Utils.exists(fileNameL)) {
            mach.getFile(fileNameR + " " + fileNameL, mf);
            if (Utils.osName().startsWith("Windows")) {
                Utils.unix2dos(new File(fileNameL));
            }
        } else {
            mf.printOUT("File " + fileNameL + " exists in your local filetree!\n"
                + "Please remove the local file before you download the new file version!");
        }
        
        // ****************************************************************************
        // Tester l'existance du fichier
        if (!Utils.exists(fileNameL)) {
            mf.printERR("Output file is not existing !");
        } else {
            mf.editFile(fileNameL.replace("/./", "/"), false);
        }
        
    }

    /**
     * @return the compileTB
     */
    public boolean isCompileTB() {
        return compileTB;
    }

    /**
     * @param compileTB the compileTB to set
     */
    public void setCompileTB(boolean compileTB) {
        this.compileTB = compileTB;
    }

    /**
     * @return the tbenerFileName
     */
    public String getTbenerFileName() {
        return tbenerFileName;
    }

    /**
     * @param tbenerFileName the tbenerFileName to set
     */
    public void setTbenerFileName(String tbenerFileName) {
        this.tbenerFileName = tbenerFileName;
    }

    /**
     * @return the graphite
     */
    public boolean isGraphite() {
        return graphite;
    }

    /**
     * @param graphite the graphite to set
     */
    public void setGraphite(boolean graphite) {
        this.graphite = graphite;
    }

    /**
     * @return the typeDosknee
     */
    public int getTypeDosknee() {
        return typeDosknee;
    }

    /**
     * @param typeDosknee the typeDosknee to set
     */
    public void setTypeDosknee(int typeDosknee) {
        this.typeDosknee = typeDosknee;
    }

    /**
     * @return the useLANDM
     */
    public boolean isUseLANDM() {
        return useLANDM;
    }

    /**
     * @param useLANDM the useLANDM to set
     */
    public void setUseLANDM(boolean useLANDM) {
        this.useLANDM = useLANDM;
    }

    /**
     * @return the L
     */
    public int getL() {
        return L;
    }

    /**
     * @param L the L to set
     */
    public void setL(int L) {
        this.L = L;
    }

    /**
     * @return the M
     */
    public int getM() {
        return M;
    }

    /**
     * @param M the M to set
     */
    public void setM(int M) {
        this.M = M;
    }

    /**
     * @return the typeGlobal
     */
    public int getTypeGlobal() {
        return typeGlobal;
    }

    /**
     * @param typeGlobal the typeGlobal to set
     */
    public void setTypeGlobal(int typeGlobal) {
        this.typeGlobal = typeGlobal;
    }

    /**
     * @return the use66
     */
    public boolean isUse66() {
        return use66;
    }

    /**
     * @param use66 the use66 to set
     */
    public void setUse66(boolean use66) {
        this.use66 = use66;
    }

    /**
     * @return the use110
     */
    public boolean isUse110() {
        return use110;
    }

    /**
     * @param use110 the use110 to set
     */
    public void setUse110(boolean use110) {
        this.use110 = use110;
    }

    /**
     * @return the use120
     */
    public boolean isUse120() {
        return use120;
    }

    /**
     * @param use120 the use120 to set
     */
    public void setUse120(boolean use120) {
        this.use120 = use120;
    }
    
    
}
