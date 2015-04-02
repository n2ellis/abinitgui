/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */

package abinitgui.projects;

import abinitgui.core.MainFrame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SLURMScript extends AbstractSubmissionScript {

    public SLURMScript() {
        this.system = "SLURM";
    }

    @Override
    public String toString() {
        String fileContent = "#!/bin/bash" + "\n";
        if(nbProcs != null && !nbProcs.isEmpty())
        {
            fileContent += "#SBATCH --ntasks=" + nbProcs + "\n";
        }
        if(simName != null && !simName.isEmpty())
        {
            fileContent += "#SBATCH --job-name=" + simName + "\n";
        }
        if(memoryMax != null && !memoryMax.isEmpty())
        {
            fileContent += "#SBATCH --mem-per-cpu=" + memoryMax + "\n";
        }
        if(timeLimit != null && !timeLimit.isEmpty())
        {
            fileContent += "#SBATCH --time=" + timeLimit + "\n";
        }
        if(email != null && !email.isEmpty())
        {
            fileContent += "#SBATCH --mail-user=" + email + "\n";
        }
        
        fileContent += ""
                + "#SBATCH --cpus-per-task=1\n"
                + "#SBATCH --mail-type=ALL\n"
                //+ "# ---------------------------" + "\n"
                + "#export OMP_NUM_THREADS=1\n"
                + "#unset SLURM_CPUS_PER_TASKS\n"
                + "\n"
                + preProcessPart
                + "\n"
                + cdPart
                + "\n";

        if (parallel) {
            fileContent += "MPI=" + mpiPath + "\n"
                    + "${MPI} -np " + nbProcs + " " + abinitPath + " < " + inputPath + " >& " + logPath;
        } else {
            fileContent += abinitPath + " < " + inputPath + " >& " + logPath;
        }

        fileContent += postProcessPart
                + "\n";

        return fileContent;
    }

    @Override
    public void writeToFile(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

            if (pw.checkError()) {
                throw new IOException("Error with pw.");
            }

            // Writing file !
            pw.println(this.toString());

            if (pw.checkError()) {
                throw new IOException("Error with pw.");
            }

            pw.println();

            pw.close();

        } catch (IOException ex) {
            MainFrame.printERR("Error while writing the file. (IOException: "
                    + ex.getMessage() + ")");
        } catch (Exception ex) {
            MainFrame.printERR("Error while writing the file. (Exception: "
                    + ex.getMessage() + ")");
        }
    }
}
